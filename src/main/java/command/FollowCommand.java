package command;

public class FollowCommand {
	
	private String from_id; // �ȷο� ��û��
	private String to_id; // �ȷο� ������
	
	public FollowCommand(){
		
	}
	
	public FollowCommand(String from_id, String to_id){
		super();
		this.from_id = from_id;
		this.to_id = to_id;
	}

	public String getFrom_id() {
		return from_id;
	}

	public void setFrom_id(String from_id) {
		this.from_id = from_id;
	}

	public String getTo_id() {
		return to_id;
	}

	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
}
