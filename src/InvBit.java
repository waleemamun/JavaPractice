import java.util.*;

public class InvBit {

    // Min Steps in Infinite Grid
    public int coverPoints(ArrayList<Integer> A, ArrayList<Integer> B) {
        int sum = 0;
        for (int i = 1; i< A.size(); i++) {
            int x = Math.abs(A.get(i) - A.get(i-1));
            int y = Math.abs(B.get(i) - B.get(i-1));
            sum+= Math.max(x,y);
        }
        return sum;

    }
}
