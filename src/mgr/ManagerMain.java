package mgr;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ManagerMain {

	public static void main(String[] args) {
		try {
			com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Large-Font","0000","TEST");
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new LoginMgrFrame();
		
	}

}
