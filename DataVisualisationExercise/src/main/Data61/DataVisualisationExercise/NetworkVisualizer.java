package Data61.DataVisualisationExercise;

import java.util.concurrent.locks.ReentrantLock;

import com.jogamp.opengl.GLAutoDrawable;

public class NetworkVisualizer {

	public GraphVisualisationApp graphVisualisationApp = null;
	private Shape networkDecorator = null;
	ReentrantLock lock = new ReentrantLock(true);

	public NetworkVisualizer(GraphVisualisationApp graphVisualisationApp, Network network) {
		this.graphVisualisationApp = graphVisualisationApp;
		this.networkDecorator = new RandomColorNetworkDecorator(network, graphVisualisationApp);
	}

	public void init(GLAutoDrawable drawable) {
		GraphicsDrawer.init(drawable);
	}

	public void resize(int w, int h) {
		GraphicsDrawer.resize(w, h);
	}

	public void drawNetwork() {

		lock.lock();
		networkDecorator.draw();
		lock.unlock();
	}
}