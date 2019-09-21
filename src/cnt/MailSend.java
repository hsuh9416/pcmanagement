package cnt;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend {
	public MailSend() {}
	  public String NaverMailSend(String userEmail) {
		    Properties props;
		    @SuppressWarnings("unused")
			String host = "smtp@naver.com";
			String user= "yull052@naver.com";
			String password = "!@#phoenix5";
			String checkNum = null;
			
			props = new Properties();
			props.put("mail.smtp.host", "smtp.naver.com");
			props.put("mail.smtp.port", 587);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", "smtp.naver.com");
			
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
					
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				//수신시 발신자 표시
				message.setFrom(new InternetAddress(user,"XXPC방"));
				
				//수신자 메일 주소
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(""+userEmail+""));
				
				//메일 제목
				message.setSubject("인증 번호 확인");
				
				checkNum = checkNum();//인증번호 생성 메서드
				//메일 내용
				message.setText("XXPC방 인증번호 : "+checkNum);
				
				Transport.send(message);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return checkNum;
	  }//mailSend()
	  
	  
	  //인증번호 생성
	  public String checkNum() {
		  String checkNum = "";
		  for(int i=0; i<5; i++) {
			  checkNum += Integer.toString((int)(Math.random()*10));//랜덤 숫자 생성후 인증번호에 저장	 
		  }
		  
		return checkNum;
	  }
}
