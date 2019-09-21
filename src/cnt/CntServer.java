package cnt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import member.bean.MemberDTO;
import mgr.InfoDTO;
import mgr.InfoMsg;
import mgr.inventory.InventoryDAO;

@SuppressWarnings("serial")
public class CntServer extends JPanel implements Runnable {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String serverIP="172.30.1.6";//각 pc마다 바뀔 서버 IP
	private String userID;//유저ID
	private String userName;

	private static ArrayList<CntServer> monitorList = new ArrayList<CntServer>();
	
	public static CntServer getlogin(MemberDTO memberDTO){
		CntServer monitor=null;
		monitor = new CntServer(memberDTO); 
		monitorList.add(monitor);
		new WorkingCntFrame(memberDTO);//로그인화면 오픈
		return monitor;
	}//생성 제약자
	
	public static CntServer getInstance(MemberDTO dto) {
		CntServer cnt = null;
		for(CntServer user : monitorList) {if(user.userID.equals(dto.getId())) cnt = user;}
		return cnt;
	}//'THE' CntServer 호출
	
	public CntServer(MemberDTO memberDTO) {
		MemberDTO memberdto = memberDTO;
		userID = memberdto.getId();
		userName = memberdto.getUserName();
	}//기본 생성자
	
	public void service(){		
		try{
			socket = new Socket(serverIP, 9555);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

		}catch(UnknownHostException e){
			System.out.println("서버를 찾을 수 없습니다");
			e.printStackTrace();
			System.exit(0);
		}catch(IOException e){
			System.out.println("서버와 연결이 안되었습니다");
			e.printStackTrace();
			System.exit(0);
		}

		Thread t = new Thread(this);//스레드 생성
		t.start();//스레드 시작

	}//service()

	public void getOrder(InfoDTO infodto) {
		InfoDTO dto = infodto;
		dto.setUserID(userID);
		dto.setUserName(userName);
		dto.setCommand(InfoMsg.ORDER);
		try{
			oos.writeObject(dto);
			oos.flush();
		}catch(IOException io){
			io.printStackTrace();
		}
	}//getOrder() 메소드 : PC자리와 회원/비회원 여부를 추가해서 managerServer로 전송한다.
	
	public void getExit(InfoDTO infodto) {
		InfoDTO dto = infodto;
		dto.setUserID(userID);
		dto.setUserName(userName);
		dto.setCommand(InfoMsg.EXIT);
		try{
			oos.writeObject(dto);
			oos.flush();
		}catch(IOException io){
			io.printStackTrace();
		}
	}//getExit() 메소드 : PC종료 후 서버에서 종료 수락 요청.
	@Override
	public void run(){
		//받는 쪽
		InfoDTO dto=null;

		while(true){
			try{
				dto = (InfoDTO)ois.readObject();//서버로부터 오는 메세지 읽기
					//전체 메세지
				if(dto.getCommand()==InfoMsg.NOTICE) {//전체 공지-모두가 수신함.
					String notice ="[공지] "+dto.getMessage();
					JOptionPane.showMessageDialog(null,notice,"[관리자로부터의 메세지]",JOptionPane.INFORMATION_MESSAGE);}
					//일반 메세지
				else if(!dto.getUserID().equals(userID)) continue;//남의 메세지는 받지 않는다.
				else{
					if(dto.getCommand()==InfoMsg.EXIT){
						try {oos.close();
						ois.close();//순서 바꿈
						socket.close();}
						catch (IOException | NullPointerException e) {e.printStackTrace();}	
						break;}

					else if(dto.getCommand()==InfoMsg.DELIVERED) {//메세지 수신시 재고 반영-물리적으로 메세지가 도달하면 물품이 제공된 것으로 간주하고, 재고에 반영한다. 
						int itemNum = dto.getItemNum();
						int itemMnt = dto.getItemMnt();
						InventoryDAO dao = InventoryDAO.getInstance();
						dao.takeInventoryOrder(itemNum,itemMnt);}}}// 확인하면 DB 반영
			catch(IOException e){e.printStackTrace();}
			catch(ClassNotFoundException e){e.printStackTrace();}

		}//while
		System.exit(0);
	}//run()
	public void sendMessage(InfoDTO infodto) {//****************
		InfoDTO dto = infodto;
		dto.setUserName(userName);
		dto.setCommand(InfoMsg.SEND);
		dto.setMessage(infodto.getMessage());
		try{
			oos.writeObject(dto);
			oos.flush();
		}catch(IOException io){
			io.printStackTrace();
		}
	}
	
	public void sendMonitorList() {
		for(CntServer data : monitorList) {
			InfoDTO dto = new InfoDTO();
			dto.setCntServer(data);
			try {
				oos.writeObject(data);
				oos.flush();
			}catch(IOException io) {io.printStackTrace();}
		}
	}
	
}//Class End
