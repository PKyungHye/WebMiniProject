package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.domain.PostDTO;
import util.DBUtil;

public class PostDAO {
	//insert
	public static int write(String userid, String ptitle, String pcontent) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("INSERT INTO post01 VALUES(post01_postno_seq.NEXTVAL, sysdate, ?, ?, ?)");
			pstmt.setString(1, userid);
			pstmt.setString(2, ptitle);
			pstmt.setString(3, pcontent);
			
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
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT postno FROM post01 ORDER BY postno DESC");
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt(1);
			} else {
				return 0;
			}
		} catch (Exception e) {
			return 0;
		} finally {
			DBUtil.close(con,pstmt);
		}
	}
	
	//목록 조회
	public static ArrayList<PostDTO> getList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<PostDTO> postList = new ArrayList<PostDTO>();
		try{
			con = DBUtil.getConnection();
			//pstmt = con.prepareStatement("SELECT * FROM post01 WHERE postno >= ? AND postno < ? ORDER BY postno DESC");
			pstmt = con.prepareStatement("SELECT * FROM post01 ORDER BY postno DESC");
			rset = pstmt.executeQuery();
			while (rset.next()) {
				postList.add( new PostDTO(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getString(5)) );
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(con, pstmt, rset);
		}
		return postList;
	}
}
