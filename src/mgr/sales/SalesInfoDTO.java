package mgr.sales;


public class SalesInfoDTO {
	private int TransactionNum;//seq 생성
	private int productNum;//식별코드
	private String productName;//상품명
	private int productMnt;//상품개수
	private int revenue;//매출합계(수입)
	private String saleDate;//일/월 합산에 사용할 자료
	private boolean isPaid;//매입여부
	private int totDeposit;
	private String perchaseID;
	
	public int getTransactionNum() {
		return TransactionNum;
	}
	public void setTransactionNum(int transactionNum) {
		TransactionNum = transactionNum;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getProductMnt() {
		return productMnt;
	}
	public void setProductMnt(int productMnt) {
		this.productMnt = productMnt;
	}
	public int getRevenue() {
		return revenue;
	}
	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public boolean getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public int getTotDeposit() {
		return totDeposit;
	}
	public void setTotDeposit(int totDeposit) {
		this.totDeposit = totDeposit;
	}	
	
	public String getPerchaseID() {
		return perchaseID;
	}
	public void setPerchaseID(String perchaseID) {
		this.perchaseID = perchaseID;
	}
	@Override
	public String toString() {//고객이 보는 목록에서 보여지는 방법
		String saleInfoTitle; 
		saleInfoTitle ="["+productName+"]  "+productMnt+"(개) : (총)   "+revenue+"(원)";//구입품목과 가격
		return saleInfoTitle;}

}
