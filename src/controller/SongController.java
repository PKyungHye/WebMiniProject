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

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		
		String command = request.getParameter("command");
		if (command.equals("login")) {
			getUser(request, response);
		} else if (command.equals("logout")) {
			response.sendRedirect("songMain.jsp");
			session.invalidate();
			session = null;
		} else {
			System.out.println("else");
		}
	}
	
	//유저 로그인(검색)
	public void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JSONObject userInfo = new JSONObject();
		try {
			//UserDTO user = SongService.getUser(request.getParameter("userid"), request.getParameter("userpw"));
			
			//임시
			ArrayList<UserDTO> al = new ArrayList<>();
			al.add(new UserDTO("tester1", "11", "테스터"));
			UserDTO user = null;
			for (int i = 0; i < al.size(); i++) {
				if ( (al.get(i).getUserid().equals(request.getParameter("userid")))
						&& (al.get(i).getUserpw().equals(request.getParameter("userpw"))) ) {
					user = al.get(i);
				}
			}
			if (user != null) {
				session.setAttribute("userInfo", user);
			} else {
				throw new ShowError("아이디나 비밀번호가 올바르지 않습니다.");
			}

			//userInfo.put("userDTO", user);
			//session.setAttribute("userInfo", user);
		} catch (Exception e) {
			session.setAttribute("errorMsg", e.getMessage());
		}
		request.getRequestDispatcher("userInfo.jsp").forward(request, response);
	}

}
