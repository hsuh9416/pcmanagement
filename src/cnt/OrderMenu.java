package cnt;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import cnt.CntServer;
import member.bean.MemberDTO;
import mgr.InfoDTO;
import mgr.inventory.InventoryDAO;
import mgr.inventory.InventoryDTO;

@SuppressWarnings("serial")
public class OrderMenu extends JFrame implements ActionListener,ListSelectionListener {
	private ButtonGroup group;
	private Container con;
	private Font labelF,textFieldF;//라벨과 텍스트 필드 두 종류의 폰트 적용
	private JButton orderB, eraseB, cancelB;//주문,지우기,돌아가기 버튼
	private JLabel itemNameL,itemuCostL,mntselL,otherTL,totCostL,messageL,wonL;
	private JRadioButton oneRB,twoRB,threeRB,otherRB;
	private JScrollPane scroll;
	private JTextField itemNameF, itemuCostF,totCostF,otherMntF;
	private JTextArea messageF;
	private DefaultListModel<InventoryDTO> model;
	private JList<InventoryDTO> list;
	private JPanel labelJP,itemInfoTJP,mntSelJP,otherSelJP,buttonJP,
				  messageTJP,totCostTJP;
	private int stock;
	private MemberDTO memberDTO;
	public OrderMenu(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;

		con = getContentPane();
		con.setLayout(null);
		
		//1.COMPONENT 생성
 		labelF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
 		textFieldF = new Font("Bahnschrift Light Condensed",Font.PLAIN,18);
			//1-1 목록 작성
		model = new DefaultListModel<InventoryDTO>();
        list = new JList<InventoryDTO>(model);
        list.setFont(textFieldF);
        
        	//1-2 선택 품목 명세
        itemNameL = new JLabel("선택하신 물품명");
        itemNameL.setFont(labelF);
        itemNameF = new JTextField("");
        itemNameF.setFont(textFieldF);
        itemNameF.setEditable(false);
        itemuCostL = new JLabel("물품의 (개당)가격");
        itemuCostL.setFont(labelF);
        itemuCostF = new JTextField("");
        itemuCostF.setFont(textFieldF);
        itemuCostF.setEditable(false);
        wonL = new JLabel("(원)");
        
        	//1-3 수량 선택 부분
        mntselL = new JLabel("주문하실 수량");
        mntselL.setFont(labelF);
        group = new ButtonGroup();
        oneRB = new JRadioButton("1개",true);
        oneRB.setFont(labelF);
        group.add(oneRB);
        twoRB = new JRadioButton("2개");
        twoRB.setFont(labelF);
        group.add(twoRB);
        threeRB = new JRadioButton("3개");
        threeRB.setFont(labelF);
        group.add(threeRB);
        otherRB = new JRadioButton("기타");
        otherRB.setFont(labelF);
        group.add(otherRB);
        otherMntF = new JTextField("");
        otherMntF.setFont(textFieldF);
        otherMntF.setEditable(false);
       
        
        otherTL = new JLabel("(개)");
        otherTL.setFont(labelF);
        
        	//1-4 합계 부분
        totCostL = new JLabel("주문 합계");
        totCostL.setFont(labelF);
        totCostF = new JTextField("");
        totCostF .setFont(textFieldF);
        totCostF.setEditable(false);
        
        	//1-5 추가 메세지 부분
        messageL = new JLabel("기타 주문사항");
        messageL.setFont(labelF);
        messageF = new JTextArea("기타 문의사항을 입력해주세요.",2,10);
        messageF.setFont(textFieldF);
        messageF.setEditable(false);//물품을 화면에서 선택할 때까지 사용 불가 상태.
        
        	//1-6 버튼 부분
		orderB = new JButton("주문 결정");
		orderB.setFont(labelF);
		eraseB = new JButton("지우기");
		orderB.setFont(labelF);
		cancelB = new JButton("돌아가기");
		orderB.setFont(labelF);
		
		//2-1. JList부분 배치+목록 불러오기
		scroll = new JScrollPane(list);
		scroll.setBounds(10,10,480,300);//490,310
		con.add(scroll);
		
		InventoryDAO dao = InventoryDAO.getInstance();
		ArrayList<InventoryDTO> inventorylist = dao.getInventoryList();
		
		for(InventoryDTO dto : inventorylist) {
			model.addElement(dto);
		}
		
		//2-2 라벨 부분 배치
		labelJP = new JPanel(new GridLayout(5,1,10,20));
		labelJP.setBounds(10,330,130,300);//140,630
		labelJP.add(itemNameL); 		labelJP.add(itemuCostL);
		labelJP.add(mntselL);			labelJP.add(totCostL);
		labelJP.add(messageL);
		con.add(labelJP);
		
		//2-3텍스트 부분 배치+단위 가격 라벨 배치
		itemInfoTJP = new JPanel(new GridLayout(2,1,10,20));
		itemInfoTJP.setBounds(140,330,120,110);//260,440
		itemInfoTJP.add(itemNameF);		itemInfoTJP.add(itemuCostF);
		con.add(itemInfoTJP);
	
		wonL.setBounds(270,400,25,30);
		con.add(wonL);
		
		//2-4수량 선택 부분 배치
		mntSelJP = new JPanel(new GridLayout(1,4,5,20));
		mntSelJP.setBounds(140,460,260,50);//400,500
		mntSelJP.add(oneRB);			mntSelJP.add(twoRB);
		mntSelJP.add(threeRB);			mntSelJP.add(otherRB);
		con.add(mntSelJP);
		
		//2-5기타 수량 선택 부분 배치
		otherSelJP = new JPanel(new GridLayout(1,2,10,10));
		otherSelJP.setBounds(400,465,100,40);//500,505
		otherSelJP.add(otherMntF);		otherSelJP.add(otherTL);	
		con.add(otherSelJP);
		
		//2-6 총 금액 textField 배치
		totCostTJP = new JPanel(new GridLayout(1,1,10,20));
		totCostTJP.setBounds(140,525,120,40);//260,565
		totCostTJP.add(totCostF);
		con.add(totCostTJP);
		
		//2-7 textArea 배치
		messageTJP = new JPanel(new GridLayout(1,1,10,20));
		messageTJP.setBounds(140,590,330,50);//470,640
		messageTJP.add(messageF);
		con.add(messageTJP);
		
		//3. 버튼 배치
		buttonJP = new JPanel(new GridLayout(1,3,20,20));
		buttonJP.setBounds(100,660,300,50);//400,710
		buttonJP.add(orderB); 			buttonJP.add(eraseB);
		buttonJP.add(cancelB);
		con.add(buttonJP);

		//4. Frame 설정
				setTitle("상품 주문");
				setBounds(600, 200, 500, 750);
				setVisible(true);
				setResizable(false);
				addWindowListener(new WindowAdapter(){
					@Override
					public void	windowClosing(WindowEvent e){
						dispose();
					}
				});
				
				//5. event 활성화[초기:주문,지우기 비활성화]
				oneRB.addActionListener(this);//직접 기입란 (비)활성화를 위해 특별히 설정
				twoRB.addActionListener(this);//직접 기입란 (비)활성화를 위해 특별히 설정
				threeRB.addActionListener(this);//직접 기입란 (비)활성화를 위해 특별히 설정
				otherRB.addActionListener(this);//직접 기입란 (비)활성화를 위해 특별히 설정
				otherMntF.addActionListener(this);//엔터를 입력하면 totCost에 반영된다.+숫자 외에는 입력할 수 없는 제약
				orderB.addActionListener(this);
				orderB.setEnabled(false);
				eraseB.addActionListener(this);
				eraseB.setEnabled(false);
				cancelB.addActionListener(this);
				list.addListSelectionListener(this);
				

			}//OrderMenu() - 생성자

