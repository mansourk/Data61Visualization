package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.util.Random;

public class RandomColorNetworkDecorator extends DefaultNetworkDecorator {

	public RandomColorNetworkDecorator(Network network, GraphVisualisationApp graphVisualisationApp) {
		super(network, graphVisualisationApp);
	}

	protected void assignColorsToAllNodes() {
		Random random = new Random();
		for (int i = 0; i < network.getNumNodes();i++) {
			Node n = network.getNode(i);
			int threeBits = random.nextInt(7);
			n.color = new Color((threeBits & 4) != 0 ? 1.0f : 0, (threeBits & 2) != 0 ? 1.0f : 0,
					(threeBits & 1) != 0 ? 1.0f : 0);
		}
	}

}
