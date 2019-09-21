package cnt;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import member.bean.MemberDTO;
import member.dao.MemberDAO;


@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener,Runnable {
	private JLabel idL, pwL, nonmemL,titleL,pcNumL1,pcNumL2;
	private JTextField idT, nonmemT, seatT;
	private JPasswordField pwF;
	private JButton loginB,joinB,loseIDB, losePWB;
 	private Font titleF,loginF1,loginF2,seatLF,seatTF;
 	private JPanel titleJP, loginLJP, loginTJP, loginBtnJP1,loginBtnJP2,pcNumJP;
 	private Join join;
 	private LoseID loseID;
 	private LosePW losePW;
	public LoginFrame() {
		Container con = getContentPane();
		con.setLayout(null);
		//0. 타이틀 메세지 영역
		titleJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titleF = new Font("Calibri Light",Font.ITALIC+Font.BOLD, 40);
		titleL = new JLabel("***PC Cafe Program***");
		titleL.setFont(titleF);
		titleL.setForeground(Color.WHITE);
		titleJP.setBounds(0,20,450,60);//450,80
		titleJP.add(titleL);
		titleJP.setOpaque(false);
 		con.add(titleJP);
		//1. 로그인 영역
		loginF1 = new Font("Bahnschrift Light Condensed",Font.BOLD, 15);	
		loginF2 = new Font("Bahnschrift Light Condensed",Font.BOLD, 20);
		seatLF = new Font("Bahnschrift Light Condensed",Font.BOLD, 15);
		seatTF = new Font("Bahnschrift Light Condensed",Font.BOLD, 20);
		//1-1. 요소 선언(instance declare) + FONT 설정	
		idL = new JLabel("회 원 번 호");
		idL.setFont(loginF1);
		pwL = new JLabel("회 원 비밀번호");
		pwL.setFont(loginF1);
		nonmemL = new JLabel("비회원 번호");
		nonmemL.setFont(loginF1);
		
		pcNumL1 = new JLabel("지금 계신 자리는 ");
		pcNumL1.setFont(seatLF);
		pcNumL1.setForeground(Color.RED);
		seatT = new JTextField(2);
		seatT.setFont(seatTF);
		pcNumL2 = new JLabel("번 좌석입니다.");
		pcNumL2.setForeground(Color.RED);
		pcNumL2.setFont(seatLF);
	
		
		idT = new JTextField(10);
		idT.setFont(loginF2);
		pwF = new JPasswordField(10);
		pwF.setFont(loginF2);
		nonmemT = new JTextField(10);
		nonmemT.setFont(loginF2);
		//(버튼 Type 1)
		loginB = new JButton("로그인");
		loginB.setFont(loginF2);
		//(버튼 Type 2)
		joinB = new JButton(new ImageIcon("src/lib/signup.png"));
		joinB.setBorderPainted(false);
		joinB.setFocusPainted(false);
		joinB.setContentAreaFilled(false);
		
		loseIDB = new JButton(new ImageIcon("src/lib/findid.png"));
		loseIDB.setBorderPainted(false);
		loseIDB.setFocusPainted(false);
		loseIDB.setContentAreaFilled(false);
		
		losePWB = new JButton(new ImageIcon("src/lib/findpass.png"));
		losePWB.setBorderPainted(false);
		losePWB.setFocusPainted(false);
		losePWB.setContentAreaFilled(false);
		
		//1-2. PC번호 라벨 배치
		pcNumJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pcNumJP.setBounds(60,150,320,60);//380,230
		//Thread t = new Thread(this);
		//t.start();
		LineBorder lb = new LineBorder(Color.GREEN,3);
		pcNumJP.setBorder(lb);
		pcNumJP.add(pcNumL1); pcNumJP.add(seatT); pcNumJP.add(pcNumL2);
		con.add(pcNumJP);
		
		//1-3. 로그인 라벨 배치+container 부착
		loginLJP = new JPanel(new GridLayout(3,1,30,20));
		loginLJP.setBounds(80,250,100,150);//180,400
		loginLJP.add(idL);
		loginLJP.add(pwL);
		loginLJP.add(nonmemL);
		con.add(loginLJP);
		
		//1-4. 로그인 입력부 배치+container 부착
		loginTJP = new JPanel(new GridLayout(3,1,30,20));
		loginTJP.setBounds(210,260,150,150);//360,410
		loginTJP.add(idT);
		loginTJP.add(pwF);
		loginTJP.add(nonmemT);
		con.add(loginTJP);
		
		//2. 버튼 배치+container 부착
		//2-1. (버튼 Type1 배치)
		loginBtnJP1 = new JPanel(new FlowLayout(FlowLayout.CENTER));	
		loginBtnJP1.setBounds(40,440,320,50);//360,480
		loginBtnJP1.add(loginB);
		con.add(loginBtnJP1);
		//2-2. (버튼 Type2 배치)
		loginBtnJP2 = new JPanel(new GridLayout(1,3,20,10));
		loginBtnJP2.setBounds(80,500,280,50);//360,550
		loginBtnJP2.add(joinB);
		loginBtnJP2.add(loseIDB);
		loginBtnJP2.add(losePWB);
		con.add(loginBtnJP2);

		//3. Frame 설정
 		setUndecorated(true);//투명창 만들기
 		setTitle("PC Cafe Program");
 		setBounds(1000,50,480,600);
 		setResizable(false);
 		setVisible(true);
 		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//4. Event 설정
		loginB.addActionListener(this);
		joinB.addActionListener(this);
		loseIDB.addActionListener(this);
		losePWB.addActionListener(this);
		idT.addActionListener(this);
		pwF.addActionListener(this);
		nonmemT.addActionListener(this);
		
 		
	}//LoginFrame()
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(loginB == e.getSource()||idT==e.getSource()||pwF==e.getSource()||nonmemT==e.getSource()) {
			if(seatT.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "좌석을 선택해주세요 ", "정보", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String id = idT.getText();
			//유저 로그인 창에서 관리자계정 접근X
			String pw = "";
			//JPasswordFiled 값 가져오기
			char[] secret_pw = pwF.getPassword();
			for(char sPW : secret_pw){ 
				Character.toString(sPW);
				pw += sPW;
			}
			String nonmem = nonmemT.getText();
			String seatNum = seatT.getText();
			//비회원 창이 비어있다면 회원 로그인, 번호입력되어있으면 비회원 로그인
			if(nonmem.equals("")) {
				if(id.equals("")){//아이디텍스트 입력값 없을시
					JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(pw.equals("")) {//비밀번호 텍스트 입력값 없을 시 
					JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(id.equals("admin")) {//유저 계정 아닐시
					JOptionPane.showMessageDialog(this, "유저 계정이 아닙니다", "정보", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				//회원 로그인
				//login결과 가져옴, 1==성공, 0==실패
				int result = login(id,pw);
				System.out.println(result);
				if (result==1){
					MemberDAO memberDAO = MemberDAO.getInstance();
					MemberDTO memberDTO = memberDAO.getMemberInfo(id,pw);//로그인 시 유저의 정보를 가져옴
					memberDTO.setSeat(Integer.parseInt(seatNum));
					memberDTO.setMember(true);
					if(memberDTO.getTime()==0) {//잔여시간 없으면 로그인 불가
						JOptionPane.showMessageDialog(this, "잔여 시간이 없습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
					}else {
						dispose();
						CntServer cnt = CntServer.getlogin(memberDTO);
						cnt.service();
						memberDAO.setOn(id);
						memberDAO.setSeatOn(seatT.getText(),id);
					}
				
				}else {
					JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호를 다시 확인해주세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}else if(!nonmem.equals("")){
				//비회원 로그인
				
				//login결과 가져옴, 1==성공, 0==실패
				int result = nonLogin(nonmem);
				System.out.println(result);
				
				if (result==1){
					MemberDAO memberDAO = MemberDAO.getInstance();
					MemberDTO memberDTO = memberDAO.getNonMemInfo(nonmem);//비회원의 정보를 가져옴
					memberDTO.setMember(false);
					if(memberDTO.getTime()==0) {//잔여시간 없으면 로그인 불가
						JOptionPane.showMessageDialog(this, "잔여 시간이 없습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
					}else {
						dispose();
						CntServer cnt = CntServer.getlogin(memberDTO);
						cnt.service();
						memberDAO.setOn(nonmem);
						memberDAO.setSeatOn(seatT.getText(),nonmem);
					}
				
				}else {
					JOptionPane.showMessageDialog(this, "비회원 번호를 확인하세요", "정보", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		}else if (joinB == e.getSource()) {
			if(join == null) join = new Join();
			else{join.dispose(); join = new Join();}
		}else if(loseIDB == e.getSource()) {
			if(loseID == null) loseID = new LoseID();
			else {loseID.dispose(); loseID = new LoseID();}
		}else if(losePWB == e.getSource()) {
			if(losePW == null) losePW = new LosePW();
			else{losePW.dispose(); losePW = new LosePW();}
		}
		
	}
	
	public int nonLogin(String nonmem) {
		MemberDAO memberDAO = MemberDAO.getInstance();
		//result가 0이면 db에 정보가 없음, 1이면 로그인성공
		int result = memberDAO.nonlogin(nonmem);
		
		return result;
		
	}

	public int login(String id,String pw) {
		MemberDAO memberDAO = MemberDAO.getInstance();
		//result가 0이면 db에 정보가 없음, 1이면 로그인성공
		int result = memberDAO.login(id,pw);
		
		return result;
	}

	@Override
	public void run() {
		boolean isGreen = false;
		while(true) {
			if(!isGreen) {
				LineBorder lb = new LineBorder(Color.GREEN,3);
				pcNumJP.setBorder(lb);
				isGreen = true;
				}
			else {			
				LineBorder lb = new LineBorder(Color.WHITE,3);
				pcNumJP.setBorder(lb);	
				isGreen = false;}
			try{Thread.sleep(2000);}
			catch(Exception e){e.printStackTrace();}

		}//while
	}//run

}
