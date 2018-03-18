package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.util.ArrayList;

public class Node {

	public ArrayList<Node> neighbours = new ArrayList<Node>();
	public String label;
	public boolean showLabel = false;
	public float x = 0, y = 0;
	public Color color;
	private int index = -1;

	public Node() {
	}

	public Node(String label) {
		this(label, -1);
	}

	public Node(String label, int index) {
		this.label = label;
		this.index = index;
	}

	public void setIndex(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

}
