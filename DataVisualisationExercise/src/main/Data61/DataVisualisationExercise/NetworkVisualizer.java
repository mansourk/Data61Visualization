package Data61.DataVisualisationExercise;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.jogamp.opengl.GLAutoDrawable;

/*
 * Manage any visualization task.
 */
public class NetworkVisualizer {

	public static int DEFAULT_PERIOD_IN_SECOND = 1;
	public static int DEFAULT_INITIAL_DELAY = 1;
	public static int DEFAULT_ROTATION_DEGREE = 30;
	public static GLPoint DEFAULT_PIVOT_POINT = new GLPoint(2, 2);

	private GraphVisualisationApp graphVisualisationApp = null;
	private Shape networkDecorator = null;
	private ReentrantLock drawNetworkLock = new ReentrantLock(true);
	private ReentrantLock userActionLock = new ReentrantLock(true);
	private boolean isToolTipOn = false;
	private boolean isRotating = false;
	private boolean isForceDirectedDrawing = false;

	public NetworkVisualizer(GraphVisualisationApp graphVisualisationApp) {
		this.graphVisualisationApp = graphVisualisationApp;
		this.networkDecorator = new ToolTipLableNetworkDecorator(graphVisualisationApp, null);
	}

	public void init(GLAutoDrawable drawable) {
		GraphicsDrawer.init(drawable);
	}

	public void resize(int w, int h) {
		GraphicsDrawer.resize(w, h);
	}

	public void handleToolTip(float mouseX, float mouseY) {

		if (isRotating || isForceDirectedDrawing)
			return;

		userActionLock.lock();

		GLPoint input = GraphicsDrawer.convertUnit2CustomCoordinate(mouseX, mouseY,
				graphVisualisationApp.getPreferredSize());
		Node node = Network.getNetworkInstance().findNearestNode(input.x(), input.y(), -10, -20);
		if (node != null || isToolTipOn) {
			((ToolTipLableNetworkDecorator) networkDecorator).setToolTipNode(node);
			graphVisualisationApp.redraw();
			isToolTipOn = (node != null);
		}
		userActionLock.unlock();

	}

	ScheduledExecutorService scheduledExecutorService = null;

	public void startRotating() {

		if (isRotating || isForceDirectedDrawing)
			return;

		scheduledExecutorService = Executors.newScheduledThreadPool(1);
		isRotating = true;
		this.networkDecorator = new RotationNetworkDecorator(Network.getNetworkInstance(), graphVisualisationApp);

		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Network.getNetworkInstance().rotate(DEFAULT_PIVOT_POINT, DEFAULT_ROTATION_DEGREE);
				graphVisualisationApp.redraw();
			}
		}, 1, DEFAULT_PERIOD_IN_SECOND, TimeUnit.SECONDS);

	}

	public void stopRotating() {

		if (isForceDirectedDrawing)
			return;

		if (isRotating) {
			isRotating = false;
			scheduledExecutorService.shutdown();
			this.networkDecorator = new ToolTipLableNetworkDecorator(graphVisualisationApp, null, true, true);
		}
	}

	public void startForceDirectedDrawing() {

		if (isRotating || isForceDirectedDrawing)
			return;

		scheduledExecutorService = Executors.newScheduledThreadPool(1);
		isForceDirectedDrawing = true;
		this.networkDecorator = new RotationNetworkDecorator(Network.getNetworkInstance(), graphVisualisationApp);

		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Network.getNetworkInstance().forceDirect();
				graphVisualisationApp.redraw();
			}
		}, 1, 100, TimeUnit.MILLISECONDS);

	}

	public void stopForceDirectedDrawing() {
		if (isForceDirectedDrawing) {
			isForceDirectedDrawing = false;
			scheduledExecutorService.shutdown();
			this.networkDecorator = new ToolTipLableNetworkDecorator(graphVisualisationApp, null, true, true);
		}
	}

	public void handleCenterGraphOnNode(float mouseX, float mouseY) {

		if (isRotating || isForceDirectedDrawing)
			return;

		userActionLock.lock();

		Network.getNetworkInstance().resetSize();
		GLPoint input = GraphicsDrawer.convertUnit2CustomCoordinate(mouseX, mouseY,
				graphVisualisationApp.getPreferredSize());
		Node centerNode = Network.getNetworkInstance().findNearestNode(input.x(), input.y(), -10, -20);

		if (centerNode != null) {
			NetworkTraversal.traverse(centerNode);
			centerNode.centered = true;
		} else {
			Network.getNetworkInstance().visible();
		}

		ToolTipLableNetworkDecorator decorator = (ToolTipLableNetworkDecorator) networkDecorator;
		decorator.setToolTipNode(centerNode);
		graphVisualisationApp.redraw();

		userActionLock.unlock();

	}

	public void drawNetwork() {
		drawNetworkLock.lock();
		networkDecorator.draw();
		drawNetworkLock.unlock();
	}
}