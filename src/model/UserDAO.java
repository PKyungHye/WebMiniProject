package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.domain.UserDTO;
import util.DBUtil;

public class UserDAO {
	//유저 로그인
	public static int login(String userid, String userpw) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		UserDTO user = null;
		
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
	public static int join(UserDTO user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("INSERT INTO user01 VALUES(?, ?, ?)");
			pstmt.setString(1, user.getUserid());
			pstmt.setString(2, user.getUserpw());
			pstmt.setString(3, user.getUsernickname());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			return 0;
		} finally {
			DBUtil.close(con, pstmt);
		}
	}
}
