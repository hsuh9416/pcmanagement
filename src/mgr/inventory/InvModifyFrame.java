package mgr.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mgr.ErrorFrame;

@SuppressWarnings("serial")
public class InvModifyFrame extends JFrame implements ActionListener {

	private JButton btn_modify, btn_cancel;

	private JPanel labelJP, textfieldJP, ConfirmBtnJP;
	private Font labelF, textF, buttonF;

	private JLabel numL, itemL, priceL, stockL;
	private JTextField numT, itemT, priceT, stockT;

	
	@SuppressWarnings("rawtypes")
	private Vector vector;
	@SuppressWarnings("unused")
	private DefaultTableModel model;

	InventoryDTO inventoryDTO;
	private final String BLCFont = "Bahnschrift Light Condensed";

	public InvModifyFrame(InventoryDTO inventoryDTO) {

		this.inventoryDTO = inventoryDTO;
		Container con = getContentPane();
		con.setLayout(null);

		//폰트 생성
		labelF = new Font(BLCFont, Font.BOLD + Font.ITALIC, 15);
		textF = new Font(BLCFont, Font.PLAIN, 18);
		buttonF = new Font(BLCFont, Font.BOLD, 15);

		
		model = new DefaultTableModel(vector, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c != 0) ? true : false;
			}
		};
		
		//라벨 생성
		numL = new JLabel("상품번호");
		numL.setFont(labelF);
		itemL = new JLabel("품 명");
		itemL.setFont(labelF);
		priceL = new JLabel("가 격");
		priceL.setFont(labelF);
		stockL = new JLabel("수 량");
		stockL.setFont(labelF);
		
		//텍스트필드 생성
		numT = new JTextField(10);
		numT.setFont(textF);
		numT.setText(Integer.toString(inventoryDTO.getItemNum()));
		numT.setEditable(false);
		itemT = new JTextField(10);
		itemT.setFont(textF);
		itemT.setText(inventoryDTO.getItem());
		priceT = new JTextField(10);
		priceT.setFont(textF);
		priceT.setText(Integer.toString(inventoryDTO.getPrice()));
		stockT = new JTextField(10);
		stockT.setFont(textF);
		stockT.setText(Integer.toString(inventoryDTO.getStock()));
		
		//버튼 생성
		btn_modify = new JButton("수정");
		btn_modify.setFont(buttonF);
		btn_cancel = new JButton("취소");
		btn_cancel.setFont(buttonF);

		setView(con);

		setTitle("물품 수정");
		setBounds(600, 200, 500, 350);
		setVisible(true);

		btn_modify.addActionListener(this);
		btn_cancel.addActionListener(this);

	}

	// 화면 구성을 위한 Label, TextField, Button 구성 메소드
	private void setView(Container con) {

		// Label 위치 지정
		labelJP = new JPanel(new GridLayout(4, 1, 0, 20));
		labelJP.setBounds(30, 40, 90, 155);
		labelJP.add(numL);
		labelJP.add(itemL);
		labelJP.add(priceL);
		labelJP.add(stockL);
		con.add(labelJP);

		// Textfield 위치 지정
		textfieldJP = new JPanel(new GridLayout(4, 1, 0, 15));
		textfieldJP.setBounds(150, 40, 230, 150);
		textfieldJP.add(numT);
		textfieldJP.add(itemT);
		textfieldJP.add(priceT);
		textfieldJP.add(stockT);
		con.add(textfieldJP);

		// Button 위치 지정
		ConfirmBtnJP = new JPanel(new GridLayout(1, 2, 20, 5));
		ConfirmBtnJP.setBounds(120, 230, 250, 50);
		ConfirmBtnJP.add(btn_modify);
		ConfirmBtnJP.add(btn_cancel);
		con.add(ConfirmBtnJP);
	}
	
	//물품수정 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_modify) {
			modifyInventory();
			new InvMntFrame();
		}else if(e.getSource() == btn_cancel) {
			new InvMntFrame();
		}
		
		dispose();
	}
	
	//물품 수정 메소드
	public void modifyInventory() {

		InventoryDTO inventoryDTO = new InventoryDTO();

		int itemNum;
		String item;
		int price;
		int stock;

		try {
			itemNum = Integer.parseInt(numT.getText());
			item = itemT.getText();
			price = Integer.parseInt(priceT.getText());
			stock = Integer.parseInt(stockT.getText());
		} catch (NumberFormatException e) {
			System.out.println(e);
			new ErrorFrame("잘못된 형식의 입력 값을 넣었습니다.");
			return;
		}

		inventoryDTO.setItemNum(itemNum);
		inventoryDTO.setItem(item);
		inventoryDTO.setPrice(price);
		inventoryDTO.setStock(stock);

		InventoryDAO inventoryDAO = InventoryDAO.getInstance();
		inventoryDAO.modifyInventory(inventoryDTO);

	}

}
