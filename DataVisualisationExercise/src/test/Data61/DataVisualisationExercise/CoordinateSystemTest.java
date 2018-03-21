package Data61.DataVisualisationExercise;

import java.awt.Dimension;

import org.junit.Test;

import junit.framework.Assert;

public class CoordinateSystemTest {

	@Test
	public void shouldConvertCoordinateFrom0To100ToNegative100Positive100RandomPoints() {
		GLPoint input = GraphicsDrawer.convertUnit2CustomCoordinate(25, 52, new Dimension(200, 100));
		Assert.assertEquals(-150f, input.x());
		Assert.assertEquals(-4f, input.y());
	}

	@Test
	public void shouldConvertCoordinateFrom0To100ToNegative100Positive100BorderPoints() {
		GLPoint input1 = GraphicsDrawer.convertUnit2CustomCoordinate(0, 100, new Dimension(200, 100));
		Assert.assertEquals(-200f, input1.x());
		Assert.assertEquals(-100f, input1.y());

		GLPoint input2 = GraphicsDrawer.convertUnit2CustomCoordinate(200, 0, new Dimension(200, 100));
		Assert.assertEquals(200f, input2.x());
		Assert.assertEquals(100f, input2.y());
		
		GLPoint input3 = GraphicsDrawer.convertUnit2CustomCoordinate(0, 0, new Dimension(200, 100));
		Assert.assertEquals(-200f, input3.x());
		Assert.assertEquals(100f, input3.y());

		GLPoint input4 = GraphicsDrawer.convertUnit2CustomCoordinate(200, 100, new Dimension(200, 100));
		Assert.assertEquals(200f, input4.x());
		Assert.assertEquals(-100f, input4.y());

	}

	@Test
	public void shouldConvertCoordinateFrom0To100ToNegative100Positive100MiddlePoints() {
		GLPoint input = GraphicsDrawer.convertUnit2CustomCoordinate(100, 50, new Dimension(200, 100));
		Assert.assertEquals(0f, input.x());
		Assert.assertEquals(0f, input.y());
	}

}
