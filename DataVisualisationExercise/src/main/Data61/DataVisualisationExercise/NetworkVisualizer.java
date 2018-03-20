package Data61.DataVisualisationExercise;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.jogamp.opengl.GLAutoDrawable;

public class NetworkVisualizer {

	private GraphVisualisationApp graphVisualisationApp = null;
	private Network network;
	private Shape networkDecorator = null;
	private ReentrantLock drawNetworkLock = new ReentrantLock(true);
	private ReentrantLock userActionLock = new ReentrantLock(true);
	private boolean isToolTipOn = false;
	private boolean isRotating = false;

	public NetworkVisualizer(GraphVisualisationApp graphVisualisationApp, Network network) {
		this.network = network;
		this.graphVisualisationApp = graphVisualisationApp;
		this.networkDecorator = new ToolTipLableNetworkDecorator(network, graphVisualisationApp, null);
	}

	public void init(GLAutoDrawable drawable) {
		GraphicsDrawer.init(drawable);
	}

	public void resize(int w, int h) {
		GraphicsDrawer.resize(w, h);
	}

	public void handleToolTip(float mouseX, float mouseY) {

		if (isRotating)
			return;

		userActionLock.lock();

		Node node = network.findNearestNode(mouseX, mouseY);
		if (node != null || isToolTipOn) {
			((ToolTipLableNetworkDecorator) networkDecorator).setToolTipNode(node);
			graphVisualisationApp.redraw();
			isToolTipOn = (node != null);
		}

		userActionLock.unlock();

	}

	ScheduledExecutorService scheduledExecutorService = null;

	public void startRotating() {

		if (isRotating)
			return;

		scheduledExecutorService = Executors.newScheduledThreadPool(1);
		isRotating = true;
		this.networkDecorator = new RotationNetworkDecorator(network, graphVisualisationApp);

		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				network.rotate(new GLPoint(2, 2), 90);
				graphVisualisationApp.redraw();
			}
		}, 1, 1, TimeUnit.SECONDS);

	}

	public void stopRotating() {
		if (isRotating) {
			isRotating = false;
			scheduledExecutorService.shutdown();
			this.networkDecorator = new ToolTipLableNetworkDecorator(network, graphVisualisationApp, null, true, true);
		}
	}

	public void handleCenterGraphOnNode(float mouseX, float mouseY) {

		if (isRotating)
			return;

		userActionLock.lock();

		Node centerNode = network.findNearestNode(mouseX, mouseY);

		if (centerNode != null) {
			NetworkTraversal.traverse(network, centerNode);
		} else {
			network.visible();
		}

		ToolTipLableNetworkDecorator decorator = (ToolTipLableNetworkDecorator) networkDecorator;
		decorator.setToolTipNode(centerNode);
		decorator.setNetwork(network);
		graphVisualisationApp.redraw();

		userActionLock.unlock();

	}

	public void drawNetwork() {
		drawNetworkLock.lock();
		networkDecorator.draw();
		drawNetworkLock.unlock();
	}
}