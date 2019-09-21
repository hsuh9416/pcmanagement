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
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class ErrorFrame extends JFrame implements ActionListener {
 	private Font errorF,confirmF;	
 	private JButton confirmB;
 	private JLabel errorL,mgrL;
 	private JPanel welcomeJP;
 	public ErrorFrame(String errorStr) {
 		Container con = getContentPane();
 		con.setLayout(null);	
 		
		//0. 물품 입력을 잘못했을때 경고창
 		errorF = new Font("Calibri Light",Font.ITALIC+Font.BOLD, 40);
 		welcomeJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
 		errorL = new JLabel("Error Occured!!");
 		errorL.setFont(errorF);
 		errorL.setForeground(Color.WHITE);
 		welcomeJP.setBounds(-50,80,500,60);
 		welcomeJP.add(errorL);
 		welcomeJP.setOpaque(false);
 		con.add(welcomeJP);
 		confirmF = new Font("Bahnschrift Light Condensed",Font.BOLD, 20);
 		mgrL = new JLabel(errorStr);
 		mgrL.setBounds(125,160,500, 30);
 		mgrL.setForeground(Color.RED);
 		mgrL.setFont(confirmF);
 		con.add(mgrL);	
 		
 		//버튼 생성
		BevelBorder bb = new BevelBorder(BevelBorder.RAISED);
		confirmB = new JButton("확인");
		confirmB.setBorder(bb);
		confirmB.setBounds(100,250,100,60);
		confirmB.addActionListener(this);
		confirmB.setFont(confirmF);
		
		con.add(confirmB);	

		//화면 생성
 		setUndecorated(true);
 		setTitle("Error");
 		setBounds(600,200,600,600);
 		setResizable(false);
 		setVisible(true);
 		setDefaultCloseOperation(EXIT_ON_CLOSE);
 	}
 	
	@Override
	public void actionPerformed(ActionEvent e) {
			this.dispose();
	}
}