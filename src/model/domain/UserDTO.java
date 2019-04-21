package model.domain;

public class UserDTO {
	private String userid;
	private String userpw;
	private String usernickname;
	
	public UserDTO() {}
	public UserDTO(String userid, String userpw, String usernickname) {
		this.userid = userid;
		this.userpw = userpw;
		this.usernickname = usernickname;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getUsernickname() {
		return usernickname;
	}
	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

	@Override
	public String toString() {
		return "UserDTO [userid=" + userid + ", userpw=" + userpw + ", usernickname=" + usernickname + "]";
	}
}
