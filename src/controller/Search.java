package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@WebServlet("/Search")
public class Search extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		JSONObject allData = new JSONObject();
		
		//String url = "https://music.bugs.co.kr/search/track?q=" + request.getParameter("searchWord");
		String url = "https://music.bugs.co.kr/search/track?q=" + "나만 봄";
		
		try {
			Document search = Jsoup.connect(url).get();
			Elements thumb = search.select("td>img");
			System.out.println(thumb);
			Elements title = search.select(".title");
		} catch (Exception e) {
			System.out.println("오류");
			ArrayList<String> msg = new ArrayList<>();
			msg.add("오류가 발생하였습니다.");
		}
	}

}
