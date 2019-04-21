package model.domain;

public class PostDTO {
	private int postno;
	private String userid;
	private String ptitle;
	private String pcontent;
	
	public PostDTO() {}
	public PostDTO(int postno, String userid, String ptitle, String pcontent) {
		this.postno = postno;
		this.userid = userid;
		this.ptitle = ptitle;
		this.pcontent = pcontent;
	}
	
	public int getPostno() {
		return postno;
	}
	public void setPostno(int postno) {
		this.postno = postno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	public String getPcontent() {
		return pcontent;
	}
	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	@Override
	public String toString() {
		return "PostDTO [postno=" + postno + ", userid=" + userid + ", ptitle=" + ptitle + ", pcontent=" + pcontent
				+ "]";
	}
}
