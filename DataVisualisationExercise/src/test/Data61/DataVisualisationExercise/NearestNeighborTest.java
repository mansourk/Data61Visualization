package Data61.DataVisualisationExercise;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class NearestNeighborTest {

	Network network = Network.getNetworkInstance();

	@Before
	public void setUP() {

		Network.getNetworkInstance().getNodes().clear();
		
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
		network.addEdge(1, 3);
		network.addEdge(1, 2);
		network.addEdge(2, 4);
		network.addEdge(2, 5);
		network.addEdge(3, 5);
		network.addEdge(4, 4);
		network.addEdge(4, 5);

		Network.DEFAULT_RADIUS = 2;

	}

	@Test
	public void shouldReturnNodeWithIdenticalCoordinatesAsNearestNeighbor() {
		Node node = network.findNearestNode(0, 2, 0, 0);
		Assert.assertEquals(node.label, "lable2");
	}

	@Test
	public void shouldReturnNodeAsNearestNeighbor() {
		Node node = network.findNearestNode(6, 10, 0, 0);
		Assert.assertEquals(node.label, "lable0");
	}

	@Test
	public void shouldNotReturnNodeAsNearestNeighbor() {
		Node node = network.findNearestNode(13, 18, 0, 0);
		Assert.assertNull(node);
	}

	@Test
	public void shouldReturnNodeAsNearestNeighborBecauseItIsAsBorderPoint() {
		Node node = network.findNearestNode(12, 17, 0, 0);
		Assert.assertEquals(node.label, "lable4");
	}

}
