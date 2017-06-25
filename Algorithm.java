package GraphAlgh;

import java.util.*;
import java.math.*;

public class Algorithm {

/**
* aStar Alg need Map
* G(X) и F(X) должны выглядеть в виде определенного упорядочного списка,
* иначе придется каждый раз применять алгоритм поиска.
* Может , в точку добавить номер ?
*
*/
//mb need static ?
public boolean aStar(Integer start, Integer end) {
	Vector<Integer> G = new Vector<>(); // vector distance from start to X-
	Vector<Integer> F = new Vector<>(); //G(X)+H(X)
	Vector<Integer> closeset = new Vector<>();
	Vector<Integer> fromset = new Vector<>(); // way.
	Queue<Integer> openset = new PriorityQueue<>(10, fieldComparator);//!!
	G[start] = 0;//!! USE SET
	F(start) = G(start) + setHeuristicFunction(start, end);//!! USE SET
	while (!openset.isEmpty()) {
		boolean better_result = false;
		Integer curr = openset.peek(); // takeCur ?

	if (curr == end) {
	reconstructPath(fromset, start, end);
	return true;
	}

	openset = removeOpenSet(openset, curr);
	closeset = includeCloseSet(closeset, curr);
	Vector<Integer> neighbour = findNeighbours(curr);//Моя идея реализации прохода по всем соседям
	for (int currNeighbour : neighbour) {

	if (closeset.contains(neighbour.elementAt(currNeighbour)))
		continue;

	double tentativeScore=G(cur)+COST(curr,neighbour) //!
	if (!openset.contains(neighbour.elementAt(currNeighbour))) {
		openset = includeOpenSet(openset, neighbour.elementAt(currNeighbour));
		better_result = true;
	} else {
		if (tentativeScore < G(currNeighbour)) //!
			better_result = true;
		else
			better_result = false;

	}
	if (better_result) {
	fromset(currNeighbour) = curr;//!!
	G(currNeighbour) = tentativeScore;//!!
	F(currNeighbour) = G(currNeighbour) + setHeuristicFunction(currNeighbour, end);//!!
	}
}

}
	return false;// FAIL
}

	private Vector<Integer> reconstructPath(Vector<Integer> fromset, Integer start, Integer end) {
		Vector<Integer> pathset = new Vector<>();

		Integer curr = end;
		pathset.add(curr);
		while (curr != start) {
			curr = fromset(curr); // ! Получаем вершину из которой пришли в
									// curr.
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
	private Integer setHeuristicFunction(Integer cell, Integer end) {
		return 1;

		// * CHOOSE YOUR DESTINY
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
	private Vector<Integer> includeCloseSet(Vector<Integer> closeset, Integer current) {
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

	private Queue<Integer> removeOpenSet(Queue<Integer> openset, Integer current) {
		openset.remove();
		return openset;
	}

	private Queue<Integer> includeOpenSet(Queue<Integer> openset, Integer current) {
		openset.add(current);
		return openset;
	}

	private Vector<Integer> includeFromSet(Vector<Integer> fromset, Integer current) {
		fromset.add(current);
		return fromset;
	}

	/**
	 * Creation vector of Neighbours
	 * 
	 * @param current
	 * @return
	 */
	private Vector<Integer> findNeighbours(Integer current) {
		Vector<Integer> finded = new Vector<>();

		// TO DO

		return finded;
	}

	protected Map _map;
	// Анонимный класс
	public static Comparator<Map> fieldComparator=new Comparator<Map>(){

	@Override public int compare(Map f1,Map f2){return(int)(f1-f2);}};
}
