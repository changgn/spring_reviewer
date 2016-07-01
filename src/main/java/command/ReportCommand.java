package command;

public class ReportCommand {
	private String id;
	private int board_num;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	
	public ReportCommand(String id, int board_num) {
		super();
		this.id = id;
		this.board_num = board_num;
	}
	
}
