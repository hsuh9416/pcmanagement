package mgr;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import member.dao.MemberDAO;

@SuppressWarnings("serial")
public class LoginMgrFrame extends JFrame implements ActionListener {
 	private Font titleF,loginF;	
 	private JButton loginB;
 	private JLabel idL,pwdL,titleL,mgrL;
 	private JPanel welcomeJP;
 	private JPasswordField pwdT;
	private JTextField idT;
 	public LoginMgrFrame() {
 		Container con = getContentPane();
 		con.setLayout(null);	
 		
		//0. 타이틀 메세지 영역
 		titleF = new Font("Calibri Light",Font.ITALIC+Font.BOLD, 40);
 		welcomeJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
 		titleL = new JLabel("***PC Cafe Program***");
 		titleL.setFont(titleF);
 		titleL.setForeground(Color.WHITE);
 		welcomeJP.setBounds(0,80,500,60);
 		welcomeJP.add(titleL);
 		welcomeJP.setOpaque(false);
 		con.add(welcomeJP);
 		loginF = new Font("Bahnschrift Light Condensed",Font.BOLD, 20);
 		mgrL = new JLabel("<관리자 전용>");
 		mgrL.setBounds(180,160,150, 30);
 		mgrL.setForeground(Color.RED);
 		mgrL.setFont(loginF);
 		con.add(mgrL);
		//1. 로그인 영역
		//1-1. 요소 선언(instance declare) + FONT 설정	
 		idL = new JLabel("아이디");
 		idL.setFont(loginF);
 		idL.setBounds(125,250,100,50);//225,300
 		pwdL = new JLabel("비밀번호");
 		pwdL.setFont(loginF);
 		pwdL.setBounds(125,320,150,50);//275,370		
 		
 		idT = new JTextField(10);
 		idT.setFont(loginF);	
 		idT.setBorder(null);		
		idT.setBounds(255,255,150,40);//405,295		
 		pwdT = new JPasswordField();
 		pwdT.setFont(loginF);
 		pwdT.setBorder(null);
 		pwdT.setBounds(255,325,150,40);		
 		
		BevelBorder bb = new BevelBorder(BevelBorder.RAISED);
		loginB = new JButton("로그인");
		loginB.setBorder(bb);
		loginB.setBounds(180,430,140,60);
		loginB.addActionListener(this);//이벤트
		loginB.setFont(loginF);
		
		//1-2. 콘테이너에 부착
 		con.add(idL);
 		con.add(idT);
 		con.add(pwdL);
 		con.add(pwdT);
		con.add(loginB);	

 		//2. Frame 설정
 		setUndecorated(true);//투명창 만들기
 		setTitle("PC Cafe Program");
 		setBounds(1200,50,500,600);
 		setResizable(false);
 		setVisible(true);
 		setDefaultCloseOperation(EXIT_ON_CLOSE);
 		
 		//이벤트
 		idT.addActionListener(this);
 		pwdT.addActionListener(this);
 	}
 	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String id = idT.getText();
		String pw = "";
		//JPasswordFiled 값 가져오기
		char[] secret_pw = pwdT.getPassword();
		for(char sPW : secret_pw){ 
			Character.toString(sPW);
			pw += sPW;
		}
		
		
		if(id.equals("")){//아이디텍스트 입력값 없을시
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		}else if(pw.equals("")) {//비밀번호 텍스트 입력값 없을 시 
			JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "정보", JOptionPane.INFORMATION_MESSAGE);
		}else if(!id.equals("admin")) {//관리자 계정 아닐시
			JOptionPane.showMessageDialog(this, "관리자 계정이 아닙니다", "정보", JOptionPane.INFORMATION_MESSAGE);
		}else {
			MemberDAO memberDAO = MemberDAO.getInstance();
			int result = memberDAO.login(id, pw);//로그인성공=1,실패=0;
			if(result==1) {
				dispose();
				new WorkingMgrFrame();				
				new MainServer();
			}else if(result==0) {//로그인실패시 알림메세지
				JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호를 다시 확인해주세요.", "정보", JOptionPane.INFORMATION_MESSAGE);
			}				
		}
		
	}
}
