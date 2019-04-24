package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.domain.PostDTO;
import util.DBUtil;

public class PostDAO {
	//글쓰기
	public static int write(String userid, String ptitle, String pcontent, String surl, String stitle, String sartist, String salbum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("INSERT INTO post01 VALUES(post01_postno_seq.NEXTVAL, sysdate, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, userid);
			pstmt.setString(2, ptitle);
			pstmt.setString(3, pcontent);
			pstmt.setString(4, surl);
			pstmt.setString(5, stitle);
			pstmt.setString(6, sartist);
			pstmt.setString(7, salbum);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			return 0;
		} finally {
			DBUtil.close(con,pstmt);
		}
	}
	
	//현재 마지막 게시글 번호 가져오기
	public static int getPostno() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT postno FROM post01 ORDER BY postno DESC");
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con,pstmt);
		}
		return result;
	}
	
	//10개씩 목록 조회
	public static ArrayList<PostDTO> getList(int pageNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<PostDTO> postList = new ArrayList<PostDTO>();
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT postno, postdate, userid, ptitle, pcontent, surl, stitle, sartist, salbum FROM (SELECT * FROM (SELECT * FROM post01 ORDER BY postno DESC) WHERE ROWNUM <= ?) MINUS (SELECT postno, postdate, userid, ptitle, pcontent, surl, stitle, sartist, salbum FROM (SELECT * FROM post01 ORDER BY postno DESC) WHERE ROWNUM <= ?) ORDER BY postno DESC");
			pstmt.setInt(1, pageNum*10);
			pstmt.setInt(2, (pageNum - 1)*10);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				postList.add( new PostDTO(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8), rset.getString(9)) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(con, pstmt, rset);
		}
		return postList;
	}
	
	//10 단위 페이징 처리를 위한 함수
	public static boolean nextPage(int pageNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean result = false;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT postno FROM (SELECT * FROM (SELECT * FROM post01 ORDER BY postno DESC) WHERE ROWNUM <= ?) MINUS (SELECT postno FROM (SELECT * FROM post01 ORDER BY postno DESC) WHERE ROWNUM <= ?) ORDER BY postno DESC");
			pstmt.setInt( 1, (pageNum + 1)*10);
			pstmt.setInt( 2, (pageNum)*10);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return result;
	}
	
	//게시글 번호로 게시글 검색
	public static PostDTO getPost(int postno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		PostDTO post = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM post01 WHERE postno = ?");
			pstmt.setInt(1, postno);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				post = new PostDTO(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8), rset.getString(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return post;
	}
	
	//게시글 번호로 게시글 검색
	public static PostDTO getPost(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		PostDTO post = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM post01 WHERE userid = ?");
			pstmt.setString(1, userid);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				post = new PostDTO(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4),
						rset.getString(5), rset.getString(6), rset.getString(7), rset.getString(8), rset.getString(9));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return post;
	}
	
	//글 수정
	public static int update(int postno, String ptitle, String pcontent, String surl, String stitle, String sartist, String salbum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("UPDATE post01 SET ptitle = ?, pcontent = ?, surl = ?, stitle = ?, sartist = ?, salbum = ? WHERE postno = ?");
			pstmt.setString(1, ptitle);
			pstmt.setString(2, pcontent);
			pstmt.setString(3, surl);
			pstmt.setString(4, stitle);
			pstmt.setString(5, sartist);
			pstmt.setString(6, salbum);
			pstmt.setInt(7, postno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		return result;
	}
	
	//탈퇴한 회원이 쓴 글 수정
	public static int update(String userid, String newid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("UPDATE post01 SET userid = ? WHERE userid = ?");
			pstmt.setString(1, newid);
			pstmt.setString(2, userid);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		return result;
	}
	
	//글 삭제
	public static int delete(int postno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("DELETE FROM post01 WHERE postno = ?");
			pstmt.setInt(1, postno);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
