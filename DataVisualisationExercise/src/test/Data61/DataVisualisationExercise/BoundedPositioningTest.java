package Data61.DataVisualisationExercise;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class BoundedPositioningTest {

	Network network = Network.getNetworkInstance();

	@Before
	public void setUp() {
		network.addNode(new Node("lable1", 0));
		network.addNode(new Node("lable1", 1));
		network.addNode(new Node("lable1", 2));
		network.addNode(new Node("lable1", 3));
		network.addNode(new Node("lable1", 4));
		network.addNode(new Node("lable1", 5));

		network.addEdge(0, 1);
		network.addEdge(0, 2);
		network.addEdge(1, 3);
		network.addEdge(1, 2);
		network.addEdge(2, 4);
		network.addEdge(2, 5);
		network.addEdge(3, 5);
		network.addEdge(4, 4);
		network.addEdge(4, 5);
	}

	@Test
	public void coordinatesOfNodesShouldbeWithinBoundray() {

		network.randomizePositionsOfNodes(new GLAlignedRectangle(new GLPoint(-5, -5), new GLPoint(5, 5)));

		for (Node node : network.getNodes().values()) {
			Assert.assertEquals(true, node.position.x() >= -5);
			Assert.assertEquals(true, node.position.x() <= 5);
			Assert.assertEquals(true, node.position.y() > -5);
			Assert.assertEquals(true, node.position.y() > -5);
		}
	}
}