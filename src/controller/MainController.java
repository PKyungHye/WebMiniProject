package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.PostDAO;
import model.UserDAO;
import model.domain.PostDTO;
import model.domain.UserDTO;

@WebServlet("/maincon")
public class MainController extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String command = request.getParameter("command");

		if (command.equals("login")) {
			login(request, response);
		} else if (command.equals("join")) {
			join(request, response);
		} else if (command.equals("logout")) {
			session.invalidate();
			session = null;
			response.sendRedirect("main.jsp");
		} else if (command.equals("userUpdate")) {
			userUpdate(request, response);
		} else if (command.equals("userDelete")) {
			userDelete(request, response);
		} else if (command.equals("postWrite")) {
			postWrite(request, response);
		} else if (command.equals("postUpdate")) {
			postUpdate(request, response);
		} else if (command.equals("postDelete")) {
			postDelete(request, response);
		} else {
			System.out.println("else");
		}
	}
	
	//로그인
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int result = UserDAO.login((String)request.getParameter("userid"), (String)request.getParameter("userpw"));
		PrintWriter script = response.getWriter();
		if (result == 1) {
			session.setAttribute("userid", request.getParameter("userid"));
			script.println("<script>location.href='main.jsp';</script>");
		} else if (result == 2) {
			script.println("<script>alert(\"비밀번호가 올바르지 않습니다.\");history.back();</script>");
		} else if (result == 3) {
			script.println("<script>alert(\"존재하지 않는 아이디 입니다.\");history.back();</script>");
		} else {	//result == 0
			script.println("<script>alert(\"오류가 발생하였습니다.\");history.back();</script>");
		}
	}
	
	//회원가입
	protected void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int result = UserDAO.join(request.getParameter("userid"), request.getParameter("userpw"), request.getParameter("usernickname"));
		PrintWriter script = response.getWriter();
		if (result == 1) {
			session.setAttribute("userid", request.getParameter("userid"));
			script.println("<script>location.href='main.jsp';</script>");
		} else { // result == 0
			script.println("<script>alert(\"이미 존재하는 아이디 입니다.\");history.back();</script>");
		}
	}
	
	//정보 수정
	protected void userUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int result = UserDAO.userUpdate((String) session.getAttribute("userid"), request.getParameter("userpw"), request.getParameter("usernickname"));
		PrintWriter script = response.getWriter();
		if (result == 1) {
			script.println("<script>location.href='userMyPage.jsp';</script>");
		} else { // result == 0
			script.println("<script>alert(\"수정에 실패하였습니다.\");history.back();</script>");
		}
	}
	
	//회원 탈퇴
	protected void userDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter script = response.getWriter();
		String userid = (String) session.getAttribute("userid");
		int result2 = 0;
		
		if (PostDAO.getPost(userid) == null) {
			result2 = 1;
		} else {
			result2 = PostDAO.update(userid, "탈퇴한 회원");
		}
		
		if (result2 == 1) {
			int result = UserDAO.userDelete(userid);
			if (result == 1) {
				session.invalidate();
				session = null;
				script.println("<script>alert(\"탈퇴하였습니다.\");location.href='main.jsp';</script>");
			} else { // result == 0
				script.println("<script>alert(\"탈퇴에 실패하였습니다.\");history.back();</script>");
			}
		} else {
			script.println("<script>alert(\"탈퇴에 실패하였습니다.\");history.back();</script>");
		}
	}
	
	protected void postWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int result = PostDAO.write((String) session.getAttribute("userid"), request.getParameter("ptitle"), request.getParameter("pcontent"),
									request.getParameter("surl"), request.getParameter("stitle"), request.getParameter("sartist"), request.getParameter("salbum"));
		if (result == 1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href='post.jsp'");
			script.println("</script>");
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('글쓰기에 실패했습니다')");
			script.println("history.back()");
			script.println("</script>");
		}
	}
	
	private void postUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int postno = 0;
		if (request.getParameter("postno") != null) {
			postno = Integer.parseInt(request.getParameter("postno"));
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글 입니다.')");
			script.println("location.href = 'post.jsp'");
			script.println("</script>");
		}
		
		PostDTO post = PostDAO.getPost(postno);
		
		if (! session.getAttribute("userid").equals(post.getUserid())) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'post.jsp'");
			script.println("</script>");
		} else {
			int result = PostDAO.update(postno, request.getParameter("ptitle"), request.getParameter("pcontent"),
										request.getParameter("surl"), request.getParameter("stitle"), request.getParameter("sartist"), request.getParameter("salbum"));
			if (result == 1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('수정되었습니다.')");
				script.println("location.href='post.jsp'");
				script.println("</script>");
			} else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글수정에 실패했습니다')");
				script.println("history.back()");
				script.println("</script>");
			}
		}
	}
	
	private void postDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		int postno = 0;
		if (request.getParameter("postno") != null) {
			postno = Integer.parseInt(request.getParameter("postno"));
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글 입니다.')");
			script.println("location.href = 'post.jsp'");
			script.println("</script>");
		}
		
		PostDTO post = PostDAO.getPost(postno);
		
		if (! session.getAttribute("userid").equals(post.getUserid())) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href = 'post.jsp'");
			script.println("</script>");
		} else {
			int result = PostDAO.delete(postno);
			if (result == 1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('삭제되었습니다.')");
				script.println("location.href='post.jsp'");
				script.println("</script>");
			} else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글수정에 실패했습니다')");
				script.println("history.back()");
				script.println("</script>");
			}
		}
	}
	
}
