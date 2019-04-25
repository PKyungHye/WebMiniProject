package model.util;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.select.Elements;

//크롤링에 반복적으로 쓰일 메소드용 클래스
public class CrawlingUtil {

	// 이용권 정보 ArrayList에 저장하기
	// 이용권별로 결제 종류가 여러 개인 경우
	public static ArrayList<JSONObject> getBuyList(Elements title, Elements info, String tag1, String tag2) {
		// 한 사이트의 이용권 데이터 모두 저장할 ArrayList
		ArrayList<JSONObject> dataList = new ArrayList<>();

		for (int i = 0; i < title.size(); i++) {
			// 결제 종류 저장할 ArrayList
			ArrayList<String> typeList = new ArrayList<>();
			// 가격 저장할 ArrayList
			ArrayList<Integer> priceList = new ArrayList<>();
			// {결제 종류, 가격} 저장할 ArrayList
			ArrayList<JSONObject> infoList = new ArrayList<>();
			// {이용권명, infoList} 저장할 JSON
			JSONObject dataJSON = new JSONObject();

			// 결제 종류를 저장
			for (int j = 0; j < info.eq(i).select(tag1).size(); j++) {
				typeList.add(info.eq(i).select(tag1).eq(j).text());
			}

			// 가격을 저장
			for (int j = 0; j < info.eq(i).select(tag2).size(); j++) {
				// 가격을 integer로 변환
				String[] arr = priceToInteger(info.eq(i).select(tag2).eq(j).text());
				priceList.add(Integer.parseInt(arr[0] + arr[1]));
			}

			// 결제 종류 정보, 가격 정보를 JSON 형태로 저장
			for (int j = 0; j < priceList.size(); j++) {
				JSONObject infos = new JSONObject();
				infos.put("type", typeList.get(j));
				infos.put("price", priceList.get(j));
				infoList.add(infos);
			}

			// 이용권 이름과 prices를 JSON 형태로 저장
			dataJSON.put("title", title.eq(i).text());
			dataJSON.put("info", infoList);
			dataList.add(dataJSON);
		}
		return dataList;
	}

	// 이용권 정보 ArrayList에 저장하기
	// 이용권별로 결제 종류가 단일인 경우
	public static ArrayList<JSONObject> getBuyList(Elements title, Elements price) {
		// 이용권 이름, 가격이 담긴 JSON을 저장할 ArrayList
		ArrayList<JSONObject> dataList = new ArrayList<>();

		for (int i = 0; i < title.size(); i++) {
			JSONObject dataJSON = new JSONObject();
			JSONObject infos = new JSONObject();
			ArrayList<JSONObject> infoList = new ArrayList<>();

			String[] arr = priceToInteger(price.eq(i).text());

			infos.put("price", Integer.parseInt(arr[0] + arr[1]));
			infoList.add(infos);

			dataJSON.put("title", title.eq(i).text());
			dataJSON.put("info", infoList);
			dataList.add(dataJSON);
		}
		return dataList;
	}

	// 가격 문자열(00,000)을 숫자로 변환
	public static String[] priceToInteger(String price) {
		int n = 0;
		String[] arr = new String[2];

		// 크롤링한 문자열이 "~원" 형태일 경우
		if (price.substring(price.length() - 1, price.length()).equals("원")) {
			n = 1;
		}

		// 가격 정보 integer로 변환
		if ((price.length() - n) <= 3) { // 1000원 미만
			arr[0] = price.substring(0, price.length() - n);
			arr[1] = "";
		} else { // 1000원 이상
			arr = price.substring(0, price.length() - n).split(",");
		}
		return arr;
	}
}
