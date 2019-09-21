package cnt;


import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import member.bean.MemberDTO;
import member.dao.MemberDAO;



@SuppressWarnings("serial")
public class Join extends JFrame implements ActionListener{	
	private JLabel idL,pwL,pwCheckL, nameL, telL,telH1L,telH2L, email1L,email2L, emailConfirmL ;
	private JTextField idT, nameT, tel2T, tel3T, email1T, email2T, emailConfirmT;
	private JPasswordField pwF, pwCF;
	private JComboBox<String> tel1C, emailC;
	private JButton idOverlapB, emailConfirmB, registB, cancelB;
	private int idCheck, emailCheck;
	private JPanel titleJP, labelJP;
	private Font titleF,loginF1,loginF2,btnF;
	private JLabel titleL;
	private JPanel textfieldJP1;
	private JPanel telfieldJP;
	private JPanel emailfieldJP;
	private JPanel emailCfieldJP;
	private JPanel confirmBtnJP;
	private String checkNum;
	public Join() {
		
		Container con = getContentPane();	
		con.setLayout(null);
		
		//0. 타이틀 메세지 영역
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(0,0,500,50);
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD,15);
		titleL = new JLabel("***등록하실 회원님의 [정보]를 정확하게 입력해주세요.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		titleJP.add(titleL);
		titleJP.setOpaque(false);
 		con.add(titleJP);
 		
 		//1.요소 선언(instance declare) + FONT 설정	
		
 		loginF1= new Font("Bahnschrift Light Condensed",Font.BOLD+Font.ITALIC, 15);	
		loginF2 = new Font("Bahnschrift Light Condensed",Font.PLAIN, 18);	
		btnF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
		
		String[] telCombo = {"010","011","019"};
		String[] emailCombo = {"직접입력","naver.com","google.com"};
		
		idL = new JLabel("I                D");
		idL.setFont(loginF1);
		pwL = new JLabel("PASSWORD");
		pwL.setFont(loginF1);
		pwCheckL = new JLabel("PASSWORD 확인");
		pwCheckL.setFont(loginF1);
		nameL = new JLabel("이              름");
		nameL.setFont(loginF1);		
		telL = new JLabel("휴  대   전  화");
		telL.setFont(loginF1);
		telH1L = new JLabel("-");
		telH2L = new JLabel("-");
		email1L = new JLabel("E - MAIL");
		email1L.setFont(loginF1);
		email2L = new JLabel("@");
		emailConfirmL = new JLabel("E - MAIL   확인");
		emailConfirmL.setFont(loginF1);
		tel1C = new JComboBox<String>(telCombo);
		tel1C.setFont(loginF1);
		emailC = new JComboBox<String>(emailCombo);
		emailC.setFont(loginF1);

		idT = new JTextField(10);
		idT.setFont(loginF2);
		pwF = new JPasswordField(10);
		pwF.setFont(loginF2);	
		pwCF = new JPasswordField(10);
		pwCF.setFont(loginF2);
		nameT = new JTextField(10);
		nameT.setFont(loginF2);
		tel2T = new JTextField(4);
		tel2T.setFont(loginF2);
		tel3T = new JTextField(4);
		tel3T.setFont(loginF2);
		email1T = new JTextField(6);
		email1T.setFont(loginF2);
		email2T = new JTextField(7);
		email2T.setFont(loginF2);
		emailConfirmT = new JTextField(10);
		emailConfirmT.setFont(loginF2);
		
		idOverlapB = new JButton("중복 확인");
		idOverlapB.setFont(btnF);
		emailConfirmB = new JButton("인증번호 받기");
		emailConfirmB.setFont(btnF);
		registB = new JButton("등록");
		registB.setFont(btnF);
		cancelB = new JButton("취소");
		cancelB.setFont(btnF);
		
		//1-2 라벨부 배치
		labelJP = new JPanel(new GridLayout(7,1,0,10));
		labelJP.setBounds(10, 40, 130, 350);//140,400
		labelJP.add(idL); 	    labelJP.add(pwL);
		labelJP.add(pwCheckL);  labelJP.add(nameL);
		labelJP.add(telL); 	    labelJP.add(email1L);	
		labelJP.add(emailConfirmL);
		con.add(labelJP);
		
		//1-2 상단 4 textField 배치
		textfieldJP1 = new JPanel(new GridLayout(4,1,10,15));
		textfieldJP1.setBounds(140,45,210,190);//350,250
		textfieldJP1.add(idT);  textfieldJP1.add(pwF);
		textfieldJP1.add(pwCF); textfieldJP1.add(nameT);
		con.add(textfieldJP1);
		
		//1-3 전화번호 판넬 배치-flowLayout
		telfieldJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		telfieldJP.setBounds(135, 250, 220, 50);//355,300
		telfieldJP.add(tel1C);  telfieldJP.add(telH1L);
		telfieldJP.add(tel2T);  telfieldJP.add(telH2L); 
		telfieldJP.add(tel3T);
		con.add(telfieldJP);
		
		//1-4 이메일 판넬 배치-flowLayout
		emailfieldJP = new JPanel(new FlowLayout());		
		emailfieldJP.setBounds(135, 300, 220, 50);//355,350
		emailfieldJP.add(email1T);
		emailfieldJP.add(email2L);
		emailfieldJP.add(email2T);
		con.add(emailfieldJP);
		
		//1-5 이메일 확인 판넬 배치
		emailCfieldJP = new JPanel(new GridLayout(1,1,0,0));	
		emailCfieldJP.setBounds(140,350,210,35);//350,385
		emailCfieldJP.add(emailConfirmT);
		con.add(emailCfieldJP);
		
		//1-6 등록, 취소 버튼 배치
		confirmBtnJP = new JPanel(new GridLayout(1,2,30,10));
		confirmBtnJP.setBounds(100,400,300,50);//400,450
		confirmBtnJP.add(registB);	confirmBtnJP.add(cancelB);
		con.add(confirmBtnJP);
		
		//1-7 직접 배치된 버튼
		idOverlapB.setBounds(360, 50, 110, 30);//470,80
		emailC.setBounds(360, 300, 120, 30);//480,330
		emailConfirmB.setBounds(360, 350, 120, 30);//480,380
		con.add(emailC);	
		con.add(idOverlapB);
		con.add(emailConfirmB);

		//2. Frame 설정
		setTitle("회원 가입");
		setBounds(600, 200, 500, 500);
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			@Override
			public void	windowClosing(WindowEvent e){
				dispose();
			}
		});
		
		//3. 이벤트
		idOverlapB.addActionListener(this);
		emailConfirmB.addActionListener(this);
		registB.addActionListener(this);
		cancelB.addActionListener(this);
		emailC.addActionListener(this);
		
		//3-1. 아이디 ,pw ,전화번호  텍스트 입력 제한 
		idT.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField id = (JTextField) e.getSource();
				if(id.getText().length()>=10) e.consume();
				
			}
		});
		pwF.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField pw = (JTextField) e.getSource();
				if(pw.getText().length()>=12) e.consume();
				
			}
		});
		pwCF.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField pw = (JTextField) e.getSource();
				if(pw.getText().length()>=12) e.consume();
				
				
			}
		});
		tel2T.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField tel = (JTextField) e.getSource();
				if(tel.getText().length()>=3) {
					tel3T.requestFocus();
					
				}
				
			}
		});
		tel3T.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField tel = (JTextField) e.getSource();
				if(tel.getText().length()>=4) e.consume();
				
				
			}
		});
		
	}//Join()-생성자

	@Override
	public void actionPerformed(ActionEvent e) {
		String naver = "naver.com";
		String google = "google.com";
		String userEmail = email1T.getText()+"@"+email2T.getText();
		
		
		if(idOverlapB==e.getSource()) {
			idCheck();
			
		}else if(emailConfirmB==e.getSource()) {
			checkNum = new MailSend().NaverMailSend(userEmail);//유저의 이메일을 MailSend클래스로 보내고 인증번호를 가져옴
			JOptionPane.showMessageDialog(this, "인증 번호가 발송되었습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
			emailCheck = 1;//인증번호를발송받았는지 확인
			
		}
		else if(registB==e.getSource()) regist();
		else if(cancelB==e.getSource()) dispose();
		
		//이메일 콤보박스 선택시 텍스트창에 주소 설정
		else if(emailC==e.getSource()) {
			if(emailC.getSelectedItem()==naver)
				email2T.setText(naver);
			else if(emailC.getSelectedItem()==google)
				email2T.setText(google);
			else {
				email2T.setText("");
				email2T.requestFocus();
			}
		}	
	}//actionPerformed
	
	//id 체크 후 결과값 받아서 0 == 사용가능, 1==사용중
	public void idCheck() {
		String id = idT.getText();
		if(id.equals("")) {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);	
		}else {
			MemberDAO memberDAO = new MemberDAO();
			int result = memberDAO.idCheck(id);
			if(result == 0) {
				JOptionPane.showMessageDialog(this, "사용가능한 아이디 입니다", "정보", JOptionPane.INFORMATION_MESSAGE);
				idCheck=1;//ID중복확인버튼이 눌렸는지 확인*****************************
			}else if(result == 1) {
				JOptionPane.showMessageDialog(this, "사용중인 아이디 입니다", "정보", JOptionPane.INFORMATION_MESSAGE);
				idT.setText("");
				idCheck=0;//ID중복확인버튼이 눌렸는지 확인*****************************
			}
		}
	}//idcheck  method

	
	public void regist() {
		String id = idT.getText();
		String pw="";
		//패스워드 필드의 비밀번호 꺼내오는 로직
		char[] secret_pw = pwF.getPassword();
		for(char sPW : secret_pw){ 
			Character.toString(sPW);
			pw += sPW;
		}
		String pw2="";
		char[] secret_pw2 = pwCF.getPassword();
		for(char sPW : secret_pw2){ 
			Character.toString(sPW);
			pw2 += sPW;
		}
		String name = nameT.getText();
		String tel1 = (String)tel1C.getSelectedItem();
		String tel2 = tel2T.getText();
		String tel3 = tel3T.getText();
		String emaiL1 = email1T.getText();
		String email2 = email2T.getText(); 
		
		//회원가입창에 입력값이 없을때 다이얼로그 출력
		if(id.equals(""))
			JOptionPane.showMessageDialog(this, "아이디를 입력하십시오", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(idCheck!=1)
			JOptionPane.showMessageDialog(this, "아이디 중복체크 해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(pw.equals(""))
			JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(pw2.equals(""))
			JOptionPane.showMessageDialog(this, "비밀번호 확인을 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(name.equals(""))
			JOptionPane.showMessageDialog(this, "이름을 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(tel2.equals("") || tel3.equals(""))
			JOptionPane.showMessageDialog(this, "휴대전화정보를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(emaiL1.equals("") || email2.equals(""))
			JOptionPane.showMessageDialog(this, "이메일을 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(emailCheck!=1)
			JOptionPane.showMessageDialog(this, "이메일을 인증해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		else if(!checkNum.equals(emailConfirmT.getText()))
			JOptionPane.showMessageDialog(this, "인증번호가 틀렸습니다, 다시 입력해 주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		//모두 입력이 되었다면 등록 시작
		else {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId(id);
			memberDTO.setPw(pw);
			memberDTO.setUserName(name);
			memberDTO.setTel1(tel1);
			memberDTO.setTel2(tel2);
			memberDTO.setTel3(tel3);
			memberDTO.setEmaiL1(emaiL1);
			memberDTO.setEmail2(email2);		
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.regist(memberDTO);
			
			JOptionPane.showMessageDialog(this, "회원가입 되었습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}

		
	}//resige method

}//join class

