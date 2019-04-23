package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.domain.UserDTO;
import util.DBUtil;

public class UserDAO {
	//유저 로그인
	public static int login(String userid, String userpw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT userpw FROM user01 WHERE userid = ?");
			pstmt.setString(1, userid);
			rset = pstmt.executeQuery();
			if(rset.next()){
				if (rset.getString(1).equals(userpw)) {
					return 1;
				} else {
					return 2;
				}
			} else {
				return 3;
			}
		} catch (Exception e) {
			return 0;
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
	}
	
	//유저 회원가입
	public static int join(String userid, String userpw, String usernickname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("INSERT INTO user01 VALUES(?, ?, ?)");
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			pstmt.setString(3, usernickname);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			return 0;
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
	
	//유저 닉네임 검색
	public static String getNickname(String userid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT usernickname FROM user01 WHERE userid = ?");
			pstmt.setString(1, userid);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				return rset.getString(1);
			} else {
				return "null";
			}
		} catch (Exception e) {
			return "오류";
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
	}
}
