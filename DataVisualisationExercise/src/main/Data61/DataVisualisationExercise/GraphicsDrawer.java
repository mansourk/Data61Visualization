package Data61.DataVisualisationExercise;

import java.lang.Math;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.gl2.GLUT;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

class GraphicsDrawer {

	private static GL2 GL2 = null;
	private static GLUT glut = null;

	private static boolean hasFrameOrResizeBeenCalledBefore = false;
	public static final float DEFAULT_NODE_RADIUS = 12;

	public static void init(GLAutoDrawable drawable) {
		GL2 = drawable.getGL().getGL2();
		if (glut == null)
			glut = new GLUT();
	}

	public static void resize(int w, int h) {
		if (!hasFrameOrResizeBeenCalledBefore) {
			GL2.glViewport(0, 0, w, h);
			hasFrameOrResizeBeenCalledBefore = true;
			return;
		}

		GL2.glViewport(0, 0, w, h);
	}

	public static void setCoordinateSystemToWorldSpaceUnits() {
		GL2.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		GL2.glLoadIdentity();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		GL2.glOrtho(-dimension.getWidth(), dimension.getWidth(), -dimension.getHeight(), dimension.getHeight(), -1, 1);
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

		if (isFilled) {
			GL2.glBegin(GL.GL_TRIANGLE_FAN);
			GL2.glVertex2f(x, y);
		} else {
			GL2.glBegin(GL.GL_LINE_LOOP);
		}

		int numSides = (int) (2 * Math.PI * radius + 1);
		float deltaAngle = 2 * (float) Math.PI / numSides;

		for (int i = 0; i <= numSides; ++i) {
			float angle = i * deltaAngle;
			GL2.glVertex2f(x + radius * (float) Math.cos(angle), y + radius * (float) Math.sin(angle));
		}
		GL2.glEnd();
	}

	public static void drawText(String label, float x, float y, float radius, Color c) {
		if (label == null || label.isEmpty())
			return;
		setColor(c);
		float x0 = x + 2 * radius;
		float y0 = y + 12 / 2;

		float ascent = (12 * 70f) / 70f;

		y0 += 12 - ascent;

		GL2.glPushMatrix();
		GL2.glTranslatef(x0, y0, 0);
		GL2.glScalef(1, 1, 1);
		float sf = ascent / 70f;
		GL2.glScalef(sf, sf, 1);
		for (int j = 0; j < label.length(); ++j)
			glut.glutStrokeCharacter(GLUT.STROKE_MONO_ROMAN, label.charAt(j));
		GL2.glPopMatrix();

	}

	public static void drawRectangle(float x, float y, float width, float height) {
		GL2.glBegin(GL2ES3.GL_QUADS);
		GL2.glVertex2f(x, y);
		GL2.glVertex2f(x, y + height);
		GL2.glVertex2f(x + width, y + height);
		GL2.glVertex2f(x + width, y);
		GL2.glEnd();
	}

	public static GLPoint convertUnit2CustomCoordinate(float x, float y, Dimension defaultSize) {
		return new GLPoint((2 * x) - defaultSize.width, -(2 * y) + defaultSize.height);
	}
}
