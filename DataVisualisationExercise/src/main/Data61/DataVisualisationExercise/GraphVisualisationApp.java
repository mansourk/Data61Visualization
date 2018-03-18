package Data61.DataVisualisationExercise;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class GraphVisualisationApp extends GLCanvas implements GLEventListener {

	private static final long serialVersionUID = 1L;
	private NetworkVisualizer visualizer = null;

	public GraphVisualisationApp(GLCapabilities caps) {
		super(caps);
		init();
	}

	private void init() {
		ApplicationContext context = new ApplicationContext();
		visualizer = new NetworkVisualizer(this, context.buildNetwork());
		addGLEventListener(this);
	}

	public void requestRedraw() {
		repaint();
	}

	public void init(GLAutoDrawable drawable) {
		visualizer.init(drawable);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		visualizer.init(drawable);
		visualizer.resize(width, height);
	}

	public void display(GLAutoDrawable drawable) {
		visualizer.init(drawable);
		visualizer.drawNetwork();
	}

	private void startApplication() {
		if (!SwingUtilities.isEventDispatchThread()) {
			System.out.println("Warning: UI is not being created in the Event Dispatch Thread!");
			assert false;
		}

		JFrame frame = new JFrame("CORA co-author publication network");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(this, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {

		GLCapabilities caps = new GLCapabilities(null);
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		GraphVisualisationApp graphVisualizationApp = new GraphVisualisationApp(caps);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				graphVisualizationApp.startApplication();
			}
		});

	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
	}

	public Dimension getPreferredSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

}
