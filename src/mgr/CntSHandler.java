package mgr;


import java.net.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import javax.swing.*;

import cnt.CntServer;
import mgr.InfoDTO;
import mgr.InfoMsg;
import mgr.CntSHandler;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoDTO;

//예비 서버임
class CntSHandler extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ArrayList<CntSHandler> list;
	private static ArrayList<CntServer> monitorList;
	public CntSHandler(Socket socket, ArrayList<CntSHandler> list){
		try{
			this.socket = socket;
			this.list = list;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());}
		catch(IOException e){
			System.out.println("클라이언트와의 연결에 실패하였습니다.");
			e.printStackTrace();
			System.exit(0);}
	}//생성자
	
	@Override
	public void run(){
		//받는 쪽
		InfoDTO infodto = null;
		String userName = null;
		String itemName = null;
		String message = null;
		int itemNum=0;
		int itemMnt=0;

		
		while(true){
			try{
				infodto = (InfoDTO)ois.readObject();
				//monitorList.add(infodto.getCntServer());에러 발생
				if(infodto.getCommand()==InfoMsg.EXIT){
					//선불요금과 사용요금을 정산하는 메소드 필요
					InfoDTO sendDTO = new InfoDTO();
					sendDTO.setUserID(infodto.getUserID());
					sendDTO.setUserName(infodto.getUserName());
					sendDTO.setCommand(InfoMsg.EXIT);
					oos.writeObject(sendDTO);
					oos.flush();
					ois.close();
					oos.close();
					socket.close();
					
					String exitAccepted = "["+infodto.getUserID()+"]님이 PC 사용을 종료하여 로그아웃하셨습니다.";
					JOptionPane.showMessageDialog(null,exitAccepted,"[사용 종료 알림]",JOptionPane.INFORMATION_MESSAGE);
					
					break;}//exit 코맨드
				
				else if(infodto.getCommand()==InfoMsg.ORDER) {//주문 수령
					itemNum = infodto.getItemNum();
					userName = infodto.getUserName();
					itemName = infodto.getItemName();
					itemMnt = infodto.getItemMnt();
					message = infodto.getMessage();
					
					String orderAccepted = "["+userName+"]님이 [상품번호]"+itemNum+" [상품명]"+itemName+"을 "
											+itemMnt+"개 주문하셨습니다.\n[추가문의사항]"+message;
					JOptionPane.showMessageDialog(null,orderAccepted,"[상품 주문]",JOptionPane.INFORMATION_MESSAGE);
					
					deliverConfirm(infodto);//확인 했으니 컨펌받음
					}	
					else if(infodto.getCommand()==InfoMsg.SEND) {					
					JOptionPane.showMessageDialog(null,"["+infodto.getUserName()+"]"+infodto.getMessage(),"[메세지 도착]",JOptionPane.INFORMATION_MESSAGE);}
						try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				}//try
		
			catch(ClassNotFoundException e){e.printStackTrace();}
			catch(IOException io){io.printStackTrace();}
		}//while
	}//run	

	public void deliverConfirm(InfoDTO infodto) {//commit하기 위한 최종 확인 작업-재고반영 및 청구
		InfoDTO sendDTO = infodto;
		sendDTO.setCommand(InfoMsg.DELIVERED);//커맨드만 바꿔서 반환
		broadcast(sendDTO);
		updateAccount(infodto);//매상에 반영
		}//deliverConfirm() 메소드

	public void updateAccount(InfoDTO infodto) {
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		int transactionNum= dao.getSeq();//거래번호 획득
		
		InfoDTO infoDTO = infodto;
		SalesInfoDTO salesInfoDTO = new SalesInfoDTO();
		
		salesInfoDTO.setTransactionNum(transactionNum);//거래번호
		salesInfoDTO.setProductNum(infoDTO.getItemNum());//상품번호
		salesInfoDTO.setProductMnt(infoDTO.getItemMnt());//상품 개수
		salesInfoDTO.setProductName(infoDTO.getItemName());//상품이름
		salesInfoDTO.setRevenue(infoDTO.getTotCost());//건당 매상액
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");//오늘날짜 추출+String format
		salesInfoDTO.setSaleDate(sdf.format(today)+"");
		salesInfoDTO.setIsPaid(false);
		salesInfoDTO.setPerchaseID(infodto.getUserID());
		dao.updatingTransaction(salesInfoDTO);

	}
	
	public void broadcast(InfoDTO sendDTO){//일단 뿌리고 catch함.
		try{
			for(CntSHandler data : list){
				data.oos.writeObject(sendDTO);
				data.oos.flush();
			}//list에 뿌리기
		}catch(IOException e){
			e.printStackTrace();}
	}//broadcast 함수

	public static ArrayList<CntServer> getMonitorList() {
		return monitorList;
	}
	
	
}
