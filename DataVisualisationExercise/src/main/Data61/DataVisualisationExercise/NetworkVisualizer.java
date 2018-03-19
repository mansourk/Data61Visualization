package Data61.DataVisualisationExercise;

import java.util.concurrent.locks.ReentrantLock;

import com.jogamp.opengl.GLAutoDrawable;

public class NetworkVisualizer {

	private GraphVisualisationApp graphVisualisationApp = null;
	private Network network;
	private Shape networkDecorator = null;
	ReentrantLock drawNetworkLock = new ReentrantLock(true);
	ReentrantLock userActionLock = new ReentrantLock(true);
	boolean isToolTipOn = false;

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

		userActionLock.lock();

		Node node = network.findNearestNode(mouseX, mouseY);
		if (node != null || isToolTipOn) {
			((ToolTipLableNetworkDecorator) networkDecorator).setToolTipNode(node);
			graphVisualisationApp.redraw();
			isToolTipOn = (node != null);
		}
		
		userActionLock.unlock();
		
	}

	public void handleCenterGraphOnNode(float mouseX, float mouseY) {

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