package test;

import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

public abstract class AbstractSimpleGraph<T, E extends Invertible<E>> implements SimpleGraph<T, E> {

    private final NavigableMap<Entry<SortableVertex, SortableVertex>, E> adjacencies;

    private final Map<Vertex<T>, SortableVertex> sortableWraps;


    public AbstractSimpleGraph() {
        this.sortableWraps = new HashMap<>();

        final Comparator<SortableVertex> sortableVertexComparator = (o1, o2) -> {

            if (o1 == o2) {
                return 0;
            }

            if (o1 == MIN || o2 == MAX) {
                return -1;
            }

            if (o2 == MIN || o1 == MAX) {
                return 1;
            }

            return o1.uuid.compareTo(o2.uuid);
        };

        this.adjacencies = new TreeMap<>(
                Entry.<SortableVertex, SortableVertex>comparingByKey(sortableVertexComparator)
                        .thenComparing(Entry.comparingByValue(
                                sortableVertexComparator)));
    }

    @Override
    public Vertex<T> addVertex(T value) {
        Vertex<T> tVertex = VertexImpl.<T>builder().value(value).build();

        sortableWraps.put(tVertex, new SortableVertex(tVertex));
        return tVertex;

    }

    @Override
    public void putEdge(Vertex<T> from, Vertex<T> to, E edge) {

        adjacencies.put(getEntry(from, to), edge);
        adjacencies.put(getEntry(to, from), edge.invert());
    }

    private Collection<Entry<E, Vertex<T>>> getNeighbors(Vertex<T> vertex) {

        return adjacencies.subMap(getFloorEntry(vertex), getCeilingEntry(vertex))
                .entrySet()
                .stream()
                .map((e) -> Map.entry(e.getValue(), e.getKey().getValue().getVertex()))
                .collect(Collectors.toList());

    }


    private Entry<SortableVertex, SortableVertex> getEntry(Vertex<T> a, Vertex<T> b) {
        return Map.entry(getSortableVertex(a), getSortableVertex(b));
    }

    private Entry<SortableVertex, SortableVertex> getFloorEntry(Vertex<T> a) {
        return Map.entry(getSortableVertex(a), MIN);
    }

    private Entry<SortableVertex, SortableVertex> getCeilingEntry(Vertex<T> a) {
        return Map.entry(getSortableVertex(a), MAX);
    }

    protected List<Entry<E, Vertex<T>>> getPath(Vertex<T> src, Vertex<T> dst, Predicate<E> walkability) {

        final LinkedList<Entry<E, Vertex<T>>> walked = new LinkedList<>();
        final boolean traverse = traverse(src, new HashSet<>(), walked, dst, walkability);

        return traverse ? walked : null;

    }

    private boolean traverse(Vertex<T> start, Set<Vertex<T>> visitedVertices, Deque<Entry<E, Vertex<T>>> walked,
            Vertex<T> search, Predicate<E> walkability) {
        if (!visitedVertices.contains(start)) {

            visitedVertices.add(start);

            if (start.equals(search)) {
                return true;
            }

            final boolean found = getNeighbors(start).stream()
                    .anyMatch(eVertexEntry -> {
                        final Vertex<T> vertex = eVertexEntry.getValue();
                        final E edge = eVertexEntry.getKey();
                        if (!walkability.test(edge)) {
                            return false;
                        }
                        walked.add(Map.entry(edge, vertex));
                        return traverse(vertex, visitedVertices, walked, search, walkability);
                    });

            if (found) {
                return true;
            }


        }
        if (!walked.isEmpty()) {
            walked.removeLast();
        }

        return false;

    }

    private final SortableVertex MIN = new SortableVertex(null);
    private final SortableVertex MAX = new SortableVertex(null);

    private SortableVertex getSortableVertex(Vertex<T> vertex) {
        return sortableWraps.get(vertex);
    }

    @Builder
    @Data
    public static class VertexImpl<T> implements Vertex<T> {

        private final T value;

    }

    @ToString
    public class SortableVertex {

        private final Vertex<T> vertex;
        private final UUID uuid;


        public SortableVertex(Vertex<T> vertex) {
            this.vertex = vertex;
            uuid = UUID.randomUUID();
        }

        public Vertex<T> getVertex() {
            return vertex;
        }
    }
}
