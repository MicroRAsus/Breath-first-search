import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;
//import java.io.FileOutputStream;
//import java.io.PrintWriter;

public class BFS {
	private static Path constructPath(GameState goal) {
		int stepCounter = 1;
		StringBuilder sb = new StringBuilder();
		sb.append(goal.stateToString() + System.getProperty("line.separator"));
		ArrayList<byte[]> bytePath = new ArrayList<byte[]>();
		bytePath.add(goal.getState());
		while(goal.getPrevState() != null) {
			goal = goal.getPrevState();
			sb.insert(0, goal.stateToString() + System.getProperty("line.separator"));
			bytePath.add(0, goal.getState());
			stepCounter++;
		}
		sb.append("Steps took to reach goal: " + stepCounter);
		Path path = new Path(sb.toString(), bytePath);
		return path;
	}
	
	public static Path search(Puzzle problem) throws Exception {
		Queue<GameState> openNodes = new LinkedList<>();
		StateComparator comp = new StateComparator();
		TreeSet<GameState> closedNodes = new TreeSet<GameState>(comp);
		openNodes.add(problem.getRoot());
		closedNodes.add(problem.getRoot());
		
		//PrintWriter out = new PrintWriter(new FileOutputStream("log.txt"));
		while(!openNodes.isEmpty()) {
			GameState subTreeRoot = openNodes.remove();
			//System.out.println("Pop: " + subTreeRoot.stateToString());
			//out.println("Pop: " + subTreeRoot.stateToString());
			if(problem.isGoal(subTreeRoot)) {
				//out.close();
				return constructPath(subTreeRoot);
			}
			
			for(GameState childrenNode : problem.get_childrens(subTreeRoot)) {
				if(closedNodes.contains(childrenNode)) {
					continue;
				}
				openNodes.add(childrenNode); 
				closedNodes.add(childrenNode);
				//System.out.println("Push: " + childrenNode.stateToString());
				//out.println("Push: " + childrenNode.stateToString());
			}
			//System.out.println("");
			//out.println("");
		}
		//out.close();
		return new Path();
	}
}