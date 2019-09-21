package mgr.account;
//시재관리 및 시재 차이 기록 class
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoDTO;

@SuppressWarnings("serial")
public class CountingMoney extends JFrame implements ActionListener {
	private JButton commitB, cancelB;
	private JLabel titleL,expMntL,realMntL,gapMntL,won1L,won2L,won3L;
	private JPanel titleJP,labelJP,textFieldJP,wonJP,buttonJP;
	private JTextField expMntT,realMntT,gapMntT;
	private Font btnF,mntF,titleF;
	
	public CountingMoney(int ExpDeposit) {
		Container con = getContentPane();
		con.setLayout(null);
		//1.
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD,18);
		mntF = new Font("Bahnschrift Light Condensed",Font.PLAIN, 20);	
		btnF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
		
		titleL = new JLabel("**실재 잔고를 확인하여 입력해주시기 바랍니다.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		
		expMntL = new JLabel("기록된 잔고 : ",SwingConstants.LEFT);
		expMntL.setFont(mntF);
		expMntT = new JTextField();
		expMntT.setText(ExpDeposit+"");
		expMntT.setFont(mntF);
		expMntT.setEditable(false);
		won1L = new JLabel("(원)",SwingConstants.LEFT);
		won1L.setFont(mntF);	
		
		realMntL = new JLabel("실재 잔고 : ");
		realMntL.setFont(mntF);
		realMntT = new JTextField();
		realMntT.setText(0+"");
		realMntT.setFont(mntF);
		won2L = new JLabel("(원)",SwingConstants.LEFT);
		won2L.setFont(mntF);	
		
		gapMntL = new JLabel("잔고 차이 : ",SwingConstants.LEFT);
		gapMntL.setFont(mntF);
		gapMntT = new JTextField();				
		gapMntT.setText("");
		gapMntT.setFont(mntF);
		gapMntT.setEditable(false);
		won3L = new JLabel("(원)",SwingConstants.LEFT);
		won3L.setFont(mntF);
		
		commitB = new JButton("잔고차이 반영");
		commitB.setFont(btnF);
		cancelB = new JButton("돌아가기");
		cancelB.setFont(btnF);
		
		//1-2. title부 부착
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(20,10,460,50);//480,60
		titleJP.add(titleL);
		con.add(titleJP);
		
		//1-3. 라벨부 부착
		labelJP = new JPanel(new GridLayout(3,1,10,5));
		labelJP.setBounds(50,60,200,200);//250,260
		labelJP.add(expMntL);		labelJP.add(realMntL);		labelJP.add(gapMntL);
		con.add(labelJP);
		
		//1-4. 텍스트부 부착
		textFieldJP = new JPanel(new GridLayout(3,1,10,10));
		textFieldJP.setBounds(250,60, 150,200);//400,260
		textFieldJP.add(expMntT);	textFieldJP.add(realMntT);	textFieldJP.add(gapMntT);
		con.add(textFieldJP);
		
		//1-5. 단위부 부착
		wonJP = new JPanel(new GridLayout(3,1,10,10));		
		wonJP.setBounds(420,60,60,200);//480,260
		wonJP.add(won1L);			wonJP.add(won2L);			wonJP.add(won3L);
		con.add(wonJP);
		
		//2. Button 부착
		buttonJP = new JPanel(new GridLayout(1,2,10,10));
		buttonJP.setBounds(120,280,260, 50);//380,200
		buttonJP.add(commitB); buttonJP.add(cancelB);
		con.add(buttonJP);
		
		//3. Frame 설정
		setTitle("[시재 관리/잔고차이 확인]");
		setBounds(600, 200, 500, 400);
		setVisible(true);
		setResizable(false);
		
		//4. event 설정
		commitB.addActionListener(this);
		cancelB.addActionListener(this);
		realMntT.addActionListener(this);
	}//생성자

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelB) dispose();
		else if(e.getSource()==commitB) {
			int option = JOptionPane.showConfirmDialog(this,"시재차이를 반영하시겠습니까?","[최종 확인]",JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				int input;
				try{input = Integer.parseInt(realMntT.getText());}
				catch(NumberFormatException nf) {
					String warning ="[주의]숫자만 입력하셔야만 합니다.";
					JOptionPane.showMessageDialog(this,warning,"[잘못된 입력 알림]",JOptionPane.INFORMATION_MESSAGE);
					realMntT.setText("");
					gapMntT.setText("");
					return;}
				int exp = Integer.parseInt(expMntT.getText());
				int gap = exp - input;
				getBalanced(gap);
				String confirnMsg="[알림] 정상적으로 반영되었습니다.";
				JOptionPane.showMessageDialog(this,confirnMsg,"[시재 차이 반영]",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else return;
		}
		
		if(e.getSource()==realMntT) {
			int real;
			try{real = Integer.parseInt(realMntT.getText());}
			catch(NumberFormatException nf) {
				String warning ="[주의]숫자만 입력하셔야만 합니다.";
				JOptionPane.showMessageDialog(this,warning,"[잘못된 입력 알림]",JOptionPane.INFORMATION_MESSAGE);
				realMntT.setText("");
				gapMntT.setText("");
				return;}
			int exp = Integer.parseInt(expMntT.getText());
			int gap = exp - real;
			gapMntT.setText(gap+"");
			if(gap==0) gapMntT.setForeground(Color.BLACK);//다른 작업 후 복귀시에 적용
			else if(gap<0) gapMntT.setForeground(Color.RED);//마이너스는 빨간색
			else if(gap>0) gapMntT.setForeground(Color.BLUE);//플러스는 파란색
		}//realMntT에 영향을 주었을 때
	}

	public void getBalanced(int gap) {
		int balance = (-1)*gap;
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		int transactionNum= dao.getSeq();//거래번호 획득
		
		SalesInfoDTO salesInfoDTO = new SalesInfoDTO();
		
		salesInfoDTO.setTransactionNum(transactionNum);//거래번호
		salesInfoDTO.setProductNum(9999);//상품번호
		salesInfoDTO.setProductMnt(1);//상품 개수
		salesInfoDTO.setProductName("잔고 차이");//상품이름
		salesInfoDTO.setRevenue(balance);//건당 매상액
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");//오늘날짜 추출+String format
		salesInfoDTO.setSaleDate(sdf.format(today)+"");
		salesInfoDTO.setIsPaid(true);
		salesInfoDTO.setPerchaseID("SYSTEM");
		dao.updatingTransaction(salesInfoDTO);
	}//getBalanced() 메소드 : DB에 반영
}
