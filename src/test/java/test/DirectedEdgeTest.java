package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectedEdgeTest {


    @Test
    public void testInvertion() {

        assertOnInvertions(DirectedEdge.THERE);
        assertOnInvertions(DirectedEdge.BACK);
        assertOnInvertions(DirectedEdge.BOTH);
    }

    private void assertOnInvertions(DirectedEdge edge) {
        assertEquals(edge.isHasBack(), edge.invert().isHasThere());
        assertEquals(edge.isHasThere(), edge.invert().isHasBack());
    }

}