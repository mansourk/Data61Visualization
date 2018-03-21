package Data61.DataVisualisationExercise;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkTraversal {

	public static int DEFAULT_TRAVERSAL_LEVEL = 3;

	public static void traverse(Network network, Node center) {

		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Node> visited = new HashSet<Node>();

		network.inVisible();
		queue.add(center);
		queue.add(null);
		int levelCounter = 0;

		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node == null) {
				levelCounter++;
				if (levelCounter < DEFAULT_TRAVERSAL_LEVEL)
					queue.add(null);
			} else {
				node.visible = true;
				visited.add(node);
				if (levelCounter < DEFAULT_TRAVERSAL_LEVEL)
					node.edges.stream().filter(edge -> !visited.contains(edge.getTo()))
							.forEach(edge -> queue.add(edge.getTo()));
			}
		}
	}
}
