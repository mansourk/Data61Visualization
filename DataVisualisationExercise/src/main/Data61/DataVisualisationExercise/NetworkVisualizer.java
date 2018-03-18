package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import com.jogamp.opengl.GLAutoDrawable;

public class NetworkVisualizer {

	public static final float DEFAULT_NODE_RADIUS = 6;

	public GraphVisualisationApp graphVisualisationApp = null;
	private GraphicsDrawer drawer = new GraphicsDrawer();
	private Network network = null;
	Random random = new Random(123456);
	ReentrantLock lock = new ReentrantLock(true);

	ArrayList<GLPoint> polygonDrawnAroundSelection = new ArrayList<GLPoint>();

	public NetworkVisualizer(GraphVisualisationApp graphVisualisationApp, Network network) {
		this.graphVisualisationApp = graphVisualisationApp;
		this.network = network;
		network.assignRandomColorsToAllNodes(random);
		Dimension dimension = graphVisualisationApp.getPreferredSize();
		float radius = (float) (Math.min(dimension.getWidth(), dimension.getHeight()) / 2.0f * 0.9f);
		network.randomizePositionsOfNodes(new GLAlignedRectangle(
				new GLPoint((float) dimension.getWidth() / 2 - radius, (float) dimension.getHeight() / 2 - radius),
				new GLPoint((float) dimension.getWidth() / 2 + radius, (float) dimension.getHeight() / 2 + radius)));
	}

	public void init(GLAutoDrawable drawable) {
		drawer.init(drawable);
	}

	public void resize(int w, int h) {
		drawer.resize(w, h);
	}

	private void drawNode(Node node, float radius, Color c, Color borderColder) {
		float x = node.x;
		float y = node.y;
		drawer.setColor(c);
		drawer.drawCircle(x - radius, y - radius, radius, true);
		drawer.setColor(borderColder);
		drawer.drawCircle(x - radius, y - radius, radius, false);

	}

	public void drawNetwork() {
		drawer.clear(1, 1, 1);
		drawer.setup();

		lock.lock();
		drawer.setCoordinateSystemToWorldSpaceUnits();
		drawer.setLineWidth(1);
		drawer.setColor(0, 0, 0);

		for (int i = 0; i < network.getNumNodes(); ++i) {
			Node n = network.getNode(i);
			for (int j = 0; j < n.neighbours.size(); ++j) {
				Node n2 = n.neighbours.get(j);
				int n2_index = network.getIndexOfNode(n2);
				if (n2_index > i) {
					drawer.drawLine(n.x, n.y, n2.x, n2.y);
				}
			}
		}
		Color foregroundColor = new Color(0.0f, 0.0f, 0.0f);

		for (int i = 0; i < network.getNumNodes(); ++i) {
			Node n = network.getNode(i);
			drawNode(n, DEFAULT_NODE_RADIUS, n.color, foregroundColor);
		}
		lock.unlock();
	}
}