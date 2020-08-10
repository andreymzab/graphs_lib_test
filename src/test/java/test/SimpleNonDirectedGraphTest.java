package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;
import test.SimpleNonDirectedGraph.SimpleEdge;

class SimpleNonDirectedGraphTest {


    @Test
    public void test() {

        SimpleNonDirectedGraph<String> graph = new SimpleNonDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");
        final Vertex<String> vertexB = graph.addVertex("vertexB");
        final Vertex<String> vertexC = graph.addVertex("vertexC");
        final Vertex<String> vertexD = graph.addVertex("vertexD");

        graph.addConnection(vertexA, vertexB);
        graph.addConnection(vertexB, vertexC);
        graph.addConnection(vertexA, vertexD);

        final List<Entry<SimpleEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexC);

        assertEquals(2, path.size());
        System.out.println(path);


    }

    @Test
    public void testNoPathNoEdge() {

        SimpleNonDirectedGraph<String> graph = new SimpleNonDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");
        final Vertex<String> vertexB = graph.addVertex("vertexB");

        final List<Entry<SimpleEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexB);

        assertNull(path);

    }

    @Test
    public void testSameEdge() {

        SimpleNonDirectedGraph<String> graph = new SimpleNonDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");

        final List<Entry<SimpleEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexA);

        assertNotNull(path);
        assertTrue(path.isEmpty());

    }

}