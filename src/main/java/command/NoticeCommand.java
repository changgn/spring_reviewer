package command;

import java.util.Date;

public class NoticeCommand {
	private String kind;
	private String id;
	private String targetid;
	private int board_num;
	private Date notice_date;
	private int read;

	public NoticeCommand(){
		
	}
	
	public NoticeCommand(String kind, String id, String targetid, int board_num){
		super();
		this.kind = kind;
		this.id = id;
		this.targetid = targetid;
		this.board_num = board_num;	
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTargetid() {
		return targetid;
	}

	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public Date getNotice_date() {
		return notice_date;
	}

	public void setNotice_date(Date notice_date) {
		this.notice_date = notice_date;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	
}
