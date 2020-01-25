import java.util.ArrayList;

public class GraphNode {
    int data;
    ArrayList<Integer> adj;
    int parent;
    int bfsDepth;
    public GraphNode (int d) {
        data = d;
        adj = new ArrayList<Integer>();
        parent = Integer.MIN_VALUE;
    }
}
