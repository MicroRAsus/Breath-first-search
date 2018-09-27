import java.util.ArrayList;

import javax.swing.JFrame;

public class Viz extends JFrame
{
	public Viz(ArrayList<byte[]> bytePath) throws Exception
	{
		View view = new View(this, bytePath);
		view.addMouseListener(view);
		this.setTitle("Puzzle");
		this.setSize(482, 505);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}