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
    // The idea is to think about the how do get a specific rectangle given all the rectangle from top,left
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

        // create the cache table building on the same idea of getting a bounded region using region starting from zero
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
        // This uses the more complicated version look at the constructor public NumMatrix(int[][] matrix, boolean skip)
        // lets not do this as it just makes the cacl complicated I think I was using top right corner here
        // The better version is below
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
    // The idea is to use Trie to solve this problem. The add is simple, its just regular try implementation.
    // The search requires to match '.' as any so requires special handling for example b.d will match bad in trie
    // so for search whenever we get '.' we need to look at all the children of the current node which is equivalent
    // to skipping '.' so we visit all the children's subtree for the result
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
                // found a '.' check all the valid children sub tree for the result
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


    // LeetCode :: 341. Flatten Nested List Iterator
    // The idea is to use a stack of iterator for the nested Iterators.
    // For example if NestedInteger is [1, [4, [6, [7]]], 8] then for each inner list we need an iterator so we keep
    // this iterators at each level in a stack We start with the top level iterator in the stack
    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * This is just to know what the NestedInteger interface will look like
     * */
     public interface NestedInteger {

          // @return true if this NestedInteger holds a single integer, rather than a nested list.
          public boolean isInteger();

          // @return the single integer that this NestedInteger holds, if it holds a single integer
          // Return null if this NestedInteger holds a nested list
          public Integer getInteger();

          // @return the nested list that this NestedInteger holds, if it holds a nested list
          // Return null if this NestedInteger holds a single integer
          public List<NestedInteger> getList();
     }
    public class NestedIterator implements Iterator<Integer> {
        Iterator<NestedInteger> itr;
        LinkedList <Iterator<NestedInteger>> stack = new LinkedList<>();
        NestedInteger nint;

        public NestedIterator(List<NestedInteger> nestedList) {
            itr = nestedList.iterator();
            // push the top level iterator in stack
            stack.push(itr);
        }

        @Override
        // All the effort for finding the next item is actually done in hasNext and the result
        // Integer is already stored in nint so we just return the integer
        public Integer next() {
            return nint.getInteger();
        }

        @Override
        // This function does all the work it checks if the next item exist if yes grab the first item and
        // put it in nint. We peek at the top of the stack and start readin item using that iterator.
        // if the top iterator is empty we peek the next iterator from the stack. If the top iterator is pointing to
        // nonempty list we just push another iterator for the non empty list  to the stack. We keep doing this until
        // we get to list which has integer item, if no such item is found and we exhaust our stack of iterator
        // then no item exist in the nested list and we return false
        public boolean hasNext() {
            // keep lookling  for nested list iterator in stack
            while(!stack.isEmpty()){
                // the top item point to valid entry
                if(stack.peek().hasNext()){
                    Iterator<NestedInteger> tempItr = stack.peek();
                    nint = tempItr.next();
                    // the item is a single int store it in our nint so the call to 'next' can return this
                    if (nint.isInteger())
                        return true;
                    // we got another nested non empty list lets get an iterator for this and push it to the stack for
                    // next processing
                    if(nint.getList().size() != 0) {
                        stack.push(nint.getList().iterator());
                    }

                } else {
                    // no valid entry pop and look for next nested list iterator
                    stack.pop();
                }
            }
            return false;
        }
    }

    // LeetCode :: 146. LRU Cache
    // The idea is to use hashmap to store the key and a doubly linked list reference as value to make the access O(1)
    // We use a double linked list manage the LRU cache new items are add to the head, when we access an item using get
    // the recently accessed item needs to move to the head. This items closer to head are recently accessed and the
    // items closer to tail ar least recently accessed and candidates for removal when the capacity exceeds.
    // As the capacity exceeds we remove the leas recently used item from tail.
    // We use a dummy head & a dummy tail to make the code more concise and easy to manage.
    public class DblLinkList {
         int val;
         int key;
         DblLinkList prev;
         DblLinkList next;
         public DblLinkList(int k,int v){
             val = v;
             key = k;
             prev = next = null;
         }
    }

    class LRUCacheDList {
        int currCapcity;
        DblLinkList head;
        DblLinkList tail;
        HashMap<Integer, DblLinkList> cache;

        public LRUCacheDList(int capacity) {
            currCapcity = capacity;
            cache = new HashMap<>();
            head = new DblLinkList(Integer.MIN_VALUE,Integer.MIN_VALUE); // dummy head
            tail = new DblLinkList(Integer.MIN_VALUE, Integer.MIN_VALUE); // dummy tail
            head.next = tail;
            tail.prev = head;
            head.prev = tail;
            tail.next = head;
        }

        // move the current node to the head of the doubly linked list
        private void moveToHead (DblLinkList node){
            if (node.next == tail && node.prev == head)
                return;
            // remove value from current position
            node.prev.next = node.next;
            node.next.prev = node.prev;
            // move value after head
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // add a node after the dummy head of the doubly linked list
        private void addToHead (DblLinkList node){
            // insert the new item to head
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // delelte the last node that is the node before the dummy tail from the doubly linked list
        private  void deleteFromTail() {
            DblLinkList dlNode = tail.prev;
            tail.prev = dlNode.prev;
            dlNode.prev.next = tail;
            // remove from the hashmap
            cache.remove(dlNode.key);
        }

        public int get(int key) {
            DblLinkList value = cache.get(key);
            if (value == null)
                return  -1;
            if (value.next == tail && value.prev == head)
                return value.val;
            // move the recently accessed value to the head
            moveToHead(value);
            return value.val;

        }

        public void put(int key, int value) {
            // get the value if it already exist otherwise create a new one
            DblLinkList node = cache.getOrDefault(key, null);
            if (node == null)  {
                node = new DblLinkList(key, value);
                // insert the new item to head
                addToHead(node);
                // put the entry on hashmap
                cache.put(key, node);
            } else {
                // We already have the node in cache so move the node to the head & update the value
                moveToHead(node);
                node.val = value;
            }
            // if we exceeded the capacity remove the oldest one,
            // the oldest one is at the tail so we remove from tail
            if (cache .size() > currCapcity) {
                deleteFromTail();
            }

        }
    }
    // This is another way to implement the LRU cache using Java's LinkedHashMap
    // LinkedHashMap is ordered map and we just need to overwrite the removeEldestEntry
    // to implement the cache.
    //
    // LinkedHashMap :
    // Hash table and linked list implementation of the Map interface, with predictable iteration order.
    // This implementation differs from HashMap in that it maintains a doubly-linked list running through all of
    // its entries. This linked list defines the iteration ordering, which is normally the order in which keys were
    // inserted into the map (insertion-order). Note that insertion order is not affected if a key is re-inserted
    // into the map.
    public class CacheMap extends LinkedHashMap<Integer, Integer> {
         final int capacity;
         public CacheMap(int cap){
             super(cap, 0.75f, true);
             capacity = cap;

         }
        @Override
        // we just override this function if the map size  is greater capacity this overwrite wil delete the LRU item
        // in the map. This function internally gets called followed by a put or putAll method call
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
             System.out.println("map size " + size() + " capacity "+ capacity);
             return size() >capacity;
        }

    }

    class LRUCache {
        CacheMap cache;
        int cap;
        public LRUCache(int capacity) {
            cap = capacity;
            cache = new CacheMap(cap);
        }

        public int get(int key) {
            return cache.getOrDefault(key , -1);
        }

        public void put(int key, int value) {
            cache.put(key,value);
        }
    }


}