			@Override
			public void actionPerformed(ActionEvent e) {
			   if(otherRB.isSelected()) {
				   otherMntF.setEditable(true);
				   if(e.getSource()==otherMntF && !otherMntF.getText().equals("")) totCostF.setText(getTotal());}
			   
			   else if(oneRB.isSelected()||twoRB.isSelected() || threeRB.isSelected()) {//다른 것을 선택할 때는 입력창 비활성화 복귀
				   otherMntF.setText("");
				   otherMntF.setEditable(false);
				   totCostF.setText(getTotal()+"");}
			   
		       if(e.getSource()==cancelB) {
		    	   erase();
		    	   dispose();}
		       else if(e.getSource()==eraseB){
		    	   erase();}
		       
		       else if(e.getSource()==orderB) {
		   			int sel = JOptionPane.showConfirmDialog(this,"구매를 확정하시겠습니까?","[구매 확정]",JOptionPane.YES_NO_OPTION);
		   			if(sel == JOptionPane.YES_OPTION) pushOrderInfo();
		   			else return; 
		   			dispose();}//주문이 완료되면 모든 화면창이 닫침.
			}//actionPerformed() 메소드

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedIndex()==-1) return;
				InventoryDTO inventoryDTO = list.getSelectedValue();
				stock = inventoryDTO.getStock();
				
				if(inventoryDTO.getStock()==0) {
					JOptionPane.showMessageDialog(this,"품절된 상품은 구입하실 수 없습니다.","[알림]",JOptionPane.INFORMATION_MESSAGE);
			  	   	orderB.setEnabled(false);
			  	   	eraseB.setEnabled(false);
			  	   	messageF.setEditable(false);
					return;
				}//품절되면 불가
				
				//선택하면 텍스트창에 뿌린다.
				itemNameF.setText(inventoryDTO.getItem());
		  	   	itemuCostF.setText(inventoryDTO.getPrice()+"");
		  	   	orderB.setEnabled(true);
		  	   	eraseB.setEnabled(true);
		  	   	messageF.setEditable(true);
		  	   	messageF.setText("");
		  	   	totCostF.setText(getTotal());
		  	   	
			}//valueChanged() 메소드
			
			public void pushOrderInfo() {
				InfoDTO dto = new InfoDTO();
				
				//선택한 수량에서 정보 추출
				if(oneRB.isSelected()) dto.setItemMnt(1); 
				else if(twoRB.isSelected()) dto.setItemMnt(2);
				else if(threeRB.isSelected()) dto.setItemMnt(3);
				else if(otherRB.isSelected())dto.setItemMnt(Integer.parseInt(otherMntF.getText()));
				
				//재고이상 주문시
				if(dto.getItemMnt() > stock) {
					JOptionPane.showMessageDialog(this,"재고 이상의 물품은 주문하실 수 없습니다. 현재 남은 수량: "+stock+"(개)",
												  "[알림]",JOptionPane.INFORMATION_MESSAGE); return;}
				
				//수량 외 다른 항목에서 정보 추출
				dto.setItemNum(list.getSelectedValue().getItemNum());//아이템 구분번호(임시 계정의 key value)
				dto.setItemName(itemNameF.getText());
				dto.setuPrice(Integer.parseInt(itemuCostF.getText()));
				dto.setTotCost(Integer.parseInt(totCostF.getText()));
				dto.setMessage(messageF.getText());
				
				CntServer cnt = CntServer.getInstance(memberDTO);//동일한 유저명을 가진 서버를 호출한다.
				cnt.getOrder(dto);//client 서버에 주문 정보를 저장

			}//pushOrderInfo()-보낼 명세를 확정하는 메소드
			
			public String getTotal() {//선택한 물품에 대한 전체 비용 출력
				int totalCost=0;
				if (list.isSelectionEmpty() || stock==0 || itemuCostF.getText().equals("") ) return "";//선택한 것이 없음,재고없는 상태 선택, 기타를 선택하고도 아무런 입력이 없을 때.
				else {
					if(oneRB.isSelected())  totalCost=Integer.parseInt(itemuCostF.getText());
					else if(twoRB.isSelected())	 totalCost=Integer.parseInt(itemuCostF.getText())*2;
					else if(threeRB.isSelected())	 totalCost=Integer.parseInt(itemuCostF.getText())*3;
					else if(otherRB.isSelected() && !otherMntF.getText().equals(""))
						try{totalCost=Integer.parseInt(itemuCostF.getText())*Integer.parseInt(otherMntF.getText());}
						catch(NumberFormatException nf) {
					   		JOptionPane.showMessageDialog(this,"숫자만 입력하세야 합니다.","[주의]",JOptionPane.INFORMATION_MESSAGE);
					   		totalCost =0;}
					else totalCost=0;}
				if (totalCost == 0) return "";//결과가 0이면 텍스트필드를 비운다.
				return totalCost+"";
			}//getTotal()-메소드 : 합계를 보여줌.
			
			public void erase() {//항목 초기화
				
		 	   itemNameF.setText("");
		 	   itemuCostF.setText("");
		 	   oneRB.setSelected(true); 	twoRB.setSelected(false);
		 	   threeRB.setSelected(false); 	otherRB.setSelected(false);
		 	   otherMntF.setText(""); 		otherMntF.setEditable(false);
		 	   totCostF.setText(""); 
		 	   messageF.setText("기타 문의사항을 입력해주세요."); messageF.setEditable(false);
		 	   orderB.setEnabled(false);
		 	   eraseB.setEnabled(false);
		 	   messageF.setEditable(false);
		 	   list.clearSelection();//리스트 선택 상태 해제
		 	   
			}//erase() 메소드
			
		}//OrderMenu Class
