import java.util.ArrayList;
import java.util.Queue;

public class Graph {
    ArrayList <GraphNode> nodeList;
    int totalNodes;

    public Graph (int maxNode) {
        totalNodes = maxNode;
        nodeList = new ArrayList<>();
    }

    public void createGraph(int [][]g ) {
        if(g.length >= totalNodes) {
            System.out.println("Number of node is bigger than the graph node");
        }
        for (int i = 0; i < totalNodes; i++) {
            GraphNode gnode = new GraphNode(i);
            nodeList.add(gnode);
            for (int j=0 ; j < g[i].length; j++ ) {
                gnode.adj.add(g[i][j]);
            }
        }
    }

    public void printGraph() {
        for (int i = 0; i < totalNodes; i++) {
            System.out.print("Node " +i + ": " );
            GraphNode gl = nodeList.get(i);
            for (int j = 0; j< gl.adj.size(); j++) {
                System.out.print(gl.adj.get(j)+"-> ");
            }
            System.out.println("null");
        }
    }

    public GraphNode getNode (int node) {
        return nodeList.get(node);
    }

    public GraphNode getParet(int node) {
        return nodeList.get(nodeList.get(node).parent);
    }


}
