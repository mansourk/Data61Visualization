package Data61.DataVisualisationExercise;

import java.awt.Dimension;

/*
 * The base decorator that draws network graph. It colors all nodes black. 
 */
public class DefaultNetworkDecorator implements Shape {

	public static final int widthPercentage = 50;
	public static final int heightPercentage = 70;

	protected GraphVisualisationApp graphVisualisationApp;

	public DefaultNetworkDecorator(GraphVisualisationApp graphVisualisationApp) {
		this.graphVisualisationApp = graphVisualisationApp;
	}

	@Override
	public void draw() {

		setPositionsOfNodes();
		GraphicsDrawer.clear(1, 1, 1);
		GraphicsDrawer.setup();
		GraphicsDrawer.setCoordinateSystemToWorldSpaceUnits();
		edgeLineWidth(1);
		GraphicsDrawer.setColor(0, 0, 0);
		drawEdges();
		assignColorsToAllNodes();
		drawNodes();
	}

	protected void assignColorsToAllNodes() {

	}

	protected Dimension getDimension() {
		return graphVisualisationApp.getPreferredSize();
	}

	/*
	 * Assign a random position to each node.
	 */
	protected void setPositionsOfNodes() {

		Dimension dimension = getDimension();
		Network.getNetworkInstance()
				.randomizePositionsOfNodes(new GLAlignedRectangle(
						new GLPoint((float) (-dimension.getWidth() * widthPercentage / 100),
								(float) (-dimension.getHeight() * heightPercentage / 100)),
						new GLPoint((float) (dimension.getWidth() * widthPercentage / 100),
								(float) (dimension.getHeight() * heightPercentage / 100))));

	}

	protected void edgeLineWidth(float width) {
		GraphicsDrawer.setLineWidth(width);
	}

	protected void drawNodes() {
		Network.getNetworkInstance().getNodes().values().stream().filter(n -> n.isVisible()).forEach(n -> {
			n.radius = defaultNodeRadius();
			n.draw();
		});
	}

	protected float defaultNodeRadius() {
		return GraphicsDrawer.DEFAULT_NODE_RADIUS;
	}

	protected void drawEdges() {
		for (int i = 0; i < Network.getNetworkInstance().getNumNodes(); i++) {
			Node n = Network.getNetworkInstance().getNode(i);
			for (Edge edge : n.edges) {
				if (edge.getFrom().isVisible() && edge.getTo().isVisible()
						&& edge.getFrom().getIndex() < edge.getTo().getIndex())
					edge.draw();
			}
		}
	}

}
