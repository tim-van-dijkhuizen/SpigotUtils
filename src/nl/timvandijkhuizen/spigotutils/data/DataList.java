package nl.timvandijkhuizen.spigotutils.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class DataList<E> implements Iterable<E> {

    private Map<E, DataAction> items = new LinkedHashMap<>();

    public DataList() {

    }

    public DataList(Collection<E> items) {
        for (E item : items) {
            this.items.put(item, DataAction.UPDATE);
        }
    }

    public void add(E item) {
        DataAction action = items.get(item);

        if (action == null) {
            items.put(item, DataAction.CREATE);
        } else if (action == DataAction.DELETE) {
            items.put(item, DataAction.UPDATE);
        }
    }

    public void remove(E item) {
        DataAction action = items.get(item);

        if (action == DataAction.CREATE) {
            items.remove(item);
            return;
        }

        items.put(item, DataAction.DELETE);
    }

    public int size() {
        return items.size();
    }

    @Override
    public Iterator<E> iterator() {
        return items.keySet().iterator();
    }

    public Set<E> getByAction(DataAction action) {
        return items.entrySet().stream()
            .filter(i -> i.getValue() == action)
            .map(i -> i.getKey())
            .collect(Collectors.toSet());
    }

    public void clearPending() {
        Set<Entry<E, DataAction>> items = this.items.entrySet();

        // Change CREATE to UPDATE
        for (Entry<E, DataAction> entry : items) {
            DataAction action = entry.getValue();

            if (action == DataAction.CREATE) {
                entry.setValue(DataAction.UPDATE);
            }
        }

        // Remove DELETE
        items.removeIf(i -> i.getValue() == DataAction.DELETE);
    }

}
