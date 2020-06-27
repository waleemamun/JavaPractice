

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;
import java.util.jar.JarEntry;

public class ScratchPad {

    public static void linleListPractice(){
        LinkedList<Integer> stack = new LinkedList<>();
        ArrayList<Integer> aStack = new ArrayList<>();
        LinkedList<Integer> queue = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        int size = 10;
        PriorityQueue<String> minHeap = new PriorityQueue<>(size, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length()- o2.length();
            }
        });
        String [] words = {"walee", "mamun","al","alm","abdullah","mama","nazeef","sabrina", "mama","la", "la", "land", ".and"};
        for(String word : words)
            minHeap.add(word);
        while (!minHeap.isEmpty()){
            System.out.println(minHeap.peek());
            minHeap.poll();
        }
        map.remove(1);
        minHeap.remove(1);

        //push
        stack.addFirst(3);
        stack.addFirst(2);
        stack.addFirst(1);
        //pop
        int x = stack.poll();
        System.out.println("pop " + x);
        //enqueue
        queue.add(3);
        queue.add(4);
        queue.add(5);

        //dequeue
        x = queue.poll();
        System.out.println("Dequeue " + x);

        // Sorts
        int [] arr = {1,3,5,2,4,7,2,94,86,33};
        Arrays.sort(arr);
        System.out.println(" Ascending Sort " + Arrays.toString(arr));
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int n : arr) {
            arrayList.add(n);
        }
        arrayList.sort(Collections.reverseOrder());
        System.out.println(" Reverse Order " + arrayList);
        ArrayList<String> wordList = new ArrayList<>();
        for (String word: words) {
            wordList.add(word);
        }
        wordList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        System.out.println(" Ascending word sort " + wordList);

        wordList.sort(new Comparator<String>() {

            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println(" Descending word sort " + wordList);

        PriorityQueue<String> maxHeap = new PriorityQueue<String>( new Comparator<String> (){
            public int compare (String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (String word: words) {
            maxHeap.add(word);
        }
        Iterator<String> iterator = maxHeap.iterator();
        System.out.println("Iterate maxHeap");
        while (iterator.hasNext()) {
            System.out.print(" " + iterator.next());
        }
        System.out.println(" Max heap removal ");
        while (!maxHeap.isEmpty()) {
            System.out.print(" "+ maxHeap.poll());
        }
        System.out.println();
        HashMap<String, Integer> strMap = new HashMap<>();
        for (String word : words) {
            int count = strMap.getOrDefault(word, 0);
            strMap.put(word,count + 1);
        }
        System.out.println("Hash Iterator");
        Iterator itrMap = strMap.entrySet().iterator();
        while (itrMap.hasNext()) {
            Map.Entry<String, Integer> mapEntry = (Map.Entry)itrMap.next();
            System.out.println( "[" + mapEntry.getKey() +":" + mapEntry.getValue()+"]");

        }
        System.out.println(" Stack ");
        LinkedList<String> stringStack = new LinkedList<>();
        for (String word: words){
            stringStack.addFirst(word);

        }
        while(!stringStack.isEmpty()) {
            System.out.print(" " + stringStack.pop());
        }
        System.out.println();
        System.out.println(" Queue");
        Queue<String> stringQueue = new LinkedList<>();
        for (String word: words){
            stringQueue.add(word);
        }

        while(!stringQueue.isEmpty()) {
            System.out.print(" " + stringQueue.poll());
        }
        System.out.println();
        int xy = 12345;
        Integer XY = xy;
        String str = XY.toString();
        System.out.println(str + " len " + str.length());
        char [] abc = {'m', 'a','m','u','n'};
        String myStr = new String(abc);
        System.out.println(myStr);

        HashSet<String> set = new HashSet<>();

        for (String word: words) {
            set.add(word);
        }

        Iterator<String> setitr = set.iterator();
        while (setitr.hasNext()) {
            System.out.print(setitr.next());
        }
        System.out.println();
        TreeSet <Integer> sortedSet = new TreeSet<>();
        int nums [] = {1,2,6,18,19,0};
        for (int n: nums){
            sortedSet.add(n);
        }
        Integer floor = sortedSet.floor(5);
        Integer ceil = sortedSet.ceiling(7);

        if (floor != null)
            System.out.println("Floor = " + floor);
        if (ceil != null) {
            System.out.println("Ceil " + ceil);
        }
        int [] nums1 = {9,5,4,3,2,1};

        int rand =0;
        int count = 20;
        while (count-- != 0) {
            rand = 1 + (int) (Math.random() * 6);
            System.out.print(rand + " ");
        }
        System.out.println();
        TreeSet<String> treeSet = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int len1 = o1.length();
                int len2 = o2.length();
                int i = 0;

                while (i <len1 && i <len2) {

                    if (o1.charAt(i) == o2.charAt(i) ||
                            o1.charAt(i) =='.') {

                        i++;
                    }
                    else
                        return o1.charAt(i) - o2.charAt(i);
                }
                return o1.length() - o2.length();
            }
        }) ;

        String [] wordds1 = {"al", "land",".and"};
        for (String wd : wordds1) {
            treeSet.add(wd);
        }
        System.out.println("Str Tree Set " + treeSet);
        if (treeSet.contains("l.nd")) {
            System.out.println("Found");
        } else {
            System.out.println("NOT FOUND");
        }

        String s2 = ".and";
        String s1 = "land";
        int j = 0;
        while (j<s1.length()) {
            if(s1.charAt(j) == s2.charAt(j)
              || s2.charAt(j) =='.')
                j++;
            else
                break;
        }

        TreeSet<int []> calendarSet = new TreeSet<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {

                if((o2[0] <= o1[0] && o1[1] < o2[1]) ||
                        (o2[0] <= o1[0] && o1[0] < o2[1]) ||
                        (o2[0] < o1[1] && o1[1] < o2[1]))
                    return 0;
                else if(o1[1] <= o2[0])
                    return -1;
                else if (o1[0] >= o2[1])
                    return 1;
                else return 0;
            }
        });

        int [][] edges = {
                {1, 4},
                {5, 8},
                {3, 5},
                {9, 10},
                {9, 12},
                {12, 14},
                {14,16},
                {16,18},
                {18,28},
                {22,28},
                {10,15},
                {8,9}
        };
        for (int []e : edges) {
            calendarSet.add(e);
        }


        System.out.println(" cal Set " + Arrays.deepToString(calendarSet.toArray()));

    }


        public static void stringList (ArrayList<String> strList, String s, int index, StringBuilder sb) {
            if(index == s.length()){
                strList.add(sb.toString());
                sb.deleteCharAt(sb.length()-1);
                return;
            }
            for (int i = index; i<s.length(); i++) {

                if (s.charAt(i) != '.')
                    sb.append(s.charAt(i));
                else
                {   int idx = sb.length();
                    for (char ch = 'a'; ch <='z'; ch++){

                        sb.append(ch);
                        stringList(strList,s,i+1, sb);
                        strList.add(sb.toString());
                        sb.delete(idx,sb.length());
                    }
                }
            }

        }


}
