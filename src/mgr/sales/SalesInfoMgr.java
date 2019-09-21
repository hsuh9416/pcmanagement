package mgr.sales;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//매상 확인 클래스

@SuppressWarnings("serial")
public class SalesInfoMgr extends JFrame implements ActionListener{
	private JButton totalViewB,todateViewB,monthViewB,nonPaidViewB,cancelB;
	private DefaultTableModel model;
	private JTable tableSale;
	private JPanel tableJP,buttonJP;
	private JScrollPane scroll;
	private ArrayList<SalesInfoDTO> saleInfolist;
	private Vector<String> vector;
	public SalesInfoMgr() {
		Container con = getContentPane();
		con.setLayout(null);

		//1-1.테이블 타이틀 부착
				vector = new Vector<String>();
				vector.addElement("거래번호");
				vector.addElement("상품번호");
				vector.addElement("상품명");
				vector.addElement("매출수량");
				vector.addElement("매출액");
				vector.addElement("매출일자");
				vector.addElement("매입여부");
				vector.addElement("매입자ID");
		//1-2.tableModel 생성 
				model = new DefaultTableModel(vector,0){
					public boolean isCellEditable(int r, int c) {
						return false;//데이터 수정 불가
					}
				};
				
		//1-3.JTable 생성+ 설정
				tableSale = new JTable(model);
				tableSale.getTableHeader().setLayout(new FlowLayout(FlowLayout.CENTER));
				tableSale.getTableHeader().setReorderingAllowed(false);//컬럼 이동방지
				tableSale.getTableHeader().setResizingAllowed(false);//크기 조절 불가
				scroll = new JScrollPane(tableSale);
				
		//1-4. Panel부착
				tableJP = new JPanel();
				tableJP.setLayout(new BorderLayout());
				tableJP.add(scroll,BorderLayout.CENTER);
				tableJP.setBounds(20,20,660,380);//680,400
				con.add(tableJP);
				
		//2-1. 버튼 생성
				totalViewB = new JButton("전체");
				todateViewB = new JButton("당일매상");
				monthViewB = new JButton("당월매상");
				nonPaidViewB = new JButton("미결제거래");
				cancelB = new JButton("돌아가기");
				
		//2-2. Panel부착
				buttonJP = new JPanel(new GridLayout(1,5,20,10));
				buttonJP.setBounds(20,420,660,40);//680,480
				buttonJP.add(totalViewB);	buttonJP.add(todateViewB);	buttonJP.add(monthViewB);
				buttonJP.add(nonPaidViewB); buttonJP.add(cancelB);
				con.add(buttonJP);
		
		//2-3 테이블에 데이터 불러오기
				SalesInfoDAO dao = SalesInfoDAO.getInstance();
				saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHALL);
				showView();
				
 		//3. Frame 설정
 		setUndecorated(true);//투명창 만들기
 		setTitle("매상 관리");
 		setBounds(450,100,700,500);
 		setResizable(false);
 		setVisible(true);
 		
 		//4. Event 설정
 		totalViewB.addActionListener(this);
 		todateViewB.addActionListener(this);
 		monthViewB.addActionListener(this); 		
 		nonPaidViewB.addActionListener(this);		
 		cancelB.addActionListener(this);
 }

	public void showView() {//제약조건이 없으면 전체조회, 제약조건이 있으면 일부 조회
		model.setRowCount(0);//시작전 초기화
		for(SalesInfoDTO dto : saleInfolist){
			Vector<Object> v = new Vector<Object>();
			v.add(dto.getTransactionNum()+"");
			v.add(dto.getProductNum());
			v.add(dto.getProductName());
			v.add(dto.getProductMnt());
			v.add(dto.getRevenue());
			v.add(dto.getSaleDate());
			v.add(dto.getIsPaid());
			v.add(dto.getPerchaseID());
			model.addRow(v);
		}
	}//totalView() 메소드 : 전체보기
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelB) dispose();
		else if(e.getSource()==totalViewB) {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHALL);
			showView();}
		else if(e.getSource()==todateViewB)  {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHTODAY);
			showView();}
		else if(e.getSource()==monthViewB)  {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHMONTH);
			showView();}
		else if(e.getSource()==nonPaidViewB)  {
			SalesInfoDAO dao = SalesInfoDAO.getInstance();
			saleInfolist = dao.getSalesInfoList(SalesInfoSel.SEARCHNONPAID);
			showView();}
		
		
	}

	

}
