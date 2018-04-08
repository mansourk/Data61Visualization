package Data61.DataVisualisationExercise;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/*
 * This class keeps all the information of the network graph. 
 * Actually, visualizer and decorators use a singleton object of this class
 */
public class Network {

	private static final Network network = new Network();
	public static int DEFAULT_RADIUS = 12;

	private ConcurrentHashMap<Integer, Node> nodes = new ConcurrentHashMap<Integer, Node>();

	private Network() {

	}

	public static Network getNetworkInstance() {
		return network;
	}

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

	/*
	 * find Nearest Node to the given point
	 */
	public Node findNearestNode(float x, float y, float xOffSet, float yOffSet) {

		x += xOffSet;
		y += yOffSet;

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
		/*
		 * Check to consider any point within circle
		 */
		if (smallestDistance <= 2 * (DEFAULT_RADIUS * DEFAULT_RADIUS))
			return nearestNode;
		return null;
	}

	public void visible() {
		this.getNodes().values().stream().forEach(node -> node.visible = true);
	}

	public void inVisible() {
		this.getNodes().values().stream().forEach(node -> node.visible = false);
	}

	public void resetSize() {
		this.getNodes().values().stream().forEach(node -> node.centered = false);
	}

	public void rotate(GLPoint pivotPoint, double rotationDegree) {
		this.nodes.values().stream().forEach(n -> n.position.rotate(pivotPoint, rotationDegree));
	}

	public void runForceDirectedLayout() {
		calculateRepulsiveForces();
		calculateAttractiveForces();
		updateNodesPositions();
	}

	private void calculateAttractiveForces() {
		for (int i = 0; i < nodes.size(); ++i) {
			Node n1 = nodes.get(i);
			Iterator<Edge> iterator = n1.edges.iterator();
			while (iterator.hasNext()) {
				Node n2 = iterator.next().getTo();
				int n2_index = network.getIndexOfNode(n2);
				if (n2_index < i)
					continue;
				float dx = n2.position.x() - n1.position.x();
				float dy = n2.position.y() - n1.position.y();
				float distance = (float) Math.sqrt(dx * dx + dy * dy);
				if (distance > 0) {
					float force = 0.2f * distance;
					dx *= force / distance;
					dy *= force / distance;

					n1.force.addX(dx);
					n1.force.addY(dy);

					n2.force.subtractX(dx);
					n2.force.subtractY(dy);
				}
			}
		}
	}

	private void calculateRepulsiveForces() {
		for (int i = 0; i < nodes.size(); ++i) {
			Node n1 = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); ++j) {
				Node n2 = nodes.get(j);
				float dx = n2.position.x() - n1.position.x();
				float dy = n2.position.y() - n1.position.y();
				if (dx == 0 && dy == 0) {
					dx = (float) Math.random() - 0.5f;
					dy = (float) Math.random() - 0.5f;
				}
				float distanceSquared = dx * dx + dy * dy;
				float distance = (float) Math.sqrt(distanceSquared);
				float force = 20000f / distanceSquared;
				dx *= force / distance;
				dy *= force / distance;

				n1.force.subtractX(dx);
				n1.force.subtractY(dy);

				n2.force.addX(dx);
				n2.force.addY(dy);
			}
		}
	}

	final int MAX_ALLOWED_DISPLACEMENT = 10;

	private void updateNodesPositions() {

		for (int i = 0; i < nodes.size(); ++i) {
			Node n = nodes.get(i);
			n.weakenForceBy(MAX_ALLOWED_DISPLACEMENT);
			n.applyForce();
		}
	}
}
