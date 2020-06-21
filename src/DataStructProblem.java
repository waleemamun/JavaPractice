import java.util.*;

public class DataStructProblem {
    // LeetCode :: 381. Insert Delete GetRandom O(1) - Duplicates allowed
    /**
     * We will keep a list to store all our elements. In order to make finding the index of elements we want
     * to remove O(1)O(1), we will use a HashMap or dictionary to map values to all indices that have those values.
     * To make this work each value will be mapped to a set of indices.
     * The idea is to have a HasHMap + HashSet + ArrayList
     * To store the val , we use the val as the key of the hashmap and in the set we store the index of that value in
     * the arraylist
     * HashMap store the unique val and the set holds all the indices of that unique val in the ArrayList
     * To hold the O(1) getRandom when we delete a val we need to update the index properly in both map/set & Arraylist
     * to delete from arraylist and not to upset any index we can swap the value with the last value and delete it from
     * there
     * */
    class RandomizedCollection {
        HashMap<Integer, HashSet<Integer>> map;
        ArrayList<Integer> alist;
        Random rand = new java.util.Random();
        /** Initialize your data structure here. */
        public RandomizedCollection() {
            map = new HashMap<>();
            alist = new ArrayList();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            HashSet<Integer> set = map.getOrDefault(val, new HashSet<Integer>());
            boolean first = false;
            if (set.size() == 0) {
                first = true;
            }
            set.add(alist.size());
            map.put(val, set);
            alist.add(val);
            return first;

        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if(!map.containsKey(val))
                return false;
            HashSet<Integer> set = map.get(val);
            Iterator<Integer> itr = set.iterator();
            int idx = itr.next();
            set.remove(idx);
            if(set.size() == 0)
                map.remove(val);
            if (idx < alist.size()-1) {
                Collections.swap(alist,idx,alist.size()-1);
                int swapVal = alist.get(idx);
                map.get(swapVal).add(idx);
                map.get(swapVal).remove(alist.size()-1);
            }
            alist.remove(alist.size()-1);
            return true;

        }

        /** Get a random element from the collection. */
        public int getRandom() {
            return alist.get(rand.nextInt(alist.size()));
        }
    }

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection obj = new RandomizedCollection();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
}
