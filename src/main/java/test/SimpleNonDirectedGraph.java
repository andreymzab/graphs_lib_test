package test;

import java.util.List;
import java.util.Map.Entry;
import test.SimpleNonDirectedGraph.SimpleEdge;

public class SimpleNonDirectedGraph<T> extends AbstractSimpleGraph<T, SimpleEdge> {

    public static class SimpleEdge implements Invertible<SimpleEdge> {

        private static final SimpleEdge instance = new SimpleEdge();

        private SimpleEdge() {

        }

        @Override
        public SimpleEdge invert() {
            return instance;
        }
    }

    public void addConnection(Vertex<T> from, Vertex<T> to) {
        putEdge(from, to, SimpleEdge.instance);
    }

    @Override
    public List<Entry<SimpleEdge, Vertex<T>>> getPath(Vertex<T> src, Vertex<T> dst) {
        return getPath(src, dst, (edge) -> true);
    }

}
