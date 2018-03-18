package Data61.DataVisualisationExercise;

import java.lang.Math;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.Color;

class GraphicsDrawer {

	private static GL2 GL2 = null;
	private static GLUT glut = null;

	private static int windowWidthInPixels = 10;
	private static int windowHeightInPixels = 10;
	private static boolean hasFrameOrResizeBeenCalledBefore = false;
	private static float offsetXInPixels = 0;
	private static float offsetYInPixels = 0;
	private static float scaleFactorInWorldSpaceUnitsPerPixel = 1.0f;
	public static final float DEFAULT_NODE_RADIUS = 6;

	public static void init(GLAutoDrawable drawable) {
		GL2 = drawable.getGL().getGL2();
		if (glut == null)
			glut = new GLUT();
	}

	
	public static void resize(int w, int h) {
		if (!hasFrameOrResizeBeenCalledBefore) {
			windowWidthInPixels = w;
			windowHeightInPixels = h;
			GL2.glViewport(0, 0, w, h);
			hasFrameOrResizeBeenCalledBefore = true;
			return;
		}

		windowWidthInPixels = w;
		windowHeightInPixels = h;
		GL2.glViewport(0, 0, w, h);
	}

	public static void setCoordinateSystemToWorldSpaceUnits() {
		GL2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		GL2.glLoadIdentity();
		GL2.glScalef(1, -1, 1);
		GL2.glTranslatef(-1, -1, 0);
		GL2.glScalef(2.0f / (scaleFactorInWorldSpaceUnitsPerPixel * windowWidthInPixels),
				2.0f / (scaleFactorInWorldSpaceUnitsPerPixel * windowHeightInPixels), 1);
		GL2.glTranslatef(offsetXInPixels * scaleFactorInWorldSpaceUnitsPerPixel,
				offsetYInPixels * scaleFactorInWorldSpaceUnitsPerPixel, 0);
	}

	public static void clear(float r, float g, float b) {
		GL2.glClearColor(r, g, b, 0);
		GL2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	}

	public static void setup() {
		GL2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		GL2.glLoadIdentity();
		GL2.glDisable(GL.GL_DEPTH_TEST);
		GL2.glDisable(GL.GL_CULL_FACE);
		GL2.glDisable(GLLightingFunc.GL_LIGHTING);
		GL2.glShadeModel(GLLightingFunc.GL_FLAT);
	}

	public static void setColor(float r, float g, float b) {
		GL2.glColor3f(r, g, b);
	}

	public static void setColor(Color c) {
		setColor(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f);
	}

	public static void setLineWidth(float width) {
		GL2.glLineWidth(width);
	}

	public static void drawLine(float x1, float y1, float x2, float y2) {
		GL2.glBegin(GL.GL_LINES);
		GL2.glVertex3f(x1, y1, 0);
		GL2.glVertex3f(x2, y2, 0);
		GL2.glEnd();
		GL2.glBegin(GL.GL_POINTS);
		GL2.glVertex2f(x2, y2);
		GL2.glEnd();
	}

	public static void drawCircle(float x, float y, float radius, boolean isFilled) {
		x += radius;
		y += radius;
		if (isFilled) {
			GL2.glBegin(GL.GL_TRIANGLE_FAN);
			GL2.glVertex2f(x, y);
		} else
			GL2.glBegin(GL.GL_LINE_LOOP);

		int numSides = (int) (2 * Math.PI * radius + 1);
		float deltaAngle = 2 * (float) Math.PI / numSides;

		for (int i = 0; i <= numSides; ++i) {
			float angle = i * deltaAngle;
			GL2.glVertex2f(x + radius * (float) Math.cos(angle), y + radius * (float) Math.sin(angle));
		}
		GL2.glEnd();
	}
}
