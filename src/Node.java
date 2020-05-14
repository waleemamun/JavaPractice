import java.util.List;

public class Node {
    int val;
    int min;

    public List<Node> children;

    public Node() {}
    Node left;
    Node right;
    Node next;
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }

    public Node(int v, int m) {
        val = v;
        min = m;
    }

}
