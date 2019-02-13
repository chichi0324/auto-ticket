package idv.auto_ticket.train;

public class OrderData {
	String person_id;// 身分證
	String getin_date;// 乘車日期
	String from_station;// 起站代碼
	String to_station;// 到站代碼
	String train_no;// 車次代碼
	String order_qty_str;// 訂票張數

	String n_order_qty_str;// 訂票張數:一般車廂(普悠瑪)
	String c_order_qty_str;// 訂票張數:桌型座位(普悠瑪)
	
	// 一般訂票
	public OrderData(String person_id, String getin_date, String from_station, String to_station, String train_no,
			String order_qty_str) {
		super();
		this.person_id = person_id;
		this.getin_date = getin_date;
		this.from_station = from_station;
		this.to_station = to_station;
		this.train_no = train_no;
		this.order_qty_str = order_qty_str;
	}

	// 普悠瑪訂票
	public OrderData(String person_id, String getin_date, String from_station, String to_station, String train_no,
			String n_order_qty_str, String c_order_qty_str) {
		super();
		this.person_id = person_id;
		this.getin_date = getin_date;
		this.from_station = from_station;
		this.to_station = to_station;
		this.train_no = train_no;
		this.n_order_qty_str = n_order_qty_str;
		this.c_order_qty_str = c_order_qty_str;
	}

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getGetin_date() {
		return getin_date;
	}

	public void setGetin_date(String getin_date) {
		this.getin_date = getin_date;
	}

	public String getFrom_station() {
		return from_station;
	}

	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}

	public String getTo_station() {
		return to_station;
	}

	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}

	public String getTrain_no() {
		return train_no;
	}

	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	public String getOrder_qty_str() {
		return order_qty_str;
	}

	public void setOrder_qty_str(String order_qty_str) {
		this.order_qty_str = order_qty_str;
	}

	public String getN_order_qty_str() {
		return n_order_qty_str;
	}

	public void setN_order_qty_str(String n_order_qty_str) {
		this.n_order_qty_str = n_order_qty_str;
	}

	public String getC_order_qty_str() {
		return c_order_qty_str;
	}

	public void setC_order_qty_str(String c_order_qty_str) {
		this.c_order_qty_str = c_order_qty_str;
	}



}
