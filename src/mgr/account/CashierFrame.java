package mgr.account;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import member.bean.MemberDTO;
import member.dao.MemberDAO;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoDTO;
import mgr.sales.SalesInfoSel;
//고객 정산 class
@SuppressWarnings("serial")
public class CashierFrame extends JFrame implements ActionListener {
	private Container con;
	private DefaultListModel<SalesInfoDTO> model;
	private Font labelF,buttonF,textF,textFieldF;
	private JButton payAllB,payPCB,payItemB,cancelB;//전체 결제, pc방요금만 결제,선결제
	private JLabel wonL,itemPaymentL,pcPaymentL, totPaymentL, leftPaymentL;
	private JTextField itemPaymentT,pcPaymentT, totPaymentT, leftPaymentT; 
	private JPanel wonJP,paymentLJP,paymentTJP,buttonJP;
	private JList<SalesInfoDTO> list;
	private JScrollPane scroll;
	private MemberDTO memberDTO;//임시
	public CashierFrame(MemberDTO memberDTO) {//리스트에서 생성될 때 활성화 되며, 그 MemberDTO의 정보를 가져오게 된다.
		this.memberDTO = memberDTO;
		con = getContentPane();
		con.setLayout(null);
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		
		//1.COMPONENT 생성
 		labelF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
 		buttonF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
 		textF = new Font("Bahnschrift Light Condensed",Font.PLAIN,18);
 		textFieldF = new Font("Bahnschrift Light Condensed",Font.PLAIN,18);
 		
 			//1-1 목록 작성
 		model = new DefaultListModel<SalesInfoDTO>();
 		list = new JList<SalesInfoDTO>(model);
 		list.setFont(textFieldF);
			
 			//1-2 라벨부 작성 	
 		wonL = new JLabel("(단위:   원)",SwingConstants.RIGHT);
 		wonL.setFont(labelF);
 		itemPaymentL = new JLabel("물품 구매액",SwingConstants.CENTER);
 		itemPaymentL.setFont(labelF);
 		pcPaymentL = new JLabel("PC방 사용료",SwingConstants.CENTER);
 		pcPaymentL.setFont(labelF);
 		totPaymentL = new JLabel("총 금액",SwingConstants.CENTER);
 		totPaymentL.setFont(labelF);
 		leftPaymentL = new JLabel("지불하실 금액",SwingConstants.CENTER);
 		leftPaymentL.setFont(labelF);
 		
			//1-3 텍스트부 작성 	 	
 		itemPaymentT = new JTextField();
 		itemPaymentT.setFont(textF);
 		int boughtItem =dao.getItemSum(memberDTO,true);
 		itemPaymentT.setText(boughtItem+"");
 		itemPaymentT.setEditable(false);
 		pcPaymentT = new JTextField();
 		pcPaymentT.setFont(textF);
 		int pcPay = memberDTO.getPostPayment();
 		pcPaymentT.setText(pcPay+"");
 		pcPaymentT.setEditable(false);
 		totPaymentT = new JTextField();
 		totPaymentT.setFont(textF);
 		int totPay = boughtItem + pcPay;
 		totPaymentT.setText(totPay+"");
 		totPaymentT.setEditable(false);
 		
 		leftPaymentT = new JTextField();
 		leftPaymentT.setFont(textF);
 		int unPaidItem =dao.getItemSum(memberDTO,false);
 		int unPaidTotal = pcPay + unPaidItem;
 		leftPaymentT.setText(unPaidTotal+"");
 		leftPaymentT.setEditable(false);
 		
 			//1-4 버튼부 작성 	 
 		payAllB = new JButton("전부결제");
 		payAllB.setFont(buttonF);
 		payPCB = new JButton("PC요금결제");//server가 종료된 경우에는 비활성화
 		payPCB.setFont(buttonF);
 		payItemB = new JButton("물품결제");//구입한 물품이 없으면 비활성화, server가 종료된 경우도 비활성화
 		payItemB.setFont(buttonF);		
 		cancelB = new JButton("돌아가기");
 		cancelB.setFont(buttonF);
 		
 		//2-1. JList부분 배치+목록 불러오기
 		scroll = new JScrollPane(list);
 		scroll.setBounds(10,10,530,250);//540,260
 		con.add(scroll);
 				
 		ArrayList<SalesInfoDTO> uPurchaselist = dao.getPersonPurchase(memberDTO);//매입항목 불러오기
 				
 		for(SalesInfoDTO dto : uPurchaselist) {
 			model.addElement(dto);}//매입되지 않은 경우에만 추가
 		
 		//2-2. 라벨부 배치
 		wonJP = new JPanel(new GridLayout(1,1,10,10));
 		wonJP.setBounds(420,280,80,50);//500,330
 		wonJP.add(wonL);
 		con.add(wonJP);
 		
 		paymentLJP= new JPanel(new GridLayout(1,4,10,10));
 		paymentLJP.setBounds(10,330,530,50);//540,380
 		paymentLJP.add(itemPaymentL); 		paymentLJP.add(pcPaymentL);
 		paymentLJP.add(totPaymentL); 		paymentLJP.add(leftPaymentL);
 		con.add(paymentLJP);
 		
 		//2-3. 텍스트부 배치 		
 		paymentTJP= new JPanel(new GridLayout(1,4,10,10));
 		paymentTJP.setBounds(10,380,530,50);//540,420
 		paymentTJP.add(itemPaymentT); 		paymentTJP.add(pcPaymentT);
 		paymentTJP.add(totPaymentT); 		paymentTJP.add(leftPaymentT);	
 		con.add(paymentTJP);
 		
 		//2-3. 버튼부 배치 	 		
 		buttonJP= new JPanel(new GridLayout(1,4,10,10));
 		buttonJP.setBounds(10,450,530,50);//540,500
 		buttonJP.add(payAllB); 		buttonJP.add(payPCB); 		buttonJP.add(payItemB);
 		buttonJP.add(cancelB);		
 		con.add(buttonJP);
 		
 		//3. Frame 설정
 		setUndecorated(true);//투명창 만들기
 		setTitle("요금 계산");
 		setBounds(450,100,550,550);
 		setResizable(false);
 		setVisible(true);
 		
 		//4. event 설정
 		payAllB.addActionListener(this);	payPCB.addActionListener(this);
 		payItemB.addActionListener(this);   cancelB.addActionListener(this);   
 		
 		//5. 상태 설정(시스템이 상시로 체크하고 돌린다.)
 		Thread t = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(memberDTO.getPostPayment()==0 || model.isEmpty()) payAllB.setEnabled(false);//둘 중 하나만 지불시에는 사용 안함
					else payAllB.setEnabled(true);
					if(memberDTO.getPostPayment()==0) {payPCB.setEnabled(false);}
					else {payPCB.setEnabled(true);}
					if(model.isEmpty()) payItemB.setEnabled(false);//매입한 것이 없으면 사용 안함.		
				 	else payItemB.setEnabled(true);
				}//while
			}//run();
		};//
		t.start();
 				
		
	}//CashierFrame() 생성자

	@Override
	public void actionPerformed(ActionEvent e) {
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		int uPay = Integer.parseInt(leftPaymentT.getText());
		String payMsg;
		int ruPaying;
		if(e.getSource()== payAllB) {
			payMsg = "(총) "+uPay+"(원)입니다. 지금 현금 지불하시겠습니까?";
			ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[현금 지불]",JOptionPane.OK_CANCEL_OPTION);
			if(ruPaying==JOptionPane.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(this,"결제 화면으로 돌아갑니다.","[지불 취소]",JOptionPane.INFORMATION_MESSAGE);
				return;}
			JOptionPane.showMessageDialog(this,"저희 ** PC방을 이용해주셔서 감사드립니다. 또 들려주세요~","[지불 완료]",JOptionPane.INFORMATION_MESSAGE);
			dao.getPaid(memberDTO, SalesInfoSel.TOTALPAY);
			memberDTO.setPostPayment(0); dispose();}//payAll:전부 결제
	
		else if(e.getSource() ==payPCB) {
			payMsg = "(총) "+uPay+"(원)입니다. 지금 현금 지불하시겠습니까?";
			ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[현금 지불]",JOptionPane.OK_CANCEL_OPTION);
			if(ruPaying==JOptionPane.CANCEL_OPTION) {
				JOptionPane.showMessageDialog(this,"결제 화면으로 돌아갑니다.","[지불 취소]",JOptionPane.INFORMATION_MESSAGE);
				return;}
			JOptionPane.showMessageDialog(this,"저희 ** PC방을 이용해주셔서 감사드립니다. 또 들려주세요~","[지불 완료]",JOptionPane.INFORMATION_MESSAGE);
			dao.getPaid(memberDTO, SalesInfoSel.PCPAY);
			memberDTO.setPostPayment(0); dispose();}//payPC : PC 요금 결제
	
		else if(e.getSource() ==payItemB) {
			if(memberDTO.getTime()*25>= uPay){
				payMsg = "선불금으로 지불하시겠습니까?";
				ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[선불금 사용]",JOptionPane.OK_CANCEL_OPTION);
				if(ruPaying==JOptionPane.OK_OPTION) {
					int time = (memberDTO.getTime()*25 - uPay)/25;
					memberDTO.setTime(time);
					MemberDAO memberDAO = MemberDAO.getInstance();
					memberDAO.setTime(time, memberDTO.getId());
				}
				if(ruPaying==JOptionPane.CANCEL_OPTION) {
					payMsg = "(총) "+uPay+"(원)입니다. 지금 현금 지불하시겠습니까?";
					ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[현금 지불]",JOptionPane.OK_CANCEL_OPTION);
					if(ruPaying==JOptionPane.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(this,"결제 화면으로 돌아갑니다.","[지불 취소]",JOptionPane.INFORMATION_MESSAGE);
						return;}}
				}//선불금이 물품비용보다 큰 경우에만 결제 가능
				
			else {
				payMsg = "(총) "+uPay+"(원)입니다. 지금 현금 지불하시겠습니까?";
				ruPaying = JOptionPane.showConfirmDialog(this,payMsg,"[현금 지불]",JOptionPane.OK_CANCEL_OPTION);
				if(ruPaying==JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(this,"결제 화면으로 돌아갑니다.","[지불 취소]",JOptionPane.INFORMATION_MESSAGE);
					return;}}
			JOptionPane.showMessageDialog(this,"저희 ** PC방을 이용해주셔서 감사드립니다. 또 들려주세요~","[지불 완료]",JOptionPane.INFORMATION_MESSAGE);
			dao.getPaid(memberDTO,SalesInfoSel.ITEMPAY);  dispose();}//payItem : Item 결제
		
		else if(e.getSource() ==cancelB) {dispose();}
			
		
	}//actionPerformed 메소드

	
}//CashierFrame class
