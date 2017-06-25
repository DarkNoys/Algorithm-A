public class Point2D {
	private int _x, _y;

	public Point2D(int x, int y) {
		_x = x;
		_y = y;
	}

	public Point2D() {
		this(0, 0);
	}

	public int y() {
		return _y;
	}

	public int x() {
		return _x;
	}

	private int set(int x, int y) {
		_x = x;
		_y = y;
	}

	private int set() {
		return set(0, 0);
	}
}