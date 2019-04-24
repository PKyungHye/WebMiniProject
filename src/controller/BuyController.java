package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@WebServlet("/buycon")
public class BuyController extends HttpServlet {
	
	public static String [] priceToInteger(String strPrice, String [] arr) {
		//크롤링한 가격이 "000" 형태일 경우
		int n = 0;
		//크롤링한 가격이 "000원" 형태일 경우
		if (strPrice.substring(strPrice.length()-1, strPrice.length()).equals("원")) {
			n = 1;
		}
		
		//가격 정보 integer로 변환
		if ( (strPrice.length() - n) <= 3) {	//1000원 미만
			arr[0] = strPrice.substring(0, strPrice.length()-n);
			arr[1] = "";
		} else {	//1000원 이상
			arr = strPrice.substring(0, strPrice.length()-n).split(",");
		}
		return arr;
	}
	
	public static ArrayList<JSONObject> getBuyList(Elements t, Elements info, String tag1, String tag2) {
		//이용권 이름, 가격이 담긴 JSON을 저장할 ArrayList
		ArrayList<JSONObject> dataList = new ArrayList<>();
		
		for (int i = 0; i < t.size(); i++) {
			//결제 종류 정보, 가격 정보 저장할 ArrayList
			ArrayList<String> typeList = new ArrayList<>();
			ArrayList<Integer> priceList = new ArrayList<>();
			ArrayList<JSONObject> infoList = new ArrayList<>();
			//JSON
			JSONObject dataJSON = new JSONObject();
			
			//결제 종류 정보 typeAL에 저장
			for (int j = 0; j < info.eq(i).select(tag1).size(); j++) {
				typeList.add(info.eq(i).select(tag1).eq(j).text());
			}
			
			//가격 정보 priceAL에 저장
			for (int j = 0; j < info.eq(i).select(tag2).size(); j++) {
				//가격 문자열 = 000원
				String priceStr = info.eq(i).select(tag2).eq(j).text();
				//가격 문자열 split 후 저장할 array
				String [] arr = new String [2];

				//가격을 integer로 변환
				arr = priceToInteger(priceStr, arr);
				priceList.add(Integer.parseInt(arr[0] + arr[1]));
			}
			
			//결제 종류 정보, 가격 정보를 JSON 형태로 저장
			for (int j = 0; j < priceList.size(); j++) {
				JSONObject infos = new JSONObject();
				infos.put("type", typeList.get(j));
				infos.put("price", priceList.get(j));
				infoList.add(infos);
			}
			
			//이용권 이름과 prices를 JSON 형태로 저장
			dataJSON.put("title", t.eq(i).text());
			dataJSON.put("info", infoList);
			dataList.add(dataJSON);
		}
		return dataList;
	}
	
	public static ArrayList<JSONObject> getBuyList(Elements t, Elements price) {
		//이용권 이름, 가격이 담긴 JSON을 저장할 ArrayList
		ArrayList<JSONObject> dataList = new ArrayList<>();

		for (int i = 0; i < t.size(); i++) {
			JSONObject datas = new JSONObject();
			JSONObject infos = new JSONObject();
			ArrayList<JSONObject> infoAL = new ArrayList<>();
			
			String strPrice = price.eq(i).text();
			String [] arr = new String [2];
			
			//가격을 integer로 변환
			arr = priceToInteger(strPrice, arr);

			//이용권 이름과 가격을 JSON 형태로 저장
			infos.put("price", Integer.parseInt(arr[0] + arr[1]));
			infoAL.add(infos);
			
			datas.put("title", t.eq(i).text());
			datas.put("info", infoAL);
			dataList.add(datas);
		}
		return dataList;
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		JSONObject allData = new JSONObject();
		
		try {
			Document genie = Jsoup.connect("https://www.genie.co.kr/buy/thirtyDays").get();
			Elements gTitle = genie.select(".divide dt");
			Elements gPrice = genie.select(".price");
			allData.put("genie", getBuyList(gTitle, gPrice));
		} catch (Exception e) {
			ArrayList<String> msg = new ArrayList<>();
			msg.add("오류가 발생하였습니다.");
			allData.put("genie", msg);
		}
		
		try {
			Document bugs = Jsoup.connect("https://music.bugs.co.kr/pay/public").get();
			Elements bTitle = bugs.select(".itemTitle strong");
			Elements bInfo = bugs.select(".price");
			allData.put("bugs", getBuyList(bTitle, bInfo, "span", "strong"));
		} catch (Exception e) {
			ArrayList<String> msg = new ArrayList<>();
			msg.add("오류가 발생하였습니다.");
			allData.put("bugs", msg);
		}
		
		try {
			Document melon = Jsoup.connect("https://www.melon.com/buy/pamphlet/all.htm").get();
			Elements mTitle = melon.select(".wrap_product h4");
			Elements mProInfo = melon.select(".product_info");
			allData.put("melon", getBuyList(mTitle, mProInfo, "dt", "dd"));
		} catch (Exception e) {
			ArrayList<String> msg = new ArrayList<>();
			msg.add("오류가 발생하였습니다.");
			allData.put("melon", msg);
		}
		
		request.setAttribute("allData", allData);
		request.getRequestDispatcher("buyData.jsp").forward(request, response);
	}

}
