import jdk.nashorn.internal.ir.SplitReturn;
import sun.util.resources.cldr.is.CalendarData_is_IS;

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
            // the value exist delete the first index for this value
            HashSet<Integer> set = map.get(val);
            Iterator<Integer> itr = set.iterator();
            int idx = itr.next();
            set.remove(idx);
            // set is empty remove the entry from map
            if(set.size() == 0)
                map.remove(val);
            // the removed index for the value is not the last index so we swap it with the last entry in the
            // arraylist, update the index properly for the swapped val
            if (idx < alist.size()-1) {
                Collections.swap(alist,idx,alist.size()-1);
                int swapVal = alist.get(idx);
                map.get(swapVal).add(idx);
                map.get(swapVal).remove(alist.size()-1);
            }
            // finally delete the last item from the arraylist
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

    // LeetCode :: 380. Insert Delete GetRandom O(1)
    // The idea is to use and HashMap for O(1) insert/remove and an ArrayList for O(1) random
    // We keep the same copies of the items in hashmap & arraylist, hashmap key is the item and
    // value is the index of the item in the arrayList
    // In this case inser should be easy we just need to insert in hashmap and arraylist and
    // store the index of the value in hashmap
    // Delete/remove needs a special trick we remove an item from hashmap we get the index of the
    // item in arraylist swap it with the last item in the arraylist & update the last items index
    // in hash map with the new swapped index. Finally delete the last item from arrayList
    // This help us run a O(1) getrandom cause we can easily call math.random on the size of the arraylist
    class RandomizedSet {

        /** Initialize your data structure here. */
        HashMap<Integer,Integer> randSet;
        ArrayList <Integer> aList;
        public RandomizedSet() {
            randSet = new HashMap<Integer,Integer>();
            aList = new ArrayList<Integer> ();

        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if(!randSet.containsKey(val)) {
                randSet.put(val,aList.size());
                aList.add(val);
                return true;
            } else
                return false;

        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(randSet.containsKey(val)) {
                // get the index of the val in array list
                int idx = randSet.get(val);
                // swap this val with the last index of the ArrayList
                Collections.swap(aList, idx, aList.size() -1);
                // get the value at this index after swap
                int swapVal = aList.get(idx);
                // update the  hashmap with the new index for swapVal
                randSet.put(swapVal,idx);
                // remove value from hasmap & ArrayList
                randSet.remove(val);
                aList.remove(aList.size()-1);
                return true;
            } else
                return false;

        }

        /** Get a random element from the set. */
        public int getRandom() {
            int size = randSet.size();
            int rand = (int)(Math.random()*size);
            return aList.get(rand);
        }
    }

        /**
         * Your RandomizedSet object will be instantiated and called as such:
         * RandomizedSet obj = new RandomizedSet();
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
    // We need to modify the hasNext as the pointers has moved because of our call in '
    // the constructor, as the pointer is moved 1 ahead we need to compare top with null
    // to check if we have reached the end
    class PeekingIterator implements Iterator<Integer> {
        Iterator<Integer> peekingIterator;
        Integer top = null;
        public PeekingIterator(Iterator<Integer> iterator) {
            // initialize any member here.
            peekingIterator = iterator;
            top = peekingIterator.hasNext() ? peekingIterator.next() : null;
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
            top = peekingIterator.hasNext() ? peekingIterator.next() : null;
            return res;

        }

        @Override
        public boolean hasNext() {
            return top != null;
        }
    }

    // LeetCode :: 304. Range Sum Query 2D - Immutable
    // The idea is to think about the how do get a specific rectangle given all the rectangle from top,left
    class NumMatrixImmurable {
        int cache[][] = null;

        public NumMatrixImmurable(int[][] matrix, boolean skip) {
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
        public NumMatrixImmurable (int [][] matrix, int v2) {
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
        public int sumRegion1(int row1, int col1, int row2, int col2) {
            int x = cache[row2][col2];
            int y = (col1 == 0 ? 0 : cache[row2][col1-1]);
            int z = (row1 == 0 ? 0:cache[row1-1][col2]);
            int a = (row1 == 0 || col1 == 0) ? 0:cache[row1 -1][col1-1];
            return x- y -z +a;
        }
        // This version is faster & easier check the constructor public NumMatrix (int [][] matrix)
        // Sum(ABCD) = Sum(OD)−Sum(OB)−Sum(OC)+Sum(OA)
        public int sumRegion2(int row1, int col1, int row2, int col2) {
            return cache[row2+1][col2+1] - cache[row2+1][col1] - cache[row1][col2+1] + cache[row1][col1];
        }

        // version 3

        public NumMatrixImmurable (int [][] matrix) {
            if (matrix.length == 0) {
                cache = null;
                return;
            }
            for (int col = 0; col < matrix[0].length; col++) {
                for (int row = 1; row < matrix.length; row++) {
                    matrix[row][col] += matrix[row-1][col];
                }
            }
            for (int row = 0; row < matrix.length; row ++){
                for (int col = 1; col< matrix[0].length; col++) {
                    matrix[row][col] += matrix[row][col-1];
                }
            }
            cache = matrix;
        }
        public int sumRegion(int row1, int col1, int row2, int col2) {
            int x = cache[row2][col2];
            int y = (col1 == 0 ? 0 : cache[row2][col1-1]);
            int z = (row1 == 0 ? 0:cache[row1-1][col2]);
            int a = (row1 == 0 || col1 == 0) ? 0:cache[row1 -1][col1-1];
            return x - y -z +a;
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
         int count;
         DblLinkList prev;
         DblLinkList next;
         public DblLinkList(int k,int v){
             val = v;
             key = k;
             prev = next = null;
         }
        public DblLinkList(int k,int v, int c){
            val = v;
            key = k;
            count = c;
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
    // LeetCode :: 460. LFU Cache
    // The idea is to use two hashmaps and a doubly linked list. The first hashmap  is similar to LRU cache  but the
    // 2nd hashmap is a frequency map and points to doubly linked list of nodes for that frequency when we get/put a
    // node we move the node from one doubly linked list to another (lower to higher frequency). We also need to keep
    // track of the min which very important for deleting the lowest frequency node. When we access a node we update
    // the frequency of that node from lower to higher by 1. Now if the lower frequency dlist becomes empty and at the
    // same time the current min is same as the lower frequency we need to update/increase min by 1.
    // When we add a new item to the LFU cache we reset min to 1.
    // Note its easier to use dlist with head/tail to code this. We can also use only head and use head.prev to point
    // to the head of the list head.next to point to the tail of the list but the code becomes hard to manage
    // during interview, so we can discuss this as an optimisation.
    class LFUCache {
        HashMap<Integer, DblLinkList> cache;
        HashMap<Integer, DblLinkList> freqMap;
        int cap;
        int min;
        public LFUCache(int capacity) {
            cap = capacity;
            min = 0;
            cache = new HashMap<>();
            freqMap = new HashMap<>();
        }

        public int get(int key) {
            DblLinkList node = cache.getOrDefault(key, null);
            if (node != null) {
                removeFromOldFreq(node);
                addToHead(node);
                return node.val;
            }
            return -1;
        }
        // Its much
        private void addHeadTail(int freq) {
            DblLinkList head = new DblLinkList(0,0,0);
            DblLinkList tail = new DblLinkList(0,0,0);
            head.next = tail;
            tail.prev = head;
            head.prev = tail;
            tail.next = head;
            freqMap.put(freq, head);
        }

        private void addToHead(DblLinkList node) {
            if (!freqMap.containsKey(node.count)){
                addHeadTail(node.count);
            }
            DblLinkList head = freqMap.get(node.count);
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeFromOldFreq(DblLinkList node) {
            DblLinkList before = node.prev;
            DblLinkList after = node.next;
            DblLinkList head = freqMap.get(node.count);
            DblLinkList tail = head.prev;
            before.next = after;
            after.prev = before;
            node.prev = node.next = null;
            // this dlist is empty and the current min is same as this  frequency so, we increase min by 1
            if (head.next == tail && min == node.count)
                min = node.count+1;
            node.count++;

        }

        private void deleteNode (int freq) {
            DblLinkList head = freqMap.get(freq);
            DblLinkList tail = head.prev;
            DblLinkList node = tail.prev;
            tail.prev = node.prev;
            node.prev.next = tail;
            // this dlist is empty and the current min is same as this  frequency so, we increase min by 1
            if (head.next == tail && min == node.count)
                min = node.count+1;
            cache.remove(node.key);
        }
        public void put(int key, int value) {
            if (cap == 0) return;
            DblLinkList node = cache.getOrDefault(key, null);
            if (node == null) {
                if (cap == cache.size()) {
                    System.out.println(key +" v " + value + " m "+ min );
                    deleteNode(min);
                }
                node = new DblLinkList(key, value, 1);
                addToHead(node);
                // new item reset current min to 1
                min = 1;
                cache.put(key,node);
            } else {
                node.val = value;
                removeFromOldFreq(node);
                addToHead(node);
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
    // 355. Design Twitter
    // The main idea is to use k sorted list merge using max heap to get the top 10 items
    class Tweet {
         Integer user;
         Integer tweet;
         int time;
         public Tweet (int u , int tw, int tm){
             user = u;
             tweet = tw;
             time = tm;
         }
    }
    // This class is required for the maxheap. It is used to select the next item from the k sorted list when
    // the top item was removed. The top removed item points out the next array (listNum) and an index into that
    // array (idx) to insert/add to the max heap
    class QueueItem {
         Tweet tw;
         int listNum;
         int idx;
         public QueueItem(Tweet t, int l, int i) {
             tw = t;
             listNum = l;
             idx = i;
         }
    }
    class Twitter {
        HashMap<Integer, LinkedList<Tweet>> timeLineMap; // per user timeline
        HashMap<Integer,HashSet<Integer>> followMap;     // per user followList

        int time;
        /** Initialize your data structure here. */
        public Twitter() {
            time = 0;
            timeLineMap = new HashMap<>();
            followMap = new HashMap<>();
        }

        /** Compose a new tweet. */
        public void postTweet(int userId, int tweetId) {
            time++;
            Tweet tw = new Tweet(userId, tweetId, time);
            // add tweet to user's own TimeLine
            timeLineMap.putIfAbsent(userId, new LinkedList<>());
            timeLineMap.get(userId).addFirst(tw);
        }

        /** Retrieve the 10 most recent tweet ids in the user's news feed.
         *  Each item in the news feed must be posted by users who the user followed or by the user herself.
         *  Tweets must be ordered from most recent to least recent. */
        // The idea here is to use K sorted list merge to get 10 max items
        // All the user timelines are time sorted (descending order)
        // So we first get the followlist from the follow list we form the array of timelIne list of (following user)
        // these list are sorted so to get the top 10 we use a max heap and use he K sorted list merge option
        public List<Integer> getNewsFeed(int userId) {
            LinkedList<Tweet> userTL = timeLineMap.getOrDefault(userId, null);
            HashSet<Integer> followList = followMap.getOrDefault(userId, null);
            LinkedList<Integer> newsFeed = new LinkedList<>();
            if (userTL == null && followList == null)
                return newsFeed;

            // create min heap create the most recent tweets from the followers
            PriorityQueue<QueueItem> maxHeap = new PriorityQueue<>(new Comparator<QueueItem>() {
                @Override
                public int compare(QueueItem o1, QueueItem o2) {
                    return o2.tw.time - o1.tw.time;
                }
            });

            // From the follower list get the timeline for each follow and add an iterator to the timeLine list
            if (followList != null) {
                LinkedList <Tweet> [] listArr = new LinkedList[followList.size()+1];
                // add the user time line to the k sorted list too so during our merge we can easily merge them
                listArr[0] = userTL;
                if (userTL != null)
                    maxHeap.add(new QueueItem(userTL.get(0), 0, 0));
                int i = 1;
                for (Integer u : followList) {
                    listArr[i] = timeLineMap.getOrDefault(u, null);
                    if (listArr[i] != null) {
                        QueueItem qi = new QueueItem(listArr[i].get(0), i, 0);
                        maxHeap.add(qi);
                    }
                    i++;
                }
                int count = 10;
                while(!maxHeap.isEmpty() && count !=0 ){
                    QueueItem qi = maxHeap.remove();
                    newsFeed.add(qi.tw.tweet);
                    if (listArr[qi.listNum].size() > qi.idx +1) {
                        qi.tw = listArr[qi.listNum].get(qi.idx+1);
                        qi.idx++;
                        maxHeap.add(qi);
                    }
                    count--;
                }

            } else {
                for (Tweet tw : userTL){
                    newsFeed.add(tw.tweet);
                    if (newsFeed.size() == 10)
                        break;
                }
            }
            return newsFeed;

        }

        /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
        public void follow(int followerId, int followeeId) {
            if (followeeId == followerId)
                return;
            followMap.putIfAbsent(followerId, new HashSet<>());
            followMap.get(followerId).add(followeeId);
        }

        /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
        public void unfollow(int followerId, int followeeId) {
            if (followeeId == followerId)
                return;
            HashSet<Integer> followList = followMap.getOrDefault(followerId, null);
            if (followList != null)
                followList.remove(followeeId);
        }
    }

    // LeetCode :: 519. Random Flip Matrix
    // The idea is to use "Fisher–Yates shuffle" modern algorithm to choose the random permutation
    // The modern Algo:
    // Store the numbers from 1 through N in an array arr[N]
    //   1. Pick a random number k between (0 - curLen)
    //   2. swap the arr[k] with arr[curLen],
    //   3. Decrease curLen by 1.
    //   4. Repeat step 1 - 4 until the curLen is zero
    // Here we also use further space optimisation by using a hashmap. Instead of using an array of len = M*N
    // we use a hashmap to store the swapped items in the proper index in the hashmap.
    // This way we dont need the whole array but just need swapped items to store in the hashmap
    // In worst case the hashmap may grow upto O(m*n) space but average case should be better
    class SolutionFlipMatrix {
         public HashMap<Integer, Integer> map;
         int total;
         Random rand;
         int totR;
         int totC;
        public SolutionFlipMatrix(int n_rows, int n_cols) {
            totR = n_rows;
            totC = n_cols;
            map = new HashMap<>();
            total = n_cols * n_rows;
            rand = new Random();
        }

        public int[] flip() {
            // get the next random index  between zero to total (exclusive)
            int randomIdx = rand.nextInt(total);
            total--;
            int randVal = map.getOrDefault(randomIdx, randomIdx);
            map.put(randomIdx, map.getOrDefault(total, total));
            return new int[] {randVal/totC, randVal % totC};
        }

        public void reset() {
            map.clear();
            total = totR * totC;

        }
    }

    // LeetCode :: 470. Implement Rand10() Using Rand7()
    /**
     * The rand7() API is already defined in the parent class SolBase.
     * public int rand7();
     * @return a random integer in the range 1 to 7
     */
    class Rand10 {
        public int rand7(){
           return new Random().nextInt(7) +1;
        }

        // The idea is to use rejection sampling to generate random10 using random7
        // we create a n*n 2d space where the numbers (1 to 10) are mapped for 7*7
        // we can map only upto 40, and 41 - 49 will not be mapped.
        // We first gen ran7 for row and rand7 for col this gives
        // a true random distribution of the row & col option after that
        // if we found a number using the row we return it if we found an empty space
        // (41 - 49 are empty space & 1 - 40 has numbers 1 - 10 sequentially for times hence 40)
        // we retry to get row & col that maps to a number 1 to 40
        public int rand10() {
            int row;
            int col;
            int idx =0;
            // repeativly try to map the row & col to a 2d space where the value is between 1 to 40
            // as row & col max is 7 so possible values are 1 - 49
            while (true) {
                row= rand7();
                col = rand7();
                idx = (row-1) * 7 + col;
                // found row col that maps to 40 we can use this as our result
                if (idx <= 40)
                    break;
            }
            if (idx%10 == 0)
                return 10;
            return idx%10;
        }
    }

    // LeetCode :: 157. Read N Characters Given Read4
    /**
     * Given a file and assume that you can only read the file using a given method read4,
     * implement a method to read n characters.
     *
     * The read4 API is defined in the parent class Reader4.
     *     int read4(char[] buf);
     *
     * Example
     * Input: file = "leetcode", n = 5
     * Output: 5
     * Explanation: After calling your read method, buf should contain "leetc".
     *              We read a total of 5 characters from the file, so return 5.
     *
     * Input: file = "abc", n = 4
     * Output: 3
     * Explanation: After calling your read method, buf should contain "abc". We read a total of 3 characters
     * from the file, so return 3. Note that "abc" is the file's content, not buf. buf is the destination buffer
     * that you will have to write the results to.
     */
    public class Reader4{ public int read4(char []buf){ return (int)Math.random()*4;}}
    public class ReaderN extends Reader4 {
        /**
         * @param buf Destination buffer
         * @param n   Number of characters to read
         * @return    The number of actual characters read
         *
         * This function will be called multiple Time the code for LeetCode:: 158
         *
         * file = "leetcodeleetcode" n=5 read called twice
         * output = 10 read "leetcodele"
         * first read 'leetc' buffer has 'ode'
         * 2dn read call buuffer already has ode so copy ode to buff; buff will have 'ode' n becomes 5 - 3 so 2
         *  read4 call will read 'leet' but the buff will ad only 2 char 'le' and rCount = 4
         */

        char []read4Buffer = new char[4];
        int lastReadOffSet = 0;
        int readRemain = 0;

        // file = 'leetcodeleetcode' n = 5  l
        // first read 'leetc'  lastReadoffset = 1 read4buf has 'ode' to be read
        // 2nd read 'odele' lastOffset 2 read4vuf to be read 'et'
        // 3rd read 'etcod' lastOffset 3 read4vuf to be read 'e'
        // 4th read 'e'
        // LeetCode :: 158. Read N Characters Given Read4 II - Call multiple times
        // The idea is as follows
        // We have to store the amount char left to read in readRemain that way we on the subsequent call we can read
        // the remaining item from the buffer read4Buffer. The read4Buffer also holds the last read item so we can read
        // from this buffer if anything is left to read to the destination buffer
        public int read(char[] buf, int n) {
            int readSofar = 0;
            while (n > 0) {
                // offset into the read4Buffer giving the current position to start the read from
                lastReadOffSet %= 4;
                if(readRemain == 0) {
                    readRemain = read4(read4Buffer);
                }
                if (readRemain == 0)
                    break;
                int curCount = 0;
                while (curCount < Math.min(readRemain, n)) {
                    buf[readSofar++] = read4Buffer[lastReadOffSet++];
                    curCount++;
                    readRemain--;
                }
                n-=curCount;
            }
            return readSofar;
        }

        // this is little faster but still O(n) as the one earlier both are ok just done different ways
        public int read2(char[] buf, int n) {
            int readSofar = 0;
            while (n > 0 && readRemain > 0) {
                buf[readSofar++] = read4Buffer[lastReadOffSet++];
                n--;
                readRemain--;
            }
            while (n > 0) {

                readRemain = read4(read4Buffer);
                if (readRemain == 0)
                    break;
                lastReadOffSet = 0;
                int count = Math.min(readRemain, n);
                while (count > 0) {
                    buf[readSofar++] = read4Buffer[lastReadOffSet++];
                    readRemain--;
                    count--;
                }
                n-= lastReadOffSet;
            }
            return readSofar;

        }

        /**
         * @param buf Destination buffer
         * @param n   Number of characters to read
         * @return    The number of actual characters read
         *
         * This function will be called once this the code for LeetCode:: 157
         */
        public int readNSimple(char[] buf, int n) {
            int readSofar =0;
            char [] tempBuf = new char[4];
            while (n > 0) {
                int rCount = read4(tempBuf);
                for (int i = 0; i < Math.min(rCount,n); i++){
                    buf[readSofar++] = tempBuf[i];
                }
                n-= rCount;
                // this condition is very much need to handle the case when you file size is smaller than n
                // for example assume file size 10 and n = 100 so we need to check if we can read any more
                if (rCount < 4) {
                    break;
                }
            }
            return readSofar;
        }

    }
    // LeetCode :: 251 Flatten 2D Vector
    /**
     * Implement an iterator to flatten a 2d vector.
     * For example,
     * Given 2d vector
     * [
     *   [1,2],
     *   [3],
     *   [4,5,6]
     * ]
     *
     * output = [1,2,3,4,5,6]
     * */
    public class Vector2D {
        Iterator<List<Integer>> outer;
        Iterator<Integer> inner;
        public Vector2D(List<List<Integer>> vec2d) {
            outer = vec2d.iterator();
            if(outer.hasNext())
                inner = outer.next().iterator();
        }
        public int next() {
            return inner.next();
        }
        public boolean hasNext() {
            if(inner.hasNext())
                return true;
            else {
                boolean has = outer.hasNext();
                if(has) {
                    List<Integer> lst = outer.next();
                    if(lst.size() ==0)
                        return false;
                    inner = lst.iterator();
                }
                return has;
            }
        }

    }
    // LeetCode :: 384. Shuffle an Array
    // The idea is use The Fisher-Yates algorithm for random shuffling
    // The Fisher-Yates algorithm runs in linear time, as generating a
    // random index and swapping two values can be done in constant time.
    // Although we managed to avoid using linear space on the auxiliary array
    // from the brute force approach, we still need it for reset, so we're stuck
    // with linear space complexity.
    // The algorithm  is
    // Store the numbers from 1 through N in an array arr[N]
    //   1. Pick a random number k between (0 - curLen)
    //   2. swap the arr[k] with arr[curLen],
    //   3. Decrease curLen by 1.
    //   4. Repeat step 1 - 4 until the curLen is zero
    public class SolutionShuffle {
        int []orig;
        int []shfl;
        Random rand;
        public SolutionShuffle(int[] nums) {
            rand = new Random();
            shfl = new int [nums.length];
            orig = nums;
            int i = 0;
            for(int n: nums)
                shfl[i++] = n;
        }

        /** Resets the array to its original configuration and return it. */
        public int[] reset() {
            // just return the original array
            return orig;
        }

        /** Returns a random shuffling of the array. */
        public int[] shuffle() {
            int len = shfl.length;
            while (len > 1) {
                int ridx = rand.nextInt(len);
                int tmp = shfl[ridx];
                shfl[ridx] = shfl[len-1];
                shfl[len-1] = tmp;
                len--;
            }
            return shfl;
        }
        /**
         * Your Solution object will be instantiated and called as such:
         * Solution obj = new Solution(nums);
         * int[] param_1 = obj.reset();
         * int[] param_2 = obj.shuffle();
         */
    }

    // LeetCode :: 295. Find Median from Data Stream
    // The idea is to get the running median of unsorted sequence of numbers, we have to use a two heap approach
    // with a minHeap and a maxHeap to solve this problem.
    class MedianFinder {
        PriorityQueue <Integer> minHeap;
        PriorityQueue <Integer> maxHeap;
        /** initialize your data structure here. */
        public MedianFinder() {
            minHeap = new PriorityQueue<>();
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        }

        public void addNum(int num) {
            // put an item in minHeap if the minheap <= maxheap size,
            // we first need to put the new item to maxHeap and extract
            // the max item from maxHeap to put it in minHeap
            // extracting the max & adding an item to heap take lgn time
            if (minHeap.size() <= maxHeap.size()) {
                maxHeap.add(num);
                minHeap.add(maxHeap.remove());
            } else {
                // do the same thing for maxHeap extract the min iteam from minHeap
                // to put it on maxHeap
                minHeap.add(num);
                maxHeap.add(minHeap.remove());
            }

        }

        public double findMedian() {
            if ((minHeap.size() + maxHeap.size()) % 2 == 0) {
                return (double) (minHeap.peek() + maxHeap.peek()) / 2.0;
            } else
                return minHeap.peek();
        }
    }

    // LeetCode :: 308. Range Sum Query 2D - Mutable
    // The idea is to build a 2d FenwickTree and then find the range sum in 2d array using the same idea as
    // Sum(ABCD) = Sum(OD)−Sum(OB)−Sum(OC)+Sum(OA) in problem 304 in this file
    // Note the 2d Fenwick tree at (i,j) holds the sum of (0,0) -> (i,j) rectangle. So we can use the equation
    // Sum(ABCD) = Sum(OD)−Sum(OB)−Sum(OC)+Sum(OA) to get the sum of any rectangle in the 2d matrix.

    class NumMatrix {
        int [][]BITree;
        int [][]nums;
        public NumMatrix(int[][] matrix) {
            BITree = new int [matrix.length +1][matrix[0].length +1];
            nums = new int [matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j =0; j< matrix[0].length; j++) {
                    update(i,j,matrix[i][j]);
                }
            }
        }
        // update the 2d fenwick tree works similar to updating the 1D fenwick tree
        public void update(int row, int col, int val) {
            int delta = val - nums[row][col];
            nums[row][col] = val;
            for (int i = row+1; i < BITree.length; i+= i & (-i)) {
                for (int j = col+1; j < BITree[0].length; j+= j & (-j)) {
                    BITree[i][j] += delta;
                }
            }
        }
        // getting sum in  the 2d fenwick tree works similar to getting sum in the 1D fenwick tree
        private int sumRegion (int row, int col) {
            int sum = 0;
            for (int i = row+1; i > 0; i-= i & (-i)) {
                for (int j = col+1; j > 0; j-= j & (-j)) {
                    sum += BITree[i][j];
                }
            }
            return sum;
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return sumRegion(row2, col2)
                    - sumRegion( row1-1, col2)
                    - sumRegion(row2, col1-1)
                    + sumRegion(row1-1, col1 -1);
        }
    }

    // LeetCode :: 528. Random Pick with Weight
    // Think how on an unsorted array you can distribute a random number.
    // The idea is to do a prefix sum. If we have a prefix sum array that array will be ascending sorted.
    // Each entry represents the range it can consume for example in case of
    //        idx  0 1  2  3  4  5
    // orig array 10 3  5  7  2  3
    // prefix sum 10 13 18 25 27 30
    // so if we generate a number between 0 to 30 and say the number is between 0 - 9 it will got to index 0 as
    // orig array[0] = 10, similarly if the random number is 12 it goes index 1 and if the number is 20 it goes
    // to idx 3. So because of the prefix sum and generating a random number between 0 to total sum.
    // The prefix sum represents the weight per index. Finally base on that above observation we can just do a
    // binary search on our prefix sum array to find the proper index.
    class WeightedRandomPick {
        int sum = 0;
        int []wt;
        public WeightedRandomPick(int[] w) {
            wt = new int [w.length];
            for (int i = 0; i <w.length; i++) {
                sum += w[i];
                wt[i] = sum;
            }
        }

        private int search(int []wt, int val) {
            int low = 0;
            int high = wt.length -1;
            while (low < high) {
                int mid = low + (high-low)/2;
                // we cannot use val == wt[mid] as the we are trying to find where this val can be inserted not
                // searching for the value itself
                if (val < wt[mid])
                    high = mid;
                else
                    low = mid + 1;
            }
            return low;
        }

        public int pickIndex() {
            int val = (int) (Math.random() * sum);
            int idx = search(wt, val);
            return idx;
        }
    }

    // LeetCode :: 173. Binary Search Tree Iterator
    // The idea is to iteratively inorder traverse a binary search tree.
    // We preprocess at constructor by traversing all the left child and put them in a stack
    class BSTIterator2 {
        Stack<TreeNode> stack;
        TreeNode node;
        public BSTIterator2(TreeNode root) {
            stack = new Stack<>();
            node = root;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

        }

        /** @return the next smallest number */
        // The next call amortized O(1) in most cases we get a O(1) run time in some when a right child exist
        // we traverse the right child and put them in stack for next recursions
        public int next() {
            node = stack.pop();
            int res = node.val;
            node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            return res;
        }

        /** @return whether we have a next smallest number */
        // This is O(1) if both stack is empty & node is null we return false
        public boolean hasNext() {
            if(node == null && stack.isEmpty())
                return false;
            else
                return true;
        }
    }
    // its slow not sure why
    class BSTIterator {
        ArrayList<Integer> nodeList;
        TreeNode node;
        int pos;
        public BSTIterator(TreeNode root) {
            pos = 0;
            nodeList = new ArrayList<>();
            node = root;
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || node != null) {
                if (node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    node = stack.pop();
                    nodeList.add(node.val);
                    node = node.right;
                }
            }
        }

        /** @return the next smallest number */
        // The next call amortized O(1) in most cases we get a O(1) run time in some when a right child exist
        // we traverse the right child and put them in stack for next recursions
        public int next() {
            return nodeList.get(pos++);
        }

        /** @return whether we have a next smallest number */
        // This is O(1) if both stack is empty & node is null we return false
        public boolean hasNext() {
            return nodeList.size() > 0 && pos < nodeList.size();
        }
    }
    // LeetCode :: 1428. Leftmost Column with at Least a One
    // The idea is to find the first row that have 1 in the final column as this are row sorted if we find the
    // first row that has 1 in the last column then we can search that row and subsequent rows for a better column
    // So we first do a linear search on the last column to get the first row that has at least an 1.
    // Now we can scan the row from right to left to find a better (low index) column that has 1 if not found we check
    // the next row and so on.
    // This produces a O(M+N) algo N to search the last column for 1 and the search in the marix the
    // to find our better column which will be M+N as we go stepwise
    interface BinaryMatrix {
        public int get(int row, int col);
        public List<Integer> dimensions();
    }
    class BinaryMatrixSearch {

        public int search (BinaryMatrix binaryMatrix, int row, int col) {
            int i = 0;
            for (i = 0 ;i <= row; i++) {
                if(binaryMatrix.get(i,col) == 1)
                    break;
            }
            return i;

        }
        public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
            List<Integer> dim = binaryMatrix.dimensions();
            int rows = dim.get(0);
            int cols = dim.get(1);
            int r = search(binaryMatrix, rows-1, cols-1);
            if (r == rows)
                return -1;
            int c = cols -1;
            for (int i = r; i< rows; i++) {
                if (binaryMatrix.get(i, c) == 1) {
                    while (c > 0 && binaryMatrix.get(i,c-1) != 0)
                        c--;
                }

            }
            return c;
        }
    }
    // LeetCode :: 348. Design Tic-Tac-Toe
    // The idea is to not impplement the board itself rather keep track of row & col.
    // We create an array rowCount to count the X or O in that row for X (player = 1) we do a +1 and
    // for O (player = 2) we do -1. So if at any point a rowCount[id] becomes +n(player1 wins) or -n (player2 wins)
    // we do the same for colCount & diag & reverse diag. The reverse diag can be calculated using the formula
    // (row + col == n-1)
    class TicTacToe {
        int diag;
        int []rowCount;
        int []colCount;
        int revDiag;
        int win;
        int n;
        /** Initialize your data structure here. */
        public TicTacToe(int n) {
            diag = 0;
            revDiag = 0;
            win = 0;
            rowCount = new int[n];
            colCount = new int[n];
            this.n = n;
        }

        /** Player {player} makes a move at ({row}, {col}).
         @param row The row of the board.
         @param col The column of the board.
         @param player The player, can be either 1 or 2.
         @return The current winning condition, can be either:
         0: No one wins.
         1: Player 1 wins.
         2: Player 2 wins. */
        public int move(int row, int col, int player) {
            // win found no already no need to proceed
            if (win != 0)
                return win;
            // no win yet
            if (player == 1) {
                rowCount[row]++;
                colCount[col]++;
                // check if its diag position & increase diag
                if (row == col)
                    diag++;
                // check if its reverse diag position & increase reverse diag
                if ((row + col) == (n-1))
                    revDiag++;
                // check if a solution is reached
                if (rowCount[row] == n || colCount[col] == n || diag == n || revDiag == n)
                    win = 1;
                return win;
            } else {
                rowCount[row]--;
                colCount[col]--;
                // check if its diag position & decrease diag
                if (row == col)
                    diag--;
                // check if its reverse diag position & decrease reverse diag
                if (row + col == n-1)
                    revDiag--;
                // check if a solution is reached
                if (rowCount[row] == -n || colCount[col] == -n || diag == -n || revDiag == -n)
                    win = 2;
                return win;
            }
        }
    }

