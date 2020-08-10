package test;

import java.util.List;
import java.util.Map.Entry;

/**
 * Represents base interface for simple graphs(for distinguishing from a multigraph). According to simple graph
 * definition, multiple edges are not allowed, see {@link #putEdge(Vertex, Vertex, E)} for more information.
 *
 * @param <T> vertex data
 * @param <E> edge data (e.g. direction, weight)
 */
public interface SimpleGraph<T, E extends Invertible<E>> {

    Vertex<T> addVertex(T value);

    /**
     * Adding possibly non-symmetric(in case of directed graphs) edge information, where orientation is defined by
     * {@code from} and {@code to} parameters. At most one edge can exist at the same time for any single {@code from,
     * to} combination. If the graph already contains an edge between given vertices, the previously existing is
     * replaced by specified one
     *
     * @param from the edge's from
     * @param to the edge's to
     * @param edge the specific edge data to be added
     */
    void putEdge(Vertex<T> from, Vertex<T> to, E edge);

    /**
     * Calculates possible path for given source and destination. The interface does not restrict to return the optimal
     * one. The {@code src} is excluded in resulting list. {@code Invertible E} must be oriented as it naturally appear
     * on the path while walking from source to destination.
     *
     * @param src - source
     * @param dest - destination
     * @return ordered list of {@link Entry} where it's {@code key} is used for an edge and {@code value} is used for
     * {@link Vertex} , {@code null} if {@code dest} is not reachable from {@code src}, may return (but not has to)
     * empty {@link List} in case {@code src == dst}
     */
    List<Entry<E, Vertex<T>>> getPath(Vertex<T> src, Vertex<T> dest);

}
