import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Main {
	public static void main(String[] args) throws Exception {
		Puzzle puzzle = new Puzzle();
		Path answer = BFS.search(puzzle);
		System.out.println(answer.getStringPath());
		PrintWriter out = new PrintWriter(new FileOutputStream("results.txt"));
		out.println(answer.getStringPath());
		out.close();
		new Viz(answer.getBytePath());
	}
}