package test;

import java.util.List;
import java.util.Map.Entry;
import lombok.Getter;
import test.SimpleDirectedGraph.DirectedEdge;

public class SimpleDirectedGraph<T> extends AbstractSimpleGraph<T, DirectedEdge> {

    @Override
    public List<Entry<DirectedEdge, Vertex<T>>> getPath(Vertex<T> src, Vertex<T> dst) {
        return getPath(src, dst, DirectedEdge::isHasThere);

    }

    @Getter
    public static class DirectedEdge implements Invertible<DirectedEdge> {

        public static DirectedEdge THERE = new DirectedEdge(true, true);
        public static DirectedEdge BACK = new DirectedEdge(false, true);
        public static DirectedEdge BOTH = new DirectedEdge(true, true);

        private final boolean hasThere;
        private final boolean hasBack;

        private DirectedEdge(boolean hasThere, boolean hasBack) {
            this.hasThere = hasThere;
            this.hasBack = hasBack;
        }

        @Override
        public DirectedEdge invert() {
            return getInstance(!hasThere, !hasBack);
        }

        private static DirectedEdge getInstance(boolean hasThere, boolean hasBack) {
            if (hasThere && !hasBack) {
                return THERE;
            } else if (hasBack && !hasThere) {
                return BACK;
            }
            return BOTH;
        }
    }
}
