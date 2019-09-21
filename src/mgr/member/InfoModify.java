package mgr.member;

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
public class InfoModify extends JFrame implements ActionListener{
	private JLabel idL,pwL, nameL, telL,telH1L,telH2L, email1L,email2L;
	private JTextField idT, nameT, tel2T, tel3T, email1T, email2T ;
	private JPasswordField pwF;
	private JComboBox<String> tel1C;
	private JButton pwResetB, modifyB, cancelB;
	private JPanel titleJP, labelJP;
	private Font titleF,loginF1,loginF2,btnF;
	private JLabel titleL;
	private JPanel textfieldJP1;
	private JPanel telfieldJP;
	private JPanel emailfieldJP;
	private JPanel confirmBtnJP;
	private MemberDTO memberDTO;
	
	public InfoModify(MemberDTO memberDTO) {
		
		Container con = getContentPane();	
		con.setLayout(null);
		
		//0. 타이틀 메세지 영역
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(0,0,500,50);
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD,15);
		titleL = new JLabel("***수정하실 회원님의 [정보]를 정확하게 입력해주세요.");
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
		
		idL = new JLabel("I                D");
		idL.setFont(loginF1);
		pwL = new JLabel("PASSWORD");
		pwL.setFont(loginF1);
		nameL = new JLabel("이              름");
		nameL.setFont(loginF1);		
		telL = new JLabel("휴  대   전  화");
		telL.setFont(loginF1);
		telH1L = new JLabel("-");
		telH2L = new JLabel("-");
		email1L = new JLabel("E - MAIL");
		email1L.setFont(loginF1);
		email2L = new JLabel("@");

		tel1C = new JComboBox<String>(telCombo);
		tel1C.setFont(loginF1);

		idT = new JTextField(10);
		idT.setFont(loginF2);
		idT.setEnabled(false);
		pwF = new JPasswordField(10);
		pwF.setFont(loginF2);	
		pwF.setEnabled(false);
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

		pwResetB = new JButton("PW 초기화");
		pwResetB.setFont(btnF);
		modifyB = new JButton("수정");
		modifyB.setFont(btnF);
		cancelB = new JButton("취소");
		cancelB.setFont(btnF);
		
		//1-2 라벨부 배치
		labelJP = new JPanel(new GridLayout(5,1,0,10));
		labelJP.setBounds(10, 40, 130, 350);//140,400
		labelJP.add(idL); 	    labelJP.add(pwL);
		labelJP.add(nameL);		labelJP.add(telL); 	    
		labelJP.add(email1L);	
		con.add(labelJP);
		
		//1-2 상단 4 textField 배치
		textfieldJP1 = new JPanel(new GridLayout(3,1,10,35));
		textfieldJP1.setBounds(140,40,210,200);//350,250
		textfieldJP1.add(idT);  textfieldJP1.add(pwF);
	    textfieldJP1.add(nameT); 
		con.add(textfieldJP1);
		
		//1-3 전화번호 판넬 배치-flowLayout
		telfieldJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		telfieldJP.setBounds(135, 265, 225, 50);
		telfieldJP.add(tel1C);  telfieldJP.add(telH1L);
		telfieldJP.add(tel2T);  telfieldJP.add(telH2L); 
		telfieldJP.add(tel3T);
		con.add(telfieldJP);
		
		//1-4 이메일 판넬 배치-flowLayout
		emailfieldJP = new JPanel(new FlowLayout());		
		emailfieldJP.setBounds(135, 340, 220, 50);//355,350
		emailfieldJP.add(email1T);
		emailfieldJP.add(email2L);
		emailfieldJP.add(email2T);
		con.add(emailfieldJP);
		
		
		//1-6 등록, 취소 버튼 배치
		confirmBtnJP = new JPanel(new GridLayout(1,2,30,10));
		confirmBtnJP.setBounds(100,400,300,50);//400,450
		confirmBtnJP.add(modifyB);	confirmBtnJP.add(cancelB);
		con.add(confirmBtnJP);

		//2. Frame 설정
		setTitle("정보 수정");
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
		
		pwResetB.addActionListener(this);
		modifyB.addActionListener(this);
		cancelB.addActionListener(this);
		
		//3-1.이름, 전화번호  텍스트 입력 제한 
		nameT.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField tel = (JTextField) e.getSource();
				if(tel.getText().length()>=5) e.consume();
				
				
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
		
		//actionPerformed에서 사용해야되서 필드로 올림
		this.memberDTO = memberDTO;
		//선택된 정보를 뿌려줌
		idT.setText(memberDTO.getId());
		nameT.setText(memberDTO.getUserName());
		tel1C.setSelectedItem(memberDTO.getTel1());
		tel2T.setText(memberDTO.getTel2());
		tel3T.setText(memberDTO.getTel3());
		email1T.setText(memberDTO.getEmaiL1());
		email2T.setText(memberDTO.getEmail2());

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(modifyB == e.getSource()) {
			int result = JOptionPane.showConfirmDialog(this, "정말 수정하시겠습니까?", "정보", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setId(this.memberDTO.getId());
				memberDTO.setUserName(nameT.getText());
				memberDTO.setTel1((String)tel1C.getSelectedItem());
				memberDTO.setTel2(tel2T.getText());
				memberDTO.setTel3(tel3T.getText());
				memberDTO.setEmaiL1(email1T.getText());
				memberDTO.setEmail2(email2T.getText());
				
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.infoModify(memberDTO);
				JOptionPane.showMessageDialog(this, "수정이 완료되었습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
				
				dispose();
				new ClientMgtFrame();//jtable에 표시될 목록을 새로뜨게 하기위해서 종료후 새창 띄워줌
				
			}
		}else if(cancelB == e. getSource()){
			dispose();
			new ClientMgtFrame();
		}
		
	}

}
