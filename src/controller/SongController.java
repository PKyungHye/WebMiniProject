package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import exception.ShowError;
import model.domain.UserDTO;

@WebServlet("/songcon")
public class SongController extends HttpServlet {

	/*protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		
		String command = request.getParameter("command");
		if (command.equals("login")) {
			login(request, response);
		} else if (command.equals("logout")) {
			response.sendRedirect("songMain.jsp");
			session.invalidate();
			session = null;
		} else {
			System.out.println("else");
		}
	}*/
	
	
	/*//유저 로그인(검색)
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			String user = SongService.login(request.getParameter("userid"), request.getParameter("userpw"));
			session.setAttribute("userInfo", user);
		} catch (Exception e) {
			session.setAttribute("errorMsg", e.getMessage());
		}
		request.getRequestDispatcher("userInfo.jsp").forward(request, response);
	}*/

}
