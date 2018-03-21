package Data61.DataVisualisationExercise;

import java.awt.Color;
import java.util.Random;

/*
 *  This decorator adds randomized coloring to nodes to the default decorator 
 */
public class RandomColorNetworkDecorator extends DefaultNetworkDecorator {

	public RandomColorNetworkDecorator(GraphVisualisationApp graphVisualisationApp) {
		super(graphVisualisationApp);
	}

	protected void assignColorsToAllNodes() {
		Random random = new Random();
		for (int i = 0; i < Network.getNetworkInstance().getNumNodes(); i++) {
			Node n = Network.getNetworkInstance().getNode(i);
			int threeBits = random.nextInt(7);
			n.color = new Color((threeBits & 4) != 0 ? 1.0f : 0, (threeBits & 2) != 0 ? 1.0f : 0,
					(threeBits & 1) != 0 ? 1.0f : 0);
		}
	}

}
