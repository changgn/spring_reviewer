package command;

public class MemberCategoryCommand {
	private String id;
	private String category_id;

	public MemberCategoryCommand(){
		
	}
	
	public MemberCategoryCommand(String id, String category_id){
		super();
		this.id = id;
		this.category_id = category_id;	
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
}
