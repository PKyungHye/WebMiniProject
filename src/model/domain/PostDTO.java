package model.domain;

public class PostDTO {
	private int postno;
	private String postdate;
	private String userid;
	private String ptitle;
	private String pcontent;
	private String surl;
	private String stitle;
	private String sartist;
	private String salbum;
	
	public PostDTO() {}
	public PostDTO(int postno, String postdate, String userid, String ptitle, String pcontent, String surl,
			String stitle, String sartist, String salbum) {
		super();
		this.postno = postno;
		this.postdate = postdate;
		this.userid = userid;
		this.ptitle = ptitle;
		this.pcontent = pcontent;
		this.surl = surl;
		this.stitle = stitle;
		this.sartist = sartist;
		this.salbum = salbum;
	}
	
	public PostDTO(String userid, String ptitle, String pcontent) {
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
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
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
	public String getSurl() {
		return surl;
	}
	public void setSurl(String surl) {
		this.surl = surl;
	}
	public String getStitle() {
		return stitle;
	}
	public void setStitle(String stitle) {
		this.stitle = stitle;
	}
	public String getSartist() {
		return sartist;
	}
	public void setSartist(String sartist) {
		this.sartist = sartist;
	}
	public String getSalbum() {
		return salbum;
	}
	public void setSalbum(String salbum) {
		this.salbum = salbum;
	}
	
	@Override
	public String toString() {
		return "PostDTO [postno=" + postno + ", postdate=" + postdate + ", userid=" + userid + ", ptitle=" + ptitle
				+ ", pcontent=" + pcontent + ", surl=" + surl + ", stitle=" + stitle + ", sartist=" + sartist
				+ ", salbum=" + salbum + "]";
	}
	
}
