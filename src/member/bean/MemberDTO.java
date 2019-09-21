package member.bean;

import java.util.ArrayList;

import member.dao.MemberDAO;

public class MemberDTO {
	private String id, pw, name, tel1, tel2, tel3 ,emaiL1 , email2;
	private int time;
	private int postPayment=0;
	private boolean isMember=false;
	private int seat;

	public static MemberDTO getMemberDTO(String id, boolean isMember) {//기존에 ID가 있으면 
		MemberDTO memberDTO = null;
		MemberDAO dao = MemberDAO.getInstance();

		if(isMember) {
			ArrayList<MemberDTO> memberlist = dao.getUserList();
			for(MemberDTO list : memberlist) {
				if(list.getId().equals(id)) memberDTO = list;}}
		else memberDTO = dao.getNonMemInfo(id);
		return memberDTO;}
	
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getUserName() {
		return name;
	}

	public void setUserName(String name) {
		this.name = name;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getTel3() {
		return tel3;
	}

	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	public String getEmaiL1() {
		return emaiL1;
	}

	public void setEmaiL1(String email1) {
		this.emaiL1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	
	public void setPostPayment(int postPayment) {
		this.postPayment = postPayment;
	}
	
	public int getPostPayment() {
		return postPayment;
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	
	
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	@Override
	public String toString() {
		String text = "["+seat+"]번  ["+name+"] 님 (ID : "+id+")";
		return text;
	}
}
