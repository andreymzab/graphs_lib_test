package test;

import lombok.Getter;

@Getter
public class DirectedEdge implements Invertible<DirectedEdge> {

    public static DirectedEdge THERE = new DirectedEdge(true, false);
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
        if (this == THERE) {
            return BACK;
        } else if (this == BACK) {
            return THERE;
        } else {
            return BOTH;
        }
    }

}
