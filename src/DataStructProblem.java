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

    // 729. My Calendar I
    // The idea is to use a TreeSet to have a Balanced tree, When adding an interval the TreeSet comparator will check
    // if this value falls into any of the interval if not then add it ot the treeSet
    // if yes then dont add anything and return false
    class MyCalendar {
        TreeSet<int []> calendarSet;
        public MyCalendar() {
            calendarSet = new TreeSet<>(new Comparator<int[]>() {
                @Override
                // This checks if the new interval  o1 is completely or partially with an interval or not
                public int compare(int[] o1, int[] o2) {

                    if(o1[1] <= o2[0])            // o1[1] i.e. newInterval end smaller than o2 start
                        return -1;
                    else if (o1[0] >= o2[1])      // o1[0] i.e. newInterval start bigger than o2 start
                        return 1;
                    // newInterval o1 completely within o2
                    // o1[0] i.e. newInterval start within o2
                    // o1[1] i.e. newInterval end within o2
                    else return 0;
                }
            });


        }

        public boolean book(int start, int end) {
            int [] interval= {start,end};
            return calendarSet.add(interval);

        }
    }
    // another implementation of the Calendar-I problem above
    // This uses the a TreeMap with start interval as the key and value is the end interval
    class MyCalendarI {
        TreeMap<Integer, Integer> calendar;

        MyCalendarI() {
            calendar = new TreeMap();
        }

        public boolean book(int start, int end) {
            // get the start interval that is smaller or equal to this newInteraval start
            Integer prev = calendar.floorKey(start);
            // get the start interval that is just bigger or equal to this newInteraval start
            Integer next = calendar.ceilingKey(start);
            // now if the new interval start is greater than its prev intervals's end
            // and the newInterval's end is smaller than the start of its next bigger
            // start interval then add it to list otherwise return false;
            if ((prev == null || calendar.get(prev) <= start) &&
                    (next == null || end <= next)) {
                calendar.put(start, end);
                return true;
            }
            return false;
        }
    }

    // LeetCode ::208. Implement Trie (Prefix Tree)
    // Implement a trie data structure with capability to check if a word exist or startwith
    // we assume the root node with value "."
    class TrieNode {
        Character val;                         // node val
        HashMap<Character, TrieNode> children; // Children nodes
        boolean hasWord; // indicates the wors is present in trie
        TrieNode [] mapChild;
        public TrieNode (Character ch){
            val = ch;
            children = new HashMap<>();
            hasWord = false;
            mapChild = new TrieNode[26];
        }

    }
    class Trie {
        TrieNode root;
        /** Initialize your data structure here. */
        public Trie() {
            root = new TrieNode('@');

        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            int i = 0;
            TrieNode node = root;
            while (i < word.length()) {
                node.children.putIfAbsent(word.charAt(i), new TrieNode(word.charAt(i)));
                node = node.children.get(word.charAt(i));
                if (i == word.length() -1)
                    node.hasWord = true;
                i++;
            }
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            int i = 0;
            TrieNode node = root;
            while (i < word.length()) {
                node = node.children.getOrDefault(word.charAt(i), null);
                // char noy found in trie word does not exist
                if (node == null)
                    return false;
                // we found the word
                if (node.hasWord && i == word.length() -1)
                    return true;
                i++;
            }
            return false;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            int i = 0;
            TrieNode node = root;
            while (i < prefix.length()) {
                node = node.children.getOrDefault(prefix.charAt(i), null);
                // char noy found in trie word does not exist
                if (node == null)
                    return false;
                // we found the word
                if (node.hasWord && i == prefix.length() -1)
                    return true;
                i++;
            }
            // the word is not found but the prefix exist
            return i==prefix.length();
        }
    }

    //LeetCode :: 284. Peeking Iterator
    // Simply store the  current value in the top. All calls to peek will return top
    // We update top during initialization once and then top is update in next call we
    // return the top value and update top with the nextValue
    class PeekingIterator implements Iterator<Integer> {
        Iterator<Integer> peekingIterator;
        Integer top = null;
        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            peekingIterator = iterator;
            if (peekingIterator.hasNext())
                top = peekingIterator.next();
        }

        // Returns the next element in the iteration without advancing the iterator.
        public Integer peek() {
            return top;

        }

        // hasNext() and next() should behave the same as in the Iterator interface.
        // Override them if needed.
        @Override
        public Integer next() {
            Integer res = top;
            if (peekingIterator.hasNext())
                top = peekingIterator.next();
            else
                top = null;
            return res;

        }

        @Override
        public boolean hasNext() {
            return peekingIterator.hasNext();
        }
    }

    // LeetCode :: 304. Range Sum Query 2D - Immutable
    class NumMatrix {
        int cache[][] = null;

        public NumMatrix(int[][] matrix, boolean skip) {
            if (matrix.length == 0) {
                cache = null;
                return;
            }
            cache = new int [matrix.length][matrix[0].length];
            cache[0][0] = matrix[0][0];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j< matrix[0].length; j++){
                    cache[i][j] = (j == 0? 0: cache[i][j-1]) + matrix[i][j];
                }
            }
            System.out.println(Arrays.deepToString(matrix));

            for (int i = 1; i< matrix.length; i++) {
                for (int j = matrix[0].length -1; j >= 0; j--) {
                    cache[i][j] = cache[i-1][j] + (j == 0 ? 0:cache[i][j-1]) + matrix[i][j];
                }
            }

            System.out.println(Arrays.deepToString(cache));

        }

        // create the case table building on the same idea of getting a bounded region using region starting from zero
        // Sum(ABCD) =  Sum(OB)+Sum(OC) + matrix[i][j] - Sum(OA)
        // Think how you can get you region by subtracting rectangles from the  bottom right rectangle
        public NumMatrix (int [][] matrix) {
            if (matrix.length == 0) {
                cache = null;
                return;
            }
            cache = new int [matrix.length + 1][matrix[0].length + 1];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    cache[i+1][j+1] = cache[i+1][j] + cache[i][j+1] + matrix[i][j] - cache[i][j];
                }
            }

        }

        public int sumRegion2(int row1, int col1, int row2, int col2) {
            int x = cache[row2][col2];
            int y = (col1 == 0 ? 0 : cache[row2][col1-1]);
            int z = (row1 == 0 ? 0:cache[row1-1][col2]);
            int a = (row1 == 0 || col1 == 0) ? 0:cache[row1 -1][col1-1];
            return x- y -z +a;
        }
        // This version is faster & easier check the constructor public NumMatrix (int [][] matrix)
        // Sum(ABCD) = Sum(OD)−Sum(OB)−Sum(OC)+Sum(OA)
        public int sumRegion(int row1, int col1, int row2, int col2) {
            return cache[row2+1][col2+1] - cache[row2+1][col1] - cache[row1][col2+1] + cache[row1][col1];
        }
    }

    // LeetCode :: 211. Add and Search Word - Data structure design

    class WordDictionary {
        TrieNode root;
        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new TrieNode('@');
        }

        /** Adds a word into the data structure. */
        public void addWord(String word) {
            int i = 0;
            TrieNode node = root;
            while(i < word.length()) {
                char ch = word.charAt(i);
                if (node.mapChild[ch -'a'] == null)
                    node.mapChild[ch -'a'] = new TrieNode(ch);
                node = node.mapChild[ch -'a'];
                if(i == word.length() -1)
                    node.hasWord = true;
                i++;
            }
        }

        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        private boolean search (String word, int index, TrieNode node) {
            if (index == word.length() || node == null)
                return false;
            char ch = word.charAt(index);
            if (ch != '.') {
                return node.mapChild[ch-'a'] != null && node.mapChild[ch-'a'].hasWord && index == word.length() -1
                        || (search(word, index+1, node.mapChild[ch-'a']));
            } else {
                for (int i = 0; i < 26; i++) {
                    if (node.mapChild[i] != null && node.mapChild[i].hasWord && index== word.length() -1
                            || (search(word, index + 1, node.mapChild[i])))
                        return true;
                }
                return false;
            }

        }

        public boolean search(String word) {
            int i = 0;
            return search(word, 0, root);
        }
    }


}
