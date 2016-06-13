package command;

import java.util.Date;

public class CommentCommand {
	
	private int comment_num;
	private int board_num;
	private String id;
	private String content;
	private Date write_date;
	
	public CommentCommand(){
		
	}
	
	public CommentCommand(int comment_num, int board_num, String id, String content, Date write_date) {
		super();
		this.comment_num = comment_num;
		this.board_num = board_num;
		this.id = id;
		this.content = content;
		this.write_date = write_date;
		
	}

	public int getcomment_num() {
		return comment_num;
	}

	public void setcomment_num(int comment_num) {
		this.comment_num = comment_num;
	}

	public int getBoard_num() {
		return board_num;
	}

	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	

}

