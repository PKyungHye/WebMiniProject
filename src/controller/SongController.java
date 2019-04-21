package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.SongService;
import model.domain.UserDTO;

@WebServlet("/songcon")
public class SongController extends HttpServlet {
	String url = "";

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String command = request.getParameter("command");
		if (command.equals("login")) {
			getUser(request, response);
		}
	}
	
	//유저 로그인(검색)
	public void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject userInfo = new JSONObject();
		try {
			UserDTO user = SongService.getUser(request.getParameter("userid"), request.getParameter("userpw"));
			userInfo.put("userDTO", user);
			request.setAttribute("userInfo", userInfo);
			url = "userInfo.jsp";
		} catch (Exception e) {
			userInfo.put("errorMsg", e.getMessage());
			request.setAttribute("userInfo", userInfo);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

}
