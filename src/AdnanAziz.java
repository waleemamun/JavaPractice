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

}
