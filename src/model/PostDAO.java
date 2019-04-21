package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.domain.PostDTO;
import util.DBUtil;

public class PostDAO {
	//insert
	public static void insert(PostDTO post) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("INSERT INTO post01 VALUES(?,?,?,?)");
			pstmt.setInt(1, post.getPostno());
			pstmt.setString(2, post.getUserid());
			pstmt.setString(3, post.getPtitle());
			pstmt.setString(4, post.getPcontent());
			
			pstmt.executeUpdate();
		} finally {
			DBUtil.close(con,pstmt);
		}
	}
	
	//select
	public static ArrayList<PostDTO> select() throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		ArrayList<PostDTO> deptList = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT postno, usernickname, ptitle, pcontent FROM post01 JOIN user01 ON post01.userid = user01.userid");
			rst = pstmt.executeQuery();
			
			deptList = new ArrayList<PostDTO>();
			while (rst.next()) {
				deptList.add( new PostDTO(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4)) );
			}
		} finally {
			DBUtil.close(con,pstmt, rst);
		}
		return deptList;
	}
	
}
