package Data61.DataVisualisationExercise;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
 *  This class does a breath-first-search to the given level starting centered node as root.
 *  It is used to find nodes and edges that should be presented when center graph based on a node
 */
public class NetworkTraversal {

	public static int DEFAULT_TRAVERSAL_LEVEL = 2;

	public static void traverse(Node center) {

		Queue<Node> queue = new LinkedList<Node>();
		HashSet<Node> visited = new HashSet<Node>();

		Network.getNetworkInstance().inVisible();
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