/**
 * Your TicTacToe object will be instantiated and called as such:
 * TicTacToe obj = new TicTacToe(n);
 * int param_1 = obj.move(row,col,player);
 */
    // LeetCode :: 901. Online Stock Span
    // The idea is to store the numbers and also maintain an array last which store the index of last bigger number
    // in last[i] for each nums[i]. The idea is similar to storing last bigger index when scanning from right to left
    // we scan from right to left as we want to stop at bigger number than price
    // This is also a DP approach, We  cannot use Mononic Queue cause we need Decreasing monotonic queue from right
    class StockSpanner {
        int []nums;
        int []last;
        int i;
        public StockSpanner() {
            nums = new int[10001]; // store the number stream
            last = new int[10001]; // store the last bigger number index
            i = 1;
            last[0] = -1;
            nums[0] = Integer.MAX_VALUE;
        }

        public int next(int price) {
            int j = i - 1;
            nums[i] = price;
            // find the lsat bigger numner index we will hop very quickly here when the previous numbers are stored
            while (j > 0 && price >= nums[j]) {
                j = last[j];
            }
            last[i] = j;
            int res = i - last[i];
            i++;
            return res;
        }
    }

    // LeetCode :: 353. Design Snake Game
    // The idea is to use a deque to track the snake position in the board when the head moves it adds to the end of
    // dequeue the head position and if no food then delete from the tail (front of the deque). One important test
    // is to allow snake head to move to tail position as the tail will follow consider a snake of size four moves
    // in 2x2 board in clock or anticlock wise
    class SnakeGame {

        /** Initialize your data structure here.
         @param width - screen width
         @param height - screen height
         @param food - A list of food positions
         E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0].            */
        int colC;
        int rowC;
        int[][]sFood;
        Deque<int []> dq; // hold the snake body including head & tail head is at the end and tail is at the begining
        HashSet<Integer> body; // hashset to track the current body position instead of creating a whole board as we have a limit on memory
        int i; // food position tracker
        public SnakeGame(int width, int height, int[][] food) {
            colC = width;
            rowC = height;
            dq = new LinkedList<>();
            dq.add(new int []{0,0});
            body = new HashSet<>();
            body.add(0);
            sFood = food;
            i = 0;
        }


        /** Moves the snake.
         @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
         @return The game's score after the move. Return -1 if game over.
         Game over when snake crosses the screen boundary or bites its body. */
        public int move(String direction) {
            int []head = dq.peekLast();
            int r = head[0];
            int c = head[1];
            if (direction.equals("U")) {
                r -= 1;
            } else if (direction.equals("D")) {
                r += 1;
            } else if (direction.equals("R")) {
                c += 1;
            } else {
                c -= 1;
            }

            if (r < 0 || c < 0 || r >= rowC || c >= colC)
                return -1;
            // remove the tail first so the snake can move to tail position if wanted
            // we can add the tail back if food found
            int []tail = dq.removeFirst();
            body.remove(tail[0] * colC + tail[1]);

            // check if snake touch its body
            int val = r * colC + c;
            if (body.contains(val))
                return -1;
            // add new cell as snake head
            dq.addLast(new int []{r,c});
            // update the snake position
            body.add(val);
            if (i < sFood.length && sFood[i][0] == r && sFood[i][1] == c) {
                i++;
                dq.addFirst(tail);
            }

            return dq.size()-1;

        }
    }

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */
    // LeetCode :: 1429. First Unique Number
    // We maintain a adoubly linked list queue of unique nodes so far
    // Same idea as LRU cache we need a hashmap and doubly linked list we add new node to the tail and read from the
    // head, if we discover a repeat we remove it from doubly linked list.
    public class DblLinkNode {
        int val;
        DblLinkNode prev;
        DblLinkNode next;
        int count;

        public DblLinkNode (int v){
            val = v;
            prev = null;
            next = null;
            count = 1;
        }
        public void incCount() {
            count++;
        }
    }

    class FirstUnique2 {
        HashMap<Integer, DblLinkNode> map;
        DblLinkNode head;
        DblLinkNode tail;

        public FirstUnique2(int[] nums) {
            map = new HashMap<>();
            head = new DblLinkNode(0);
            tail = new DblLinkNode(0);
            head.next = tail;
            head.prev = tail;
            tail.next = head;
            tail.prev = head;
            for (int n : nums) {
                add(n);
            }

        }

        private void addTail(DblLinkNode node) {
            node.next = tail;
            node.prev = tail.prev;
            tail.prev.next = node;
            tail.prev = node;
        }

        public int showFirstUnique() {
            if (head.next == tail)
                return -1;
            return head.next.val;
        }
        private void remove(DblLinkNode node){
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.next = null;
            node.prev = null;
        }

        public void add(int value) {
            DblLinkNode node = map.getOrDefault(value, null);
            if (node == null) {
                node = new DblLinkNode(value);
                addTail(node);
                map.put(value, node);
            } else {
                if (node.count == 1)
                    remove(node);
                node.incCount();
            }
            //print();

        }
        public void print (){
            DblLinkNode node = head;
            while (node != tail){
                System.out.print(node.val + " ");
                node = node.next;
            }
            System.out.println();
        }
    }


    class FirstUnique {

        private Set<Integer> setQueue = new LinkedHashSet<>();
        private Map<Integer, Boolean> isUnique = new HashMap<>();

        public FirstUnique(int[] nums) {
            for (int num : nums) {
                this.add(num);
            }
        }

        public int showFirstUnique() {
            // If the queue contains values, we need to get the first one from it.
            // We can do this by making an iterator, and getting its first item.
            if (!setQueue.isEmpty()) {
                return setQueue.iterator().next();
            }
            return -1;
        }

        public void add(int value) {
            // Case 1: This value is not yet in the data structure.
            // It should be ADDED.
            if (!isUnique.containsKey(value)) {
                isUnique.put(value, true);
                setQueue.add(value);
                // Case 2: This value has been seen once, so is now becoming
                // non-unique. It should be REMOVED.
            } else if (isUnique.get(value)) {
                isUnique.put(value, false);
                setQueue.remove(value);
            }
        }
    }

}
