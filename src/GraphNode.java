import javafx.util.Pair;

import java.util.*;

public class GraphNode {
    int data;
    ArrayList<Integer> adj;
    int parent;
    int bfsDepth;

    public GraphNode (int d) {
        data = d;
        adj = new ArrayList<Integer>();
        parent = Integer.MIN_VALUE;
    }
    public GraphNode () {
        data = -1;
        adj = new ArrayList<Integer>();
        parent = Integer.MIN_VALUE;
    }

    public HashMap<Integer, HashSet<Integer>> buildGraph(int edges[][]) {
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
        for (int i =0 ; i< edges.length; i++) {
            HashSet<Integer> adjset = graph.getOrDefault(edges[i][0], new HashSet<Integer>());
            adjset.add(edges[i][1]);
            graph.put(edges[i][0], adjset);
            graph.putIfAbsent(edges[i][1], new HashSet<Integer>());
        }
        System.out.println("Graph node Size " + graph.size());
        return graph;
    }

    public void dfs(HashMap<Integer, HashSet<Integer>> graph) {
        int []color = new int [graph.size() +1];
        Set<Integer> nodeSet = graph.keySet();
        Iterator itr = nodeSet.iterator();
        while(itr.hasNext()) {
            Integer u = (Integer) itr.next();
            if(color[u] == 0) // white vertex
                dfsVisit(u, graph, color);
        }
    }
    public void dfsVisit(Integer u,  HashMap<Integer, HashSet<Integer>> graph, int []color){
        color[u] = 1; // grey vertex
        HashSet<Integer> adjset = graph.get(u);
        Iterator itr = adjset.iterator();
        while(itr.hasNext()) {
            Integer v = (Integer) itr.next();
            if(color[v] == 0) { // unexplored
                dfsVisit(v, graph, color);
            } else if(color[v] == 1) { // already visited grey vertex
                System.out.println("LOOP detected!");
            }
        }
        color[u] = 2; // black vertex
        System.out.println(u);

    }



    // LeetCode :: 127. Word Ladder & Adna Aziz 19.7
    // The most interesting part is how the adjacency was built, for example  hot  create three keys (H*t, *ot,ho*)
    // we store (H*t, *ot,ho*) as keys and hot as value for each key; note that *ot will also map to dot
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        // Since all words are of same length.
        int L = beginWord.length();

        // Dictionary to hold combination of words that can be formed,
        // from any given word. By changing one letter at a time.
        Map<String, List<String>> allComboDict = new HashMap<>();

        wordList.forEach(
                word -> {
                    for (int i = 0; i < L; i++) {
                        // Key is the generic word
                        // Value is a list of words which have the same intermediate generic word.
                        String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
                        List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
                        transformations.add(word);
                        allComboDict.put(newWord, transformations);
                    }
                });

        // Q
        // ueue for BFS
        Queue<Pair<String, Integer>> Q = new LinkedList<>();
        Q.add(new Pair(beginWord, 1));

        // Visited to make sure we don't repeat processing same word.
        Map<String, Boolean> visited = new HashMap<>();
        visited.put(beginWord, true);

        while (!Q.isEmpty()) {
            Pair<String, Integer> node = Q.remove();
            String word = node.getKey();
            int level = node.getValue();
            for (int i = 0; i < L; i++) {

                // Intermediate words for current word
                String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

                // Next states are all the words which share the same intermediate state.
                for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<>())) {
                    // If at any point if we find what we are looking for
                    // i.e. the end word - we can return with the answer.
                    if (adjacentWord.equals(endWord)) {
                        return level + 1;
                    }
                    // Otherwise, add it to the BFS Queue. Also mark it visited
                    if (!visited.containsKey(adjacentWord)) {
                        visited.put(adjacentWord, true);
                        Q.add(new Pair(adjacentWord, level + 1));
                    }
                }
            }
        }

        return 0;
    }
}
