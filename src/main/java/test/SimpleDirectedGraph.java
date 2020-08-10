package test;

import java.util.List;
import java.util.Map.Entry;

public class SimpleDirectedGraph<T> extends AbstractSimpleGraph<T, DirectedEdge> {

    @Override
    public List<Entry<DirectedEdge, Vertex<T>>> getPath(Vertex<T> src, Vertex<T> dst) {
        return getPath(src, dst, DirectedEdge::isHasThere);

    }

}
