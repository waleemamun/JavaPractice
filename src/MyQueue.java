import java.util.Stack;

public class MyQueue {
    Stack <Integer> s1;
    Stack <Integer> s2;

    public MyQueue() {
        s1 = new Stack<Integer>();
        s2 = new Stack<Integer>();
    }
    public int size () {
        return s1.size() + s2.size();
    }
    public void enqueue (int val) {
        s1.push(val);
    }
    public int dequeue () {
        if (s2.empty()) {
            while (!s1.empty()) {
                s2.push(s1.pop());
            }
        }
        return s2.pop();
    }
}
