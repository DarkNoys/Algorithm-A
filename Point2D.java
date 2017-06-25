public class Point2D {
	private int _x, _y;

	@Override
	public boolean equals(Object other) {
		return (_x == ((Point2D) other)._x && _y == ((Point2D) other)._y);
	}

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

	private void set(int x, int y) {
		_x = x;
		_y = y;
	}

	private void set() {
		set(0, 0);
	}
}