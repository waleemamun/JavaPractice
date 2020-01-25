public class StackNode {
    private int top;
    private Node [] data;
    private int val;
    private int size;
    private int min = Integer.MAX_VALUE;

    public StackNode(int size) {
        data = new Node[size];
        top = 0;
        this.size = size;
    }
    public void push(int val) {
        min = Math.min(min,val);
        Node n = new Node(val,min);
        if (top == size) {
            System.out.println("No More Space!");
        }
        data[top++] = n;
    }
    public Node pop () {
        if ((top - 1) < 0) {
            System.out.println("Empty Stack");
            return new Node(Integer.MIN_VALUE,Integer.MIN_VALUE);
        }
        min = data[top-1].min;
        return data[--top];
    }
    public Node peek() {
        if (top == 0 ){
            System.out.println("Empty Stack");
            return new Node(Integer.MIN_VALUE,Integer.MIN_VALUE);
        }
        return data[top-1];
    }
    public int minimumVal () {
        Node n = peek();
        return n.min;
    }
    public void printStack(){
        for (int i = 0; i < top; i++) {
            System.out.print(data[i].val + " ");
        }
        System.out.println("EOS");
    }

}
