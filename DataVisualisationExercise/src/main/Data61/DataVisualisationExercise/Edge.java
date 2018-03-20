package Data61.DataVisualisationExercise;


public class Edge implements Shape {

	private final Node from;
	private final Node to;

	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public void draw() {
		GraphicsDrawer.drawLine(from.position.x(), from.position.y(), to.position.x(), to.position.y());
	}

	public boolean equals(Edge other) {
		if (this.from.equals(other.from))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return -1;

	}

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}
}
