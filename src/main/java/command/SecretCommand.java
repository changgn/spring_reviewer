package command;

public class SecretCommand {
	private String id;
	private int board_num;
	
	public SecretCommand(String id, int board_num){
		this.id = id;
		this.board_num = board_num;
	}

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
}
