import java.util.*;

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

    // LeetCode :: 133. Clone Graph
    // Use either bfs or dfs to construct the graph
    // this is the bfs approach
    public Node cloneGraph2(Node node) {
        if (node == null)
            return node;
        Node start = new Node(node.val);
        Queue<Node> queue = new LinkedList<>();
        HashMap<Integer,Node> nodeMap = new HashMap<>();
        queue.add(node);
        nodeMap.put(node.val, start);
        while (!queue.isEmpty()) {
            Node oldNode = queue.poll();
            List<Node> adjList = oldNode.neighbors;
            Node newNode = nodeMap.getOrDefault( oldNode.val,null);
            for (Node n : adjList) {
                Node adj = nodeMap.getOrDefault(n.val, new Node(n.val));
                newNode.neighbors.add(adj);
                if (!nodeMap.containsKey(n.val)) {
                    nodeMap.put(adj.val,adj);
                    queue.add(n);
                }
            }
        }
        return start;
    }
    // this is the dfs approach
    public Node cloneGraph(Node node) {
        if (node == null)
            return node;
        HashMap<Integer,Node> nodeMap = new HashMap<>();
        return createCloneGraph(node, nodeMap);
    }
    private Node createCloneGraph(Node node , HashMap<Integer, Node> map) {
        if (map.containsKey(node.val))
            return map.get(node.val);
        Node newNode = new Node(node.val);
        map.put(newNode.val, newNode);
        for (Node n : node.neighbors) {
            newNode.neighbors.add(createCloneGraph(n,map));
        }
        return newNode;
    }





}
