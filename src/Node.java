import java.util.List;

public class Node {
    int val;
    int min;

    public List<Node> children;
    public List<Node> neighbors;

    public Node() {}
    Node left;
    Node right;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
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
