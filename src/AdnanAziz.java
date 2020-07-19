import org.omg.CORBA.MARSHAL;

import java.lang.reflect.Array;
import java.util.*;

public class AdnanAziz {

    // Adnan Aziz 14.1
    public ArrayList<Integer> intersectTwoSortedArrays(int []nums1, int []nums2) {
        int i = 0;
        int j = 0;
        ArrayList<Integer> resList = new ArrayList<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]){
                i++;
            } else {
                if (resList.size() == 0)
                    resList.add(nums1[i]);
                else if(resList.get(resList.size()-1) != nums1[i]) {
                    resList.add(nums1[i]);
                }
                i++;
                j++;
            }
        }
        return resList;
    }


    // Adnan Aziz 18.5 FIND THE MAJORITY ELEMENT more than half of the array contains the same element
    // find that in O(n) time & O(1) space
    // To solve focus on the majority is greater than half of the elements so in a corner case this would be for example
    // abacada , in all other case the at least two majority element will be consecutive for example abacdaa
    // This use the  Boyer-Moore Majority Vote Algorithm.
    // Check here : https://gregable.com/2013/10/majority-vote-algorithm-find-majority.html
    public static char majoritySearch(String str) {
        char candidate = '\0';
        int candidateCount = 0;
        int i = 0 ;
        char iter;
        while (i < str.length()){
            iter = str.charAt(i);
            if (candidateCount == 0) {
                candidate = iter;
                candidateCount = 1;
            } else if (candidate == iter) {
                ++candidateCount ;
            } else {
                --candidateCount ;
            }
            i++;
        }
        // make sure a majority elements exist so do a second pass
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == candidate)
                count++;
        }
        if (count <= str.length()/2)
            candidate = 0;
        return candidate ;
    }
    // search for the first index of an element in a sorted array
    public static int searchFirsIndexOf(int arr[], int target) {
        int low = 0;
        int high = arr.length -1;

        int result = -1;
        while (low <= high) {
            int mid = low + (high-low)/2;
            if (target == arr[mid]) {
                result = mid;
                high = mid -1;
            } else if (target < arr[mid]) {
                high = mid-1;
            } else {
                low = mid+1;
            }
        }
        return result;
    }
    // search for the last index of an element in a sorted array
    public static int searchForLastIdx(int arr[], int target) {
        int low = 0;
        int high = arr.length -1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high -low)/2;
            if (arr[mid] == target) {
                result = mid;
                low = mid+1;
            } else if ( target < arr[mid]) {
                high = mid -1;
            } else {
                low = mid +1;
            }
        }
        return result;
    }

    // 11.2 SORT AN K INCREASING-DECREASING ARRAY
    public static List<Integer> sortKIncreasingDecreasingArray(int nums[]) {
        List <List<Integer>> ksortedLists = new LinkedList<>();
        boolean increasing = true;
        List<Integer> tempList = new LinkedList<>();
        int start = 0;
        int i = 0;
        while (i < nums.length) {
            if(increasing) {
                while (i + 1< nums.length && nums[i] <= nums[i+1]){
                    tempList.add(nums[i]);
                    i++;
                }
                tempList.add(nums[i]);
                increasing = false;
            } else {
                while (i + 1< nums.length && nums[i] > nums[i+1]){
                    tempList.add(nums[i]);
                    i++;
                }
                tempList.add(nums[i]);
                Collections.reverse(tempList);
                increasing = true;
            }
            ksortedLists.add(tempList);
            tempList = new ArrayList<>();
            i++;
        }
        System.out.println(ksortedLists);
        PriorityQueue<Integer[]> minHeap = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[0] - o2[0];
            }
        });
        // init the priority queue with the first items from k sorted list
        int k = 0;
        for (List<Integer> lst : ksortedLists) {
            Integer []item = {lst.remove(0), k++};
            minHeap.add(item);
        }
        ArrayList<Integer> resList = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            Integer [] item = minHeap.remove();
            resList.add(item[0]);
            if ( ksortedLists.get(item[1]).size()!=0) {
                item[0] = ksortedLists.get(item[1]).remove(0);
                minHeap.add(item);
            }

        }
        return resList;
    }
    static int []tree;
    static int n;
    public static void build(int []nums) {
        if (nums.length > 0) {
            n = nums.length;
            tree = new int[n * 2];
            buildTree(nums);
        }
    }

    private static void buildTree(int[] nums) {
        for (int i = n, j = 0;  i < 2 * n; i++,  j++)
            tree[i] = nums[j];
        for (int i = n - 1; i > 0; --i)
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
        System.out.println(Arrays.toString(tree));
    }

    // Adnan Aziz 14.4 RENDER A CALENDAR
    // We are using a slightly different approach
    // We first get a minStart & maxStart event, create a map that can contain all the event for example
    // in case of calendar it would be all the hours or minutes of a day.
    // Now for each start time we store a +1 and end time we store -1 in the map
    // Next we walk through the map (from min to max event start time) and get a running sum,
    // the max running sum is out result and also the peakTime
    // The runtime for the problem is O(T + E); T == time in a day (min(1440) or hour (24)) & E = num of events
    //
    // NOTE : *** A same type of problem would be finding the maximum population year given the
    // list of life span of a population. We can use the exactly same approach described here
    // the event time map  will years and population life span is the intervals array,
    // we will find the min & max birth year, then map the birth & death years to the map as we have done for events
    public static int findMaxSimultaneousEvents(int [][] intervals){
        int minStart = Integer.MAX_VALUE;
        int maxStart = Integer.MIN_VALUE;
        // get min max start time
        for (int i = 0; i <intervals.length; i++) {
            minStart = Math.min(minStart, intervals[i][0]);
            maxStart = Math.max(maxStart, intervals[i][0]);
        }
        // create the mapping array this works as a hashmap
        int [] map = new int[maxStart +1];
        // we walk through intervals & map the start time & end time
        // we will do +1 for start & -1 for end time
        for (int inv[] :intervals) {
            map[inv[0]]++;
            map[inv[1]]--;
        }
        int sum = 0;
        int maxEvent = Integer.MIN_VALUE;
        int peakTime = 0;
        // now we walk through the map and calc the running sum for each time.
        // We store the max concurrent event & peak time
        for (int i = minStart; i <= maxStart; i++) {
            sum+= map[i];
            if (maxEvent < sum) {
                maxEvent = sum;
                peakTime = i;
            }
        }
        return maxEvent;
    }

}
