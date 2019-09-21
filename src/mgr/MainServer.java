package mgr;

import java.net.*;
import java.io.*;
import java.util.*;

	//예비서버임,관리자
public class MainServer extends Thread {
	private ServerSocket serverSocket;
	private ArrayList<CntSHandler> list;
	
	
	public MainServer(){
		this.start();
	}//생성자
	
	@Override
	public void run(){
		try{
			serverSocket = new ServerSocket(9555);
			System.out.println("서버준비완료...");

			list = new ArrayList<CntSHandler>();
		while(true){
			Socket socket = serverSocket.accept();
			CntSHandler handler = new CntSHandler(socket,list);
			handler.start();//스레드 시작-스레드 실행(run())
			list.add(handler);
		}//while
		}catch(IOException e){e.printStackTrace();}
	}//run() 메소드

}//MainServer class
