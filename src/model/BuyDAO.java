package model;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import model.util.CrawlingUtil;

public class BuyDAO {
	
	//지니 이용권 크롤링
	public static ArrayList<JSONObject> getGenie() throws Exception {
		ArrayList<JSONObject> list = new ArrayList<>();
		try {
			Document doc = Jsoup.connect("https://www.genie.co.kr/buy/thirtyDays").get();
			Elements title = doc.select(".divide dt");
			Elements price = doc.select(".price");
			list = CrawlingUtil.getBuyList(title, price);
		} finally {
			return list;
		}
	}
	
	//벅스 이용권 크롤링
	public static ArrayList<JSONObject> getBugs() throws Exception {
		ArrayList<JSONObject> list = new ArrayList<>();
		try {
			Document doc = Jsoup.connect("https://music.bugs.co.kr/pay/public").get();
			Elements title = doc.select(".itemTitle strong");
			Elements info = doc.select(".price");
			list = CrawlingUtil.getBuyList(title, info, "span", "strong");
		} finally {
			return list;
		}
	}
	
	//지니 이용권 크롤링
	public static ArrayList<JSONObject> getMelon() throws Exception {
		ArrayList<JSONObject> list = new ArrayList<>();
		try {
			Document doc = Jsoup.connect("https://www.melon.com/buy/pamphlet/all.htm").get();
			Elements title = doc.select(".wrap_product h4");
			Elements info = doc.select(".product_info");
			list = CrawlingUtil.getBuyList(title, info, "dt", "dd");
		} finally {
			return list;
		}
	}
	
}
