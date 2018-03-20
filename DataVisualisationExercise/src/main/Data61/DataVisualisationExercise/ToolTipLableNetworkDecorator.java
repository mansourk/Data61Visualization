package Data61.DataVisualisationExercise;

import java.awt.Color;

public class ToolTipLableNetworkDecorator extends RandomColorNetworkDecorator {

	protected Node toolTipNode;
	protected boolean isPositioningDone = false;
	protected boolean isRandomColoringDone = false;

	public ToolTipLableNetworkDecorator(Network network, GraphVisualisationApp graphVisualisationApp,
			Node toolTipNode) {
		super(network, graphVisualisationApp);
		this.toolTipNode = toolTipNode;
	}

	public ToolTipLableNetworkDecorator(Network network, GraphVisualisationApp graphVisualisationApp, Node toolTipNode,
			boolean isPositioningDone, boolean isRandomColoringDone) {
		this(network, graphVisualisationApp, toolTipNode);
		this.isPositioningDone = isPositioningDone;
		this.isRandomColoringDone = isRandomColoringDone;
	}

	@Override
	public void draw() {
		super.draw();
		if (toolTipNode != null	&& toolTipNode.visible) {
			drawBackGround(toolTipNode);
			GraphicsDrawer.drawText(toolTipNode.label, toolTipNode.position.x(), toolTipNode.position.y(), toolTipNode.radius,
					toolTipNode.color);
		}
	}

	private void drawBackGround(Node toolTipNode) {
		GraphicsDrawer.setColor(Color.WHITE);
		float width = (toolTipNode.label.length() * 24);
		float height = (toolTipNode.radius) + 36;
		float y = toolTipNode.position.y() - toolTipNode.radius;
		GraphicsDrawer.drawRectangle(toolTipNode.position.x(), y, width, height);
	}

	public void setToolTipNode(Node toolTipNode) {
		this.toolTipNode = toolTipNode;
	}
	
	public void setNetwork(Network network) {
		this.network = network;
	}

	protected void assignColorsToAllNodes() {
		if (!isRandomColoringDone) {
			super.assignColorsToAllNodes();
			isRandomColoringDone = true;
		}
	}

	protected void setPositionsOfNodes() {
		if (!isPositioningDone) {
			super.setPositionsOfNodes();
			isPositioningDone = true;
		}
	}
}
