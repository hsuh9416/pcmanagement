package mgr.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import mgr.ErrorFrame;

@SuppressWarnings("serial")
public class InvAddFrame extends JFrame implements ActionListener {

	private JPanel labelJP, textfieldJP, ConfirmBtnJP;
	private Font labelF, textF, buttonF;

	private JLabel numL, itemL, priceL, stockL;
	private JTextField numT, itemT, priceT, stockT;
	private JButton addB, cancelB;

	@SuppressWarnings("rawtypes")
	private Vector vector;
	private DefaultTableModel model;

	private final String BLCFont = "Bahnschrift Light Condensed";
	
	//물품추가 프레임
	public InvAddFrame() {

		Container con = getContentPane();
		con.setLayout(null);

		labelF = new Font(BLCFont, Font.BOLD + Font.ITALIC, 15);
		textF = new Font(BLCFont, Font.PLAIN, 18);
		buttonF = new Font(BLCFont, Font.BOLD, 15);

		model = new DefaultTableModel(vector, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c != 0) ? true : false;
			}
		};

		getInventoryList();
		
		//물품추가 라벨 
		numL = new JLabel("상품번호");
		numL.setFont(labelF);
		itemL = new JLabel("품 명");
		itemL.setFont(labelF);
		priceL = new JLabel("가 격");
		priceL.setFont(labelF);
		stockL = new JLabel("수 량");
		stockL.setFont(labelF);

		//물품추가 텍스트필드
		numT = new JTextField(10);
		numT.setFont(textF);
		itemT = new JTextField(10);
		itemT.setFont(textF);
		priceT = new JTextField(10);
		priceT.setFont(textF);
		stockT = new JTextField(10);
		stockT.setFont(textF);
		
		//확인 취소버튼 버튼생성
		addB = new JButton("추가");
		addB.setFont(buttonF);
		cancelB = new JButton("취소");
		cancelB.setFont(buttonF);
		
		//컨터네이너 
		setView(con);

		setTitle("물품 추가");
		setBounds(600, 200, 500, 350);
		setVisible(true);

		//이벤트 발생
		addB.addActionListener(this);
		cancelB.addActionListener(this);
	}

	// 인벤토리 테이블의 모든 데이터를 불러와 화면에 띄워주는 메소드
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getInventoryList() {

		InventoryDAO inventoryDAO = InventoryDAO.getInstance();
		ArrayList<InventoryDTO> list = inventoryDAO.getInventoryList();

		for (InventoryDTO inventoryDTO : list) {

			Vector vec = new Vector<String>();
			vec.addElement(inventoryDTO.getItemNum());
			vec.addElement(inventoryDTO.getItem());
			vec.addElement(Integer.toString(inventoryDTO.getPrice()));
			vec.addElement(Integer.toString(inventoryDTO.getStock()));

			model.addRow(vec);
		}
	}

	// 화면 구성을 위한 Label, Textfield, Button 구성 메소드
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
		ConfirmBtnJP.add(addB);
		ConfirmBtnJP.add(cancelB);
		con.add(ConfirmBtnJP);
	}
	
	//이벤트 
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addB) {
			addInventory();
			dispose();
			new InvMntFrame();
		}else if(e.getSource() == cancelB) {
			new InvMntFrame();
			dispose();
		}

	}

	//물품추가 메소드
	public void addInventory() {

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

		inventoryDAO.addInventory(inventoryDTO);
		
		



	}//addInventory

}