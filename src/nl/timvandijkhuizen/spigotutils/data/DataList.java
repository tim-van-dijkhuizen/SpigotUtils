package nl.timvandijkhuizen.spigotutils.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DataList<E> implements Iterable<E> {

	private Set<E> items = new HashSet<E>();
	private Set<E> toAdd = new HashSet<E>();
	private Set<E> toRemove = new HashSet<E>();
	
	public DataList() {

	}
	
	public DataList(Collection<E> items) {
		this.items.addAll(items);
	}
	
	public void add(E e) {
		if(items.add(e)) {
			toAdd.add(e);
		}
	}
	
	public void remove(E e) {
		if(items.remove(e)) {
			toRemove.add(e);
		}
	}

	public int size() {
		return items.size();
	}
	
	@Override
	public Iterator<E> iterator() {
		return items.iterator();
	}
	
	public Set<E> getToAdd() {
		return Collections.unmodifiableSet(toAdd);
	}
	
	public Set<E> getToRemove() {
		return Collections.unmodifiableSet(toRemove);
	}

}
