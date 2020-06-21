import java.util.Arrays;

public class SegmentTree {

    /**
     * Here we discuss the use of Segment Tree to solve the range sum problems with frequent updates/ queries in array
     * The main Idea is described here
     * https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
     * There is a visaulization for the Segment Tree here Use : 1,3,5,10,20 to visualize
     * https://visualgo.net/en/segmenttree
     *
     * Note : The Segment tree is mainly used to solve problems with querying range (i,j) in an array.
     *        This is used when the range is different queried multiple times & also updates in the array
     *        is performed multiple time. So we do some preprocessing using the Segment tree where ranges
     *        are stored in the segment tree and the key for the tree is the operation (sum, min, max).
     *        This will make all the queries O(lgn) and updates O(lgn) if there are multiple query/update
     *
     *
     */
    int [] tree;
    int len = 0;
    public SegmentTree(int nums[]) {
        // Allocate memory for segment tree
        // Height of segment tree
        len = nums.length;
        int x = (int) (Math.ceil(Math.log(len) / Math.log(2)));

        //Maximum size of segment tree
        int maxSize = 2 * (int) Math.pow(2, x) - 1;
        tree = new int [maxSize];
        buildSegTree(nums,0,0,len-1);
        System.out.println("Seg Tree " +Arrays.toString(tree));
    }
    // This three method can be used to perform different operation on Segment tree
    // Merge gives the sum of of node root node will have the cumulative sum all the items in the array
    // merge function can be used for the purpose of sum
    private int merge (int a, int b) {
        return a + b;
    }
    // Min operation gives the min of two nodes so root will have min of the array so min can be used for
    // problems with finding min
    private int min (int a, int b) {
        return Math.min(a,b);
    }
    // Max operation gives the max of two nodes so root will have max of the array so min can be used for
    // problems with finding max between two indices i,j
    private int max (int a, int b) {
        return Math.max(a,b);
    }

    private void buildSegTree(int  []arr, int treeIndex, int lo, int hi) {
        if (lo == hi) {                 // leaf node. store value in node.
            tree[treeIndex] = arr[lo];
            return;
        }

        int mid = lo + (hi - lo) / 2;   // recurse deeper for children.
        buildSegTree(arr, 2 * treeIndex + 1, lo, mid);
        buildSegTree(arr, 2 * treeIndex + 2, mid + 1, hi);

        // merge build results
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }

    // Wrapper for querying the Seg Tree
    public int lookUpSegTree (int  i, int j) {
        return querySegTree(0, 0, len -1, i,j);
    }

    // call this method as querySegTree(0, 0, n-1, i, j);
    // Here [i,j] is the range/interval you are querying.
    // This method relies on "null" nodes being equivalent to storing zero.
    // The method returns a result when the queried range matches exactly
    // with the range represented by a current node. Else it digs deeper into
    // the tree to find nodes which match a portion of the node exactly.
    private int querySegTree(int treeIndex, int lo, int hi, int i, int j) {
        // query for arr[i..j]

        if (lo > j || hi < i)               // segment completely outside range
            return 0;                       // represents a null node

        if (i <= lo && j >= hi)             // segment completely inside range
            return tree[treeIndex];

        int mid = lo + (hi - lo) / 2;       // partial overlap of current segment and queried range. Recurse deeper.
        // lets check if we need to move right or left if the range we are looking for is completely on the
        // right of mid we move to right, if the range is completely on left of mid we move to left
        if (i > mid) // range lies on the right subtree
            return querySegTree(2 * treeIndex + 2, mid + 1, hi, i, j);
        else if (j <= mid) // range lies on left subtree
            return querySegTree(2 * treeIndex + 1, lo, mid, i, j);
        // So the range is not completely on the left or right of mid
        // Now we need to split the range in to to halves based on mid so each half falls on different sides of mid
        // one falls in left and other falls in right side of mid. We also store the result so that later this can
        // be used in merge
        int leftQuery = querySegTree(2 * treeIndex + 1, lo, mid, i, mid);
        int rightQuery = querySegTree(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

        // merge query results
        return merge(leftQuery, rightQuery);
    }

    public void updateSegTree (int i, int val) {
        updateValSegTree(0, 0, len -1, i, val);
    }

    // Update the Seg Tree almost the same wey we created it
    // We need to update the leaf that matches the index i, we update  its value to val.
    // We recursively find such a leaf in the segment tree and update it. But we also need to update the all
    // the ancestor nodes of that leaf node. So when coming back up from recursion we update the ancestor node
    // Note : do not confuse it with heapify
    // call this method as updateValSegTree(0, 0, n-1, i, val);
    // Here you want to update the value at index i with value val.
    private void updateValSegTree(int treeIndex, int lo, int hi, int arrIndex, int val) {
        if (lo == hi) {                 // leaf node. update element.
            tree[treeIndex] = val;
            return;
        }

        int mid = lo + (hi - lo) / 2;   // recurse deeper for appropriate child

        if (arrIndex > mid)             // go to the right
            updateValSegTree(2 * treeIndex + 2, mid + 1, hi, arrIndex, val);
        else if (arrIndex <= mid)       // or go to left
            updateValSegTree(2 * treeIndex + 1, lo, mid, arrIndex, val);

        // merge updates, update the ancestor  nodes with new updated result of the leaf 'nums[i]'
        tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
    }


}
