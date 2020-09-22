import java.util.Arrays;

public class FenwickTree {
    // The array to hold the fenwick tree
    // remember that each index contain sum node in the tree
    // but not all of them are leaf node we cannot just print the whole
    // binaryIndexedTree for sum we have to query it using the getSum to
    // get the results
    private int binaryIndexedTree[];

    /**
     * Creating tree is like updating Fenwick tree for every value in array
     */
    public FenwickTree(int nums[]){
        binaryIndexedTree = new int[nums.length+1];
        for(int i = 0; i < nums.length; i++){
            updateBinaryIndexedTree(nums[i], i);
        }

    }

    /**
     * Start from index+1 if you updating index in original array. Keep adding this value
     * for next node till you reach outside range of tree. While updating we need to update
     * all the entries that can be affected. Remember we may have some internal node that holds
     * some sum, so we dont need to update each entry just updating internal node and may work
     */
    public void updateBinaryIndexedTree(int val, int index){
        index = index + 1;
        while(index < binaryIndexedTree.length){
            binaryIndexedTree[index] += val;
            index = getNext(index);
        }
    }

    /**
     * Using this we get the prefix sum upto index from zero
     * Start from index+1 if you want prefix sum 0 to index. Keep adding value
     * till you reach 0. We need start from index+1 node sum that node and all its parents to
     * get the prefix sum of index
     */
    public int getSum(int index){
        index = index + 1;
        int sum = 0;
        while(index > 0){
            sum += binaryIndexedTree[index];
            // get the parent index by dropping the last set bit
            index = getParent(index);
        }
        return sum;
    }


    /**
     * To get parent
     * We just need to drop the last set bit to get the parent
     */
    private int getParent(int index){
        return index & (index -1);
    }

    /**
     * To get next
     * 1) 2's complement of get minus of index
     * 2) AND this with index
     * 3) Add it to index
     */
    private int getNext(int index){
        return index + (index & -index);
    }

    // Call this way to get the sum of the whole array
    public void print() {
        for (int i = 0; i<binaryIndexedTree.length -1; i++)
            System.out.print(" " + getSum(i));
        System.out.println();
    }


}
