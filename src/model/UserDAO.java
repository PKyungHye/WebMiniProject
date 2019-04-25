package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.domain.UserDTO;
import model.util.DBUtil;

public class UserDAO {
	//유저 로그인
	public static int login(String userid, String userpw) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT userpw FROM user01 WHERE userid = ?");
			pstmt.setString(1, userid);
			rset = pstmt.executeQuery();
			if(rset.next()){
				if (rset.getString(1).equals(userpw)) {	//비밀번호 일치할 경우
					result = 1;
				} else {	//비밀번호 불일치할 경우
					result = 2;
				}
			} else {
				//아이디 존재하지 않을 경우
				result = 3;
			}
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return result;
	}
	
	//유저 회원가입
	public static boolean join(String userid, String userpw, String usernickname) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("INSERT INTO user01 VALUES(?, ?, ?)");
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			pstmt.setString(3, usernickname);
			int i = pstmt.executeUpdate();
			if (i == 1) {
				result = true;
			}
		} finally {
			DBUtil.close(con, pstmt);
		}
		return result;
	}
	
	//유저 닉네임 검색
	public static String getNickname(String userid) throws SQLException {
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
//		} catch (Exception e) {
//			return "오류";
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
	}
	
	//유저 id로 유저 검색
	public static UserDTO getUser(String userid) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		UserDTO user = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM user01 WHERE userid = ?");
			pstmt.setString(1, userid);
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				user = new UserDTO(rset.getString(1), rset.getString(2), rset.getString(3));
			}
//		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return user;
	}
	
	//유저id로 정보 수정
	public static int userUpdate(String userid, String usernickname) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("UPDATE user01 SET usernickname = ? WHERE userid = ?");
			pstmt.setString(1, usernickname);
			pstmt.setString(2, userid);
			result = pstmt.executeUpdate();
		} finally {
			DBUtil.close(con, pstmt);
		}
		return result;
	}


	//유저 정보 삭제(회원 탈퇴)
	public static int userDelete(String userid) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("DELETE FROM user01 WHERE userid = ?");
			pstmt.setString(1, userid);
			result = pstmt.executeUpdate();	
//		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			DBUtil.close(con, pstmt);
		}
		return result;
	}
	
}
