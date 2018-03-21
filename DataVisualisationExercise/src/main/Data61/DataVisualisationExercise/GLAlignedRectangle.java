package Data61.DataVisualisationExercise;

/*
 * This class defines a rectangle specification in that decorator draws graph on the screen. 
 */
public class GLAlignedRectangle {

	public boolean isEmpty = true;
	private GLPoint min = new GLPoint(0,0);
	private GLPoint max = new GLPoint(0,0);

	public GLAlignedRectangle() {
	}
	
	public GLAlignedRectangle(GLPoint p0, GLPoint p1) {
		min.copy(p0);
		max.copy(p1);
		isEmpty = false;
	}

	public void clear() { isEmpty = true; }

	public void bound( GLPoint p ) {
		if (isEmpty) {
			min.copy(p);
			max.copy(p);
			isEmpty = false;
		}
		else {
			if ( p.x() < min.x() ) min.p[0] = p.x();
			else if ( p.x() > max.x() ) max.p[0] = p.x();

			if ( p.y() < min.y() ) min.p[1] = p.y();
			else if ( p.y() > max.y() ) max.p[1] = p.y();
		}
	}

	public void bound( GLAlignedRectangle rect ) {
		bound( rect.min );
		bound( rect.max );
	}

	public boolean isEmpty() { return isEmpty; }

	public boolean contains( GLPoint p ) {
		return !isEmpty
			&& min.x() <= p.x() && p.x() <= max.x()
			&& min.y() <= p.y() && p.y() <= max.y();
	}

	public GLPoint getMin() { return min; }
	
	public GLPoint getMax() { return max; }
	
	public GLVector getDiagonal() { return GLPoint.diff(max,min); }
	
	public GLPoint getCenter() {
		return GLPoint.average( min, max );
	}
}

