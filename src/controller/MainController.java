package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import model.BuyDAO;
import model.PostDAO;
import model.SongDAO;
import model.UserDAO;

@WebServlet("/maincon")
public class MainController extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter script = response.getWriter();
		
		String command = request.getParameter("command");

		if (command.equals("login")) {
			login(request, response, session, script);
		} else if (command.equals("join")) {
			join(request, response, session, script);
		} else if (command.equals("logout")) {
			logout(request, response, session);
		} else if (command.equals("userUpdate")) {
			userUpdate(request, response, session, script);
		} else if (command.equals("userDelete")) {
			userDelete(request, response, session, script);
		} else if (command.equals("postWrite")) {
			postWrite(request, response, session, script);
		} else if (command.equals("postUpdate")) {
			postControl(request, response, session, script);
		} else if (command.equals("postDelete")) {
			postControl(request, response, session, script);
		} else if (command.equals("buy")) {
			buy(request, response);
		} else {	//command=search
			search(request, response, script);
		}
	}
	
	//로그인
	private void login(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter script) throws ServletException, IOException {
		try {
			int result = UserDAO.login((String)request.getParameter("userid"), (String)request.getParameter("userpw"));
			if (result == 1) {
				session.setAttribute("userid", request.getParameter("userid"));
				script.println("<script>location.href='main.jsp';</script>");
			} else if (result == 2) {
				script.println("<script>alert(\"비밀번호가 올바르지 않습니다.\");history.back();</script>");
			} else {	//result == 3
				script.println("<script>alert(\"존재하지 않는 아이디 입니다.\");history.back();</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			script.println("<script>alert(\"오류가 발생하였습니다.\");history.back();</script>");
		}
	}
	
	//회원가입
	private void join(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter script) throws ServletException, IOException {
		try {
			if ( UserDAO.join(request.getParameter("userid"), request.getParameter("userpw"), request.getParameter("usernickname")) ) {
				session.setAttribute("userid", request.getParameter("userid"));
				script.println("<script>location.href='main.jsp';</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			script.println("<script>alert(\"이미 가입된 아이디 입니다.\");history.back();</script>");
		}
	}
	
	//로그아웃
	private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.invalidate();
		session = null;
		try {
			response.sendRedirect("main.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//정보 수정
	private void userUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter script) throws ServletException, IOException {
		try {
			if (UserDAO.userUpdate((String) session.getAttribute("userid"), request.getParameter("userpw"), request.getParameter("usernickname")) == 1) {
				script.println("<script>location.href='userMyPage.jsp';</script>");
			} else {
				script.println("<script>alert(\"아이디를 불러오지 못했습니다.\");history.back();</script>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			script.println("<script>alert(\"수정에 실패하였습니다.\");history.back();</script>");
		}
		
		
	}
	
	//회원 탈퇴
	private void userDelete(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter script) throws ServletException, IOException {
		String userid = (String) session.getAttribute("userid");
		int result = 0;
		
		try {
			//기존 작성한 게시글의 작성자를 "탈퇴한 회원"으로 변경(탈퇴해도 게시글은 유지)
			if (PostDAO.getPost(userid) == null) {	//기존 작성한 게시글이 없는 경우
				result = 1;
			} else {
				result = PostDAO.update(userid, "탈퇴한 회원");
			}
			
			//탈퇴
			if (result == 1) {
				if (UserDAO.userDelete(userid) == 1) {
					session.invalidate();
					session = null;
					script.println("<script>alert(\"탈퇴하였습니다.\");location.href='main.jsp';</script>");
				} else {
					script.println("<script>alert(\"아이디를 불러오지 못했습니다.\");history.back();</script>");
				}
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
			script.println("<script>alert(\"탈퇴에 실패하였습니다.\");history.back();</script>");
		}
	}
	
	//게시글 작성
	private void postWrite(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter script) throws ServletException, IOException {
		try {
			int result = PostDAO.write((String) session.getAttribute("userid"), request.getParameter("ptitle"),
					request.getParameter("pcontent"), request.getParameter("surl"), request.getParameter("stitle"),
					request.getParameter("sartist"), request.getParameter("salbum"));
			if (result == 1) {
				script.println("<script>");
				script.println("location.href='post.jsp'");
				script.println("</script>");
			} else {
				script.println("<script>");
				script.println("alert('글쓰기에 실패했습니다')");
				script.println("history.back()");
				script.println("</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			script.println("<script>");
			script.println("alert('글쓰기에 실패했습니다')");
			script.println("history.back()");
			script.println("</script>");
		}
		
	}
	
	//게시글 수정 or 삭제
	private void postControl(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter script) throws ServletException, IOException {
		int postno = 0;
		
		if (request.getParameter("postno") != null) {
			postno = Integer.parseInt(request.getParameter("postno"));
		} else {
			script.println("<script>");
			script.println("alert('유효하지 않은 글 입니다.')");
			script.println("location.href = 'post.jsp'");
			script.println("</script>");
		}
		
		try {
			if (! session.getAttribute("userid").equals(PostDAO.getPost(postno).getUserid())) {
				script.println("<script>");
				script.println("alert('권한이 없습니다.')");
				script.println("location.href = 'post.jsp'");
				script.println("</script>");
			} else {
				if (request.getParameter("command") == "postUpdate") {
					if (PostDAO.update(postno, request.getParameter("ptitle"), request.getParameter("pcontent"),
							request.getParameter("surl"), request.getParameter("stitle"),
							request.getParameter("sartist"), request.getParameter("salbum")) == 1) {
						script.println("<script>");
						script.println("alert('수정되었습니다.')");
						script.println("location.href='post.jsp'");
						script.println("</script>");
					} else {
						script.println("<script>");
						script.println("alert('유효하지 않은 글 입니다.')");
						script.println("location.href = 'post.jsp'");
						script.println("</script>");
					}
				} else {	//command=postDelete
					if (PostDAO.delete(postno) == 1) {
						script.println("<script>");
						script.println("alert('삭제되었습니다.')");
						script.println("location.href='post.jsp'");
						script.println("</script>");
					} else {
						script.println("<script>");
						script.println("alert('유효하지 않은 글 입니다.')");
						script.println("location.href = 'post.jsp'");
						script.println("</script>");
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			script.println("<script>");
			script.println("alert('오류가 발생했습니다')");
			script.println("history.back()");
			script.println("</script>");
		}
	}
	
	//이용권 검색
	private void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject allData = new JSONObject();
		ArrayList<String> msg = new ArrayList<>();
		msg.add("오류가 발생하였습니다.");
		
		if (request.getParameter("genie").equals("1")) {
			try {
				allData.put("genie", BuyDAO.getGenie());
				System.out.println(allData.get("genie"));
			} catch (Exception e) {
				e.printStackTrace();
				allData.put("genie", msg);
			}
		}
		
		if (request.getParameter("bugs").equals("1")) {
			try {
				allData.put("bugs", BuyDAO.getBugs());
			} catch (Exception e) {
				e.printStackTrace();
				allData.put("bugs", msg);
			}
		}

		if (request.getParameter("melon").equals("1")) {
			try {
				allData.put("melon", BuyDAO.getMelon());
			} catch (Exception e) {
				e.printStackTrace();
				allData.put("melon", msg);
			}
		}
		request.setAttribute("allData", allData);
		request.getRequestDispatcher("buyData.jsp").forward(request, response);
	}

	//노래 검색
	private void search(HttpServletRequest request, HttpServletResponse response, PrintWriter script) throws ServletException, IOException {
		ArrayList<JSONObject> songList = null;
		try {
			songList = SongDAO.getSongList(request.getParameter("searchWord"));
		} catch (Exception e) {
			e.printStackTrace();
			script.println("<script>alert(\"오류가 발생하였습니다.\");</script>");
		}
		request.setAttribute("songData", songList);
		request.getRequestDispatcher("songData.jsp").forward(request, response);
	}

	
}
