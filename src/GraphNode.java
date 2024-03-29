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

    /***
     * Note: Look how we build the graph using the HashMap its way faster to code/build graph like this
     */
    public HashMap<Integer, HashSet<Integer>> buildGraph(int edges[][]) {
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
        for (int i =0 ; i< edges.length; i++) {
            graph.computeIfAbsent(edges[i][0], k-> new HashSet<>()).add(edges[i][1]);
            graph.computeIfAbsent(edges[i][1], k-> new HashSet<>()).add(edges[i][0]);
        }
        // Note how we are printing the code in 1 line its faster if we just need to one thig in the loop
        graph.forEach((k,v) -> System.out.println(k +" :: " + v));
        return graph;
    }

    public void dfs(HashMap<Integer, HashSet<Integer>> graph) {
        System.out.println("DFS");
        int []color = new int [graph.size() +1];
        for (Integer u : graph.keySet()){
            if(color[u] == 0) {// white vertex
                dfsVisit(u, graph, color);
                System.out.println();
            }
        }
    }

    public void dfsVisit(Integer u,  HashMap<Integer, HashSet<Integer>> graph, int []color){
        color[u] = 1; // grey vertex
        HashSet<Integer> adjset = graph.get(u);
        for (Integer v: adjset) {
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
            for(Integer v: adjSet) {
                if (color[v] == 0){ // white vertex
                    queue.add(v);
                    color[v] = 1; // grey vertex
                }
            }
        }
        System.out.println();
    }



    // LeetCode :: 127. Word Ladder & Adna Aziz 19.7
    // check out another version of this below implement in a easy to read way
    // The most interesting part is how the adjacency was built, for example  hot  create three keys (H*t, *ot,ho*)
    // we store (H*t, *ot,ho*) as keys and hot as value for each key; note that *ot will also map to dot
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {

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
            String word = node.getFirst();
            int level = node.getSecond();
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
    // we visit a node we dont mark them grey /black rather we delete the edge that we use to visit the graph. So we start
    // with a src node and start traversing the graph and keep removing visited edges. In this problem the graph is
    // directed so we need to move just one direction other for undirected need to remove both direction. We keep
    // visiting an removing edges until all the edges of a node is visited. When all the edges a visited we add this
    // node to our path. We need to add the node in path in stack order cause that's the way the will be visited
    // (think about the depth of recursion). This way of visit will give us an Eulerian Path.
    // Note we used a priority queue/ Min Heap for adjlist because the problem definition ask us to pick the
    // solution/node in Lexical order so when exploring edges we pick the edge that leads to lexically smaller node
    // check this : https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/
    // Note: here the dfs is modified so we allow traversing grey vertices as we can travel a vertex multiple times in '
    // an eulerian path, ths is why we can construct the euler path by adding node to eulerPath at the end of the dfs
    // call. Because we allow grey vertex traversal we will see same vertex couple of times. This is the trick to
    // construct euler path
    private void dfsEuler (HashMap<String, PriorityQueue<String>> graph,
                           String node, LinkedList<String> eulerPath) {
        PriorityQueue<String> adjList = graph.get(node);
        // we need to check null for adjList cause there could exist a node with no out degree, so no adj
        while (adjList!= null && !adjList.isEmpty()) {
            // remove the visited edge and traverse the graph
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



    // LeetCode :: 269 Alien Dictonary (Hard)
    // The idea here is to build a graph representing the order relation among the chars
    // Then we just need to do a toplogical sort of the graph.
    // The problem is hard for a reason as it explores a lot of edge cases.
    // Some of the edge case is related to building the graph and some of them are related
    // to the topological sort
    // The DFS  here does a proper toplogical sort of a directed graph where the start vertex is not known
    private boolean dfsAlien(Character u, HashMap<Character,ArrayList<Character>> graph,
                             int [] colors, StringBuilder sb) {
        ArrayList<Character> adjList = graph.getOrDefault(u, null);
        colors[u] = 1;// grey
        boolean res = true;
        for (Character v : adjList) {
            // color is white we can visit this vertex
            if(colors[v] == 0) {
                colors[v] = 1; // grey
                // store the current result of recursion
                res = dfsAlien(v, graph, colors,sb);
                // if the result of recursion broke our condition that is the result of recursive
                // call is false we return immediately, we need to break recusion when result is
                // false so return immediately, for true case we can proceed with recursion so no
                // need to break
                if (!res)
                    return false;
            } else if (colors[v] == 1) { // color is grey loop detected
                // grey vertex indicate a loop and there cannot be any loop in topological sort
                // so we return false here. Black vertex are ok if we visit a black vertex we do
                // not need to return false
                return false;
            } // color is black do nothing
        }
        colors[u] = 2; // black
        sb.append(u);
        return true;
    }

    public String alienOrder(String []words){
        HashMap<Character,ArrayList<Character>> graph = new HashMap<>();

        // build the graph from the lexical ordering of words
        for (String w : words) {
            for (int i = 0; i < w.length(); i++){
                graph.putIfAbsent(w.charAt(i), new ArrayList<>());
            }
        }
        for (int j = 1; j <words.length; j++) {
            String first = words[j-1];
            String second = words[j];
            int i = 0;
            int len = Math.min(first.length(), second.length());
            while (i< len && first.charAt(i) == second.charAt(i)) {
                i++;
            }
            if (i < len) {
                // add edge to the adjlist
                graph.get(first.charAt(i)).add(second.charAt(i));
                // count the indegree of the graph
            } else {
                if (second.length() < first.length())
                    return "";
            }
        }

        int []colors = new int[128];
        StringBuilder sb = new StringBuilder();
        for (Character src : graph.keySet()) {
            if (colors[src] == 0) {
                // do dfs + topological sort of the graph to get the order of the alien dictionary
                boolean res = dfsAlien(src, graph, colors, sb);
                if (!res)
                    return "";
            }
        }

        return sb.reverse().toString();
    }
    // This is the BFS approcah to the problem BFS is little slower but easier to
    // implement in interview if not specified bfs can be used fot topo sort
    public String alienOrderBFS(String []words){
        HashMap<Character,ArrayList<Character>> graph = new HashMap<>();
        HashMap<Character, Integer> inDegree = new HashMap<>();
        // build the graph from the lexical ordering of words
        for (String w : words) {
            for (int i = 0; i < w.length(); i++){
                graph.putIfAbsent(w.charAt(i), new ArrayList<>());
                inDegree.put(w.charAt(i), 0);
            }
        }
        for (int j = 1; j <words.length; j++) {
            String first = words[j-1];
            String second = words[j];
            int i = 0;
            int len = Math.min(first.length(), second.length());
            while (i< len && first.charAt(i) == second.charAt(i)) {
                i++;
            }
            if (i < len) {
                // add edge to the adjlist
                graph.get(first.charAt(i)).add(second.charAt(i));
                // increment the indegree of the second node
                inDegree.put(second.charAt(i), inDegree.getOrDefault(second.charAt(i), 0) +1);
            } else {
                // handle wrong input like abcd & abc
                if (second.length() < first.length())
                    return "";
            }
        }

        Queue<Character> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : inDegree.entrySet()) {
            Character src = entry.getKey();
            if (entry.getValue() == 0) {
                queue.add(src);
            }
        }
        return bfsTopologicalSearchAlien(queue, graph, inDegree);
    }

    // BFS + topological sort
    /**
     * ************************** BFS + Topological Sort *********************************************
     * *********** Note: Its better to use BFS for toposort if the start vertex is not known *********
     *
     * The following descrives how to implement a topological sort of a DAG using BFS
     *
     * Step-1: Compute in-degree (number of incoming edges) for each of the vertex present in the DAG
     *         and initialize the count of visited nodes as 0.
     *
     * Step-2: Pick all the vertices with in-degree as 0 and add them into a queue (Enqueue operation)
     *
     * Step-3: Remove a vertex from the queue (Dequeue operation) and then.
     *      add the remove vertex to a path list (this will give the toposort path if one exist depending on the
     *      condition on step 5)
     *      Increment count of visited nodes by 1.
     *      Decrease in-degree by 1 for all its neighboring nodes.
     *      If in-degree of a neighboring nodes is reduced to zero, then add it to the queue.
     *
     * Step 4: Repeat Step 3 until the queue is empty.
     * Step 5: If count of visited nodes is not equal to the number of nodes in the graph
     *         then the topological sort is not possible for the given graph.
     *
     *
     * */
    public  String bfsTopologicalSearchAlien (Queue<Character> queue, HashMap<Character,ArrayList<Character>> graph,
                                          HashMap<Character, Integer> inDegree) {

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
        return visitedNodeCount == totalNodeCount ? sb.toString() : "";
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

    // LeetCode :: 210. Course Schedule II
    // The idea is to do a topological sort using BFS. As we dont know which node is the start node for topological sort
    // its better to use BFS as it will require less memory. First we need to find a vertex with 0 in-degree.
    // if no such vertex the topo sort not possible. Next using the indegee 0 vertex we as start we do bfs topo sort
    // if topo sort exist (visite node count == tot node count) we have a topo sort otherwise no
    //  Note:: Use BFS for TOPO sort dont worry about loop
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList<Integer> []graph = new ArrayList[numCourses];
        int [] indegree = new int [numCourses];
        for (int [] edges : prerequisites) {
            if(graph[edges[1]] ==null)
                graph[edges[1]] = new ArrayList<>();
            graph[edges[1]].add(edges[0]);
            indegree[edges[0]]++;
        }

        Queue <Integer> queue = new LinkedList<>();
        for (int i =0; i<numCourses; i++) {
            if(indegree[i] == 0)
                queue.add(i);
        }

        if(queue.size() == 0)
            return new int[0];
        int totCount = 0;
        int []pOrder = new int[numCourses];
        while (!queue.isEmpty()) {
            int u = queue.remove();
            pOrder[totCount] = u;
            totCount++;
            // traverse the adj list  and remove the visited edges aka reduce the in-degrees of the adjacency
            ArrayList<Integer> adjList = graph[u];
            if(adjList != null) {
                for (Integer v : adjList) {
                    indegree[v]--;
                    if(indegree[v] == 0)
                        queue.add(v);
                }
            }
        }
        if(totCount != numCourses)
            return new int[0];
        return pOrder;
    }
    // LeetCode :: 127. Word Ladder
    // The idea is do a BFS on the wordlist we start with beginWord and try to find the endWord using BFS if we find it
    // the distance would be the shortest as we are using bfs here (all edge have same weight).
    // The most interesting part is how the adjacency was built, for example  hot  create three keys (H*t, *ot,ho*)
    // we store (H*t, *ot,ho*) as keys and hot as value for each key; note that *ot will also map to dot
    class StrPair {
        String str;
        Integer len;
        public StrPair(String s, int l){
            str = s;
            len = l;
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        HashMap<String, ArrayList<String>> graph = new HashMap<>();
        HashSet<String> visited = new HashSet<>();

        int wordLen = beginWord.length();
        if (wordList.size() == 0)
            return 0;
        // build the graph
        for (String word : wordList) {
            char w[] = word.toCharArray();
            for (int i = 0; i<wordLen; i++) {
                char ch = w[i];
                w[i] = '*';
                String ws = new String(w);
                graph.putIfAbsent(ws, new ArrayList<>());
                graph.get(ws).add(word);
                w[i] = ch;
            }
        }

        // do BFS to find the shortest path
        Queue<StrPair> queue = new LinkedList<>();
        queue.add(new StrPair(beginWord,1));
        visited.add(beginWord);
        int len = 0;
        while (!queue.isEmpty()) {
            // remove from queue
            StrPair u = queue.remove();

            // look up the adj list of u to put the next node in bfs queue
            char str[] = u.str.toCharArray();
            // create the posible word keys for example hot has ho*, h*t, *ot three possible keys
            for (int i =0; i<wordLen; i++) {
                char ch = str[i];
                str[i] ='*';
                // get the adj list
                ArrayList<String> adjList = graph.getOrDefault(new String(str), null);
                if (adjList != null) {
                    for (String v : adjList) {
                        // found the dest node return the shortest length
                        // Note this is ok to report at this point instead of inside the visited check because this
                        // the end vertex and this will be discovered and even if its put on the queue later that is
                        // fine the discovery time is same for all the node in the same level so for endVertex this
                        // should work fine
                        if(v.equals(endWord)) {
                            return u.len+1;
                        }
                        // only visit the undiscovered node
                        if(!visited.contains(v)) {
                            // add the node to bfs queue
                            queue.add(new StrPair(v, u.len +1));
                            // mark this node as visited
                            visited.add(v);
                        }
                    }
                }
                str[i] = ch;
            }
        }

        return len;
    }

    // LeetCode :: 133. Clone Graph
    // Use either bfs or dfs to construct the graph
    // this is the bfs approach
    public Node cloneGraph2(Node node) {
        if (node == null)
            return node;
        Node start = new Node(node.val);
        Queue<Node> queue = new LinkedList<>();
        HashMap<Integer,Node> nodeMap = new HashMap<>();
        queue.add(node);
        nodeMap.put(node.val, start);
        while (!queue.isEmpty()) {
            Node oldNode = queue.poll();
            List<Node> adjList = oldNode.neighbors;
            Node newNode = nodeMap.getOrDefault( oldNode.val,null);
            for (Node n : adjList) {
                Node adj = nodeMap.getOrDefault(n.val, new Node(n.val));
                newNode.neighbors.add(adj);
                if (!nodeMap.containsKey(n.val)) {
                    nodeMap.put(adj.val,adj);
                    queue.add(n);
                }
            }
        }
        return start;
    }
    // this is the dfs approach
    public Node cloneGraph(Node node) {
        if (node == null)
            return node;
        HashMap<Integer,Node> nodeMap = new HashMap<>();
        return createCloneGraph(node, nodeMap);
    }
    private Node createCloneGraph(Node node , HashMap<Integer, Node> map) {
        if (map.containsKey(node.val))
            return map.get(node.val);
        Node newNode = new Node(node.val);
        map.put(newNode.val, newNode);
        for (Node n : node.neighbors) {
            newNode.neighbors.add(createCloneGraph(n,map));
        }
        return newNode;
    }

    // LeetCode :: 138. Copy List with Random Pointer
    // Lets try a BFS approach to solve this, we uses the same approach as the clone graph problem.
    // Hear each node only has two neighbors (random, next). We have to maintain a BFS visited nodeList
    // the visited is implemented using hashmap. We start a bgs traversal from the head node.
    // Think of this linkedList as graph where very node has two edges (next & random)
    public Node copyRandomList2(Node head) {
        if (head == null)
            return head;
        HashMap< Node, Node> visited = new HashMap<>();
        Queue <Node> queue = new LinkedList<>();
        Node newHead = new Node(head.val);
        visited.put(head, newHead);
        queue.add(head);

        while (!queue.isEmpty()) {
            Node node = queue.remove();
            Node newNode = visited.get(node);
            if (node.next != null) {
                Node next = visited.getOrDefault(node.next , new Node(node.next.val));
                newNode.next = next;
                if (!visited.containsKey(node.next)) {
                    queue.add(node.next);
                    visited.put(node.next, next);
                }
            }
            if (node.random != null) {
                Node random = visited.getOrDefault(node.random , new Node(node.random.val));
                newNode.random = random;
                if(!visited.containsKey(node.random)) {
                    queue.add(node.random);
                    visited.put(node.random, random);
                }
            }
        }
        return newHead;
    }
    // This idea is to do a two pass on the list in the first pass we create the next pointers and in the second
    // pass we create the random pointer. The trick here is to index the main list from 0 to n-1 and put it in
    // arraylist so when we need to update random pointer in the old list the random pointer will actually give the
    // index from which we can extrac the proper node pointed by the random pointer random.
    // we add the new nodes based on the index of old list
    public Node copyRandomList3(Node head) {
        if (head == null)
            return head;
        // store the new list node in 0 to n-1 based index
        ArrayList<Node> nodeList = new ArrayList<>();
        Node dummy= new Node(-1);
        Node prev = dummy;
        Node cur = head;

        int count = 0;
        // create the next pointers also update the old list value as 0 to n-1
        while (cur != null) {
            prev.next = new Node(cur.val);
            cur.val = count++;
            cur = cur.next;
            prev = prev.next;
            nodeList.add(prev);

        }

        cur = head;
        prev = dummy.next;
        // update the randon pointer using the stored nodelist based on the oldlist index
        // for example if old list node 1's random  points index 3 we get the third node from nodeList
        while (cur!= null) {
            Node random = cur.random;
            if(random != null) {
                prev.random = nodeList.get(random.val);
            }
            cur = cur.next;
            prev = prev.next;
        }

        return  dummy.next;
    }

    // This is O(n) & O(1) algo
    // this is very interesting the idea is to stich the list & unstich the list
    // so for example if we have a list 1->2->3->4
    // after stich we have 1->1'->2->2'->3->3'->4->4'
    // after that we update random pointer for updated new items it easy as we the random pointer of 1' will
    // be random.next pointer of 1
    // after that we unstich and get two list
    public Node copyRandomList(Node head) {
        if (head == null)
            return head;
        Node cur = head;
        while (cur != null) {
            Node node = new Node(cur.val);
            node.next = cur.next;
            cur.next = node;
            cur = node.next;
        }
        cur = head;
        while (cur != null) {
            if(cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }
        Node dummyHead = new Node(-1);
        cur = head;
        Node copy = dummyHead;
        while (cur != null) {
            Node next = cur.next.next;
            copy.next = cur.next;
            copy = copy.next;
            cur.next = next;
            cur = next;
        }

        return dummyHead.next;
    }

    // LeetCode :: 785. Is Graph Bipartite?
    // The idea is to use BFS to two color the whole graph if possible then we can Bipartite otherwise not
    // Note that we already given a an adjacency list so we dont need to build the graph any more.
    // Trying to build a graph would be really foolish
    public boolean isBipartiteBFS(int[][] graph) {

        // 0 -- is no color
        // 2 -- is Red
        // 5 -- id Blue
        int []color = new int[graph.length];
        for (int i = 0; i< graph.length; i++) {
            // process only vertices that are not colored
            if(color[i] == 0) {
                int src = i;
                color[src] = 2 ; // red color first node
                Queue<Integer> queue = new LinkedList<>();
                queue.add(src);
                while (!queue.isEmpty()) {
                    int u = queue.remove();
                    int[] adjList = graph[u];
                    for (Integer v : adjList) {
                        if(color[v] == color[u])
                            return false;
                        if (color[v] == 0) {
                            color[v] = color[u] ^ 0x7;
                            queue.add(v);
                        }
                    }
                }
            }
        }
        return true;
    }

    // The idea is to do a DFS to two color the graph to check if the graph is birpartite
    // One interesting observation was the DFS version took 0ms while the BFS took 5ms
    // most likely due to the input graph is not dense
    private boolean isTwoColorPossible(int u, int [][] graph, int []color) {
        // 0 -- is no color
        // 2 -- is Red
        // 5 -- id Blue
        int [] adjList = graph[u];
        for (int v : adjList) {
            if (color[v] == color[u])
                return false;
            if (color[v] == 0) { // no color vertex
                color[v] = color[u] ^ 0x7; // invert the color of the neighbor node
                if (!isTwoColorPossible(v, graph, color))
                    return false;
            }
        }
        return true;

    }
    public boolean isBipartite(int[][] graph) {
        int []color = new int[graph.length];
        for (int i = 0; i< graph.length; i++) {
            if(color[i] == 0) {
                color[i] = 2 ; // red vertex
                if(!isTwoColorPossible(i, graph, color))
                    return false;
            }
        }
        return true;

    }

    // LeetCode :: 721. Accounts Merge
    // This uses the idea of Union & Find algorithm used in Kruskal's minimum STP. Here all the email in the same input
    // list can be thoought of as edges and we need to merge union them.
    // The basic idea comes from "Disjoint Sets using union by rank and path compression Graph Algorithm" of Kruskal STP
    // First we need to make set so we need to make each node a set of its own. The we try to union two set if they
    // do not belong to the same set.
    // This cane also done with DFS
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        HashMap<String, String> owner = new HashMap<>();
        HashMap<String, String> parent = new HashMap<>();
        HashMap<String, TreeSet<String>> union = new HashMap<>();
        List<List<String>> rList = new ArrayList<>();
        // make every node point to it self as parent this is the makeSet step
        // we also keep track of the email to owner/name mapping as our problem demands
        for (List<String> strList : accounts) {
            for (int i = 1; i < strList.size(); i++) {
                owner.put(strList.get(i), strList.get(0));
                parent.put(strList.get(i), strList.get(i));
            }
        }
        // this is kind of the find & union step
        for (List<String> strList : accounts) {
            String p = findSet(strList.get(1), parent);
            for (int i = 2; i < strList.size(); i++) {
                parent.put(findSet(strList.get(i), parent),p);
            }
        }
        // this step does the union & grouping the same data together & sort them as requested by the program output
        for (List<String> strList : accounts) {
            String p = findSet(strList.get(1), parent);
            union.putIfAbsent(p, new TreeSet<>());
            for (int i = 1; i < strList.size(); i++) {
                union.get(p).add(strList.get(i));
            }
        }
        // this prepares the result in output format
        for (String u: union.keySet()) {
            ArrayList<String> tList = new ArrayList<>();
            tList.add(0, owner.get(u));
            tList.addAll(union.get(u));
            rList.add(tList);
        }

        return rList;
    }
    // find parent of a node if node is its own parent return node
    private String findSet(String node, HashMap<String, String> parent) {
        if (node.equals(parent.get(node)))
            return node;
        return findSet(parent.get(node), parent);
    }

    // The dfs approach creates a dfs forest in the graph and returns the trees of the forest in list which is our
    // solution the dfs approach is simple but the above approach uses Kruskal's merge& union algo implementation
    // which is a good excercise
    public void acountMergeDFS(String u, HashSet<String> visited,
                               HashMap<String, HashSet<String>> graph,
                               TreeSet<String> traverseList){
        visited.add(u);
        HashSet<String> adjList = graph.get(u);
        for (String v: adjList) {
            if(!visited.contains(v)) {
                acountMergeDFS(v,visited, graph, traverseList);
            }
        }
        traverseList.add(u);

    }
    public List<List<String>> accountsMergeV2(List<List<String>> accounts) {
        HashMap<String, HashSet<String>> graph = new HashMap<>();
        HashMap<String, String> owner = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        // build the graph
        for (List<String> strList : accounts) {
            for (int i = 1; i < strList.size() ; i++) {
                owner.put(strList.get(i), strList.get(0));
                graph.putIfAbsent(strList.get(i), new HashSet<>());
                if (i +1 < strList.size()) {
                    graph.putIfAbsent(strList.get(i+1), new HashSet<>());
                    graph.get(strList.get(i)).add(strList.get(i+1));
                    graph.get(strList.get(i+1)).add(strList.get(i));
                }
            }
        }
        List<List<String>> rList = new ArrayList<>();
        for (String u : graph.keySet()) {
            if (!visited.contains(u)){
                TreeSet<String> traverseList = new TreeSet<>();
                acountMergeDFS(u, visited, graph, traverseList);
                ArrayList<String> tList = new ArrayList<>(traverseList);
                tList.add(0,owner.get(u));
                rList.add(tList);
            }
        }
        return rList;
    }

    // LeetCode :: 994. Rotting Oranges
    // The idea is to do a in-place BFS traversal of the grid we mark the fresh tomato = 1 by rotten value + 1
    // rotten value start with 2 so the next level of bfs will mark neighbor 3 then the next level mark neighbor 4
    // and so on.
    // One important observation how to keep track of all fresh tomato has been marked rotten the best idea is to count
    // the number of fresh tomato initially and then reducde the number by 1 when the tomato is rotten meaning put
    // in the bfs queue. Note we dont need to use a hashset to keep track of the tomato cells as we only ar interested
    // in the fresh Tomato count. If we are asked to find the remaining fresh tomato then hashset can be used
    public int orangesRotting(int[][] grid) {
        int mins = -1;
        int fresh = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // rotten oranges
                if (grid[i][j] == 2)
                    queue.add(new int[] {i,j});
                else if(grid[i][j] == 1) {
                    fresh++;
                }
            }
        }
        if (fresh == 0)
            return 0;
        int [][]dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int []u = {-1,-1};
        //System.out.println(Arrays.deepToString(grid));
        //System.out.println(fresh);
        while (!queue.isEmpty()) {
            u = queue.remove();
            int r = u[0];
            int c = u[1];
            // visit all neighbors
            for (int i = 0; i <dir.length;i++) {
                int incR = dir[i][0];
                int incC = dir[i][1];
                if (r + incR < grid.length && r + incR >= 0
                        && c + incC < grid[0].length && c + incC >= 0
                        && grid[r + incR][c + incC] == 1) {
                    grid[r + incR][c + incC] = grid[r][c] + 1;
                    queue.add(new int [] {r + incR, c + incC});
                    fresh--;
                }

            }
        }
        //System.out.println(Arrays.deepToString(grid));
        if(fresh == 0 && u[0]!=-1)
            mins = grid[u[0]][u[1]] -2;
        return mins;
    }

    // Articulation points/ cut vertex of a graph
    /**
     *
     * Articulation points/ cut vertex of a graph
     *
     * The idea is to use DFS (Depth First Search). In DFS, we follow vertices in tree form called DFS tree.
     * In DFS tree, a vertex u is parent of another vertex v, if v is discovered by u (obviously v is an
     * adjacent of u in graph). In DFS tree, a vertex u is articulation point if one of the following
     * two conditions is true.
     *  1) u is root of DFS tree and it has at least two children.
     *  2) u is not root of DFS tree and it has a child v such that no
     *     vertex in subtree rooted with v has a back edge to one of the ancestors (in DFS tree) of u.
     *
     *  Why this low[u] works?
     *  Low is keeping track of if there is a back edge; for a back edge low will be updated to the ancestor's
     *  discovery value  low[u] = min (low[u], disc[v]) here v is already visited so v must be an ancestor of u.
     *
     *  Also after every dfs call we update low depending on the low of adjacency vertes which is
     *  low[u] = min(low[u],low[v])
     *  if we see that there exist atleast one such vertex v whose low is >= this node u's discovery; it means v is
     *  v is not part of the back edge cycle and if we remove u, v become disconnected
     *  if (low[v] >= disc[u]) then u is a cutvertex
     *
     *  This works because critical edge  does not belong in  loop, so when we discover a vertex which has
     *  low value < u's discovery value it means we have seen a cycle. and if we have seen a cycle none of the edge are
     *  critical so we can return this low value for all the edge in the cycle. Note a vertex on the cycle can still be
     *  our cut vertex provide that vertex has another neighbor v which is not part of cycle and that's when (u,v)
     *  becomes a critical edge and u becomes cut vertex
     * */
    int dTime = 0;
    public List<Integer> articulationPoint(int n, List<List<Integer>> connections) {
        List<Integer> cutVertex = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[n];
        int []color = new int[n];
        int []discovery = new int [n];
        // low[] --> earliest visited vertex (the vertex with minimum
        // discovery time) that can be reached from subtree
        // rooted with current vertex
        int []low = new int[n];
        int []parent = new int[n];
        Arrays.fill(parent, -1);

        for (int i =0; i< graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        // build the graph
        for (List<Integer> con : connections) {
            graph[con.get(0)].add(con.get(1));
            graph[con.get(1)].add(con.get(0));
        }
        // we start with the first vertex assuming only one strongly connected component in the graph
        // if more than one strongly connected component we would have to run dfs for all nodes
        dfsCutVertex(0, graph, color, discovery, low, parent, cutVertex);
        return cutVertex;
    }
    private void dfsCutVertex(int u, List<Integer>[] graph,
                              int []color, int []discovery,
                              int []low, int []parent,
                              List<Integer> cutVertex) {
        ++dTime;
        color[u] = 1;// grey
        List<Integer> adjList = graph[u];
        discovery[u] = dTime;
        low[u] = dTime;
        int child = 0;
        for (Integer v : adjList) {
            if(color [v] == 0) {
                parent[v] = u;
                child++;
                dfsCutVertex(v, graph, color, discovery, low, parent, cutVertex);
                low[u] = Math.min(low[u], low[v]);
                // this is the root of the dfs tree so if it has at least two children its a cut vertex
                if (parent[u] == -1 && child > 1)
                    cutVertex.add(u);
                // if this not the root and non of the node in u's subtree has back edge
                // to u's ancestor then u is a cut vertex. As low[v] >= discovery time of u it means no back to u's
                // ancestor its possible that this condition can be written as low[v] > discovery[u]
                if (parent[u] != -1 && low[v] >= discovery[u]) {
                    cutVertex.add(u);
                }

            } else if (parent[u] != v) { // not parent node & either grey or black vertex
                // we found an back edge so lets update the low for this node
                low[u] = Math.min(low[u], discovery[v]);
            }
        }

    }

    // LeetCode :: 1192. Critical Connections in a Network
    // The idea is the same as articulation point finding of a connected graph, Here we need the critical edge
    // that is connected to cut vertex so whenever we find a cut vertex we update the  critical edge say we find cut
    // vertex as u's after visiting it neighbor v so (u,v) is the critical edge
    // Note we are trying to find cut edge not vertex so we need to use low[v] > disc[u] as our condition instead of
    // low[v] >= disc[u]
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        List<List<Integer>> critEdge = new ArrayList<>();
        List<Integer>[] graph = new ArrayList[n];
        int []discovery = new int [n];
        int []low = new int[n];

        for (int i =0; i< graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        // build the graph
        for (List<Integer> con : connections) {
            graph[con.get(0)].add(con.get(1));
            graph[con.get(1)].add(con.get(0));
        }
        // we start with the first vertex assuming only one strongly connected component in the graph
        // if more than one strongly connected component we would have to run dfs for all nodes
        for (int i =0; i< n; i++) {
            if (discovery[i] == 0)
                dfsCriticalConnection(0, graph, discovery, low, i, critEdge);
        }
        return critEdge;
    }

    private void dfsCriticalConnection(int u, List<Integer>[] graph,
                                       int []discovery,
                                       int []low, int parent,
                                       List<List<Integer>> critEdge) {
        ++dTime;
        List<Integer> adjList = graph[u];
        discovery[u] = dTime;
        low[u] = dTime;
        for (Integer v : adjList) {
            if(discovery [v] == 0) {
                dfsCriticalConnection(v, graph, discovery, low, u, critEdge);
                // update the current low for the vertex u
                low[u] = Math.min(low[u], low[v]);
                // if this not the root and non of the node in u's subtree has back edge
                // to u's ancestor then u is a cut vertex. As low[v] >= discovery time of u it means no back to u's
                // ancestor so the edge (u,v) is the critical edge.
                if (low[v] > discovery[u]) {
                    ArrayList<Integer> tList = new ArrayList<>();
                    tList.add(u);
                    tList.add(v);
                    critEdge.add(tList);
                }
            } else if (parent != v) { // not parent node & either grey or black vertex
                // we found an back edge so lets update the low for this node
                low[u] = Math.min(low[u], discovery[v]);
            }
        }

    }

    // LeetCode :: 1135. Connecting Cities With Minimum Cost
    // find the parent of x node
    // The idea is to use Kruskal's algorithm to find the MST
    // we use the union/find algo for Kruskal's MST
    // Note this lago run time is O(ELogE)
    private int find(int x , int []parent) {
        if (parent[x] == x)
            return x;
        // find the parent
        int p = find(parent[x], parent);
        // update the this node parent for path compression
        parent[x] = p;
        return p;
    }

    // we are going to make a union of x & y node
    private void union(int x, int y, int [] parent) {
        // find parent of x
        int px = find(x, parent);
        // find parent of y
        int py = find(y, parent);
        // union x & y if they have different parent
        if (px != py) {
            parent[py] = px;
        }

    }

    public int minimumCost(int N, int[][] connections) {
        int mstCost = 0;
        int nodeCount = 0;
        int []parent = new int[N];
        nodeCount = N;
        // make set
        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }
        // sort the edges so that we pick the smallest edge first
        Arrays.sort(connections, (a,b)-> (a[2] - b[2]));
        for (int [] edges : connections) {
            int u = edges[0];
            int v = edges[1];
            int cost = edges[2];
            // find if both vertex belongs to two different set & merge/union the set
            if (find(u, parent) != find(v, parent)) {
                // calc the mst cost
                mstCost += cost;
                nodeCount--;
                // union. merge u & ve vertex so this edge (u,v) is a part of our MST
                union(u,v, parent);
            }
        }
        if (nodeCount > 1)
            return -1;
        return mstCost;
    }
    // we can also implement this using prim's algorithm but not the O(VLogV) version as that version requires a heap
    // implementation with decrease key option

    public int minimumCostPRIM(int N, int[][] connections) {
        int minCost = 0;
        HashMap<Integer, List<int[]>> graph = new HashMap<>();
        // build the graph
        for (int [] con : connections) {
            graph.computeIfAbsent(con[0],(k)->new ArrayList<>()).add(new int[] {con[1], con[2]});
            graph.computeIfAbsent(con[1],(k)->new ArrayList<>()).add(new int[] {con[0], con[2]});
//            graph.putIfAbsent(con[0], new ArrayList<>());
//            graph.putIfAbsent(con[1], new ArrayList<>());
//            graph.get(con[0]).add(new int [] {con[1], con[2]});
//            graph.get(con[1]).add(new int [] {con[0], con[2]});
        }
        PriorityQueue<int []> minPQ = new PriorityQueue<>((a,b)->(a[2] - b[2]));
        minPQ.add(new int [] {1 ,1, 0});
        HashSet<Integer> visited = new HashSet<>();
        while (!minPQ.isEmpty()) {
            int []edge = minPQ.poll();
            int u = edge[0];
            int v = edge[1];
            int cost = edge[1];
            if (!visited.contains(v)) {
                List<int []>adjList = graph.get(v);
                visited.add(v);
                minCost+= cost;
                for (int []n : adjList ) {
                    minPQ.add(new int [] {v, n[0], n[1]});
                }
            }
        }
        if (visited.size() != N)
            return -1;
        return minCost;
    }

    // LeetCode :: 787. Cheapest Flights Within K Stops
    // The idea is to use the Dijktsra's shortest path a modified version of it. We need to consider
    // not only shortest path but also a slightly costlier less hop path
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        HashMap<Integer, ArrayList<Integer []>> graph = new HashMap<>();
        Integer [] dist = new Integer[n];
        Integer [] stopCount = new Integer[n];
        PriorityQueue<Integer []> minHeap = new PriorityQueue<>((a,b)->(a[1] - b[1]));
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(stopCount, Integer.MAX_VALUE);
        for (int []edge : flights) {
            graph.computeIfAbsent(edge[0], (key) -> new ArrayList<>()).add(new Integer[] {edge[1],edge[2]});
            graph.computeIfAbsent(edge[1], (key)-> new ArrayList<>());
        }
        minHeap.add(new Integer[]{src, 0, 0});
        dist[src] = 0;
        stopCount[src] = 0;
        while (!minHeap.isEmpty()) {
            Integer[] u = minHeap.remove();
            int nodeU = u[0], cost = u[1], hop = u[2];
            // found the dest this should be the lowest cost as we are maintaining the min heap
            // we always reach the destination with smaller cost first due to the minHeap
            if (nodeU == dst)
                return cost;
            // we have used more hops we cannot process anything on this path
            if (hop == k+1)
                continue;
            ArrayList<Integer[]> adjList = graph.get(nodeU);
            for (Integer []v : adjList) {
                int nodeV= v[0], edgeWeight = v[1];
                if (dist[nodeV] > cost + edgeWeight) {
                    // note how we are using the cost instead of the dist[u] this is very important cause we
                    // want to check if a slightly costlier path with less hop is present, if dist[u] was used it will
                    // only give the shortest path
                    minHeap.add(new Integer[] {nodeV, cost + edgeWeight, hop + 1});
                    // we only update the dist & the stopCount array in this path because in the else if case we are just
                    // trying to see if we can get another less hop more expensive path but not necessary its our
                    // solution and the dist & stopcount arrays are for storing  optimal values
                    dist[nodeV] = cost + edgeWeight;
                    stopCount[nodeV] = hop + 1;
                } else if (hop +1 < stopCount[nodeV]) {
                    // there is slightly higher cost path with less hop lets add it to heap for reconsider
                    minHeap.add(new Integer[] {nodeV, cost + edgeWeight, hop + 1});
                }

            }

        }

        return dist[dst] == Integer.MAX_VALUE? -1 : dist[dst];
    }

    // The DFS DP approach for the above problem
    // This is interesting how DFS+DP was used to figure out the cheapest path with at most K hops for this problem
    // DFS cannot be used for weighted graph shortest path but here we are looking for cheapest path with K hop so
    // we give priority to the hops first and the optimize on the cost hence DFS works. The DP is just memoizing the
    // solution so we can avoid repeated recurse and cache the result. This has better run time for this also as DFS has
    // better runtime than shortest path like dijkstra
    private int[][] adjMatrix;
    private HashMap<Pair<Integer, Integer>, Long> memo;

    public int findCheapestPricev2(int n, int[][] flights, int src, int dst, int K) {
        this.adjMatrix = new int[n][n];
        this.memo = new HashMap<Pair<Integer, Integer>, Long>();
        for (int[] flight: flights) {
            this.adjMatrix[flight[0]][flight[1]] = flight[2];
        }
        long ans = this.findShortest(src, K, dst, n);
        return ans >= Integer.MAX_VALUE ? -1 : (int)ans;
    }

    public long findShortest(int node, int stops, int dst, int n) {
        // No need to go any further if the destination is reached
        if (node == dst) {
            return 0;
        }
        // Can't go any further if no stops left
        if (stops < 0) {
            return Integer.MAX_VALUE;
        }
        Pair<Integer, Integer> key = new Pair<Integer, Integer>(node, stops);
        // If the result of this state is already cached, return it
        if (this.memo.containsKey(key)) {
            return this.memo.get(key);
        }
        // Recursive calls over all the neighbors
        long ans = Integer.MAX_VALUE;
        for (int neighbor = 0; neighbor < n; ++neighbor) {
            int weight = this.adjMatrix[node][neighbor];
            // 0 value means no edge
            if (weight > 0) {
                ans = Math.min(ans, this.findShortest(neighbor, stops - 1, dst, n) + weight);
            }
        }
        // Cache the result
        this.memo.put(key, ans);
        return ans;
    }

    // LeetCode :: 797. All Paths From Source to Target
    // The idea is to run a modified dfs to traverse the graph and get the path to the dest, The modification to DFS
    // is we allow traversing visited vertex again, we dont have any issue traversing a visited vertex because its
    // a DAG so no cycle in the graph and its safe to visit grey vertex
    private void dfsAllPath(int [][]graph, int u, ArrayList<Integer> tlist, int dest, List<List<Integer>> pathList) {
        if (u == dest){
            pathList.add(new ArrayList<>(tlist));
            return;
        }
        int []adj = graph[u];
        for(int v: adj) {
            tlist.add(v);
            dfsAllPath(graph, v, tlist, dest, pathList);
            tlist.remove(tlist.size() -1);
        }
    }
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> pathList = new ArrayList<>();
        int src = 0;
        int dest = graph.length -1;
        ArrayList<Integer> tlist = new ArrayList<>();
        tlist.add(src);
        dfsAllPath(graph, 0, tlist,dest, pathList);
        return pathList;
    }

    // LeetCode:: 323. Number of Connected Components in an Undirected Graph
    // The idea is to run a DFS for all the nodes and count the root of unique dfs tree which gives the number
    // of SCC in graph
    public int countComponents(int n, int[][] edges) {
        int count = 0;
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i<n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int []edg : edges) {
            graph.get(edg[0]).add(edg[1]);
            graph.get(edg[1]).add(edg[0]);
        }
        int [] color = new int[n];
        Arrays.fill(color, 0);
        for (int u = 0; u < n; u++) {
            // white vertex this means this vertex is a root of the dfs tree
            if (color[u] == 0) {
                // count the root of the dfs tree
                count++;
                dfsSCC(u, color, graph);
            }
        }
        return count;
    }
    private void dfsSCC(Integer u, int []color, HashMap<Integer, ArrayList<Integer>> graph) {
        color[u] = 1;
        ArrayList<Integer> adjList = graph.get(u);
        for (Integer v : adjList) {
            if(color[v] == 0)
                dfsSCC(v, color, graph);
        }
        color[u] = 2;
    }

    // The idea here is to use the run the "Disjoint Sets using union by rank and path compression Graph Algorithm"
    // to find out the SCC. This is a very well known technique to find the SCC's of a graph. This algo runs faster than
    // DFS as DFS is O(V+E) but this is O(aE) where a is approximately = 4 so this algo becomes O(E). THis is due to the
    // use of path compression using the rank array & findParent function where we update parent of all the node
    // in the path to the new parent
    private int findParent(int x, int []parent) {
        if (x == parent[x])
            return x;
        parent[x] = findParent(parent[x], parent);
        return parent[x];
    }
    private void unionNodes(int x, int y, int []parent, int []rank) {
        int px = findParent(x, parent);
        int py = findParent(y, parent);
        if (px != py) {
            if(rank[px] > rank[py]) {
                parent[py] = px;
                rank[px]+= rank[py];
            } else {
                parent[px] = py;
                rank[py] += rank[px];
            }
        }
    }
    public int countComponentsV2(int n, int[][] edges) {
        int count = 0;
        int []parent = new int[n];
        int []rank = new int[n];

        for (int i = 0; i <n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        count = n;
        for (int []e : edges) {
            int u = e[0] , v = e[1];
            if (findParent(u, parent) != findParent(v, parent)) {
                count--;
                unionNodes(u, v, parent, rank);
            }
        }
        return count;
    }

    //LeetCode :: 1319. Number of Operations to Make Network Connected
    // The idea is to run dfs to get count the SCC and then we check if we have enough edges to connect all nodes
    // if yes we only need scc -1 edges if no  we cannot connect the graph. The run time is very slow for this DFS
    // based solution. Lets check is we can use "Disjoint Sets using union by rank and path compression Graph Algorithm"
    // Check the solution V2 with Disjoint Set Algo the runtime is significantly faster as it run in O(E) time
    private void dfsMakeConnection(HashMap<Integer, HashSet<Integer>> graph, int [] color, int u, int parent) {
        color[u] = 1;
        HashSet<Integer> adj = graph.get(u);
        for (int v : adj) {
            if (color[v] == 0) {
                dfsMakeConnection(graph, color, v, u);
            }
        }
    }
    public int makeConnected(int n, int[][] connections) {
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
        for (int i = 0; i<n; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int []e : connections) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        int [] color = new int[n];
        Arrays.fill(color,0);
        int scc = 0;

        for (int u = 0; u < n; u++) {
            if(color[u] == 0) {
                scc++;
                dfsMakeConnection(graph, color, u, -1);
            }

        }
        if (connections.length < n -1)
            return -1;
        return scc-1;
    }

    // The idea here is also same we need to find the number of SCC but in this case we use the
    // "Disjoint Sets using union by rank and path compression Graph Algorithm". This makes the runtime very fast as it
    // O(E) compared to O(V+E) for DFS. So its better to use this algo if we are looking for SCC count
    private int findMC(int x, int [] parent) {
        if (x==parent[x])
            return x;
        parent[x] = findMC(parent[x], parent);
        return parent[x];
    }
    private void unionMC(int x, int y, int []parent, int []rank) {
        int px = findMC(x, parent);
        int py = findMC(y,parent);
        if(px != py) {
            if(rank[px] > rank[py]) {
                parent[py] = px;
                rank[px] += rank[py];
            } else {
                parent[px] = py;
                rank[py] += rank[px];
            }
        }
    }
    public int makeConnectedV2(int n, int[][] connections) {
        int []parent = new int[n];
        int []rank = new int[n];
        // make set
        for (int i = 0; i<n ;i++){
            parent[i] = i;
            rank[i] = 1;
        }
        int scc = n;
        for (int []e : connections) {
            int u = e[0], v = e[1];
            // find & union
            if (findMC(u,parent) != findMC(v, parent)){
                unionMC(u, v, parent, rank);
                scc--;
            }
        }

        if (connections.length < n -1)
            return -1;
        return scc-1;
    }

    // LeetCOde :: 261. Graph Valid Tree
    // The idea is to run BFS or DFS to detect a loop and count the number of SCC. if loop found we return false
    // if no loop found but we found more than 1 SCC we return false
    // Any problem that requires getting SCC we will find a better runtime with Disjoint Set Union Find algo
    // BFS/DFS will have higher runtime
    private boolean bfsVT(int src, HashMap<Integer, ArrayList<Integer>> graph, int []color){
        Queue<Integer> q = new LinkedList<>();
        color[src] = 1; // grey vertex
        q.add(src);
        while(!q.isEmpty()) {
            int u = q.remove();
            ArrayList<Integer> adj = graph.get(u);
            color[u] = 2; // black vertex need to color black for loop detection, only grey -> grey means loop
            for (int v : adj) {
                if (color[v] == 0) {
                    q.add(v);
                    color[v] = 1;
                } else if (color[v]== 1) { // visited another grey vertex so loop detected
                    return false;
                }
            }
        }
        return true;
    }
    public boolean validTreeV2(int n, int[][] edges) {
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int []e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
        int [] color = new int[n];
        Arrays.fill(color, 0);
        int scc = 0;
        // run bfs for all node and count SCC
        for (int src = 0; src < n; src++){
            if (color[src] == 0) {
                scc++;
                if (scc > 1 || !bfsVT(src, graph, color))
                    return false;

            }
        }
        return true;
    }
    // Same idea find a loop and count the SCC if loop detected we return false, if scc > 1 we return false
    // In this version we use the Disjoint set union/find algo and the run time is much better than the BFS above
    private int findVT(int x, int []parent) {
        if (x == parent[x])
            return x;
        parent[x] = findVT(parent[x], parent);
        return parent[x];
    }
    private void unionVT(int x, int y, int []parent, int []rank) {
        int px = parent[x];
        int py = parent[y];
        if (px != py) {
            if(rank[px] > rank[py]) {
                parent[py] = px;
                rank[px] += rank[py];
            } else {
                parent[px] = py;
                rank[py] += rank[px];
            }
        }
    }
    public boolean validTree(int n, int[][] edges) {
        int []parent = new int[n];
        int []rank = new int[n];
        for (int i = 0; i<n ;i++){
            parent[i] = i;
            rank[i] = 1;
        }
        int scc = n;
        for (int []e : edges) {
            int u = e[0], v = e[1];
            if (findVT(u,parent) != findVT(v,parent)) {
                scc--;
                unionVT(u,v,parent,rank);
            } else { // loop detected
                return false;
            }
        }
        return scc == 1;
    }






}
