import javafx.util.Pair;

import javax.xml.ws.spi.http.HttpHandler;
import java.lang.reflect.Array;
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
            HashSet<Integer> adjSetA = graph.getOrDefault(edges[i][0], new HashSet<Integer>());
            adjSetA.add(edges[i][1]);
            HashSet<Integer> adjSetB = graph.getOrDefault(edges[i][1], new HashSet<Integer>());
            adjSetB.add(edges[i][0]);
            graph.put(edges[i][0], adjSetA);
            graph.put(edges[i][1], adjSetB);
        }
        System.out.println("Graph node Size " + graph.size());
        return graph;
    }

    public void dfs(HashMap<Integer, HashSet<Integer>> graph) {
        System.out.println("DFS");
        int []color = new int [graph.size() +1];
        Set<Integer> nodeSet = graph.keySet();
        Iterator<Integer> itr = nodeSet.iterator();
        while(itr.hasNext()) {
            Integer u =  itr.next();
            if(color[u] == 0) {// white vertex
                dfsVisit(u, graph, color);
                System.out.println();
            }
        }
    }

    public void dfsVisit(Integer u,  HashMap<Integer, HashSet<Integer>> graph, int []color){
        color[u] = 1; // grey vertex
        HashSet<Integer> adjset = graph.get(u);
        Iterator<Integer> itr = adjset.iterator();
        while(itr.hasNext()) {
            Integer v = itr.next();
            if(color[v] == 0) { // unexplored
                dfsVisit(v, graph, color);
            } else if(color[v] == 1) { // already visited grey vertex
                //System.out.println("LOOP detected!");
            }
        }
        color[u] = 2; // black vertex
        System.out.print(u + " ");

    }

    public void bfs(HashMap<Integer, HashSet<Integer>> graph, int nodes) {
        System.out.println("BFS");
        int [] color = new int [nodes+1];
        for (int i = 1; i <= nodes; i++) {
            if(color[i] == 0) {
                bfsVisit(graph, i,color);
                System.out.println();
            }
        }

    }

    public void bfsVisit(HashMap<Integer, HashSet<Integer>> graph,  Integer srcNode, int []color) {

        Queue<Integer> queue = new LinkedList<>();
        queue.add(srcNode);
        color[srcNode] = 2; //black
        while (!queue.isEmpty()) {
            Integer u = queue.remove();
            color[u] = 2; // black
            System.out.print(u + " ");
            HashSet<Integer> adjSet = graph.get(u);
            Iterator<Integer> itr = adjSet.iterator();
            while (itr.hasNext()) {
                Integer v = itr.next();
                if (color[v] == 0){ // white vertex
                    queue.add(v);
                    color[v] = 1; // grey vertex
                }
            }
        }
        System.out.println();
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

    // LeetCode :: 310. Minimum Height Trees
    // The node or nodes that will create the minimum tree is in the middle nodes of the longest path of the graph. If
    // the longest path len is even we have two middle nodes otherwise one. So to get the middle nodes one approach is
    // go from the border / leaf nodes (nodes wih only one edge) an remove them to move closer to the middle
    // The idea is removes the leaves from the graph which will create new leaves then remove the new leaves to get
    // another set of leaves and so on until there is only 2 or 1 node left
    // The run time of this algo is interesting its O(V + E) when its not sparse graph this algo
    // actually performs better
    public List<Integer> findMinHeightTreesV2(int n, int[][] edges) {
        ArrayList <Integer> leaves = new ArrayList<>();
        if (edges.length == 0) {
            leaves.add(0);
            return leaves;
        }
        List<HashSet<Integer>> adjList = new ArrayList<>();
        for (int i =0; i< n; i++) {
            adjList.add(new HashSet<>());
        }
        for (int i = 0; i<edges.length; i++){
            adjList.get(edges[i][0]).add(edges[i][1]);
            adjList.get(edges[i][1]).add(edges[i][0]);
        }
        // lets get the initial leaves from the boundary of the graph
        for (int i = 0; i < n; i++) {
            if (adjList.get(i).size()==1)
                leaves.add(i);
        }
        // The run time of this loop is amortised O(n)
        // We use initial levaes and remove them to find new set of leaves exposed after removing the older leaves
        // we do this until there is one or two nodes left, this is how we go into the middle
        while (n>2) {
            n-= leaves.size();
            ArrayList<Integer> newLeaves = new ArrayList<>();
            for (int i : leaves) {
                int j = adjList.get(i).iterator().next();
                adjList.get(j).remove(i);
                if(adjList.get(j).size() == 1)
                    newLeaves.add(j);
            }
            leaves = newLeaves;
        }

        return leaves;
    }

    // The idea of this solution is interesting although its slower
    // The idea here is to get a random node and get the longest path using that random node as the src
    // with longest path we get the node that is furthest from the random node, lets say the furthest node is y
    // node we use y as source and find the longest path to node z. the path between y & z for this tree like graph
    // is guranteed to be one of the longest path which will contain the mid node of the graph. So we run two bfs
    // first to find y then to find z and use the middle nodes in the path from y to z as out solution
    // Run Time O(V+E)
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        ArrayList <Integer> leaves = new ArrayList<>();
        if (edges.length == 0) {
            leaves.add(0);
            return leaves;
        }
        List<HashSet<Integer>> adjList = new ArrayList<>();
        for (int i =0; i< n; i++) {
            adjList.add(new HashSet<>());
        }
        for (int i = 0; i<edges.length; i++){
            adjList.get(edges[i][0]).add(edges[i][1]);
            adjList.get(edges[i][1]).add(edges[i][0]);
        }
        Integer src = new Random().nextInt(n);
        int [] parent = new int[n];
        int [] dist = new int[n];
        int leafNode = simpleBFS(src, parent, dist, n, adjList);

        Arrays.fill(dist, 0);
        int longestPathLeaf = simpleBFS(leafNode, parent,dist,n, adjList);
        int length = dist[longestPathLeaf]/2;
        if (dist[longestPathLeaf] %2 == 0)
            length--;
        int p = longestPathLeaf;
        while (p >=0 &&length > 0) {
            p = parent[p];
            length--;
        }
        List<Integer> midList = new ArrayList<>();
        midList.add(p);
        if (dist[longestPathLeaf]%2 ==0)
            midList.add(parent[p]);
        return midList;


    }
    public int simpleBFS (int src, int []parent, int []dist, int n , List<HashSet<Integer>> adjList) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);
        parent[src] = -1;
        dist[src] = 1;
        int [] color = new int[n];
        int u = -1;
        color[src] = 0; // white
        while (!queue.isEmpty()) {
            u = queue.remove();
            color[u] = 2; // black
            Iterator<Integer> itr = adjList.get(u).iterator();
            while (itr.hasNext()) {
                int v = itr.next();
                if (color[v] == 0) {
                    queue.add(v);
                    color[v] = 1; // grey
                    parent[v] = u;
                    dist[v] = dist[u] + 1;
                }
            }

        }
        return u;

    }

    // LeetCode :: 332. Reconstruct Itinerary
    // This proble is interesting it actually finds the Eulerian Trail in a graph. Note this algo assumes the given
    // graph consist a valid Eulerian Trail. To find a Eulerian Trail we can use a modified DFS. IN this case when
    // we visit a node we dont mark them grey /black rather we delete the that we use to visit the graph. So we start
    // with a src node and start traversing the graph and keep removing visited edges. In this problem the graph is
    // directed so we need to move just one direction other for undirected need to remove both direction. We keep
    // visiting an removing edges until all the edges of a node is visited. When all the edges a visited we add this
    // node to our path. We need to add the node in path in stack order cause that's the way the will be visited
    // (think about the depth of recursion). This way of visit will give us an Eulerian Path.
    // Note we used a priority queue/ Min Heap for adjlist because the problem definition ask us to pick the
    // solution/node in Lexical order so when exploring edges we pick the edge that leads to lexically smaller node
    private void dfsEuler (HashMap<String, PriorityQueue<String>> graph,
                           String node, LinkedList<String> eulerPath) {
        PriorityQueue<String> adjList = graph.get(node);
        // we need to check null for adjList cause there could exist a node with no out degree, so no adj
        while (adjList!= null && !adjList.isEmpty()) {
            dfsEuler(graph, adjList.poll(), eulerPath);
        }
        // All the edges are visited lets add this node to path in stack order
        eulerPath.addFirst(node);
    }

    public List<String> findItinerary(List<List<String>> tickets) {
        HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
        LinkedList<String> eulerPath = new LinkedList<>();
        // Create the directed graph we use a Min Heap for adjList as the
        // problem requires us to visit node in Lexical order
        for (List<String> tk : tickets) {
            graph.putIfAbsent(tk.get(0) , new PriorityQueue<String>());
            graph.get(tk.get(0)).add(tk.get(1));
        }
        // The src is given as JFK, Visit the graph using the modified dfs to find the Eulerian Path
        dfsEuler(graph, "JFK", eulerPath);
        return eulerPath;
    }

    //LeetCode :: 269 Alien Dictonary
    private void dfsAlien(Character u, HashMap<Character,ArrayList<Character>> graph,
                          int [] colors, StringBuilder sb) {
        ArrayList<Character> adjList = graph.getOrDefault(u, null);
        colors[u] = 1;// grey
        if (adjList != null) {
            for (Character v : adjList) {
                // color is white
                if(colors[v] == 0) {
                    colors[v] = 1; // grey
                    dfsAlien(v, graph, colors,sb);
                }
            }
        }
        colors[u] = 2; // black
        sb.append(u);
    }

    public String alienOrder(String []words){
        HashMap<Character,ArrayList<Character>> graph = new HashMap<>();
        HashMap<Character, Integer> inDegree = new HashMap<>();
        // build the graph from the lexical ordering of words
        for (int j = 1; j <words.length; j++) {
            String first = words[j-1];
            String second = words[j];
            int len = Math.min(first.length(), second.length());
            int i = 0;
            while (i< len && first.charAt(i) == second.charAt(i))
                i++;
            // add edge to the adjlist
            graph.putIfAbsent(first.charAt(i), new ArrayList<>());
            graph.get(first.charAt(i)).add(second.charAt(i));
            // count the indegree of the graph
            inDegree.putIfAbsent(first.charAt(i), 0);
            inDegree.put(second.charAt(i), inDegree.getOrDefault(second.charAt(i), 0) +1);
        }
        Character src = '*' ;
        // get the src node for dfs, the src node has zero indegrees. There will always be a zero indegree node because
        // the alien alphabet must have a  start
        for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
            if(entry.getValue() == 0) {
                src = entry.getKey();
                break;
            }
        }
        if (src == '*') {
            System.out.println("No zero Indegree src");
            return "";
        }

        // we can either use bfs or dfs to do topological sort I have implemented both
        // if you want the fds version uncomment the following lines and comment out the bfs version

        // do dfs + topological sort of the graph to get the order of the alien dictionary
        //int []colors = new int[128];
        // StringBuilder sb = new StringBuilder();
        // dfsAlien(src, graph, colors, sb);

        // do bfs + topo sort
        String sb = bfsTopologicalSearchAlien(src, graph, inDegree);
        return sb;
    }

    // BFS + topological sort
    /**
     * ************************** BFS + Topological Sort *******************************************
     *
     * The following descrives how to implement a topological sort of a DAG using BFS
     *
     * Step-1: Compute in-degree (number of incoming edges) for each of the vertex present in the DAG
     *         and initialize the count of visited nodes as 0.
     *
     * Step-2: Pick all the vertices with in-degree as 0 and add them into a queue (Enqueue operation)
     *
     * Step-3: Remove a vertex from the queue (Dequeue operation) and then.
     *
     *      Increment count of visited nodes by 1.
     *      Decrease in-degree by 1 for all its neighboring nodes.
     *      If in-degree of a neighboring nodes is reduced to zero, then add it to the queue.
     *
     * Step 4: Repeat Step 3 until the queue is empty.
     * Step 5: If count of visited nodes is not equal to the number of nodes in the graph
     *         then the topological sort is not possible for the given graph.
     *
     * */
    public  String bfsTopologicalSearchAlien (Character src, HashMap<Character,ArrayList<Character>> graph,
                                          HashMap<Character, Integer> inDegree) {
        Queue<Character> queue = new LinkedList<>();
        queue.add(src);
        StringBuilder sb = new StringBuilder();
        // we need the total node count assuming graph has all the node so total count is graph/adjlist rep size
        int totalNodeCount = inDegree.size();

        int visitedNodeCount = 0;
        while(!queue.isEmpty()) {
            Character u = queue.remove();
            // add to topo sort list
            sb.append(u);
            // increase the visited node count
            visitedNodeCount++;
            // traverse the adjacency
            ArrayList<Character> adjList = graph.getOrDefault(u, null);
            if (adjList != null) {
                for (Character v : adjList) {
                    // decrease the indegree of the neighbor/adjacency
                    inDegree.put(v, inDegree.get(v) -1);
                    // if indegree of a node is zero add it to the queue to process in following iteration
                    if (inDegree.get(v) == 0){
                        queue.offer(v);
                    }
                }
            }
        }
        return visitedNodeCount == totalNodeCount ? sb.toString() : "No Topological sort exist for this graph";
    }


    // LeetCode :: 207. Course Schedule
    // The idea is to use a dfs to detect if loop exist in a the course dependency graph
    // First using the prerequisite we build the course dependency graph
    // a prerequisite [u,v] means an edge v->u course v needs to be taken before course u
    // After builiding the graph we can just do a dfs to check for a loop if loop exist
    // we cannot resolve course prerequisite otherwise yes
    private boolean dfsVisitCourse(int u , ArrayList<Integer> [] graph, int []color) {
        ArrayList<Integer> adjList = graph[u];
        color[u] = 1; // grey
        if (adjList != null) {
            for (int v : adjList) {
                if (color[v] == 1) { // grey vertex
                    // loop detected so return false course pre-requisite has loop cannot be solved
                    return false;
                } else if(color[v] == 0) { // if vertex is white
                    if(!dfsVisitCourse(v, graph, color))
                        return false;
                }
            }
        }
        color[u] = 2; //black
        return true;
    }

    private boolean dfsCourses (ArrayList<Integer> [] graph, int numCourse) {
        int []color = new int[numCourse];
        // run dfs on all white nodes of the graph
        for (int u = 0; u < numCourse; u++) {
            if (color[u] == 0) { // white vertex
                if(!dfsVisitCourse(u, graph, color))
                    return false;
            }
        }
        return true;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<Integer> [] graph = new ArrayList[numCourses];
        // build the graph
        for (int []edges : prerequisites) {
            if(graph[edges[1]] == null)
                graph[edges[1]] = new ArrayList<>();
            graph[edges[1]].add(edges[0]);

        }
        // do dfs to detecr if loop exist
        return  dfsCourses(graph, numCourses);

    }


}
