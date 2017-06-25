import java.util.Random;
import java.io.*;

public class Map {
	public final static int DEF_SIZE = 10;
	public final static int N_SEED = 30;
	public final static int N_UPDATE = 50;

	public Map() {
		this(DEF_SIZE, DEF_SIZE);
	}

	public Map(int height, int width) {
		_height = height;
		_width = width;
		defMap();
	}

	public boolean isExist(Point2D point) throws NullPointerException {
		return _map[point.x()][point.y()].exist;
	}

	public void setExist(Point2D point, boolean exist) {
		_map[point.x()][point.y()].exist = exist;
	}

	public void print() throws IOException {
		FileOutputStream writer = new FileOutputStream("map.txt");
		for (Node[] m : _map) {
			for (Node p : m) {
				writer.write((p.exist ? (Integer.toString(p.height)+" ") : "0 ").getBytes());
			}
			writer.write("\n".getBytes());
		}
	}

	private void defMap() {
		_map = new Node[_width][_height];
		for (Node[] a : _map) {
			for (int i = 0; i < a.length; i++) {
				a[i] = new Node();
			}
		}

	}

	public int getHeight() {
		return _height;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight(Point2D point) {
		return _map[point.x()][point.y()].height;
	}

	public void setHeight(Point2D point, int height) {
		_map[point.x()][point.y()].height = height;
	}

	public int getWay(Point2D point1, Point2D point2) {
		return Math.abs(getHeight(point1) - getHeight(point2)) + 1;
	}

	public void generate() {
		defMap();
		Random rand = new Random();
		int[] seedPos = new int[N_SEED];
		int numUpdates = N_UPDATE;

		for (int i = 0; i < seedPos.length; i++) {
			seedPos[i] = Math.abs(rand.nextInt() % (_height * _width));
			setExist(new Point2D(seedPos[i] % _width, seedPos[i] / _width), false);
		}

		for (int i = 0; i < numUpdates; i++) {
			for (int j = 0; j < seedPos.length; j++) {
				switch (Math.abs(rand.nextInt()) % 4) {

				case 0:
					seedPos[j] -= _width;
					break;
				case 1:
					seedPos[j]++;
					break;
				case 2:
					seedPos[j] += _width;
					break;
				case 3:
					seedPos[j]--;
					break;
				}

				if (!(seedPos[j] < 0 || seedPos[j] >= (_height * _width))) {
					setExist(new Point2D(seedPos[j] % _width, seedPos[j] / _width), false);
				}
			}
		}

		// Отображение мини-карты
		// vRenderMinimap();
		// Воспроизведение звука, сообщающего о завершении операции

	}

	private class Node {
		public Node() {
			exist = true;
			height = 1;
		}

		public boolean exist;
		public int height;
	}

	private Node[][] _map;
	private int _height, _width;
}
