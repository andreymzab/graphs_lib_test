package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

class SimpleDirectedGraphImplTest {


    @Test
    public void test() {

        SimpleDirectedGraph<String> graph = new SimpleDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");
        final Vertex<String> vertexB = graph.addVertex("vertexB");
        final Vertex<String> vertexC = graph.addVertex("vertexC");
        final Vertex<String> vertexD = graph.addVertex("vertexD");

        graph.putEdge(vertexA, vertexB, DirectedEdge.THERE);
        graph.putEdge(vertexB, vertexC, DirectedEdge.THERE);
        graph.putEdge(vertexA, vertexD, DirectedEdge.THERE);

        final List<Entry<DirectedEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexC);

        assertEquals(2, path.size());
        System.out.println(path);


    }

    @Test
    public void testNoPathEdgeIsWrongDirection() {

        SimpleDirectedGraph<String> graph = new SimpleDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");
        final Vertex<String> vertexB = graph.addVertex("vertexB");

        graph.putEdge(vertexA, vertexB, DirectedEdge.BACK);

        final List<Entry<DirectedEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexB);

        assertNull(path);

    }

    @Test
    public void testNoPathNoEdge() {

        SimpleDirectedGraph<String> graph = new SimpleDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");
        final Vertex<String> vertexB = graph.addVertex("vertexB");

        final List<Entry<DirectedEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexB);

        assertNull(path);

    }

    @Test
    public void testSameEdge() {

        SimpleDirectedGraph<String> graph = new SimpleDirectedGraph<>();

        final Vertex<String> vertexA = graph.addVertex("vertexA");

        final List<Entry<DirectedEdge, Vertex<String>>> path = graph.getPath(vertexA, vertexA);

        assertNotNull(path);
        assertTrue(path.isEmpty());

    }


}