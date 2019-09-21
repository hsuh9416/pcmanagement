package mgr;

import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class ClockMgr extends JPanel implements Runnable {
	private JLabel date,clock;
	private JSplitPane jsp;
	public ClockMgr() {
		jsp = new JSplitPane();
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		date = new JLabel("");
		date.setFont(new Font("Bahnschrift Light Condensed", Font.BOLD, 30));
		clock = new JLabel("");
		clock.setFont(new Font("Bahnschrift Light Condensed", Font.BOLD, 30));
		jsp.setTopComponent(date);
		jsp.setBottomComponent(clock);
		add(date);
		add(clock);
		Thread t = new Thread(this);
		t.start();
		
		setBounds(30,650,250,100);
		setVisible(true);
		
	}


	@Override
	public void run(){
		while(true){
			SimpleDateFormat input1 = new SimpleDateFormat("yyyy년 MM월 dd일");
			SimpleDateFormat input2 = new SimpleDateFormat("HH : mm");

			Date d = new Date();
			date.setText(input1.format(d));
			clock.setText("현재 시간  "+input2.format(d));
			try{Thread.sleep(1000);}
			catch(Exception e){e.printStackTrace();}
		}//while
	}//run method
}//ClockMgr class
