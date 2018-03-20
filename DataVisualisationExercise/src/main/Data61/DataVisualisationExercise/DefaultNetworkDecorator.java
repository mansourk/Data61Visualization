package Data61.DataVisualisationExercise;

import java.awt.Dimension;

public class DefaultNetworkDecorator implements Shape {

	protected Network network;
	protected GraphVisualisationApp graphVisualisationApp;

	public DefaultNetworkDecorator(Network network, GraphVisualisationApp graphVisualisationApp) {
		this.network = network;
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

	protected void setPositionsOfNodes() {

		Dimension dimension = getDimension();

		network.randomizePositionsOfNodes(new GLAlignedRectangle(
				new GLPoint((float) (-dimension.getWidth() * 50 / 100), (float) (-dimension.getHeight() * 50 / 100)),
				new GLPoint((float) (dimension.getWidth() * 50 / 100), (float) (dimension.getHeight() * 50 / 100))));
	}

	protected void edgeLineWidth(float width) {
		GraphicsDrawer.setLineWidth(width);
	}

	protected void drawNodes() {
		network.getNodes().values().stream().filter(n -> n.isVisible()).forEach(n -> {
			n.radius = defaultNodeRadius();
			n.draw();
		});
	}

	protected float defaultNodeRadius() {
		return GraphicsDrawer.DEFAULT_NODE_RADIUS;
	}

	protected void drawEdges() {
		for (int i = 0; i < network.getNumNodes(); i++) {
			Node n = network.getNode(i);
			for (Edge edge : n.edges) {
				if (edge.getFrom().isVisible() && edge.getTo().isVisible()
						&& edge.getFrom().getIndex() < edge.getTo().getIndex())
					edge.draw();
			}
		}

	}

}
