package Data61.DataVisualisationExercise;

import java.util.concurrent.ConcurrentHashMap;

public class Network {

	private ConcurrentHashMap<Integer, Node> nodes = new ConcurrentHashMap<Integer, Node>();

	public int getNumNodes() {
		return nodes.size();
	}

	public Node getNode(int index) {
		if (index < 0 || index > nodes.size())
			return null;
		return nodes.get(index);
	}

	public ConcurrentHashMap<Integer, Node> getNodes() {
		return nodes;
	}

	public void addNode(Node n) {
		if (!nodes.containsKey(n.getIndex()))
			nodes.put(n.getIndex(), n);
	}

	public void addEdge(Node from, Node to) {
		if (from == to || from == null || to == null)
			return;
		from.addEdge(to);
		to.addEdge(from);
	}

	public void addEdge(int nodeIndex1, int nodeIndex2) {
		addEdge(getNode(nodeIndex1), getNode(nodeIndex2));
	}

	public int getIndexOfNode(Node n) {
		assert n != null;
		int index = n.getIndex();
		if (index < 0 || index >= nodes.size() || getNode(index) != n) {
			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).setIndex(i);
			}
		}
		assert getNode(n.getIndex()) == n;
		return n.getIndex();
	}

	public void randomizePositionsOfNodes(GLAlignedRectangle boundingRectangle) {
		if (boundingRectangle == null)
			return;
		GLAlignedRectangle rectangle = new GLAlignedRectangle();
		float distance = rectangle.getDiagonal().length();
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			if (boundingRectangle == null) {
				n.position.add(new GLPoint((float) (distance * (Math.random() - 0.5f)),
						(float) (distance * (Math.random() - 0.5f))));
			} else {
				n.position.setX(
						(float) (boundingRectangle.getMin().x() + Math.random() * boundingRectangle.getDiagonal().x()));
				n.position.setY(
						(float) (boundingRectangle.getMin().y() + Math.random() * boundingRectangle.getDiagonal().y()));
			}

			if (boundingRectangle != null) {
				if (n.position.x() < boundingRectangle.getMin().x())
					n.position.setX(boundingRectangle.getMin().x());
				else if (n.position.x() > boundingRectangle.getMax().x())
					n.position.setX(boundingRectangle.getMax().x());
				if (n.position.y() < boundingRectangle.getMin().y())
					n.position.setY(boundingRectangle.getMin().y());
				else if (n.position.y() > boundingRectangle.getMax().y())
					n.position.setY(boundingRectangle.getMax().y());
			}
		}
	}

	public Node findNearestNode(float x, float y) {

		x -= 10;
		y = y > 0 ? (y - 15) : (y - 20);
		Node nearestNode = null;
		float smallestDistance = 0;
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			float dx = x - n.position.x();
			float dy = y - n.position.y();
			float distanceSquared = dx * dx + dy * dy;
			if (nearestNode == null || distanceSquared < smallestDistance) {
				smallestDistance = distanceSquared;
				nearestNode = n;
			}
		}
		if (smallestDistance <= 12 * 12)
			return nearestNode;
		return null;
	}

	public void visible() {
		this.getNodes().values().stream().forEach(node -> node.visible = true);
	}

	public void inVisible() {
		this.getNodes().values().stream().forEach(node -> node.visible = false);
	}

	public void rotate(GLPoint pivotPoint, double rotationDegree) {
		this.nodes.values().stream().forEach(n -> n.position.rotate(pivotPoint, rotationDegree));
	}

}
