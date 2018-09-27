import java.util.ArrayList;

public class Path {
	private String stringPath;
	private ArrayList<byte[]> bytePath;
	
	Path(){
		this.stringPath = "Search failed";
	}
	
	Path(String stringPath, ArrayList<byte[]> bytePath) {
		this.stringPath = stringPath;
		this.bytePath = bytePath;
	}
	
	public String getStringPath() {
		return stringPath;
	}
	
	public ArrayList<byte[]> getBytePath() {
		return bytePath;
	}
}
