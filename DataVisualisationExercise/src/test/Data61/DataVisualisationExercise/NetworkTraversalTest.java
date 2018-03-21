package Data61.DataVisualisationExercise;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class NetworkTraversalTest {

	Network network = new Network();

	@Before
	public void setUP() {

		Node node1 = new Node("lable0", 0);
		node1.position.setX(5);
		node1.position.setY(10);
		network.addNode(node1);

		Node node2 = new Node("lable1", 1);
		node2.position.setX(8);
		node2.position.setY(10);
		network.addNode(node2);

		Node node3 = new Node("lable2", 2);
		node3.position.setX(0);
		node3.position.setY(2);
		network.addNode(node3);

		Node node4 = new Node("lable3", 3);
		node4.position.setX(-2);
		node4.position.setY(2);
		network.addNode(node4);

		Node node5 = new Node("lable4", 4);
		node5.position.setX(10);
		node5.position.setY(15);
		network.addNode(node5);

		Node node6 = new Node("lable5", 5);
		node6.position.setX(10);
		node6.position.setY(11);
		network.addNode(node6);

		network.addEdge(0, 1);
		network.addEdge(0, 2);
		network.addEdge(1, 2);
		network.addEdge(2, 4);
		network.addEdge(2, 5);
		network.addEdge(3, 4);
		network.addEdge(3, 5);

		Network.DEFAULT_RADIUS = 2;

	}

	@Test
	public void shouldTraverseAllNodesForLevel3StartFromNode0() {

		NetworkTraversal.DEFAULT_TRAVERSAL_LEVEL = 3;
		NetworkTraversal.traverse(network, network.getNode(0));

		for (Node node : network.getNodes().values()) {
			Assert.assertTrue(node.isVisible());
		}
	}

	@Test
	public void shouldTraverseSomeofNodesForLevel2StartFromNode0() {

		NetworkTraversal.DEFAULT_TRAVERSAL_LEVEL = 2;
		NetworkTraversal.traverse(network, network.getNode(0));

		for (Node node : network.getNodes().values()) {
			if (node.label.equals("lable3")) {
				Assert.assertFalse(node.isVisible());
			} else {
				Assert.assertTrue(node.isVisible());
			}
		}
	}

}
