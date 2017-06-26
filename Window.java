import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Queue;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Window extends Algorithm {

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 600;
	private static final double PROPOTION_RIGHT = 0.08;
	private static final double PROPOTION_TOP = 0.05;
	private static final int BUTTON_SIZE = 10;
	private static final long DELAY = 20;

	private Canvas canvas;
	private Point saveStart, saveFinish;
	private JFrame frame;

	public Window() {
		super(new Map(70, 50));
	}

	public void init() {
		frame = new JFrame("Algorithm A*");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridBagLayout());

		JButton generateButton = new JButton("Generate map");
		JButton runButton = new JButton("Run");
		JButton clearButton = new JButton("Clear way");
		canvas = new Canvas();

		GridBagConstraints c = new GridBagConstraints();
		Insets insets = new Insets(30, 30, 0, 15);

		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = PROPOTION_RIGHT;
		c.weighty = PROPOTION_TOP;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = insets;
		c.ipady = BUTTON_SIZE;
		frame.add(generateButton, c);
		generateButton.addMouseListener(new GenerateListener());

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = PROPOTION_RIGHT;
		c.weighty = PROPOTION_TOP;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = insets;
		frame.add(runButton, c);
		runButton.addMouseListener(new RunListener());

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = PROPOTION_RIGHT;
		c.weighty = 1 - 2 * PROPOTION_TOP;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = insets;
		frame.add(clearButton, c);
		clearButton.addMouseListener(new ClearListener());

		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		c.weightx = 1 - PROPOTION_RIGHT;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(30, 10, 10, 10);
		frame.add(canvas, c);
		canvas.addMouseListener(new SelectStartEndListener());

		frame.setVisible(true);
	}

	@Override
	protected Queue<Point> includeOpenSet(Queue<Point> openset, Point current) {
		if (!(current.equals(saveStart)) && !(current.equals(saveFinish)))
			canvas.drawPoint(new Point(current.x * 10, current.y * 10),
					new Color(5, 75, 140));  //очень красивый
		
		return super.includeOpenSet(openset, current);
	}

	@Override
	protected Vector<Point> includeCloseSet(Vector<Point> closeset,
			Point current) {
		if (!(current.equals(saveStart)) && !(current.equals(saveFinish)))
			canvas.drawPoint(new Point(current.x * 10, current.y * 10),
					new Color(129, 184, 234));
		try {
			Thread.sleep(DELAY);
		} catch (Exception e) {

		}
		return super.includeCloseSet(closeset, current);
	}

	private void printWay(Vector<Point> way) {
		if (way == null) {
			JOptionPane.showMessageDialog(frame, "There is not way!");
			return;
		}

		for (Point p : way) {
			if (!(p.equals(saveStart)) && !(p.equals(saveFinish)))
				canvas.drawPoint(new Point(p.x * 10, p.y * 10), new Color(217, 39, 39));
		}
	}

	private int correctCoordtoMap(int x) {
		return (x - x % 10) / 10;
	}

	public class GenerateListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			saveStart = null;
			saveFinish = null;
			_map.generate();
			canvas.drawMap(_map);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	public class RunListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			if (saveStart != null && saveFinish != null) {
				printWay(aStar(saveStart, saveFinish));
			} else
				JOptionPane.showMessageDialog(frame,
						"There is no start and / or finish!");
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	public class ClearListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			saveStart = null;
			saveFinish = null;
			canvas.drawMap(_map);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	public class SelectStartEndListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Integer x = e.getX();
			Integer y = e.getY();

			if (_map.isExist(new Point(correctCoordtoMap(x),
					correctCoordtoMap(y)))) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					saveStart = new Point(correctCoordtoMap(x),
							correctCoordtoMap(y));
					canvas.setStart(x, y);
				} else {
					saveFinish = new Point(correctCoordtoMap(x),
							correctCoordtoMap(y));
					canvas.setFinish(x, y);
				}
			} else
				JOptionPane.showMessageDialog(frame,
						"There's an obstacle here!");

		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

}
