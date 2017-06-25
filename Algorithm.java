package GraphAlgh;

import java.util.*;
import java.math.*;

public class Algorithm {

	/**
	 * aStar Alg
	 * 
	 */
	public Algorithm(Map map) {
		_map = map;
		G = new int[_map.getWidth()][_map.getHeight()];
		F = new int[_map.getWidth()][_map.getHeight()];
	}

	public boolean aStar(Point2D start, Point2D end) {
		for (int i = 0; i < _map.getWidth(); i++) {
			for (int q = 0; q < _map.getHeight(); q++) {
				G[i][q] = Integer.MAX_VALUE;
				F[i][q] = Integer.MAX_VALUE;
			}
		}
		Vector<Point2D> closeset = new Vector<>();
		Point2D[][] fromset = new Point2D[_map.getWidth()][_map.getHeight()];
		Queue<Point2D> openset = new PriorityQueue<>(_map.getWidth() * _map.getHeight(), fieldComparator);
		G[start.x()][start.y()] = 0;
		F[start.x()][start.y()] = G[start.x()][start.y()] + setHeuristicFunction(start, end);

		while (!openset.isEmpty()) {
			boolean better_result = false;
			Point2D curr = takeMin(openset);
			if (curr == end) {
				reconstructPath(fromset, start, end);
				return true;
			}

			openset = removeOpenSet(openset, curr);
			closeset = includeCloseSet(closeset, curr);
			Vector<Point2D> neighbour = findNeighbours(curr);

			for (int currNeighbour = 0; currNeighbour < neighbour.size(); currNeighbour++) {

				if (closeset.contains(neighbour.elementAt(currNeighbour)))
					continue;

				int tentativeScore = G[curr.x()][curr.y()] + _map.getWay(curr);

				if (!openset.contains(neighbour.elementAt(currNeighbour))) {
					openset = includeOpenSet(openset, neighbour.elementAt(currNeighbour));

					better_result = true;
				} else {
					if (tentativeScore < G[neighbour.elementAt(currNeighbour).x()][neighbour.elementAt(currNeighbour)
							.y()])
						better_result = true;
					else
						better_result = false;

				}
				if (better_result) {
					fromset[neighbour.elementAt(currNeighbour).x()][neighbour.elementAt(currNeighbour)
							.y()] = includeFromSet(curr);
					G[neighbour.elementAt(currNeighbour).x()][neighbour.elementAt(currNeighbour).y()] = tentativeScore;
					F[neighbour.elementAt(currNeighbour).x()][neighbour.elementAt(currNeighbour)
							.y()] = G[neighbour.elementAt(currNeighbour).x()][neighbour.elementAt(currNeighbour).y()]
									+ setHeuristicFunction(neighbour.elementAt(currNeighbour), end);// !!

				}
			}

		}
		return false;
	}

	private Vector<Point2D> reconstructPath(Point2D[][] fromset, Point2D start, Point2D end) {
		Vector<Point2D> pathset = new Vector<>();

		Point2D curr = end;
		pathset.add(curr);
		while (curr != start) {
			curr = fromset[curr.x()][curr.y()];
			pathset.add(curr);

		}
		Collections.reverse(pathset);
		return pathset;

	}

	/**
	 * Setting HeuristicFunction
	 * 
	 * @param cell
	 * @param end
	 */
	private Integer setHeuristicFunction(Point2D cell, Point2D end) {

		return Math.max(cell.x() - end.x(), cell.y() - end.y());

		// * CHOOSE
		// 1) return Math.abs(cell.x-end.x)-Math.abs(cell.y-end.y); //good
		// 2) return Math.max(cell.x-end.x,cell.y-end.y ); //also good (mby
		// greatest)
		// 3) return
		// Math.sqrt(((cell.x-end.x)*(cell.x-end.x)+(cell.y-end.y)*(cell.y-end.y)));
		// meh

	}

	/**
	 * Include in closeset elemets
	 * 
	 * @param closeset
	 * @param current
	 * @return
	 */
	private Vector<Point2D> includeCloseSet(Vector<Point2D> closeset, Point2D current) {
		closeset.add(current);
		return closeset;
		// ! return closeset.add(current) -ERROR
	}

	/**
	 * remove element from openset
	 * 
	 * @param openset
	 * @param current
	 * @return
	 */

	private Queue<Point2D> removeOpenSet(Queue<Point2D> openset, Point2D current) {
		openset.remove();
		return openset;
	}

	private Queue<Point2D> includeOpenSet(Queue<Point2D> openset, Point2D current) {
		openset.add(current);
		return openset;
	}

	private Point2D includeFromSet(Point2D current) {
		return current;
	}

	private Point2D takeMin(Queue<Point2D> openset) {
		Point2D curr = openset.peek();
		return curr;
	}

	/**
	 * Creation vector of Neighbours
	 * 
	 * @param current
	 * @return
	 */
	private Vector<Point2D> findNeighbours(Point2D current) {
		Vector<Point2D> finded = new Vector<>();
		boolean parametr1 = current.x() + 1 <= _map.getHeight() - 1;
		boolean parametr2 = current.y() + 1 <= _map.getWidth() - 1;
		boolean parametr3 = current.x() - 1 >= 0;
		boolean parametr4 = current.y() - 1 >= 0;

		if (parametr1)
			finded.add(new Point2D(current.x() + 1, current.y()));
		if (parametr2)
			finded.add(new Point2D(current.x(), current.y() + 1));
		if (parametr3)
			finded.add(new Point2D(current.x() - 1, current.y()));
		if (parametr4)
			finded.add(new Point2D(current.x(), current.y() - 1));
		if (parametr1 && parametr2)
			finded.add(new Point2D(current.x() + 1, current.y() + 1));
		if (parametr3 && parametr4)
			finded.add(new Point2D(current.x() - 1, current.y() - 1));
		if (parametr1 && parametr4)
			finded.add(new Point2D(current.x() + 1, current.y() - 1));
		if (parametr2 && parametr3)
			finded.add(new Point2D(current.x() - 1, current.y() + 1));

		return finded;
	}

	protected Map _map;
	private static int[][] G;
	private static int[][] F;

	public static Comparator<Point2D> fieldComparator = new Comparator<Point2D>() {

		@Override
		public int compare(Point2D p1, Point2D p2) {

			return (int) F[p1.x()][p2.y()] - F[p2.x()][p2.y()];

		}
	};

}
