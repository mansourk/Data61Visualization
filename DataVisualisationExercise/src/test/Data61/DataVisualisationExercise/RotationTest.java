package Data61.DataVisualisationExercise;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class RotationTest {

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
		node3.position.setX(1);
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
	public void testRotationBasedonOriginPoint() {
		network.rotate(new GLPoint(0, 0), 45);

		for (Node node : network.getNodes().values()) {

			if (node.label.equals("lable0")) {

				Assert.assertEquals(-4, Math.round(node.position.x()));
				Assert.assertEquals(11, Math.round(node.position.y()));

			} else if (node.label.equals("lable1")) {

				Assert.assertEquals(-1, Math.round(node.position.x()));
				Assert.assertEquals(13, Math.round(node.position.y()));

			} else if (node.label.equals("lable2")) {

				Assert.assertEquals(-1, Math.round(node.position.x()));
				Assert.assertEquals(2, Math.round(node.position.y()));

			} else if (node.label.equals("lable3")) {

				Assert.assertEquals(-3, Math.round(node.position.x()));
				Assert.assertEquals(0, Math.round(node.position.y()));

			} else if (node.label.equals("lable4")) {

				Assert.assertEquals(-4, Math.round(node.position.x()));
				Assert.assertEquals(18, Math.round(node.position.y()));

			} else if (node.label.equals("lable5")) {

				Assert.assertEquals(-1, Math.round(node.position.x()));
				Assert.assertEquals(15, Math.round(node.position.y()));

			}
		}
	}

	@Test
	public void testRotationBasedonAPivotPoint() {

		network.rotate(new GLPoint(2, 2), 45);

		for (Node node : network.getNodes().values()) {

			if (node.label.equals("lable0")) {

				Assert.assertEquals(-2, Math.round(node.position.x()));
				Assert.assertEquals(10, Math.round(node.position.y()));

			} else if (node.label.equals("lable1")) {

				Assert.assertEquals(1, Math.round(node.position.x()));
				Assert.assertEquals(12, Math.round(node.position.y()));

			} else if (node.label.equals("lable2")) {

				Assert.assertEquals(1, Math.round(node.position.x()));
				Assert.assertEquals(1, Math.round(node.position.y()));

			} else if (node.label.equals("lable3")) {

				Assert.assertEquals(-1, Math.round(node.position.x()));
				Assert.assertEquals(-1, Math.round(node.position.y()));

			} else if (node.label.equals("lable4")) {

				Assert.assertEquals(-2, Math.round(node.position.x()));
				Assert.assertEquals(17, Math.round(node.position.y()));

			} else if (node.label.equals("lable5")) {

				Assert.assertEquals(1, Math.round(node.position.x()));
				Assert.assertEquals(14, Math.round(node.position.y()));

			}
		}
	}

	@Test
	public void testRotationBasedonOriginPoint360Degree() {

		network.rotate(new GLPoint(0, 0), 360);

		for (Node node : network.getNodes().values()) {

			if (node.label.equals("lable0")) {

				Assert.assertEquals(5f, node.position.x());
				Assert.assertEquals(10f, node.position.y());

			} else if (node.label.equals("lable1")) {

				Assert.assertEquals(8f, node.position.x());
				Assert.assertEquals(10f, node.position.y());

			} else if (node.label.equals("lable2")) {

				Assert.assertEquals(1f, node.position.x());
				Assert.assertEquals(2f, node.position.y());

			} else if (node.label.equals("lable3")) {

				Assert.assertEquals(-2f, node.position.x());
				Assert.assertEquals(2f, node.position.y());

			} else if (node.label.equals("lable4")) {

				Assert.assertEquals(10f, node.position.x());
				Assert.assertEquals(15f, node.position.y());

			} else if (node.label.equals("lable5")) {

				Assert.assertEquals(10f, node.position.x());
				Assert.assertEquals(11f, node.position.y());

			}

		}
	}

}
