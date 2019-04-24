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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@WebServlet("/search")
public class Search extends HttpServlet {
	private static ArrayList<String> getList(Elements e) {
		ArrayList<String> list = new ArrayList<>();
		for (Element v : e) {
			list.add(v.text());
		}
		return list;
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
		String url = "https://music.bugs.co.kr/search/track?q=" + request.getParameter("searchWord");
		Document search = Jsoup.connect(url).get();
		ArrayList<String> titles = getList(search.select(".title[adult_yn]"));
		ArrayList<JSONObject> songList = new ArrayList<>();
		
		int lastNum = 10;
		if (titles.size() < 10) {
			lastNum = titles.size();
		}
		for (int i = 0; i < lastNum; i++) {
			JSONObject songJSON = new JSONObject();
			songJSON.put("thumbUrl", ( (ArrayList<String>)search.select(".thumbnail").select("img").eachAttr("src") ).get(i));
			songJSON.put("title", titles.get(i));
			songJSON.put("artist", ( (ArrayList<String>)search.select(".artist").select("a").eachAttr("title") ).get(i));
			songJSON.put("album", getList(search.select(".left").select(".album")).get(i));
			songList.add(songJSON);
		}
		
		request.setAttribute("songData", songList);
		request.getRequestDispatcher("searchData.jsp").forward(request, response);
	}
}
