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
				n.x += distance * (Math.random() - 0.5f);
				n.y += distance * (Math.random() - 0.5f);
			} else {
				n.x = (float) (boundingRectangle.getMin().x() + Math.random() * boundingRectangle.getDiagonal().x());
				n.y = (float) (boundingRectangle.getMin().y() + Math.random() * boundingRectangle.getDiagonal().y());
			}

			if (boundingRectangle != null) {
				if (n.x < boundingRectangle.getMin().x())
					n.x = boundingRectangle.getMin().x();
				else if (n.x > boundingRectangle.getMax().x())
					n.x = boundingRectangle.getMax().x();
				if (n.y < boundingRectangle.getMin().y())
					n.y = boundingRectangle.getMin().y();
				else if (n.y > boundingRectangle.getMax().y())
					n.y = boundingRectangle.getMax().y();
			}
		}
	}

	public Node findNearestNode(float x, float y) {

		x = GraphicsDrawer.convertPixelsToWorldSpaceUnitsX(x);
		y = GraphicsDrawer.convertPixelsToWorldSpaceUnitsY(y);

		Node nearestNode = null;
		float smallestDistance = 0;
		for (int i = 0; i < nodes.size(); i++) {
			Node n = nodes.get(i);
			if (n == null) {
				System.out.println(i);
			}
			float dx = x - n.x;
			float dy = y - n.y;
			float distanceSquared = dx * dx + dy * dy;
			if (nearestNode == null || distanceSquared < 11) {
				smallestDistance = distanceSquared;
				nearestNode = n;
			}
		}
		if (smallestDistance <= 11 * 11)
			return nearestNode;
		return null;
	}

	public void visible() {
		this.getNodes().values().stream().forEach(node -> node.visible = true);
	}

	public void inVisible() {
		this.getNodes().values().stream().forEach(node -> node.visible = false);
	}

}
