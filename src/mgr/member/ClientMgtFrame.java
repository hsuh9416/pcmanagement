package mgr.member;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import member.bean.MemberDTO;
import member.dao.MemberDAO;

@SuppressWarnings("serial")
public class ClientMgtFrame extends JFrame implements ActionListener {
	//private ArrayList<MemberDTO> list;
	private JButton modifyCntB, deleteCntB, nonMemB;
	private DefaultTableModel model;
	private JTable tableCnt;
	private JPanel tableJP,buttonJP;
	private JScrollPane scroll;
	private Vector<String> vector;
	
	public ClientMgtFrame() {
		
		
		Container con = getContentPane();
		con.setLayout(null);
		
		//1-1.테이블 타이틀 부착
		vector = new Vector<String>();
		vector.addElement("ID");
		vector.addElement("NAME");
		vector.addElement("TEL1");
		vector.addElement("TEL2");
		vector.addElement("TEL3");
		vector.addElement("EMAIL1");
		vector.addElement("EMAIL2");
		
		//1-2.tablemodel 생성
		model = new DefaultTableModel(vector,0){
			public boolean isCellEditable(int r, int c) {
				return (c!=0) ? true : false;
			}
		};
		
		//1-3.JTable 생성
		tableCnt = new JTable(model);
		scroll = new JScrollPane(tableCnt);
		
		
		//1-4. Panel부착
		tableJP = new JPanel();
		tableJP.setLayout(new BorderLayout());
		tableJP.add(scroll,BorderLayout.CENTER);
		tableJP.setBounds(20,20,660,380);//680,400
		con.add(tableJP);
	
		//2-1. 버튼 생성
		modifyCntB = new JButton("회원 정보 수정");
		deleteCntB = new JButton("회원 정보 삭제");
		nonMemB = new JButton("비회원 생성");
		
		//2-2. Panel부착
		buttonJP = new JPanel(new GridLayout(1,3,10,10));
		buttonJP.setBounds(150,420,400,40);//550,460
		buttonJP.add(modifyCntB); buttonJP.add(deleteCntB);
		buttonJP.add(nonMemB);
		con.add(buttonJP);
		
 		//3. Frame 설정
 		setUndecorated(true);//투명창 만들기
 		setTitle("회원 관리");
 		setBounds(450,100,700,500);
 		setResizable(false);
 		setVisible(true);
 		
 		//4. Event 설정
 		modifyCntB.addActionListener(this);
 		deleteCntB.addActionListener(this);
 		nonMemB.addActionListener(this);
 		
 		
 		//db에 있는 회원들의 정보를 가져와서 뿌려줌*************************
 		MemberDAO memberDAO = MemberDAO.getInstance();
 		ArrayList<MemberDTO> userList = memberDAO.getUserList();//db에있는 유저 리스트 뽑아옴
 		
 		for(MemberDTO memberDTO : userList) {//벡터에 memberDTO 추가후 model에 추가
 			System.out.println(memberDTO.getId());
 			Vector<String >vector  = new Vector<String>();
 			vector.addElement(memberDTO.getId());
 			vector.addElement(memberDTO.getUserName());
 			vector.addElement(memberDTO.getTel1());
 			vector.addElement(memberDTO.getTel2());
 			vector.addElement(memberDTO.getTel3());
 			vector.addElement(memberDTO.getEmaiL1());
 			vector.addElement(memberDTO.getEmail2());
 			
 			model.addRow(vector);
 			
 		}
 		
 	
 		
	}//ClientMgtFrame()-생성자 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(modifyCntB == e.getSource()) {//****************************************
			
			if(tableCnt.getSelectedRow()!=-1) { //테이블 선택안하고 버튼누를시 메세지 출력, 선택시 수정창 띄움
				MemberDTO memberDTO = new MemberDTO();
				//선택된 행의 값을 가져옴
				memberDTO.setId((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 0));
				memberDTO.setUserName((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 1));
				memberDTO.setTel1((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 2));
				memberDTO.setTel2((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 3));
				memberDTO.setTel3((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 4));
				memberDTO.setEmaiL1((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 5));
				memberDTO.setEmail2((String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 6));
				new InfoModify(memberDTO);
				dispose();
				
			}else {
				JOptionPane.showMessageDialog(this, "개인정보를 선택해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(deleteCntB == e.getSource()) {
			if(tableCnt.getSelectedRow()!=-1) {
				int result = JOptionPane.showConfirmDialog(this, "해당 회원정보를 삭제하시겠습니까?", "정보", JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION) {
					//선택된 행의 id값을 가져옴
					String id = (String) tableCnt.getValueAt(tableCnt.getSelectedRow(), 0);
					MemberDAO memberDAO = MemberDAO.getInstance();
					memberDAO.deleteInfo(id);//db연동 회원정보삭제
					JOptionPane.showMessageDialog(this, "삭제 완료 되었습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					new ClientMgtFrame();
					
				}
			}else {
				JOptionPane.showMessageDialog(this, "개인정보를 선택해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
			}
		}else if(nonMemB==e.getSource()) {
			int checkSW=0;
			String nonMemNum = null;
			
			MemberDAO memberDAO = MemberDAO.getInstance();
			ArrayList<String> list = memberDAO.getNonMemNumList();
			
			while(true) {
				//3자리의 랜덤 비회원번호
				int num =(int)(Math.random()*999);
				nonMemNum= String.valueOf(num);
				
				//db테이블에 비회원 번호가 있는지 없는지 체크하는 로직
				for(String nonNum : list) {
					if(nonMemNum.equals(nonNum)) {
						checkSW=1;
						break;
					}
					else checkSW=0;
				}
				if(checkSW==0) break;
				
			}
			
			
			memberDAO.setNonMem(nonMemNum);
			JOptionPane.showMessageDialog(this, "비회원 번호 : "+nonMemNum, "정보", JOptionPane.INFORMATION_MESSAGE);
		}

	
	}


}//ClientMgtFrame Class
