package Data61.DataVisualisationExercise;

public class GLPoint {

	public float[] p = new float[2];

	public GLPoint() {
		p[0] = p[1] = 0;
	}

	public GLPoint(float x, float y) {
		p[0] = x;
		p[1] = y;
	}

	public float x() {
		return p[0];
	}

	public float y() {
		return p[1];
	}

	public float setX(float x) {
		return p[0] = x;
	}

	public float setY(float y) {
		return p[1] = y;
	}

	public void copy(GLPoint point) {
		p[0] = point.p[0];
		p[1] = point.p[1];
	}

	public void addX(float val) {
		p[0] += val;
	}

	public void addY(float val) {
		p[1] += val;
	}

	public void add(GLPoint a) {
		p[0] += a.x();
		p[1] += a.y();
	}

	public void subtractX(float val) {
		p[0] -= val;
	}

	public void subtractY(float val) {
		p[1] -= val;
	}

	public void subtract(GLPoint a) {
		p[0] -= a.x();
		p[1] -= a.y();
	}

	public void rotate(GLPoint pivotPoint, double rotationDegree) {
		subtract(pivotPoint);

		float temp = (float) (p[0] * Math.cos(Math.toRadians(rotationDegree))
				- p[1] * Math.sin(Math.toRadians(rotationDegree)));
		p[1] = (float) (p[0] * Math.sin(Math.toRadians(rotationDegree))
				+ p[1] * Math.cos(Math.toRadians(rotationDegree)));
		p[0] = temp;
		add(pivotPoint);
	}

	static public GLVector diff(GLPoint a, GLPoint b) {
		return new GLVector(a.x() - b.x(), a.y() - b.y());
	}

	static public GLPoint sum(GLPoint a, GLVector b) {
		return new GLPoint(a.x() + b.x(), a.y() + b.y());
	}

	public float distance(GLPoint otherPoint) {
		return diff(this, otherPoint).length();
	}

	static GLPoint average(GLPoint a, GLPoint b) {
		return new GLPoint((a.x() + b.x()) * 0.5f, (a.y() + b.y()) * 0.5f);
	}

	public boolean equals(GLPoint other) {
		return x() == other.x() && y() == other.y();
	}

}
