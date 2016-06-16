package command;

public class RecommendCommand {

	private String id;
	private int recommend_num;
	
	public RecommendCommand(){
		
	}
	
	public RecommendCommand(String id, int recommend_num){
		this.id=id;
		this.recommend_num=recommend_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRecommend_num() {
		return recommend_num;
	}

	public void setRecommend_num(int recommend_num) {
		this.recommend_num = recommend_num;
	}
	
}
