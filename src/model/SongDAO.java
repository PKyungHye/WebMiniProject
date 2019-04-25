package model;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SongDAO {
	
	//썸네일 url, 노래명, 아티스트, 앨범명 크롤링
	public static ArrayList<JSONObject> getSongList(String searchWord) throws Exception {
		String url = "https://music.bugs.co.kr/search/track?q=" + searchWord;
		Document doc = Jsoup.connect(url).get();
		
		//노래명 저장할 ArrayList
		ArrayList<String> titles = new ArrayList<>();
		for ( Element v : doc.select(".title[adult_yn]") ) {
			titles.add(v.text());
		}

		//앨범명 저장할 ArrayList
		ArrayList<String> albums = new ArrayList<>();
		for ( Element v : doc.select(".left").select(".album") ) {
			albums.add(v.text());
		}
		
		//검색된 모든 곡을 저장할 ArrayList
		ArrayList<JSONObject> songList = new ArrayList<>();
		
		//최대 10개까지만 검색하기
		int lastNum = 10;
		if (titles.size() < 10) {
			lastNum = titles.size();
		}
		for (int i = 0; i < lastNum; i++) {
			JSONObject songJSON = new JSONObject();
			songJSON.put("thumbUrl", ( (ArrayList<String>)doc.select(".thumbnail").select("img").eachAttr("src") ).get(i));
			songJSON.put("title", titles.get(i));
			songJSON.put("artist", ( (ArrayList<String>)doc.select(".artist").select("a").eachAttr("title") ).get(i));
			songJSON.put("album", albums.get(i));
			songList.add(songJSON);
		}
		
		return songList;
	}
}
