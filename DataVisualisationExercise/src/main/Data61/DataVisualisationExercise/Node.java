package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.util.HashSet;

public class Node implements Shape {

	public HashSet<Edge> edges = new HashSet<Edge>();
	public String label;
	public float x = 0, y = 0;
	public Color color = Color.black;
	public Color borderColor = Color.black;
	public float radius;
	private int index = -1;

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

	public void addEdge(Node to) {
		edges.add(new Edge(this, to));
	}

	@Override
	public void draw() {
		float x = this.x;
		float y = this.y;
		GraphicsDrawer.setColor(color);
		GraphicsDrawer.drawCircle(x - radius, y - radius, radius, true);
		GraphicsDrawer.setColor(borderColor);
		GraphicsDrawer.drawCircle(x - radius, y - radius, radius, false);
	}

}
