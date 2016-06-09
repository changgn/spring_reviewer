package command;

public class PhotoCommand {
	private String fileName;
	private String o_fileName;
	private int board_num;
	private String realPath;
	
	public PhotoCommand(){
		
	}
	
	public PhotoCommand(String fileName, String o_fileName, int board_num, String realPath) {
		super();
		this.fileName = fileName;
		this.o_fileName = o_fileName;
		this.board_num = board_num;
		this.realPath = realPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getO_fileName() {
		return o_fileName;
	}

	public void setO_fileName(String o_fileName) {
		this.o_fileName = o_fileName;
	}
	
	public int getboard_num() {
		return board_num;
	}

	public void setboard_num(int board_num) {
		this.board_num = board_num;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

}
