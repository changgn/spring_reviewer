package command;

public class MemberRecommendDeleteCommand {
	String id;
	int board_recommend_num;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getBoard_recommend_num() {
		return board_recommend_num;
	}
	public void setBoard_recommend_num(int board_recommend_num) {
		this.board_recommend_num = board_recommend_num;
	}
	public MemberRecommendDeleteCommand(String id, int board_recommend_num) {
		super();
		this.id = id;
		this.board_recommend_num = board_recommend_num;
	}
	public MemberRecommendDeleteCommand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
