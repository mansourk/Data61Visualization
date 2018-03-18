package Data61.DataVisualisationExercise;


import java.lang.Math;

public class GLVector {

	public float [] v = new float[2];

	public GLVector() {
		v[0] = v[1] = 0;
	}

	public GLVector( float x, float y ) {
		v[0] = x;
		v[1] = y;
	}

	public GLVector( GLPoint P ) {
		v[0] = P.p[0];
		v[1] = P.p[1];
	}

	public void copy( float x, float y ) {
		v[0] = x;
		v[1] = y;
	}

	public void copy( GLVector V ) {
		v[0] = V.v[0];
		v[1] = V.v[1];
	}

	public float x() { return v[0]; }
	public float y() { return v[1]; }

	public float lengthSquared() {
		return x()*x() + y()*y();
	}
	public float length() {
		return (float)Math.sqrt( lengthSquared() );
	}

	// The returned angle is in [0, 2*pi]
	public float angle() {
		return angle( 0 );
	}

	// The returned angle is in [lowerBound, lowerBound+2*pi]
	public float angle(
		float lowerBound // in radians
	) {
		float l = length();
		if ( l <= 0 )
			return lowerBound;
		float angle = (float)Math.asin(y()/l);
		// Now angle is in [-pi/2,pi/2]
		if ( x() < 0 )
			angle = (float)Math.PI - angle;
		// Now angle is in [-pi/2,3*pi/2]

		float TwoPi = (float)( 2*Math.PI );
		float upperBound = lowerBound + TwoPi;
		while ( angle > upperBound )
			angle -= TwoPi;
		while ( angle < lowerBound )
			angle += TwoPi;
		// Now angle is in [lowerBound, upperBound]
		return angle;
	}

	public GLVector negated() {
		return new GLVector(-x(),-y());
	}

	public GLVector normalized() {
		float l = length();
		if ( l > 0 ) {
			float k = 1/l; // scale factor
			return new GLVector(k*x(),k*y());
		}
		else return new GLVector(x(),y());
	}

	// returns the dot-product of the given vectors
	static public float dot( GLVector a, GLVector b ) {
		return a.x()*b.x() + a.y()*b.y();
	}

	// returns the sum of the given vectors
	static public GLVector sum( GLVector a, GLVector b ) {
		return new GLVector( a.x()+b.x(), a.y()+b.y() );
	}

	// returns the difference of the given vectors
	static public GLVector diff( GLVector a, GLVector b ) {
		return new GLVector( a.x()-b.x(), a.y()-b.y() );
	}

	// returns the product of the given vector and scalar
	static public GLVector mult( GLVector a, float b ) {
		return new GLVector( a.x()*b, a.y()*b );
	}

	// Computes the angle of rotation from v1 to v2 around the origin.
	// The angle returned is in the interval [-pi,pi],
	// where a positive angle corresponds to a counterclockwise rotation
	// (assuming x+ right, y+ up).
	static public float computeSignedAngle( GLVector v1, GLVector v2 ) {
		//return Vector3D.computeSignedAngle(
		//	new Vector3D( v1.x(), v1.y(), 0 ),
		//	new Vector3D( v2.x(), v2.y(), 0 ),
		//	new Vector3D( 0, 0, 1 )
		//);

		float productOfLengths = v1.length() * v2.length();
		if ( productOfLengths <= 0 )
			return 0;

		// Compute the z component of the cross product of v1 and v2
		// (Note that the x and y components of the cross product are zero,
		// because the z components of a and b are both zero)
		double crossProduct_z = v1.x()*v2.y() - v1.y()*v2.x();

		double sineOfAngle = Math.abs(crossProduct_z) / productOfLengths;

		// Due to numerical inaccuracies, the sine we computed
		// may be slightly more than 1.
		// Calling arcsin on such a value could be bad, so we don't.
		double angle = ( sineOfAngle >= 1 ) ? Math.PI/2 : Math.asin( sineOfAngle );

		// Compute the dot product of v1 and v2
		float dotProduct = GLVector.dot( v1, v2 );

		if ( dotProduct < 0 )
			angle = Math.PI - angle;

		if ( crossProduct_z < 0 )
			angle = - angle;

		return (float)angle;
	}

}
