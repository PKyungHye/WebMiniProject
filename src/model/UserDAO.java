package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.domain.UserDTO;
import util.DBUtil;

public class UserDAO {
	//유저 로그인(검색)
	public static UserDTO getUser(String userid) throws SQLException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		UserDTO user = null;
		
		try{
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement("select * from user01 where userid=?");
			pstmt.setString(1, userid);
			rset = pstmt.executeQuery();
			if(rset.next()){
				user = new UserDTO(rset.getString(1), rset.getString(2), rset.getString(3));
			}
		} finally {
			DBUtil.close(con, pstmt, rset);
		}
		return user;
	}
}
