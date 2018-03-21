package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.util.HashSet;

public class Node implements Shape {

	public HashSet<Edge> edges = new HashSet<Edge>();
	public String label;
	public GLPoint position = new GLPoint();
	public Color color = Color.black;
	public Color borderColor = Color.black;
	public float radius;
	public boolean visible = false;
	public boolean centered = false;
	public GLPoint forcePosition = new GLPoint();

	private int index = -1;

	public Node(String label) {
		this(label, -1);
	}

	public Node(String label, int index) {
		this.label = label;
		this.index = index;
	}

	public Node(String label, int index, boolean visible) {
		this(label, index);
		this.visible = visible;
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

	public boolean isVisible() {
		return visible;
	}

	public boolean isCentered() {
		return centered;
	}

	@Override
	public void draw() {
		float x = this.position.x();
		float y = this.position.y();
		GraphicsDrawer.setColor(color);
		radius = isCentered() ? radius * 3 : radius;
		GraphicsDrawer.drawCircle(x, y, radius, true);
		GraphicsDrawer.setColor(borderColor);
		GraphicsDrawer.drawCircle(x, y, radius, false);

	}

}
