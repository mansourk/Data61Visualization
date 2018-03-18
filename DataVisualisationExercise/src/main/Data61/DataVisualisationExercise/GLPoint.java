package Data61.DataVisualisationExercise;

public class GLPoint {

	public float [] p = new float[2];

	public GLPoint() {
		p[0] = p[1] = 0;
	}

	public GLPoint( float x, float y ) {
		p[0] = x;
		p[1] = y;
	}

	public GLPoint( GLPoint P ) {
		p[0] = P.p[0];
		p[1] = P.p[1];
	}

	public GLPoint( GLVector V ) {
		p[0] = V.v[0];
		p[1] = V.v[1];
	}

	public void copy( float x, float y ) {
		p[0] = x;
		p[1] = y;
	}

	public void copy( GLPoint P ) {
		p[0] = P.p[0];
		p[1] = P.p[1];
	}

	public boolean equals( GLPoint other ) {
		return x() == other.x() && y() == other.y();
	}

	public float x() { return p[0]; }
	public float y() { return p[1]; }

	public float [] get() { return p; }

	static public GLVector diff( GLPoint a, GLPoint b ) {
		return new GLVector( a.x()-b.x(), a.y()-b.y() );
	}

	static public GLPoint sum( GLPoint a, GLVector b ) {
		return new GLPoint( a.x()+b.x(), a.y()+b.y() );
	}

	static public GLPoint diff( GLPoint a, GLVector b ) {
		return new GLPoint( a.x()-b.x(), a.y()-b.y() );
	}

	public float distance( GLPoint otherPoint ) {
		return diff( this, otherPoint ).length();
	}

	static GLPoint average( GLPoint a, GLPoint b ) {
		return new GLPoint( (a.x()+b.x())*0.5f, (a.y()+b.y())*0.5f );
	}

}

