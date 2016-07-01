package command;

public class ProfilePhotoCommand {
	private String fileName;
	private String o_fileName;
	private String id;
	private String realPath;
	
	public ProfilePhotoCommand(){
		
	}
	
	public ProfilePhotoCommand(String fileName, String o_fileName, String id, String realPath) {
		super();
		this.fileName = fileName;
		this.o_fileName = o_fileName;
		this.id = id;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

}
