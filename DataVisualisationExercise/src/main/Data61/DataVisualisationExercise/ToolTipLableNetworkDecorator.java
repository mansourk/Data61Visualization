package Data61.DataVisualisationExercise;

import java.awt.Color;

public class ToolTipLableNetworkDecorator extends RandomColorNetworkDecorator {

	protected Node toolTipNode;
	private boolean isPositioningDone = false;
	private boolean isRandomColoringDone = false;

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
		if (toolTipNode != null) {
			drawBackGround(toolTipNode);
			GraphicsDrawer.drawText(toolTipNode.label, toolTipNode.x, toolTipNode.y, toolTipNode.radius,
					toolTipNode.color);
		}
	}

	private void drawBackGround(Node toolTipNode) {
		GraphicsDrawer.setColor(Color.WHITE);
		float width = (toolTipNode.label.length() * 12);
		float height = (toolTipNode.radius) + 12;
		float y = toolTipNode.y - 6 - toolTipNode.radius / 2;
		GraphicsDrawer.drawRectangle(toolTipNode.x, y, width, height);
	}

	public void setToolTipNode(Node toolTipNode) {
		this.toolTipNode = toolTipNode;
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
