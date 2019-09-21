package cnt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import member.bean.MemberDTO;
import mgr.InfoDTO;
import mgr.InfoMsg;


@SuppressWarnings("serial")
public class ClientChat extends JFrame implements ActionListener{
	private JTextField input;
	private JButton sendB;
	private MemberDTO memberDTO;
	
	public ClientChat(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;

		input = new JTextField();
		sendB = new JButton("보내기");
	
		JPanel southP = new JPanel();
		southP.setLayout(new BorderLayout());
		southP.add("Center",input);
		southP.add("East", sendB);
		
		Container con = getContentPane();
	
		//con.add("Center", scroll);
		con.add(southP);
	
		setBounds(600,200,300,100);
		setVisible(true);
		
		sendB.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(input.getText().equals(""))
			JOptionPane.showMessageDialog(ClientChat.this, "입력해라", "정보", JOptionPane.INFORMATION_MESSAGE);
		else 
			sendMessage();
			input.setText("");
			JOptionPane.showMessageDialog(ClientChat.this, "메세지가 전송 되었습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
			dispose();
	}


	public void sendMessage() {
		String message = input.getText();
		
		InfoDTO dto = new InfoDTO();
		dto.setUserID(memberDTO.getId());//식별 id가 있어야 함.
		dto.setCommand(InfoMsg.SEND);
		dto.setMessage(message);
		CntServer cnt = CntServer.getInstance(memberDTO);
		cnt.sendMessage(dto);
	}


}
