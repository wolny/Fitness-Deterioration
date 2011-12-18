package ki.edu.agh.utils;

import java.util.LinkedList;

/**
 * 
 * @author slo
 * 
 * @param <T>
 */
public class LRUCache<T> {
	private LinkedList<T> q;
	private final int size;

	public int getSize() {
		return size;
	}

	public LRUCache(int size) {
		this.size = size;
		this.q = new LinkedList<T>();
	}

	public void add(T obj) {
		if (q.size() < size) {
			q.addFirst(obj);
		} else {
			q.removeLast();
			q.addFirst(obj);
		}
	}

	public T getFirst() {
		return q.getFirst();
	}

	public T getLast() {
		return q.getLast();
	}

	public boolean isFull() {
		return size == q.size();
	}

	public void clear() {
		q.clear();
	}
}
