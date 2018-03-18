package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Network {

	private ArrayList<Node> nodes = new ArrayList<Node>();

	public void assignRandomColorsToAllNodes(Random random) {
		for (int i = 0; i < nodes.size(); ++i) {
			Node n = nodes.get(i);
			int threeBits = random.nextInt(7);
			n.color = new Color((threeBits & 4) != 0 ? 1.0f : 0, (threeBits & 2) != 0 ? 1.0f : 0,
					(threeBits & 1) != 0 ? 1.0f : 0);
		}
	}

	public int getNumNodes() {
		return nodes.size();
	}

	public Node getNode(int index) {
		if (index < 0 || index >= nodes.size())
			return null;
		return nodes.get(index);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void addNode(Node n) {
		if (!nodes.contains(n))
			nodes.add(n);
	}

	public void addEdge(Node n1, Node n2) {
		if (n1 == n2 || n1 == null || n2 == null)
			return;
		if (!n1.neighbours.contains(n2)) {
			n1.neighbours.add(n2);
		}
		if (!n2.neighbours.contains(n1)) {
			n2.neighbours.add(n1);
		}
	}

	public void addEdge(int nodeIndex1, int nodeIndex2) {
		addEdge(getNode(nodeIndex1), getNode(nodeIndex2));
	}

	public int getIndexOfNode(Node n) {
		assert n != null;
		int index = n.getIndex();
		if (index < 0 || index >= nodes.size() || getNode(index) != n) {
			for (int i = 0; i < nodes.size(); ++i) {
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
		for (int i = 0; i < nodes.size(); ++i) {
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
}
