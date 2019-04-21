package model;

import java.sql.SQLException;

import exception.ShowError;
import model.domain.UserDTO;

public class SongService {
	
	//로그인(검색)
	public static UserDTO getUser(String userid, String userpw) throws Exception{
		UserDTO user = UserDAO.getUser(userid);
		if(user == null){
			throw new ShowError("가입되지 않은 아아디 입니다.");
		} else if (user.getUserpw() != userpw) {
			throw new ShowError("비밀번호가 올바르지 않습니다.");
		} else {
			return user;
		}
	}
	
}
