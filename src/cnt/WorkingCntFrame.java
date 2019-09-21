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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import cnt.CntServer;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import mgr.InfoDTO;

@SuppressWarnings("serial")
public class WorkingCntFrame extends JFrame implements ActionListener,Runnable {
	private JButton messageB,orderB,stopB,exitB;
	private JLabel uTimeL1,uTimeL2,prepayMntL1,prepayMntL2;
	private JPanel buttonJP,titleJP,uTimeJP;
	private Font titleF,labelF;
	private Container titleL;
	private String userID;
	private String userName;
	private OrderMenu order;
	private ClientChat chat;
	private int stopSW; 
	private Thread t;
	private MemberDTO memberDTO;
	private int fixTime;
	
	public WorkingCntFrame(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
		this.userID  = memberDTO.getId();
		this.userName = memberDTO.getUserName();
		fixTime = memberDTO.getTime();
		
		Container con = getContentPane();
		con.setLayout(null);
		//0. 타이틀 메세지 영역
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(10,0,750,50);
		titleF = new Font("Bahnschrift Light Condensed",Font.BOLD,20);
		titleL = new JLabel("["+userName+"]님 안녕하세요. **** PC방에 오신 것을 진심으로 환영합니다.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		titleJP.add(titleL);
		titleJP.setOpaque(false);
 		con.add(titleJP);
 		
		//1-1. 요소 선언(instance declare) + FONT 설정	
 		
 		
 		labelF = new Font("Bahnschrift Light Condensed",Font.BOLD,18);
 		uTimeL1 = new JLabel("남은 시간");
 		uTimeL1.setFont(labelF);
 		
		int time = memberDTO.getTime();
		int minute = 0;
		if(time>=60) {//db에 저장된 분단위의 값을 시간으로 변경
			minute = time%60;
			time /= 60;
			uTimeL2 = new JLabel(time+"시간 "+minute+"분",SwingConstants.RIGHT);}
		else {
			minute = time;
			uTimeL2 = new JLabel(minute+"분",SwingConstants.RIGHT);}
 		uTimeL2.setFont(labelF);
 		
 		
 		prepayMntL1 = new JLabel("선불 잔액");
 		prepayMntL1.setFont(labelF);
 		prepayMntL2 = new JLabel(fixTime*25+"원",SwingConstants.RIGHT);
 		prepayMntL2.setFont(labelF);
 		messageB = new JButton(new ImageIcon("src/lib/messa.png"));
		messageB.setBorderPainted(false);
		messageB.setFocusPainted(false);
		messageB.setContentAreaFilled(false);
		
		orderB = new JButton(new ImageIcon("src/lib/foods.png"));
		orderB.setBorderPainted(false);
		orderB.setFocusPainted(false);
		orderB.setContentAreaFilled(false);
		
	
		stopB = new JButton(new ImageIcon("src/lib/chains.png"));
		stopB.setOpaque(true);
		stopB.setBorderPainted(false);
		stopB.setFocusPainted(false);
		stopB.setContentAreaFilled(false);
		
		exitB = new JButton(new ImageIcon("src/lib/stop.png"));
		exitB.setBorderPainted(false);
		exitB.setFocusPainted(false);
		exitB.setContentAreaFilled(false);
		

		//1-2.모니터 구역(모니터의 숫자 표시)
		//JPanel compNumJP = new JPanel();
		JLabel compNumJP = new JLabel(memberDTO.getSeat()+"",SwingConstants.CENTER);
		//JLabel seatL = new JLabel("좌석번호 : "+memberDTO.getSeat());
		compNumJP.setFont(new Font("Bahnschrift Light Condensed",Font.BOLD,120));
		Font nameLF = new Font("Bahnschrift Light Condensed",Font.BOLD,20);
		JLabel nameL1 = new JLabel("I 		           D",SwingConstants.CENTER);
		JLabel nameL2 = new JLabel(memberDTO.getUserName(),SwingConstants.CENTER);
		nameL1.setFont(nameLF); 	nameL2.setFont(nameLF);
		compNumJP.setLayout(new GridLayout(1,1,5,5));
		compNumJP.setBounds(20,50,200,200);//220,250
		//compNumJP.add(seatL);;
		LineBorder lb = new LineBorder(Color.GREEN,5);
		compNumJP.setBorder(lb);
		con.add(compNumJP);
		
		//1-3. 요금 정산 구역(판넬 통합, 선불요금 추가)
		uTimeJP = new JPanel(new GridLayout(3,2,20,20));
		uTimeJP.setBounds(250,50,260,200);//520,250
		uTimeJP.add(uTimeL1); 	  uTimeJP.add(uTimeL2);
		uTimeJP.add(prepayMntL1); uTimeJP.add(prepayMntL2);
		uTimeJP.add(nameL1);		  uTimeJP.add(nameL2);
		con.add(uTimeJP);
		
		
		//1-4. 버튼 구역
		buttonJP = new JPanel(new GridLayout(2,2,20,20));
		buttonJP.setBounds(520,50,260,200);//780,250
		buttonJP.add(messageB);		buttonJP.add(orderB);
		buttonJP.add(stopB);		buttonJP.add(exitB);
		con.add(buttonJP);
		
		//사용시간 Thread 시작
		t = new Thread(this);
		t.start();
				
		
 		//2. Frame 설정
 		setTitle("PC Cafe Program");
 		setBounds(450,100,800,300);
 		setResizable(false);
 		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
 		//3. event 설정
 		messageB.addActionListener(this);
 		orderB.addActionListener(this);
 		stopB.addActionListener(this);
 		exitB.addActionListener(this);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == messageB) {
			if (chat == null) new ClientChat(memberDTO);
			else {chat.dispose(); chat = new ClientChat(memberDTO);}}
		if(e.getSource()==orderB) {
			if(order == null) order = new OrderMenu(memberDTO);
			else {order.dispose(); order = new OrderMenu(memberDTO);}}//중복창 금지(단점: 창이 리뉴얼됨)
		if(e.getSource()== stopB){ // 유열씨 메소드
			if(stopSW==0) {//stopSW기본값0, 처음 눌렸을 때 작동
				JOptionPane.showMessageDialog(this, "일시 정지 되었습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
				stopSW=1;//정지버튼이 눌렸다는 표시 =1;
				stopB.setIcon(new ImageIcon("lib/chains1.png"));
				messageB.setEnabled(false);
				orderB.setEnabled(false);
				try {//인터럽트로 중지
					t.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					t.interrupt();//인터럽트 강제 발생
				}
			
			}else if(stopSW==1) {
				JOptionPane.showMessageDialog(this, "일시 정지가 해제되었습니다", "정보", JOptionPane.INFORMATION_MESSAGE);
				stopSW=0;
				stopB.setIcon(new ImageIcon("lib/chains.png"));
				messageB.setEnabled(true);
				orderB.setEnabled(true);
				
			}
		}
		if(e.getSource()== exitB) {//화종씨 구현
			int result = JOptionPane.showConfirmDialog(this,"정말 종료하시겠습니까?","[PC 사용 종료]",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION){
				MemberDAO dao = MemberDAO.getInstance();
				dao.setOff(memberDTO.getId());
				dao.setSeatOut(userID);//좌석 정보를 먼저 발송한 후 메세지전송
				CntServer cnt = CntServer.getInstance(memberDTO);
				InfoDTO infoDTO = new InfoDTO();
				infoDTO.setUserName(userName);
				infoDTO.setUserID(userID);
				infoDTO.setUserTime(memberDTO.getTime());
				infoDTO.setTotCost(memberDTO.getPostPayment());
				cnt.getExit(infoDTO);

				this.dispose();
				new LoginFrame();
			}
			else return;			
		}
		
	}//actionPerformed 메소드
	
	@Override
	public void run() {
		int time = fixTime;
		int minute =0;
		int sw=0;
		if (time<60) {
			
			minute=time;
			time=0;
		}
		
		for(int i=0; i<=fixTime; i++) {//처음 저장시간만큼 돌림
			if(i%4==0) {//5분마다 삭감
				int lefttime = fixTime -i;
				prepayMntL2.setText(lefttime*25+"(원)");
				
			while(true) {//인터럽트가 걸리면 빠져나감, stopSW가0이면 정지버튼이 눌리지 않은것이므로 빠져나감
				if(t.isInterrupted()||stopSW==0)break;

				}//
			}
			
			if(time>=60) {//60분 이상이면 1시간 으로 변경
				minute = time%60;
				time /= 60;
			}else
			
			if(sw==0) {
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.setTime((time*60)+minute, memberDTO.getId());
				if(minute == -1) {//분이 -1 이 되면 59로 변경후 시간 1시간 차감
					time-=1;
					minute= 59;
				}
				if(time==0) {//시간이 없으면 분만 표시
					uTimeL2.setText(minute+"분");
				}else {
					uTimeL2.setText(time+"시간 "+minute+"분");
				}
				sw=1;
				minute-=1;
				
			}else if(sw!=0){
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.setTime((time*60)+minute, memberDTO.getId());
				if(minute == -1) {
					time-=1;
					minute = 59;
				}
				if(time==0) {
					uTimeL2.setText(minute+"분");
				}else {
					uTimeL2.setText(time+"시간 "+minute+"분");
				}
				sw=0;
				minute-=1;
			}

			
			try{
		
				Thread.sleep(900);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}//for
		
		//시간이 다 끝난 후 현재 창 종료 후 로그인창 띄움.
		
		MemberDAO dao = MemberDAO.getInstance();  
		dao.setTime(time, userID);//DB에 아이디와 종료시간 0 을 보내서 update시킴
		dao.setOff(userID);
		dao.setSeatOut(userID);
		
		CntServer cnt = CntServer.getInstance(memberDTO);
		InfoDTO infoDTO = new InfoDTO();
		infoDTO.setUserName(userName);
		infoDTO.setUserID(userID);
		infoDTO.setUserTime(memberDTO.getTime());
		infoDTO.setTotCost(memberDTO.getPostPayment());
		cnt.getExit(infoDTO);
		
		dispose();
		new LoginFrame();
		
	}//run
	
}//WorkingCntFrame Class