package mgr;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import member.bean.MemberDTO;
import member.dao.MemberDAO;

@SuppressWarnings("serial")
public class UserList extends JPanel implements Runnable {
	private JList<MemberDTO> list;//로그인된 유저목록
	private DefaultListModel<MemberDTO> model;
	private MemberDAO dao = new MemberDAO();
	private int listSize;
	private ArrayList<MemberDTO> memberList = null;
	public UserList() {
		model = new DefaultListModel<MemberDTO>();
		list = new JList<MemberDTO>(model);
		
		
		
		add(list);		
		Thread t = new Thread(this);
		t.start();
		
		list.setBounds(360,40,300,220);
		setVisible(true);
	}
	
	
	@Override
	public void run() {
		while(true) {
			memberList = dao.getMemberList();
			if(listSize != memberList.size()) {
				model.removeAllElements();
				for(MemberDTO data : memberList) {				
					model.addElement(data);
					
				}
			}
			repaint();
			listSize = memberList.size();
			try{Thread.sleep(1000);}
			catch(Exception e){e.printStackTrace();}
			
		}	
	}
	public JList<MemberDTO> getList(){
		return this.list;
	}
}
