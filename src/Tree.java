
import java.util.*;

public class Tree {

    public TreeNode root;
    public Tree (int rootVal){
        root = new TreeNode(rootVal);
    }
    public Tree () {
        root = new TreeNode(Integer.MIN_VALUE); // creating a tree but the root value is not init
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }
    public void printRight (TreeNode root) {
        while (root != null) {
            System.out.print(" " + root.val);
            root = root.right;
        }
        System.out.println();
    }

    public void createBinaryTree (int arr[]) {
        if (arr.length == 0) {
            System.out.println("Array size zero");
        }
        int mid = arr.length/2;
        root.data = arr [mid];
        root.left = createBinaryTreeRecurse(0, mid-1, arr);
        root.right = createBinaryTreeRecurse(mid+1, arr.length-1,arr);

    }

    public TreeNode createBSTree (int low , int high, int arr []){
        if (low > high) {
            return null;
        }
        int mid = (low + high)/2;
        TreeNode node = new TreeNode(arr[mid]);
        //System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
        node.left = createBSTree(low, mid-1, arr);
        node.right = createBSTree(mid+1, high, arr);
        return node;
    }

    private TreeNode createBinaryTreeRecurse (int low, int high, int arr []) {
        if (low > high) {
            return null;
        }
        int mid = (low + high)/2;
        TreeNode node = new TreeNode(arr[mid]);
        System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
        node.left = createBinaryTreeRecurse(low, mid-1, arr);
        node.right = createBinaryTreeRecurse(mid+1, high, arr);
        return node;
    }

    public void inOrderTraversalRec (TreeNode node) {
        if ( node != null) {
            inOrderTraversalRec(node.left);
            System.out.print(node.data + " ");
            inOrderTraversalRec(node.right);
        }
    }

    public void preOrderTraversalRec (TreeNode node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrderTraversalRec(node.left);
            preOrderTraversalRec(node.right);
        }
    }

    public void postOrderTraversalRec(TreeNode node) {
        if (node != null) {
            postOrderTraversalRec(node.left);
            postOrderTraversalRec(node.right);
            System.out.print(node.data + " ");
        }

    }

    public TreeNode search(int data, TreeNode node) {
        if (node == null) {
            System.out.println("Data not found");
            return null;
        }
        if (data == node.data) {
            return node;
        }
        else if (data < node.data) {
            return search(data, node.left);
        } else {
            return search(data, node.right);
        }
    }

    public TreeNode searchBiggest (TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.right == null)
            return node;
        return searchBiggest(node.right);

    }

    public TreeNode searchSmallest (TreeNode node) {
        if (node == null) {
            return null;
        }
        if (node.left == null)
            return node;
        return searchSmallest(node.left);

    }

    public TreeNode inOrderPredecessorWithoutParent (TreeNode node) {
        if (node.left != null) {
            return searchBiggest(node.left);
        }
        TreeNode pred = null;
        TreeNode rt = root;

        while (rt != null) {
            if (node.data < rt.data) {
                rt = rt.left;
            } else if (node.data > rt.data) {
                pred = rt;
                rt = rt.right;
            } else {
                break;
            }
        }
        return pred;
    }

    public TreeNode inOrderSuccesorWithParent (TreeNode node) {
        if (node.right != null) {
            return searchSmallest(node.right);
        }
        TreeNode pnode = node.parent;
        while(pnode != null && pnode.right == node) {
            pnode = pnode.parent;
            node = node.parent;
        }
        return pnode;
    }
    public TreeNode inOrderSuccessorWithoutParent (TreeNode node) {
        TreeNode rt = root;
        TreeNode succ = null;

        if (node.right != null) {
            return searchSmallest(node.right);
        }

        while (rt != null) {
            if (node.data < rt.data) {
                succ = rt;
                rt = rt.left;
            } else if (node.data > rt.data) {
                rt = rt.right;
            } else
                break;
        }
        return succ;

    }

    public void levelOrderTraversalAdd2List () {
        ArrayList<LinkedList<TreeNode>> listArray = new ArrayList<>();
        int level = 0;
        LinkedList<TreeNode> list  = new LinkedList();
        list.add(root);
        listArray.add(level,list);
        System.out.println(root.val);
        while (true) {
            list = new LinkedList<>();
            for (int i = 0; i < listArray.get(level).size(); i++) {
                TreeNode node = (TreeNode) listArray.get(level).get(i);
                if (node.left != null) {
                    list.add(node.left);
                    System.out.print(node.left.val +" ");
                }
                if (node.right != null) {
                    list.add(node.right);
                    System.out.print(node.right.val +" ");
                }
            }
            System.out.println();
            if (list.size() == 0) {
                break;
            }
            level++;
            listArray.add(level,list);

        }

    }
    public boolean covers (int data, TreeNode node) {
        if (node == null) {
            return false;
        }
        if (node.data == data)
            return true;
        else
            return covers(data, node.left) | covers(data, node.right);
    }
    public TreeNode commonAncestor(TreeNode nodeA, TreeNode nodeB ) {
        TreeNode root = this.root;
        if (nodeA == null || nodeB == null) {
            return root;
        }
        if (isAncestor(nodeA , nodeB))
            return nodeA;
        if (isAncestor(nodeB,nodeA)) {
            return nodeB;
        }
        return commonAncestor(root, nodeA.data, nodeB.data);

    }
    private TreeNode commonAncestor(TreeNode ancestor, int nodeAData, int nodeBData) {
        boolean aOnLeft = covers(nodeAData, ancestor.left);
        boolean bOnleft = covers(nodeBData, ancestor.left);
        //System.out.println("aOnLeft " + aOnLeft + " bOnleft " + bOnleft);

        if (aOnLeft != bOnleft)
            return ancestor;
        return aOnLeft ? commonAncestor(ancestor.left, nodeAData, nodeBData) :
                         commonAncestor(ancestor.right, nodeAData, nodeBData);


    }
    public boolean isAncestor (TreeNode ancestor, TreeNode child) {
        TreeNode node = ancestor;
        return covers(child.data, ancestor.left) | covers(child.data, ancestor.right);
    }

    public boolean getPathFromNode (ArrayList<Integer> path, TreeNode src, TreeNode dest) {
        if (src == null)
            return false;

        if (src.data == dest.data) {
            path.add(dest.data);
            return true;
        }
        if ( getPathFromNode(path,src.left, dest) ||
                getPathFromNode(path, src.right, dest)) {
            path.add(src.data);
            return true;
        } else {
            return false;
        }
    }
    /*
    *  This will give the common ancestor of two nodes using the getPathFromNode method
    *  If we can get the path from root to nodeA and nodeB then the point where the path diverges
    *  is the first common ancestor
    * */
    public TreeNode getCommonAncestor(TreeNode nodeA, TreeNode nodeB) {

        if (nodeA == null || nodeB == null)
            return getRoot();

        TreeNode ancestor = null;
        ArrayList<Integer> pathNodeA = new ArrayList<>();
        ArrayList<Integer> pathNodeB = new ArrayList<>();

        if (getPathFromNode(pathNodeA , getRoot(), nodeA) &&
                getPathFromNode(pathNodeB, getRoot(), nodeB)) {

            int i = pathNodeA.size() - 1;
            int j = pathNodeB.size() - 1 ;
            while (i>= 0 && j >= 0 &&
                    (pathNodeA.get(i) == pathNodeB.get(j))) {
                i--;
                j--;
            }
            ancestor = search(pathNodeA.get(i+1),getRoot());
        } else {
            System.out.println("One of the node is not in the tree");
        }
        return ancestor;
    }

    // LeetCode :: 94. Binary Tree Inorder Traversal
    // The basic idea is to use a stack to do the traversal, We dont need a complicated stack push pop.
    // Just consider the inorder traversal order we need to keep pushing to stack when there is a left child
    // if there is no more left child we pop & push the right child in a iterative manner
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        // we start with a empty stack but node is not null so we will push the first node anyways
        while (!stack.isEmpty() || node != null){
            // if the node is not null we push this node
            if (node != null) {
                stack.push(node);
                // move to the left node so that we can push it next time
                node = node.left;
            } else {
                // no more left sub tree we visit the node & move node to right for next push
                node = stack.pop();
                rList.add(node.data);
                node = node.right;
            }
        }

        return rList;
    }

    // LeetCode :: 144. Binary Tree Preorder Traversal
    // use a stack visit node first then stack push the right child before the left child
    // check version2 its easy to read; but same as this one
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            if(node != null) {
                rList.add(node.data);
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (node.right != null)
                    node = node.right;
                else
                    node = null;
            }
        }
        return rList;
    }
    // version2 same logic as before but easy to read
    public List<Integer> preorderTraversalV2(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = null;
        // push the root node
        stack.push(root);
        while (!stack.isEmpty()) {
            // preoder visit the node
            node = stack.pop();
            rList.add(node.data);
            // node lets put the right child on the stack first then the left child so that we visit right subtree after
            // we have visited the left subtree, because we are using stack we use
            // this reverse order of right child before left child
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
        return rList;
    }

    // LeetCode :: 145. Binary Tree Postorder Traversal (Hard)
    // The postorder traversal is similar to iterative inorder or preorder traversal
    // The only difference is we have to keep track if the right subtree has been visited
    // for the current node if yes then we can pop this node from stack and visit current node
    // otherwise we have not visited the right substree so move node to the right to put the right
    // substree in the stack
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> rList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode lastPoppedNode = null;
        TreeNode node = root;
        int count = 0;

        while (!stack.isEmpty() || node != null) {
            if(node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.peek();
                // check if right subtree has been visited, if yes lets visit current node
                if (node.right == null ||
                        node.right == lastPoppedNode) {
                    lastPoppedNode = stack.pop();
                    rList.add(lastPoppedNode.data);
                    // this node has been visited, lets make it null so that we can process
                    // the next node from the stack
                    node = null;
                } else {
                    // right subtree not visited lets go to right to stack right subtree
                    node = node.right;
                }
            }
        }
        return rList;
    }
    // LeetCode :: 590. N-ary Tree Postorder Traversal
    // Post order traversal can be achieved by traversing root right subtree & left sub tree
    // then reversing the whole traversal
    // The nary post order traversal is easy because we have the list of node children.
    // At each step we take a node and put its children into the stack, and put the node in a list.
    // The children are popped from the stack in LIFO so the rightmost child will get popped first,
    // so we will in next iteration process the right child's subtree this allow as us to visit the
    // subtrees of children from right to left. We add node in the list in the same order so to get our
    // result we just need to revere the final list
    public List<Integer> postorder(Node root) {
        List<Integer> rList = new ArrayList<>();
        if (root == null)
            return rList;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        stack.push(node);
        while (!stack.empty()) {
            node = stack.pop();
            rList.add(node.val);
            // add children into the stack left to right so
            // when popped they will be popped right to left
            for (Node n : node.children) {
                stack.push(n);
            }
        }
        // we have the result in reverse order so lets fix it
        Collections.reverse(rList);
        return rList;
    }

    // LeetCode :: 589. N-ary Tree Preorder Traversal
    // The same approach as the Postorder traversal, only difference is adding the children
    // into the stack in right to left order so when we pop them the come out as left to right.
    // This allows traversing the children list from left to right as needed by preorder
    public List<Integer> preorder(Node root) {
        List<Integer> rList = new ArrayList<>();
        if (root == null)
            return rList;
        Stack<Node> stack = new Stack<>();
        Node node = root;
        stack.push(node);
        while (!stack.empty()) {
            node = stack.pop();
            rList.add(node.val);
            int i = node.children.size() - 1;
            // add the children from right to left so that
            // we can traverse the tree from left to right
            for (;i>= 0;i--) {
                stack.push(node.children.get(i));
            }
        }
        return rList;
    }
    // LeetCode :: 95. Unique Binary Search Trees II
    // The Problems requires us to generate all BST possible for 1 to n. So we actually have to generate those tree.
    // The idea is to go by each node from 1 to n as root and create a tree. So for each node as root we need to
    // build a left subtree & a right subtree and then add the left subtree & right subtree as to the root.
    // We use two lists to store the left subtrees & right subtrees, Then for root we take one node from left subtree
    // & one node from rightSubtree. Finally return the list
    private List<TreeNode> genBSTree (int start, int end) {
        List<TreeNode> rList = new ArrayList<>();
        if (start > end) {
            rList.add(null);
            return rList;
        }
        if (start == end) {
            rList.add( new TreeNode(start));
            return rList;
        }
        for (int i = start; i <= end; i++) {
            // left subtree list holds all the possible left subtrees
            List<TreeNode> leftSubTreeList = genBSTree(start, i - 1);
            // right subtree list holds all the possible right subtrees
            List<TreeNode> rightSubTreeList = genBSTree(i+1, end);
            for (TreeNode leftSubTree : leftSubTreeList) {
                for (TreeNode rightSubTree : rightSubTreeList) {
                    // for this root pick a left subtree & right subtree and the to the list
                    // we need to add root to the list multiple time with multiple options of
                    // left & right subtree, we will have total pf leftSubtree.size * rightSubtree.size
                    // for example (1,2)  3 (4,5) ; for root 3 there are 4 distinct trees with 3 as root
                    TreeNode root  = new TreeNode(i);
                    root.left = leftSubTree;
                    root.right= rightSubTree;
                    rList.add(root);
                }
            }
        }
        return rList;
    }
    public List<TreeNode> generateTrees(int n) {
        if (n == 0)
            return new ArrayList<TreeNode>();
        return genBSTree(1,n);
    }

    // 98. Validate Binary Search Tree
    // This is the fastest so far, The idea is to create a limit for each subtree and check if all
    // the nodes in the subtree follows that limit, for example 1,2,3,4,5  rooted at 3 the left subtree
    // limit is (-MIN, 2) & (4, MAX)
    private boolean isValidBSTRec (TreeNode parent, TreeNode node, int min, int max) {
        if (node == null)
            return true;
        // we need to handle special cases when the node value is equal to MAX or MIN then the
        // comparision fails so we need to check the parent node to make sure the limit condition
        // are met we check if the nodes value is bigger than parent
        if (parent != null) {
            if (parent.val == node.val)
                return false;
            else if (parent.val > node.val && parent.right == node)
                return false;
            else if (parent.val < node.val && parent.left == node)
                return false;
        }
        if (node.val >= min && node.val <= max)
            return isValidBSTRec(node,node.left, min, node.val -1) &&
                    isValidBSTRec(node,node.right , node.val + 1, max);
        else
            return false;
    }

    public boolean isValidBSTV2(TreeNode root) {
        return isValidBSTRec(null, root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    // this is little slow
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> alist = new ArrayList<>();
        TreeNode node = root;
        while (!stack.empty() || node != null) {
            if(node !=null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                alist.add(node.val);
                node = node.right;
            }
        }
        for (int i = 1; i<alist.size(); i++) {
            if(alist.get(i-1) >= alist.get(i))
                return false;
        }
        return true;
    }
    // this is slow too the idea is to iteratively inorder traverse the tree ,
    // while traversing if we found a node that has smaller value than the prev
    // node in inorder traversal then this is not a BST as inorder traversal gives
    // you node ascending sorted
    public boolean isValidBSTV3(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        ArrayList<Integer> alist = new ArrayList<>();
        TreeNode node = root;
        double lastVal = -Double.MAX_VALUE;
        while (!stack.empty() || node != null) {
            if(node != null) {
                stack.push(node);
                node = node.left;
                continue;
            }
            node = stack.pop();
            if (node.val <= lastVal)
                return false;
            lastVal = node.val;
            node = node.right;

        }
        return true;
    }
    // This has same run time 1 ms which is better but technically the iterative version should be faster.
    // one benefit of this approach is we can terminate early so based on the tree this may become faster
    private boolean validBST(TreeNode root,  Integer low, Integer high) {
        if (root == null)
            return true;
        if (low != null && low >= root.val)
            return false;
        if (high != null && high <= root.val)
            return false;
        return validBST(root.left, low, root.val)
                && validBST(root.right, root.val, high);
    }
    // 101. Symmetric Tree check the version 2 its more neat
    private boolean isSymmetricRec (TreeNode p, TreeNode q) {
        if( p == null || q == null){
            if(p == null && q == null)
                return true;
            return false;
        }
        if(p.val == q.val)
            return isSymmetricRec(p.left,q.right) && isSymmetricRec(p.right, q.left);
        return false;
    }


    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        if (root.left != null && root.right!= null)
            return isSymmetricRec(root.left, root.right);
        return false;
    }
    // The idea is to check if left subtree & right substree are symmetric by comparing the mirror of two trees
    public boolean isSymmetricV2(TreeNode root) {
        if (root == null)
            return true;
        return isSymmetricRecV2(root.left, root.right);
    }
    // The function below actually checks if the nodes in two trees are mirrored
    // returns true if mirrored
    private boolean isSymmetricRecV2 (TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p== null || q == null)
            return false;
        return ((p.val == q.val)) &&
                isSymmetricRecV2(p.left,q.right) &&
                isSymmetricRecV2(p.right, q.left);
    }
    // This is the iterative version of symmetric tree problem
    // The idea is to use a queue to detect the symmetry
    // We add nodes in queue in such a way that two consequtive nodes are mirrored,
    // if the two consequtive nodes are not equal then we dont have a solution,
    // so how to add two consequtive mirrored nodes in the queue, the trick is when
    // we add node q's child we add them  in the reverse order of  node p's child
    public boolean isSymmetricIter(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        // add root twice as we need two nodes to start the comparision
        // we could have added left & right child of root that would have worked too
        queue.add(root);
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode p = queue.poll();
            TreeNode q = queue.poll();
            if (p == null && q == null)
                continue;
            if (p == null || q == null)
                return false;
            if (p.val != q.val)
                return false;
            // add q's child it the reverse order of p's child,
            // Note this works if we have any number of child cause
            // we are traversing the tree in level order so we will visit all the nodes
            // in a level before going to the next level
            queue.add(p.left);
            queue.add(q.right);
            queue.add(p.right);
            queue.add(q.left);
        }

        return true;
    }

    // LeetCode :: 100. Same Tree
    public boolean isSameTreeV2(TreeNode p, TreeNode q) {
        if ( p !=null && q!= null ) {
            if (p.val == q.val)
                return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
            else
                return false;
        } else if (p == null && q == null){
            return true;
        }
        return false;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        Queue <TreeNode> queue = new LinkedList<>();
        queue.add(p);
        queue.add(q);
        while (!queue.isEmpty()){
            TreeNode p1 = queue.poll();
            TreeNode q1 = queue.poll();
            if(p1 == null & q1 == null)
                continue;
            if(p1 == null || q1 == null)
                return false;
            if(p1.val != q1.val)
                return false;
            queue.add(p1.left);
            queue.add(q1.left);
            queue.add(p1.right);
            queue.add(q1.right);
        }
        return true;
    }
    // LeetCode :: 102. Binary Tree Level Order Traversal
    // The idea is to use 2D list of TreeNodes so that we can save the nodes per level
    // We start with the root node in the 2d list first and then in the loop we remove all the node
    // from current level and add the children in the next level , we increase the level to before the
    // iteration so the next time we process the next level nodes
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> rList  = new ArrayList<>();
        if (root == null)
            return rList;
        List<List<TreeNode>> nodeList = new ArrayList<>();
        ArrayList<TreeNode> clist = new ArrayList<>();
        ArrayList<Integer>  tempList = new ArrayList<>();
        int level = 0;
        // store the root
        clist.add(root);
        tempList.add(root.val);
        nodeList.add(level,clist);
        rList.add(level,tempList);

        while (true) {
            clist = new ArrayList<>();
            tempList = new ArrayList<>();
            // get the parent nodes at this level and add the children to next level
            for (TreeNode t: nodeList.get(level)) {
                if (t.left != null) {
                    clist.add(t.left);
                    tempList.add(t.left.val);
                }
                if (t.right != null){
                    clist.add(t.right);
                    tempList.add(t.right.val);
                }

            }
            // no more children left so we are done
            if (tempList.size() == 0)
                break;
            level++;
            nodeList.add(level, clist);
            rList.add(level, tempList);
        }
        return rList;
    }
    // easy to read version of levelOrder traversal
    // This more concise & easy to explain version of level order traversal
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> rList = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return rList;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> tempList = new ArrayList<>();
            for ( int i =0; i< size; i++) {
                TreeNode node =  queue.remove();
                tempList.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            rList.add(tempList);
        }
        return rList;
    }
    // level order traversal in recursive manner its little faster , does not require as much as memory
    // this is basically using a preorder traversal but using a list of list to store the nodes in level
    // order traversal manner
    public List<List<Integer>> levelOrderRec(TreeNode root) {
        List<List<Integer>> rList = new ArrayList<>();
        if (root == null)
            return rList;
        levelOrderRecHelper(rList, 1, root);
        return rList;
    }
    private void levelOrderRecHelper(List<List<Integer>> rList, int level, TreeNode node){
        if( node == null)
            return;
        // if the list is not initialized, init it
        if (rList.size() < level ) {
            rList.add(new ArrayList<>());
        }
        // get the list at this level and add the node , remember levels are 0 based indexed
        List<Integer> ls = rList.get(level-1);
        ls.add(node.val);
        levelOrderRecHelper(rList, level + 1, node.left);
        levelOrderRecHelper(rList, level + 1, node.right);
    }

    // 103. Binary Tree Zigzag Level Order Traversal
    // Same idea as levelOrder traversal only difference is to add or print
    // the list of nodes in reverse order when the level is Odd. That ways alternative levels will be reversed
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> rList  = new ArrayList<>();
        if (root == null)
            return rList;
        List<List<TreeNode>> nodeList = new ArrayList<>();
        ArrayList<TreeNode> clist = new ArrayList<>();
        ArrayList<Integer>  tempList = new ArrayList<>();
        int level = 0;
        // store the root
        clist.add(root);
        tempList.add(root.val);
        nodeList.add(level,clist);
        rList.add(level,tempList);

        while (true) {
            clist = new ArrayList<>();
            tempList = new ArrayList<>();
            // get the parent nodes at this level and add the children to next level
            for (TreeNode t: nodeList.get(level)) {
                if (t.left != null) {
                    clist.add(t.left);
                    tempList.add(t.left.val);
                }
                if (t.right != null){
                    clist.add(t.right);
                    tempList.add(t.right.val);
                }

            }
            // no more children left so we are done
            if (tempList.size() == 0)
                break;
            level++;
            nodeList.add(level, clist);
            if (level % 2 != 0)
                Collections.reverse(tempList);
            rList.add(level, tempList);
        }
        return rList;
    }

    // same idea as the levelOrder traversal only trick is to add the a reversed list for even levels
    public List<List<Integer>> zigzagLevelOrderV2(TreeNode root) {
        List<List<Integer>> rlist = new ArrayList<>();
        if (root == null) return rlist;
        Queue<TreeNode> queue = new LinkedList<>();
        int level = 1;
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> tlist = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.remove();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);

                tlist.add(node.val);

            }
            if (level %2 == 0)
                Collections.reverse(tlist);
            rlist.add(tlist);
            level++;

        }
        return rlist;
    }

    // LeetCode :: 104. Maximum Depth of Binary Tree
    // Get the max height of the tree recursively
    public int maxDepth(TreeNode root) {
        // null nodes are not counted
        if (root == null)
            return 0;
        // return 1 + the max of left & right child
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));

    }
    // one line code :)
    public int maxDepth2(TreeNode root) {
        return (root==null) ? 0: 1+Math.max(maxDepth2(root.left),maxDepth2(root.right));
    }

    // LeetCode :: 105. Construct Binary Tree from Preorder and Inorder Traversal
    // Remember to use hashMap whenever you only do lookup in an unsorted array never
    // do O(n) search unless the search is done just once
    // The idea to solve the problem is to add root nodes from the preorder array and found that node
    // position in inorder array, when we found such a position the left side will give the left subtree
    // & right side will give right sub tree. So now we can recursively look to solve the problem in
    // right & left subtree. One thing to remember is the boundary in the inorder array is different
    // from preorder array on the preorder array fro right subtree we need to jump from current
    // position + the length of left subtree so the calc is (end - start + 1 + prestart + 1)
    // Check the version 4 its the best solution
    HashMap<Integer,Integer> inorderMap = new HashMap<>();
    public TreeNode buildTreeV2(int[] preorder, int[] inorder) {
        if(preorder.length == 0 || inorder.length == 0 || preorder.length != inorder.length)
            return null;
        int preStart = 0;
        // build a hasmap as this lookup will be done  multiple times in the recursion so
        // building a hashmap makes the algo run surprisingly fast
        for (int i = 0; i<inorder.length; i++) {
            inorderMap.put(inorder[i],i);
        }
        int r = inorderMap.get(preorder[preStart]);
        int start = 0, end = r-1;
        TreeNode root = new TreeNode(preorder[preStart]);
        root.left = buildTreefromInPreOrder(preorder, inorder,start, end,
                                    preStart +1);
        root.right = buildTreefromInPreOrder(preorder, inorder, r + 1,
                                inorder.length -1, end - start + preStart + 2);
        return root;
    }
    private TreeNode buildTreefromInPreOrder (int []preorder, int []inorder,int start, int end, int pStart) {
        if (start > end || pStart >= preorder.length)
            return null;
        if(start == end) {
            TreeNode node = new TreeNode(preorder[pStart]);
            return node;
        }

        int r = inorderMap.get(preorder[pStart]);

        TreeNode node = new TreeNode(preorder[pStart]);
        int s = start, e = r -1;
        node.left = buildTreefromInPreOrder(preorder, inorder,s, e, pStart +1);
        node.right = buildTreefromInPreOrder(preorder, inorder,r+1, end, e-s+pStart+2);
        return node;

    }

    // This is easier to read as we use four indexes (two set of start & end)
    private TreeNode buildTreePreOrderRecV3(int[] inorder, int[] preorder, int inorderSt,
                                             int inorderEnd, int preorderSt, int preorderEn) {
        if (inorderEnd < inorderSt || preorderEn < preorderSt)
            return null;
        int inOrderRootIdx = inorderMap.get(preorder[preorderSt]);
        // for post oder we cal the right sub tree size as the right sub tree appears after root node
        // for indorder case we just need to calc left subtree size instead using inOrderRootIdx - inorderSt
        int leftSubtreeSize = inOrderRootIdx - inorderSt;
        TreeNode root = new TreeNode(preorder[preorderSt]);
        //System.out.println(root.val + " " + rightSubtreeSize + " " + inorderEnd +" " + inOrderRootIdx);
        root.left = buildTreePreOrderRecV3(inorder, preorder, inorderSt,
                inOrderRootIdx -1, preorderSt + 1, preorderSt + leftSubtreeSize);
        root.right = buildTreePreOrderRecV3(inorder, preorder, inOrderRootIdx +1 ,
                inorderEnd , preorderSt + leftSubtreeSize +1, preorderEn);
        return root;
    }

    // This is the easiest solution uses start & end pointers along with an index to traverse the preorder tree
    int preSt = -1;
    private TreeNode buildTreePreOrderRecV4(int[] inorder,
                                            int[] preorder,
                                            int inorderSt,int inorderEnd) {
        if (inorderEnd < inorderSt)
            return null;
        preSt++;
        int inOrderRootIdx = inorderMap.get(preorder[preSt]);
        // for post oder we cal the right sub tree size as the right sub tree appears after root node
        // for indorder case we just need to calc left subtree size instead using inOrderRootIdx - inorderSt

        TreeNode root = new TreeNode(preorder[preSt]);
        //System.out.println(root.val + " " + rightSubtreeSize + " " + inorderEnd +" " + inOrderRootIdx);
        root.left = buildTreePreOrderRecV4(inorder, preorder, inorderSt,
                inOrderRootIdx -1);
        root.right = buildTreePreOrderRecV4(inorder, preorder, inOrderRootIdx +1 ,
                inorderEnd);
        return root;
    }
    // This is a very nice solution, We build the tree in preorder
    // note that we add nodes from a preorder array from 0 to n order, hence we can have a pointer in preOrder array
    // that gives us preorder node in every step, another pointer we use in inorder array to know the boundary for
    // left subtree
    int inIdx = 0;
    int preIdx = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeHelper(preorder,inorder, Integer.MIN_VALUE);
    }
    private  TreeNode buildTreeHelper (int[] preorder, int[] inorder, int stop) {
        if (preIdx >= preorder.length)
            return null;
        // we reached the parent node stop, this is the boundary for left subtree & right subtree
        // this right subtree boundary for example pre: [1 2 4 6] in: [4 2 6 1] when we reach node 6 after node 2
        // in inroder the
        System.out.println(preorder[preIdx] +" " +stop + " " +inIdx);
        if (inorder[inIdx] == stop) {
            inIdx++;
            return null;
        }
        TreeNode node = new TreeNode(preorder[preIdx++]);
        System.out.println("ND=" + node.val);
        node.left = buildTreeHelper(preorder,inorder, node.val);
        node.right = buildTreeHelper(preorder, inorder, stop);
        return node;
    }


    // LeetCode :: 106. Construct Binary Tree from Inorder and Postorder Traversal
    // This can be solved the same as problem 105 above (where inoreder & preorder was given)
    // The diff is for post order we have to traverse the post order array from right to left and
    // add right sub tree & then left subtree, (the order we create left / right sub tree does not matter )
    public TreeNode buildTreePostOrder(int[] inorder, int[] postorder) {

        if(postorder.length == 0 || inorder.length == 0 || postorder.length != inorder.length)
            return null;
        int postStart = postorder.length -1;
        // build a hasmap as this lookup will be done  multiple times in the recursion so
        // building a hashmap makes the algo run surprisingly fast
        for (int i = 0; i<inorder.length; i++) {
            inorderMap.put(inorder[i],i);
        }
        int r = inorderMap.get(postorder[postStart]);
        int start = r+1, end = postorder.length -1;
        int len = end -start +1;
        TreeNode root = new TreeNode(postorder[postStart]);
        root.right = buildTreefromInPostOrder(postorder, inorder, r + 1,
                inorder.length -1, postStart-1 );
        root.left = buildTreefromInPostOrder(postorder, inorder, 0, r-1, postStart - len -1);
        return root;
    }

    private TreeNode buildTreefromInPostOrder (int []postder, int []inorder,int start, int end, int pStart) {

        if (start > end || pStart < 0)
            return null;
        if (start == end) {
            TreeNode node = new TreeNode(postder[pStart]);
            return node;
        }
        System.out.println(postder[pStart]);
        int r = inorderMap.get(postder[pStart]);
        TreeNode node = new TreeNode(postder[pStart]);
        int len = end - r;
        // we could have created the left sub tree before the right sub tree it does not matter
        // what matters is the calc of the pstart
        node.right = buildTreefromInPostOrder(postder, inorder, r+1, end, pStart -1);
        node.left = buildTreefromInPostOrder(postder,inorder, start, r-1, pStart -len -1);
        return node;
    }
    // This version is easy to read but little slower as for psotorder we reverse the array to make the calc easier
    public TreeNode buildTreePostOrderV2(int[] inorder, int[] postorder) {
        if(postorder.length == 0 || inorder.length == 0 || postorder.length != inorder.length)
            return null;
        // build a hasmap as this lookup will be done  multiple times in the recursion so
        // building a hashmap makes the algo run surprisingly fast
        int left = 0, right = postorder.length -1;
        for (int i = 0; i<inorder.length; i++) {
            inorderMap.put(inorder[i],i);
            // reverse the post order array this way we can deal with th postorder traversal the
            // same way we can handle inorder, we dont need to go into the complexity of negative calc
            if (left < right) {
                int temp = postorder[left];
                postorder[left] = postorder[right];
                postorder[right] = temp;
                left++;
                right--;
            }
        }
        return buildTreePostOrderV2Rec(inorder,postorder, 0, inorder.length-1, 0, postorder.length -1);
    }

    // This is easier to read as we use four indexes (two set of start & end)
    private TreeNode buildTreePostOrderV2Rec(int[] inorder, int[] postorder, int inorderSt,
                                             int inorderEnd, int postorderSt, int postorderEn) {
        if (inorderEnd < inorderSt || postorderEn < postorderSt)
            return null;
        int inOrderRootIdx = inorderMap.get(postorder[postorderSt]);
        // for post oder we cal the right sub tree size as the right sub tree appears after root node
        // for indorder case we just need to calc left subtree size instead using inOrderRootIdx - inorderSt
        int rightSubtreeSize = inorderEnd - inOrderRootIdx ;
        TreeNode root = new TreeNode(postorder[postorderSt]);
        //System.out.println(root.val + " " + rightSubtreeSize + " " + inorderEnd +" " + inOrderRootIdx);
        root.right = buildTreePostOrderV2Rec(inorder, postorder, inOrderRootIdx +1 ,
                inorderEnd , postorderSt + 1,
                postorderSt + rightSubtreeSize);
        root.left = buildTreePostOrderV2Rec(inorder, postorder, inorderSt,
                inOrderRootIdx -1, postorderSt + 1 + rightSubtreeSize , postorderEn);
        return root;
    }

    // This implementation is easy to read & less complicated. We build the tree node by scanning the postorder
    // array but to pick the right & left sub tree we use the inorder array. Note we are basically doing a  modified preorder
    // traversal while building the tree as postorder node can traversed  (node right left) from the right in preorder
    int postSt;
    public TreeNode buildTreeV3(int[] inorder, int[] postorder) {
        postSt = postorder.length;
        for (int i = 0; i<inorder.length; i++) {
            inorderMap.put(inorder[i],i);
        }
        return buildTreePost(inorder, postorder, 0, inorder.length -1);

    }
    private TreeNode buildTreePost(int [] inorder, int[] postorder, int start, int end) {
        if (start > end)
            return null;
        postSt--;
        if (start == end) {
            return new TreeNode(postorder[postSt]);
        }
        int mid = inorderMap.get(postorder[postSt]);
        TreeNode node = new TreeNode(postorder[postSt]);
        node.right = buildTreePost(inorder, postorder, mid+1, end);
        node.left = buildTreePost(inorder,postorder, start, mid -1);
        return node;

    }
    // LeetCode :: 107. Binary Tree Level Order Traversal II
    // The idea is to recursively call to add nodes per level.
    // We start with empty list at each level we increase the list size
    // Note : when some normal operation is asked to be done in reverse order we can make use of
    // adding to the start of a list
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> rList = new ArrayList<>();
        if (root == null)
            return rList;
        levelOrderBottomRec(rList, 0, root);
        return rList;
    }

    private void levelOrderBottomRec(List<List<Integer>> rList, int level, TreeNode node){
        if( node == null)
            return;
        // increase the list size for this level
        if (rList.size() <= level) {
            // when increasing list size we have add each list to index zero this is very
            // important to create the bottom up because if we add new list at zero,
            // the previous first list becomes the second list and so on. So even the we add
            // item in the list in level order they move down
            rList.add(0, new ArrayList<>());

        }
        levelOrderBottomRec(rList, level + 1, node.left);
        levelOrderBottomRec(rList, level + 1, node.right);
        // add the node to the list in revrese order so for level 2
        // if list size is 3 the node wiil be added to list 0
        List<Integer> ls = rList.get(rList.size() - 1 - level);
        ls.add(node.val);


    }
    // This is the iterative approach that uses a single queue,
    // first we start with a queue size 1 adding root to it, Then we get the current queue size and remove exactly
    // that number of node from the queue and add their children to the queue , next iteration use the new queue
    // size to get the new parent
    public List<List<Integer>> levelOrderBottomIter(TreeNode root) {

        List<List<Integer>> list = new LinkedList<List<Integer>>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if(root==null)return list;
        queue.add(root);
        while(queue.size()>0){

            int size = queue.size();
            List<Integer> nlist = new LinkedList<Integer>();
            for(int i=0;i<size;i++){
                TreeNode curr = queue.poll();
                if(curr.left!=null)queue.add(curr.left);
                if(curr.right!=null)queue.add(curr.right);
                nlist.add(curr.val);
            }
            // adding to the head of the list so that it works like stack
            // Note : When we add & get items from list item 0 it becomes a stack
            list.add(0,nlist);
        }
        return list;
    }
    // LeetCode :: 110. Balanced Binary Tree
    // Check the version 2 thats better this one visit node unnecessarily
    private  int getHeight (TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null)
            return true;
        int leftH = getHeight(root.left);
        int rightH = getHeight(root.right);
        return Math.abs(rightH - leftH) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }
    // This is a better solution. The idea is to get the height of the left & right subtree
    // if they differ by more than 1 we fail. We  use the getHeighV2 function to not only calc the heigh
    // but check the diff of left & right subtree height
    public boolean isBalancedV2(TreeNode root) {
        if(getHeighV2(root) == -1)
            return false;
        else
            return true;
    }
    // Check the height of the left & right subtree recursively,
    // if at any  point the left & right subtree's height differ by more than 1 we return & propagate -1;
    // Note: at each node we can make some decision based on its left & right subtree, as the we have
    // recursively solved for left & right subtree and as the whole tree needs to hold the same property.
    // this idea can be leveraged in other problems
    private  int getHeighV2 (TreeNode node) {
        // null node no height
        if (node == null) return 0;
        // get left sub tree height
        int left = getHeighV2(node.left);
        // if the left subtree's children are not balanced ie height differ by more
        // than 1 return & propagate-1 we dont need to check any further
        if (left == -1) return -1;
        // same as left subtree
        int right = getHeighV2(node.right);
        if (right == -1) return -1;
        // height differ by more than 1 return -1
        if (Math.abs(right - left) > 1)
            return -1;
        else
            return 1 +  Math.max(left,right);
    }
    // LeetCode :: 111. Minimum Depth of Binary Tree
    // Calc the minimum depth from the root to leaf so the min depth will not be zero
    // special handling requires if only one child is null, then we need to consider only the non-null child
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        if (left == 0)
            return 1 + right;
        if (right == 0)
            return 1 + left;
        return 1 + Math.min(left,right);
    }
    // LeetCode :: 112. Path Sum
    // The idea is to check if there is path fom root to leaf recursively
    public boolean hasPathSum(TreeNode root, int sum) {
        // we reached the end but did not sum that matches root--> leaf
        if (root == null)
            return false;
        // check if this target value has reached & this is a leaf node
        if (( sum - root.val == 0) && root.left == null && root.right == null)
            return true;
        // check for the sum path in left & right sub tree
        // only need to find once such path hence we used an OR condition
        return hasPathSum(root.left, sum - root.val) ||
                hasPathSum(root.right, sum - root.val);
    }
    // LeetCode :: 113. Path Sum II
    public List<List<Integer>> pathSum (TreeNode root, int sum) {
        List<List<Integer>> rlist = new ArrayList<>();
        pathSumHelper(root, sum, rlist, new ArrayList<>());
        return rlist;
    }
    private void pathSumHelper (TreeNode root, int sum,
                                List<List<Integer>> rlist,
                                ArrayList<Integer> path) {
        if (root == null)
            return;
        // check if this target value has reached & this is a leaf node
        path.add(root.val);
        if (( sum - root.val == 0) && root.left == null && root.right == null) {
            rlist.add(new ArrayList<>(path));
        }

        pathSumHelper(root.left, sum - root.val, rlist, path);
        pathSumHelper(root.right, sum - root.val, rlist, path);
        path.remove(path.size() -1);
    }

    // LeetCode :: 114. Flatten Binary Tree to Linked List
    // Check the V2 it avoids the unnecessary while loop in the recursive function
    // The idea is to traverse the tree in revrese post order (right subtree , left subtree , node)
    // for each node after traversing the right subtree we store it & traverse the left subtree & store it
    // now if there is no left subtree, this node's right child can directly point to right subtree,
    // otherwise we find the last node in left subtree make its right pointer point to right subtree
    // finally  make the left subtree its right child
    // Note:: It DOES NOT matter if you flatten the right tree first or the left tree
    public TreeNode flattenRec (TreeNode node){
        if (node == null)
            return null;
        // get the flattened right & left subtree
        TreeNode rstree = flattenRec(node.right);
        TreeNode lstree = flattenRec(node.left);
        // if left side is null then make the right side point to the right subtree
        // we can remove the if condition check the version 2
        if (lstree == null) {
            node.right = rstree;
        } else {
            // left subtree is not empty find the right most child in left subtree
            TreeNode walk = lstree;
            while (walk.right != null)
                walk = walk.right;
            // right most child of left subtree points right subtree
            walk.right = rstree;
            // right child point to left subtree
            node.right = lstree;
            // make left node null
            node.left = null;

        }
        return node;
    }

    // The idea is similar to the approach above but here we avoid the while loop by just returning the tail node
    // instead of the current node. we get both left tail & right tail node  & if the right tail is not null we return
    // the right tail. Returning tail works because consider the smallest example a node has both right & left child now
    // after flatening we will return the right node which will be the tail node. Try this example by hand if you
    // are confused.
    public TreeNode flattenRecV2 (TreeNode node){
         if(node == null || (node.left == null && node.right == null)) {
             return node;
         }
         TreeNode leftTail = flattenRecV2(node.left);
         TreeNode rightTail = flattenRecV2(node.right);
         if (leftTail != null) {
             leftTail.right = node.right;
             node.right = node.left;
             node.left = null;
         }
         return rightTail != null ? rightTail: leftTail;
    }
    public void flatten(TreeNode root) {
        flattenRecV2(root);
    }

    // LeetCode :: 116. Populating Next Right Pointers in Each Node
    // This one use level order check the next one its way better
    public Node connectV2(Node root) {
        if (root == null)
            return root;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size()>0) {
            int size = queue.size();
            Node lastNode = null;
            for (int i = 0 ; i < size; i++){
                Node node = queue.poll();
                if (lastNode != null)
                    node.next = lastNode;
                lastNode = node;
                if (node.right != null)
                    queue.add(node.right);
                if (node.left != null)
                    queue.add(node.left);
            }
        }
        return root;
    }
    // We are given the next pointer so lets make use of that next pointer. The prev level will connect the siblings
    // left to right. so when we process the current level its siblings are connected or it is root of tree. So we can
    // use the idea of connected siblings and connect the next level
    public Node connect(Node root) {
        Node node = root;
        if (root == null)
            return root;
        while (node != null && node.left != null) {
            Node cur = node;
            while (cur!= null) {
                // make the left child point to the right child
                cur.left.next = cur.right;
                // let the right child point to the left child of its sibling using next pointer
                if (cur.next != null)
                    cur.right.next = cur.next.left;

                cur = cur.next;
            }
            node = node.left;
        }
        return root;
    }

    // Leetcode :: 117. Populating Next Right Pointers in Each Node II
    public Node connect2(Node root) {
        Node node = root;
        if (root == null)
            return root;
        while (node != null &&
                (node.left != null ||
                 node.right != null ||
                 node.next != null)) {
            Node cur = node;
            while (cur!= null) {
                // make the left child point to the right child
                Node moveRight = cur.next;
                while (moveRight != null &&
                        moveRight.left == null && moveRight.right == null)
                    moveRight = moveRight.next;
                if (cur.left != null) {
                    if (cur.right != null)
                        cur.left.next = cur.right;
                    else if (moveRight != null && moveRight.left != null)
                        cur.left.next = moveRight.left;
                    else if (moveRight != null && moveRight.right != null)
                        cur.left.next = moveRight.right;

                }
                if (cur.right != null) {
                    if (moveRight != null && moveRight.left != null)
                        cur.right.next = moveRight.left;
                    else if (moveRight != null && moveRight.right != null)
                        cur.right.next = moveRight.right;

                }
                cur = moveRight;
            }
            if(node.left != null)
                node = node.left;
            else if (node.right != null)
                node = node.right;
            else
                node = node.next;
        }
        return root;
    }

    // LeetCode :: 124. Binary Tree Maximum Path Sum (Hard)
    // We have to calc the max path sum, and the path can include both left path and right path with its parent added.
    // So basically we go from down to up and then up to down to see find the max path for a subtree, then we only
    // return the max of left subtree's or right sub tree's max path + the current node's val so whe we return we on
    // return a path from this node to left subtree or right subtree. But to get total max path we need to save the
    // path in global variable so that if we encounter a max path in some subtree its saved
    private int maxTreePathSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxTreePathSum = Integer.MIN_VALUE;
        maxPathSumRec(root);
        return maxTreePathSum;
    }

    private int maxPathSumRec(TreeNode node) {
        if (node == null)
            return 0;
        // get the maxPath from left subtree if the path is negative we dont consider the path, so assign zero
        int leftMax = Math.max(0, maxPathSumRec(node.left));
        // get the maxPath from right subtree if the path is negative we dont consider the path, so assign zero
        int rightMax = Math.max(0, maxPathSumRec(node.right));
        // store the current maxPath into the global maxPath if its greater than global maxPath, Note that
        // here we need to use both left & right subtree + node.val cause a path can be like this
        maxTreePathSum = Math.max(maxTreePathSum, leftMax + rightMax + node.val);
        // The trick is to return only the left or right child + node.val so that a single path is return
        // and  not a forked path cause if we return both left & right child + node it will contain a forked path
        return Math.max(leftMax,rightMax) + node.val;
    }
    // LeetCode :: 235. Lowest Common Ancestor of a Binary Search Tree
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;
        if (p == null) return q;
        if (q == null) return p;
        if( p.val <= root.val && q.val <= root.val) {
            return lowestCommonAncestorBST(root.left, p, q);
        } else if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestorBST(root.right, p, q);
        } else
            return root;
    }
    // This is the iterative approach to find a lowest ancestor in BST
    // if both p & q are on left go left if both p & q are on right go right
    public TreeNode lowestCommonAncestorBSTV2(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node = root;
        while (node != null) {
            if (p == node || q == node)
                break;
            if (p.val <= node.val && q.val <= node.val)
                node = node.left;
            else if (p.val > node.val && q.val > node.val)
                node = node.right;
            else
                break;
        }
        return node;
    }

    // LeetCode :: 236. Lowest Common Ancestor of a Binary Tree
    // We need to find the lowest common ancestor of two node in a btree.
    // The idea is not to look for one node in lefts subtree and another in right subtree rather
    // we need to search both node in left & right subtree if both appears on left side & right side
    // it means one of them is in left & one of them in right subtree we dont need to know which one is
    // in left and which one is in right and this node is the LCA so save it in global variable. If both
    // are in left tree OR right tree we recurse to find an ancestor for which search says both are in
    // left AND right subtree
    private TreeNode savedLCA = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q )
            return root;
        if (p == null) return q;
        if (q == null) return p;
        lcaRec(root,p,q);
        return savedLCA;
    }

    public boolean lcaRec(TreeNode node, TreeNode p, TreeNode q) {
        if(node == null)
            return false;
        // search both in left side if we found one or both in left lets mark it true
        int left = lcaRec(node.left, p,q) ? 1:0;
        // search both in left side if we found one or both in right lets mark it true
        int right = lcaRec(node.right, p,q) ? 1:0;
        // one of the node or both node found lets mark mid to 1
        int mid = (node == p) || (node == q) ? 1:0;
        // if this is >= 2 it means either p & q are on left & right
        // side this node or this node is either p or q and the left
        // or right subtree of this node has the other node hence this
        // node is the LCA save it in global array
        if (left + right + mid >= 2)
            savedLCA = node;
        // return true if this node is equal to p or q or one of
        // its subtree have seen p or q
        return (left + right + mid) > 0;
    }

    // LeetCode :: 230. Kth Smallest Element in a BST
    // The idea is to ise the inorder traverse property of a BST as inorder traversal will give sorted
    // output for a BST so is we use iterative inorder traversal for a BST the kth item will be the result
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.empty() || node !=null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                k--;
                if (k == 0)
                    break;
                node = node.right;
            }
        }
        return node.val;
    }

    //LeetCode :: 109. Convert Sorted List to Binary Search Tree
    // The idea is to find the mid node of the list and create a TreeNode from that then recursively create
    // the left subtree by using the node from start to mid node & create the right subtree by using the node
    // from mid.next node to end node
    // Remember in this case we consider the end node to exclusive & start node to be inclusive so (start,end]
    // check the v2
    public TreeNode sortedListToBST(LinkList head) {
        TreeNode root = null;
        if (head == null)
            return root;
        // end node is exclusive
        root = sortedListToBSTRec(head, null);
        return root;
    }
    // create the current node & recursively create the left & right subtree
    private TreeNode sortedListToBSTRec(LinkList start, LinkList end) {
        if (start == end)
            return null;
        if (start.next == end) {
            TreeNode node = new TreeNode(start.data);
            return node;
        }
        LinkList slow = start;
        LinkList fast = start.next;
        // find the mid node using slow & fast pointers
        while (fast!= end && fast.next != end) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // slow points to the mid node of the current linked list create the Treenode using that
        TreeNode node = new TreeNode(slow.data);
        // create the left subtree using list frome the left of mid (slow)
        node.left = sortedListToBSTRec(start,slow);
        // create the right subtree using the  list from right of mid
        node.right = sortedListToBSTRec(slow.next, end);
        return node;
    }
    // This uses the same idea when you are given an inorder array and asked to create a balances BST
    // The idea is to build the tree inorder while traversing the list in ascending order. We need a global head pointer.
    // we move the head along the list and only move it when a valid node can be produced. for example low > high
    // would be an invalid case.
    // The first node in the inorder list will create the left most node in BST, that's why we need to build the using
    // inorder approach. We keep track of the low & high index in the list for each subtree. Everytime we create a valid
    // node it has to be withing the boundary of (low, high). As we start with (0 , size of list) range, on every step
    // the range is split to (low, mid -1) & (mid + 1, high) this range allows the node to be created in proper order (inorder).
    // Note:: we can also build a perfect BST same way if we are given a array of inorder traversal. It be same approach
    // only no need to manage a linkedlist but an array, so just neeed a global index
    LinkList inNode = null;
    public TreeNode sortedListToBSTV2(LinkList head) {
        inNode = head;
        int sz = 0;
        while(head != null) {
            head = head.next;
            sz++;
        }
        return sortedListToBSTRecV2(0, sz - 1);
    }
    private TreeNode sortedListToBSTRecV2(int low, int high) {
        if (inNode == null || low > high)
            return null;
        int mid = (low + high)/2;
        TreeNode lstree = sortedListToBSTRecV2(low, mid -1);
        TreeNode node = new TreeNode(inNode.val);
        node.left = lstree;
        inNode = inNode.next;
        node.right = sortedListToBSTRecV2(mid+1, high);
        return node;
    }

    // LeetCode :: 129. Sum Root to Leaf Numbers
    // The idea is to do a pre-order traversal to get calc the root to leaf path digit
    // we use a global var to update the sum of all path when we reach the leaf node
    private int totTreePathSum;
    public int sumNumbers(TreeNode root) {
        if (root == null)
            return 0;

        sumNumberRec(root, 0);
        return totTreePathSum;
    }

    private void sumNumberRec(TreeNode node, int sum) {
        // node need to process null nodes
        if (node != null) {
            // leaf node update the global variable sum of all path
            if (node.left == null && node.right == null) {
                totTreePathSum+= sum + node.val;
                return;
            }
            // calc the digit at this node & pre-order traverse the whole tree
            sum = (sum + node.val) * 10;
            sumNumberRec(node.left, sum);
            sumNumberRec(node.right, sum);
        }
    }

    // Adnan Aziz 10.11 inorder in O(1) space given parent pointer
    public List<Integer> inOrderTraversalWithParent(TreeNode root) {
        TreeNode curr = root;
        TreeNode parent = null;
        TreeNode prev = null;
        TreeNode next = null;
        List <Integer> traverseList = new ArrayList<>();
        while (curr != null) {
            if(prev == curr.parent) {
                if (curr.left != null){
                    next = curr.left;
                } else {
                    traverseList.add(curr.data);
                    if(curr.right == null)
                        next = curr.parent;
                    else
                        next = curr.right;
                }
            } else if (prev == curr.left) {
                traverseList.add(curr.data);
                if(curr.right == null)
                    next = curr.parent;
                else
                    next = curr.right;
            } else {
                curr = curr.parent;
            }
            prev = curr;
            curr = next;

        }
        return traverseList;

    }

    // Adnan Aziz Find next node greater than the given a pointer to a node in BST
    private TreeNode successorNodeRec(int val, TreeNode node, TreeNode leftAncestor) {
        if (node == null)
            return node;
        if (val == node.val) {
            return leftAncestor;
        } else if (val < node.val) {
            leftAncestor = node;
            return successorNodeRec(val,node.left,leftAncestor);
        } else{
            return successorNodeRec(val, node.right, leftAncestor);
        }
    }
    // Check V2 for iterative check v3 for iterative & concise
    public TreeNode successorNode (TreeNode node, TreeNode root) {
        // if the righ subtree is not empty then the lowest node on the right subtree is our result
        if (node.right != null) {
            TreeNode rightChild = node.right;
            while (rightChild.left != null)
                rightChild = rightChild.left;
            return rightChild;
        }
        // right subtree is empty we need to find the successor in its ancestor.
        // The lowest ancestor for which this node exist in left subtree is the node we want
        TreeNode ancestor = successorNodeRec(node.val, root, null);
        return ancestor;
    }

    public TreeNode successorNodeV2 (TreeNode node, TreeNode root) {
        if (root == null || node == null)
            return null;
        TreeNode curr = root;
        TreeNode leftAncestor = null;
        while (curr != null && curr.val != node.val) {
            if(node.val < curr.val) {
                leftAncestor = curr;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        if (curr == null)
            return null;
        if (curr.right ==null)
            return leftAncestor;
        curr = curr.right;
        while (curr.left!= null)
            curr = curr.left;

        return curr;
    }
    // This is more concise all of them have same run time O(h)
    public TreeNode successorNodeV3 (TreeNode node, TreeNode root) {
        if (root == null || node == null)
            return null;
        TreeNode curr = root;
        TreeNode leftAncestor = null;
        while (curr != null) {
            if(node.val < curr.val) {
                leftAncestor = curr;
                curr= curr.left;
            } else {
                curr = curr.right;
            }
        }
        return leftAncestor;
    }

    public TreeNode predecessorNodeV2(TreeNode node, TreeNode root){
        if (root == null || node == null)
            return null;
        TreeNode curr = root;
        TreeNode rightAncestor = null;
        while (curr != null && curr.val != node.val) {
            if (node.val > curr.val) {
                rightAncestor = curr;
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return rightAncestor;
    }

    private int rootIdx = 0;
    // The idea is to create the tree in preorder while maintaining a range boundary
    // we can start with a root node and (-INF to +INF) boundary and as we move lef or right we shrink the boundary
    private TreeNode buildBSTFromPreOrderRec(int []preorder, int lower , int upper) {
        if(rootIdx >= preorder.length)
            return null;
        int rootVal = preorder[rootIdx];
        // we should not process this entry as child of the current recursion as it violates the boundary
        // it also means value belongs to some node in the right
        if (rootVal < lower || rootVal > upper)
            return null;
        // now increase the pointer to preorder sequence, we should not increase it earlier as
        // we dont want to process any value beyond the lower/upper bound
        rootIdx++;
        TreeNode root = new TreeNode(rootVal);
        root.left = buildBSTFromPreOrderRec(preorder, lower, rootVal);
        root.right = buildBSTFromPreOrderRec(preorder, rootVal,upper);
        return root;
    }
    // Adnan Aziz BST
    // build the BST tree from PreOrder Traversal
    public TreeNode buildBSTFromPreOrder( int []preorder){
        return buildBSTFromPreOrderRec(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);

    }
    // build the BST tree from PostOrder Traversal
    private TreeNode buildBSTFromPostOrderRec(int []postOrder, int lower , int upper) {
        if(rootIdx >= postOrder.length)
            return null;
        int rootVal = postOrder[rootIdx];
        if (rootVal < lower || rootVal > upper)
            return null;
        // now increase the pointer to preorder sequence, we should not increase it earlier as
        // we dont want to process any value beyond the loewr/upper bound
        rootIdx++;
        TreeNode root = new TreeNode(rootVal);
        // we are processing reversed post order traversal hence process right node before left node
        root.right = buildBSTFromPostOrderRec(postOrder, rootVal,upper);
        root.left = buildBSTFromPostOrderRec(postOrder, lower, rootVal);
        return root;
    }
    public TreeNode buildBSTFromPostOrder( int []postOrder){
        int left = 0;
        int right = postOrder.length -1;
        // reverse the traversal to handle it easily from left to right
        while (left <right) {
            int temp = postOrder[left];
            postOrder[left] = postOrder[right];
            postOrder[right] = temp;
            left++;
            right--;
        }
        // recusively resolve the reversed traversal to build the tree
        return buildBSTFromPostOrderRec(postOrder, Integer.MIN_VALUE, Integer.MAX_VALUE);

    }

    // LeetCode :: 199. Binary Tree Right Side View
    // The idea is to find the right nodes in levelorder Traversal
    public List<Integer> rightSideViewV2(TreeNode root) {
        List<Integer> resList = new ArrayList<>();
        if (root == null)
            return resList;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (i == 0)
                    resList.add(node.val);
                if (node.right != null)
                    queue.add(node.right);
                if (node.left != null)
                    queue.add(node.left);
            }
        }
        return resList;
    }
    // The idea is to traverse the node in root right left  and add the first rightmost child
    public void rightSideViewRec (TreeNode node, int height, List<Integer> nodeList) {
        if (node == null)
            return;
        if (height == nodeList.size()) {
            nodeList.add(node.val);
        }
        rightSideViewRec(node.right, height +1, nodeList);
        rightSideViewRec(node.left, height +1, nodeList);

    }
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> nodeList = new ArrayList<>();
        if (root == null)
            return nodeList;
        rightSideViewRec(root, 0, nodeList);
        return nodeList;
    }

    // LeetCode :: 222. Count Complete Tree Nodes
    // This is a very interesting solution because its a complete binary tree we can discard visiting
    // half the node on each iteration.
    // if the height of the tree  is h, then if h-1 is equal to height of the
    // right subtree then left sub tree must be a full tree so the count of node for left subtree
    // will be 2^h - 1 so we can discard visiting the left subtree. so the left subtree + root node count will be
    // 2^h -1 + 1 = 2^h = 1<<h
    // if the height of root h -1 != height o right subtree it means the left subtree is not full so we traverse the
    // left subtree discarding th right subtree because the right sub tree is full with
    // 2^(h -1) -1 +1(for root) == 2^(h -1)nodes
    private int getCompleteBSTheight (TreeNode root){
        if (root == null)
            return -1;
        int height = -1;
        while(root !=null) {
            root = root.left;
            height++;
        }
        return height;
    }

    public int countNodes(TreeNode root) {
        int height = getCompleteBSTheight(root);
        if (height < 0)
            return 0;
        // left sub tree is full skip the left subtree processing
        if(height -1 == getCompleteBSTheight(root.right)) {
            return (1 << height) + countNodes(root.right);
        } else // right subtree is full slip the right sub tree processing
            return  (1 << (height -1)) + countNodes(root.left);

    }

    //leetCode :: 938 Range Sum of BST
    // Check the Version 3 version 1 & 2 are slow cause O(nodes) runtime
    // Version 3  uses the BST property and gets O(nodes) runtime in worst case when the range is huge
    // but in average case the runtime should be O(log nodes = height of tree) because of using bst property
    int rangeSum= 0;
    private void rangeSumBSTRec (TreeNode root, int L, int R) {
        if (root == null)
            return;
        if (L <= root.val && root.val <= R)
            rangeSum += root.val;
        rangeSumBSTRec(root.right, L, R);
        rangeSumBSTRec(root.left, L, R);
    }

    public int rangeSumBSTV2(TreeNode root, int L, int R) {
        rangeSumBSTRec(root, L,R);
        return rangeSum;
    }

    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null)
            return 0;
        int left = rangeSumBST(root.left, L, R);
        int right = rangeSumBST(root.right, L, R);
        int rootVal = left + right;
        if(L <= root.val && root.val <= R)
            rootVal+= root.val;
        return rootVal;
    }
    public int rangeSumBST3(TreeNode root, int L, int R) {
        if(root == null)
            return 0;

        if (root.val > R) {
            return rangeSumBST3(root.left, L, R);
        } else if(root.val < L) {
            return rangeSumBST3(root.right, L, R);
        } else {
            return root.val +
                    rangeSumBST3(root.left, L, root.val -1) +
                    rangeSumBST3(root.right, root.val +1, R);
        }

    }

    // LeetCode :: 297. Serialize and Deserialize Binary Tree
    // PreOrder traversal of the tree node and add X for null to ecnode the string
    // Encodes a tree to a single string.
    private void serializeRec(TreeNode root, StringBuilder sb) {
        // add X for null
        if(root == null) {
            sb.append("X,");
            return;
        }
        // add tree node
        sb.append(root.val);
        sb.append(",");
        serializeRec(root.left, sb);
        serializeRec(root.right, sb);
    }

    public String serialize(TreeNode root) {

        StringBuilder sb = new StringBuilder();
        serializeRec(root, sb);
        return sb.toString();

    }

    // Decodes your encoded data to tree.
    private int desIndex = 0;
    private TreeNode deserializeRec (List<String> alist) {
        if (desIndex >= alist.size())
            return null;
        if (alist.get(desIndex).compareTo("X") == 0) {
            desIndex++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(alist.get(desIndex)));
        desIndex++;
        node.left = deserializeRec(alist);
        node.right = deserializeRec(alist);
        return node;
    }
    // node decode the  preorder traveresal with null presented as X
    public TreeNode deserialize(String data) {
        List<String> nodeList = new ArrayList<>();
        // create an arraylist from the string
        nodeList.addAll(Arrays.asList(data.substring(0,data.length()-1).split(",")));
        System.out.println(nodeList);
        TreeNode root =  deserializeRec(nodeList);
        return root;
    }

    // LeetCode :: 865. Smallest Subtree with all the Deepest Nodes
    private HashMap<TreeNode, Integer> mapTreeNode = new HashMap<>();
    // Get Height using memoization
    private int getHeightOfNode(TreeNode node){
        if (node == null)
            return 0;
        if (mapTreeNode.containsKey(node)){
            return mapTreeNode.get(node);
        }
        int left = getHeightOfNode(node.left);
        int right = getHeightOfNode(node.right);
        int height = 1 + Math.max(left, right);
        mapTreeNode.put(node, height);
        return height;
    }
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        if(root == null)
            return null;
        int left = getHeightOfNode(root.left);
        int right = getHeightOfNode(root.right);
        if (left == right)
            return root;
        else if (left > right)
            return subtreeWithAllDeepest(root.left);
        else
            return subtreeWithAllDeepest(root.right);
    }
    public class NodeDeptth {
        int depth;
        TreeNode node;
        public NodeDeptth(int h, TreeNode n){
            depth = h;
            node = n;
        }
    }

    // here note how we depend on the height to get the result node in such scenario
    // we can use the a new object to hold the height and the node and return both as a object
    public TreeNode subtreeWithAllDeepestV2(TreeNode root) {
        return subtreeWithAllDeepestRecV2(root).node;
    }
    public NodeDeptth subtreeWithAllDeepestRecV2(TreeNode root){
        if(root == null)
            return new NodeDeptth(0,null);

        NodeDeptth leftTree = subtreeWithAllDeepestRecV2(root.left);
        NodeDeptth rightTree = subtreeWithAllDeepestRecV2(root.right);
        if (leftTree.depth > rightTree.depth)
            return new NodeDeptth(leftTree.depth + 1, leftTree.node);
        else if (leftTree.depth < rightTree.depth)
            return new NodeDeptth(rightTree.depth + 1, rightTree.node);
        else
            return new NodeDeptth(leftTree.depth + 1, root);
    }




    // LeetCode :: 257. Binary Tree Paths
    private void binaryTreePathsRec(TreeNode root, List<String> pathList, StringBuilder sb) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            int idx = sb.toString().length();
            sb.append(root.val);
            pathList.add(sb.toString());
            sb.delete(idx,sb.length());
            return;
        }
        int idx = sb.toString().length();
        sb.append(root.val);
        sb.append("->");
        binaryTreePathsRec(root.left, pathList ,sb);
        binaryTreePathsRec(root.right, pathList ,sb);
        sb.delete(idx,sb.length());
    }
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> pathList = new ArrayList<>();
        binaryTreePathsRec(root, pathList, new StringBuilder());
        return pathList;
    }
    // Iterative approach PreOrder/DFS traversal
    public List<String> binaryTreePathsV2(TreeNode root) {
        List<String> pathList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        Stack<String> pathStack = new Stack<>();
        TreeNode node = root;
        stack.push(node);
        pathStack.push(Integer.toString(node.val));
        while (!stack.empty()){
            node = stack.pop();
            String path = pathStack.pop();
            if (node.left == null && node.right == null) {
                pathList.add(path);
            }
            if (node.right != null) {
                stack.push(node.right);
                pathStack.push(path+"->"+node.right.val);
            }
            if (node.left != null) {
                stack.push(node.left);
                pathStack.push(path+"->"+node.left.val);
            }
        }

        return pathList;
    }
    // iterative BFS approachy
    public List<String> binaryTreePathsV3(TreeNode root) {
        List<String> pathList = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        Queue<String> pathQueue = new LinkedList<>();
        if (root == null)
            return pathList;
        q.add(root);
        pathQueue.add(Integer.toString(root.val));
        while (!q.isEmpty()){
            TreeNode node = q.remove();
            String path = pathQueue.remove();
            if (node.left == null && node.right == null) {
                pathList.add(path);
            }
            if (node.left != null) {
                q.add(node.left);
                pathQueue.add(path+"->"+node.left.val);
            }
            if (node.right != null) {
                q.add(node.right);
                pathQueue.add(path+"->"+node.right.val);
            }
        }

        return pathList;
    }

    // Problem Def: Find the closest element in Binary Search Tree
    // Given a binary search tree and a target node K.
    // The task is to find the node with minimum absolute difference with given target value K.
    // The idea is to maintain a closest value as we search for the element in BST if the value is found
    // that should be the result and closestDiff would smallest == 0. If the value is not in the bst
    // we still find the closest by doing a binary search for the value and on bst path update the closest
    private int closetDiff;
    private int closestNode;
    private void findClosestInBSTRec (TreeNode root, int target) {
        if (root == null)
            return;
        int diff  = target - root.val;
        if(closetDiff > Math.abs(diff)) {
            closetDiff = Math.abs(diff);
            closestNode = root.val;
        }
        if (diff == 0)
            return;
        else if (diff < 0)
            findClosestInBSTRec(root.left, target);
        else
            findClosestInBSTRec(root.right, target);
    }
    public int findClosesInBST (TreeNode root, int target) {
        closetDiff = Integer.MAX_VALUE;
        closestNode = Integer.MAX_VALUE;
        findClosestInBSTRec(root, target);
        return closestNode;
    }

    // LeetCode 314 :: Given a binary tree, print it vertically. left to right
    // The idea is to hash the nodes based on their vertical distance from the root. Instead of actual hashmap
    // we are using a LIst to use asa Hash, as the keys are basically indexes from (-x to +x), we just need to
    // translate it to (0 to x) this is done by getting the horizontal min/max using the function minMaxVertical
    // After we traverse the Tree in Preorder and add node to the Hash (implemented using List index)
    // We have to maintain a special order on the Level if level of a node on the sam vertcial line is samller it
    // should appear before higher level, so we use the levelList which is a linkedList of level and based on the
    // levelList we insert our values accordingly in the correct position in result list

    private int minHz;
    private int maxHz;
    private void minMaxVertical(TreeNode node, int dist) {
        if (node == null)
            return;
        if (dist > maxHz)
            maxHz = dist;
        if (dist < minHz)
            minHz = dist;
        minMaxVertical(node.left, dist -1);
        minMaxVertical(node.right, dist +1);

    }

    private void verticalTraverseL2R (TreeNode node, List<List<Integer>> resList, int dist, int level, List<List<Integer>> levelList) {
        if (node == null)
            return;
        int idx = dist + Math.abs(minHz);
        int l = 0;
        // insert in proper order based on the level, note if they are on the same level the
        // we just ignore and increase the l so its inserted after. This works for same because
        // we are using a preorder traversal so left node will be visited before right nodes.
        // So we increase l and right node goes after left node
        while (l < levelList.get(idx).size() && level >= levelList.get(idx).get(l))
            l++;
        levelList.get(idx).add(l,level);
        resList.get(idx).add(l,node.val);
        verticalTraverseL2R(node.left, resList, dist -1, level +1, levelList);
        verticalTraverseL2R(node.right, resList, dist + 1, level + 1, levelList);

    }

    public List<List<Integer>> verticalTraversalLeftToRight(TreeNode root) {
        List<List<Integer>> resList = new ArrayList<>();
        List<List<Integer>> levelList = new ArrayList<>();
        if (root == null)
            return resList;
        minHz = Integer.MAX_VALUE;
        maxHz = Integer.MIN_VALUE;
        minMaxVertical(root, 0);
        int totVertical = maxHz - minHz +1;
        for (int i =0 ; i < totVertical;i++) {
            // we are using a linked list her for faster insertion during recursion
            resList.add(new LinkedList<>());
            levelList.add(new LinkedList<>());
        }
        verticalTraverseL2R(root, resList,0, 0, levelList);
        return resList;
    }

    // Trying it with level order traversal
    public class VerticalNode {
        TreeNode node;
        int pos;
        public VerticalNode(TreeNode n, int p) {
            node = n;
            pos = p;
        }
    }

    public List<List<Integer>> verticalOrder(TreeNode root) {
        // write your code here
        List<List<Integer>> rlist = new ArrayList<>();

        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        Queue<VerticalNode> q = new LinkedList<>();
        if (root == null)
            return rlist;
        q.add(new VerticalNode(root, 0));
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        while(!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i <size; i++) {
                VerticalNode nd = q.remove();
                ArrayList<Integer> tList = map.getOrDefault(nd.pos, new ArrayList<>());
                tList.add(nd.node.val);
                map.put(nd.pos, tList);
                min = Math.min(min, nd.pos);
                max = Math.max(max, nd.pos);
                if (nd.node.left != null)
                    q.add(new VerticalNode( nd.node.left, nd.pos -1));
                if (nd.node.right != null) {
                    q.add(new VerticalNode(nd.node.right, nd.pos +1));
                }

            }
        }
        List<Integer> [] list = new List[max - min +1];
        for (Integer pos : map.keySet()) {
            list[pos + Math.abs(min)] = map.get(pos);
        }
        rlist.addAll(Arrays.asList(list));
        return rlist;
    }

    // LeetCode :: 987. Vertical Order Traversal of a Binary Tree (Hard)
    // The idea is to hash the nodes based on their vertical distance from the root. Instead of actual hashmap
    // we are using a LIst to use asa Hash, as the keys are basically indexes from (-x to +x), we just need to
    // translate it to (0 to x) this is done by getting the horizontal min/max using the function minMaxVertical
    // After we traverse the Tree in Preorder and add node to the Hash (implemented using List index)
    // We have to maintain a special order on the Level if level of a node on the sam vertcial line is samller it
    // should appear before higher level, so we use the levelList which is a linkedList of level and based on the
    // levelList we insert our values accordingly in the correct position in result list
    // If nodes are same level the samller node appears  before bigger node value
    private void verticalTraverse (TreeNode node, List<List<Integer>> resList, int dist, int level, List<List<Integer>> levelList ) {
        if (node == null)
            return;
        int idx = dist + Math.abs(minHz);
        int l = 0;
        // We have to maintain a special order on the Level if level of a node on the same vertical line is smaller it
        // should appear before higher level, so we use the levelList which is a linkedList of level insert the node
        // at current level in appropiate level position
        while (l < levelList.get(idx).size() && level >= levelList.get(idx).get(l)) {
            int lc = levelList.get(idx).get(l);
            // If nodes are same level the samller node appears  before bigger node value
            if (lc == level && node.val  < resList.get(idx).get(l))
                break;
            l++;
        }
        // insert the the level & the result in proper position
        levelList.get(idx).add(l,level);
        resList.get(idx).add(l,node.val);
        verticalTraverse(node.left, resList, dist -1 ,level +1, levelList);
        verticalTraverse(node.right, resList, dist + 1, level +1, levelList);


    }
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        // we need to use arraylist here for faster access & for using the List as hash
        List<List<Integer>> resList = new ArrayList<>();
        List<List<Integer>> levelList = new ArrayList<>();
        if (root == null)
            return resList;
        minHz = Integer.MAX_VALUE;
        maxHz = Integer.MIN_VALUE;
        minMaxVertical(root, 0);
        int totVertical = maxHz - minHz +1;
        for (int i =0 ; i < totVertical;i++) {
            // the inner list are linkedlist as we want to insert in the middle so its faster to insert
            // as it will require less shifting cost as insert O(1);
            resList.add(new LinkedList<>());
            levelList.add(new LinkedList<>());
        }
        verticalTraverse(root, resList, 0 ,0, levelList);
        return resList;

    }

    // LeetCode :: 331. Verify Preorder Serialization of a Binary Tree
    // The idea is to do a preorder scanning of the preorder traversal so we start with the first entry in preorder
    // and recursively called the function twice to process left & right sub trees without actually creating the tree
    // when we encounter 'X'  we dont need to proceed any more with recursion. The non-leaf entries will call the
    // recursion enough number of times.
    int preOrIdx = 0;
    private boolean isValidSerializationRec (List<String> nodeList) {
        // if in any recusion we exceed the size it means  this a an invalid preoder cause the recursion should
        // end with a 'X' entries so if we reach here we know our preorder representation is invalid
        if(preOrIdx >= nodeList.size()) {
            return false;
        }
        // found x just increase preorder index we dont need to recurse any more, t
        if (nodeList.get(preOrIdx).equals("#")){
            preOrIdx++;
            return true;
        }
        preOrIdx++;
        return isValidSerializationRec(nodeList) &&
                    isValidSerializationRec(nodeList);

    }
    public boolean isValidSerialization(String preorder) {
        List<String> nodeList = new ArrayList<>();
        nodeList.addAll(Arrays.asList(preorder.split(",")));
        // we need to also make sure we covered the whole preoder sequence if the recusion ended
        // before traversing the whole sequence its not valid the condition below checks this
        return isValidSerializationRec(nodeList) && preOrIdx == nodeList.size();
    }
    // This is a really samrt way to solve this. It uses the in & out degrees of the nodes in the tree.
    // if we specify null node as valid nodes then total difference of out & in degree would be zero for
    // a valid tree. The null node has zero out degree & 1 in degree all other nodes (except root) has
    // 1 in & 2 out degree. So we scan the preoder fro each node we decrease diff by 1 for indegree &
    // if node is not X then we increase out degree by 2 at the end fora valid tree we have diff == 0
    // if any time during our scan diff is negative that violates the condition for a valid preorder
    // sequence
    public boolean isValidSerializationV2(String preorder){
        String []nodes = preorder.split(",");
        // we start diff = 1
        int diff = 1;
        for (String n: nodes) {
            diff--;
            // diff is negative so the preorder is invalid
            if (diff < 0)
                return false;
            if(!n.equals("X"))
                diff+=2;
        }
        return diff == 0;
    }

    // LeetCode 226. Invert Binary Tree
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode tempL = invertTree(root.right);
        TreeNode tempR = invertTree(root.left);
        root.left =tempL;
        root.right = tempR;
        return root;
    }

    // Boundary Traversal Of a Binary Tree
    private void traverseLeft (TreeNode node, List<Integer> left) {
        if(node == null)
            return;

        if(node.left != null) {
            left.add(node.val);
            traverseLeft(node.left, left);
        }
        else if(node.right != null) {
            left.add(node.val);
            traverseLeft(node.right, left);
        }
    }
    private void traverseRight (TreeNode node, List<Integer> right) {
        if(node == null)
            return;

        if(node.right != null) {
            right.add(node.val);
            traverseLeft(node.right, right);
        }
        else if(node.left != null) {
            right.add(node.val);
            traverseLeft(node.left, right);
        }
    }

    public void leafNodes(TreeNode node, List<Integer> leaf) {
        if(node == null)
            return;
        leafNodes(node.left, leaf);
        if(node.right == null && node.left == null)
            leaf.add(node.val);
        leafNodes(node.right, leaf);
    }

    public List<Integer> boundaryTraversal (TreeNode root) {
        List<Integer> leafBoundary = new ArrayList<>();
        List<Integer> leftBoundary = new ArrayList<>();
        List<Integer> rightBoundary = new ArrayList<>();
        traverseLeft(root, leftBoundary);
        traverseRight(root, rightBoundary);
        leafNodes(root, leafBoundary);
        rightBoundary.remove(0);
        leftBoundary.addAll(leafBoundary);
        leftBoundary.addAll(rightBoundary);
        return leftBoundary;
    }

    // LeetCode :: 437. Path Sum III
    // The idea is same as the range sum of an array the sum of any i,j of an array can be defined as
    // sum(i,j) = running_sum[j] - running_sum[i-1].
    // We use the same idea of "LeetCode :: 325 Maximum Size Subarray Sum Equals k"
    // In this problem instead of an array we have a Tree and we need find the sum(i,j) in the tree going
    // top to bottom direction.
    // First we keep map to store the running sum and there counts  at any point if the runningSum - k is
    // found in the map we increase our path count
    private void pathSumHelperIII (TreeNode root, int k,
                                   HashMap<Integer, Integer> map, int runningSum) {
        // base case, mo need to process further
        if(root == null)
            return;
        // calc the current running sum on this path
        runningSum += root.val;
        // check if the runningSUm - k has been seen in the map if yes we have found our 'sum' which is 'k' here
        // in the current path so update the result
        if (map.containsKey(runningSum - k)) {
            pathSumCount+= map.get(runningSum -k);
        }
        // updathe current running sum count for this path
        map.put(runningSum, map.getOrDefault(runningSum, 0) + 1);
        // recurse on left & right sub tree
        pathSumHelperIII(root.left, k, map, runningSum);
        pathSumHelperIII(root.right, k, map, runningSum);
        // revese the current result cause we allow only top down path so we need to remove the runningSum count after
        // we have process the sub tree under this root node so that other node which are not a part of sub tree of
        // this node for example its sibling or other nodes does not include this runningSUm in their path
        // this idea is similar to backtracking, and we only care about path count hence making it zero works. But
        // otherwise we had to remove the entry from the map
        map.put(runningSum, map.get(runningSum) - 1);
    }
    int pathSumCount;
    public int pathSumIII(TreeNode root, int sum) {
        pathSumCount = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,1);
        // let recursively solve the sum count
        pathSumHelperIII(root, sum, map, 0);
        return pathSumCount;
    }

    // Morris Inorder Traversal O(1) space no parent pointers needed in a BST
    public TreeNode getPredNode(TreeNode node) {
        if (node.left == null)
            return null;
        TreeNode pred = node.left;
        while (pred.right != null && pred.right != node)
            pred = pred.right;
        return pred;
    }

    // The idea is to find the pred node of the a node that has left child and make th pred node's right pointer
    // point to the curr node so we have a way to go back to cur node when we visit pred node
    public List<Integer> morrisInorderTraversal(TreeNode root) {
        List <Integer> resList = new ArrayList<>();
        if (root == null)
            return resList;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                resList.add(cur.val);
                cur = cur.right;
            } else {
                TreeNode predecessor = getPredNode(cur);
                if (predecessor.right == null) {
                    predecessor.right = cur;
                    cur = cur.left;
                } else {
                    resList.add(cur.val);
                    predecessor.right = null;
                    cur = cur.right;
                }

            }
        }
        return resList;
    }

    public List<Integer> morrisPreorderTraversal(TreeNode root) {
        List <Integer> resList = new ArrayList<>();
        if (root == null)
            return resList;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) {
                resList.add(cur.val);
                cur = cur.right;
            } else {
                TreeNode predecessor = getPredNode(cur);
                if (predecessor.right == null) {
                    resList.add(cur.val);
                    predecessor.right = cur;
                    cur = cur.left;
                } else {
                    predecessor.right = null;
                    cur = cur.right;
                }

            }
        }
        return resList;
    }

    public TreeNode specialSuccessor (TreeNode node, TreeNode root) {
        if (root == null || node == null)
            return null;
        TreeNode cur = node.right;
        if (cur == null){
            cur = root;
            TreeNode leftAncestor = null;
            while (cur != null && cur.val != node.val){
                if(node.val < cur.val) {
                    leftAncestor = cur;
                    cur = cur.left;
                } else{
                    cur = cur.right;
                }
            }
            cur = leftAncestor;

        } else {
            while(cur.left != null){
                cur = cur.left;
            }
        }

        return cur;
    }

    public TreeNode specialPredecessor(TreeNode node, TreeNode root){
        if (root == null || node == null)
            return null;
        TreeNode cur = node.left;
        if (cur == null){
            cur = root;
            TreeNode rightAncestor = null;
            while (cur != null && cur.val != node.val){
                if(node.val < cur.val) {

                    cur = cur.left;
                } else{
                    rightAncestor = cur;
                    cur = cur.right;
                }
            }
            cur = rightAncestor;

        } else {
            while(cur.right != null){
                cur = cur.right;
            }
        }
        return cur;
    }
    private TreeNode doubleHead = null;
    private TreeNode bst2DoubleHelper (TreeNode node, TreeNode root) {
        if (node == null)
            return null;
        if (doubleHead == null && node.left == null)
            doubleHead = node;
        TreeNode pred = specialPredecessor(node, root);
        TreeNode succ = specialSuccessor(node, root);
        //System.out.println((pred == null?"null":pred.val) + " "+node.val+ " "+ (succ==null?"null":succ.val));
        bst2DoubleHelper(node.left, root);
        bst2DoubleHelper(node.right, root);
        node.left = pred;
        node.right = succ;
        //System.out.println("#"+(node.left == null?"null":node.left.val) + " "+node.val+ " "+ (node.right==null?"null":node.right.val));
        return node;
    }

    public TreeNode bst2DoublyLinkList(TreeNode root) {
        bst2DoubleHelper(root, root);
        int count = 0;
        TreeNode cur = doubleHead;

        while (cur.right != null){
            cur = cur.right;
        }
        TreeNode tail = cur;
        tail.right = doubleHead;
        doubleHead.left = tail;
        cur = doubleHead;
        count = 0;
        while (cur != tail){
            System.out.print(" " +cur.val);
            cur = cur.right;
        }
        while (cur != doubleHead){
            System.out.print(" " + cur.val);
            cur = cur.left;
        }
        System.out.println(" " + cur.val);

        return doubleHead;
    }

    // LeetCode :: 1026. Maximum Difference Between Node and Ancestor
    // The idea is to keep track of low and high value of the ancestors. At each node we check if the node's value is
    // lower than the current low of ancestor then we update the low to the new low for the ancestor. if the node's
    // value is higher than the ancestor high we update the ancestor high. Next we calc the current diff for this node
    // with ancestor low & high. We also keep track of maxDiff in a global variable
    int maxDiff = 0;
    private void maxAncestorDiffHelper (TreeNode node, int low, int high){
        if(node == null)
            return;
        // update ancestor low or high depending on the node's value
        if (node.val > high)
            high = node.val;
        else if (node.val < low)
            low = node.val;
        // calc the current diff
        int currDiff = Math.abs(high - node.val);
        currDiff += Math.max(currDiff, Math.abs(low - node.val));
        // update the global max diff if required
        maxDiff = Math.max(maxDiff, currDiff);
        // traverse the tree
        maxAncestorDiffHelper(node.left, low, high);
        maxAncestorDiffHelper(node.right, low, high);
    }
    public int maxAncestorDiff(TreeNode root) {
        if (root != null)
            maxAncestorDiffHelper(root,root.val,root.val);
        return maxDiff;
    }

    // LeetCode :: 863. All Nodes Distance K in Binary Tree
    // The idea is to search the target node's subtree for k distant node
    // and get k -i ancestor and search the k - i th ancestor for k-i distant child. We need to search the ancestor
    // opposit subtree meaning if the target node is found on the left we search ancestor's right subtree and vice versa
    private void getKDistantNodes(TreeNode root, int k, List<Integer> nodeList) {
        if (root == null || k < 0)
            return;
        if (k == 0) {
            nodeList.add(root.val);
            return;
        }
        getKDistantNodes(root.left, k -1, nodeList);
        getKDistantNodes(root.right, k -1, nodeList);
    }

    private int kDistanteNodes (TreeNode node, TreeNode target, int k, List<Integer> nodeList) {
        if (node == null)
            return -1;
        if (node == target) {
            getKDistantNodes(target, k, nodeList);
            return 1;
        }
        int l = kDistanteNodes(node.left, target, k, nodeList);
        int r = kDistanteNodes(node.right, target, k , nodeList);
        if (l > 0) {
            if(l < k) {
                getKDistantNodes(node.right, k - l -1, nodeList);
            } else if (l==k)
                nodeList.add(node.val);
            return l + 1;
        } else if (r > 0) {
            if(r < k) {
                getKDistantNodes(node.left, k - r -1, nodeList);
            } else if (r==k)
                nodeList.add(node.val);
            return r + 1;
        } else
            return  -1;
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> rList = new ArrayList<>();
        int val = kDistanteNodes(root, target, K, rList);
        return rList;
    }

    // LeetCode :: 1120. Maximum Average Subtree
    // Problem def : Given a binary tree, find the subtree with maximum average. Return the root of the subtree.
    //               It's guaranteed that there is only one subtree with maximum average.
    // The idea is calc the left & right subtree node count & average in a bottom up manner
    // At each node we get the left subtree node count, value sum & right subtree node count, value sum
    // so at each node node count is : left_count + 1 + right_count
    // and value sum : left_sum + node.val + right_sum
    // we keep track of maxAvg in a global variable
    double maxAvg = 0;
    private long [] maximumAvgSubtreeHelper (TreeNode node) {
        // base null node has zero node count & zero val
        if (node == null)
            return new long [] {0,0};
        // get the left subtree node count & value_sum
        long[] left = maximumAvgSubtreeHelper(node.left);
        // get the right subtree node count & value_sum
        long[] right = maximumAvgSubtreeHelper(node.right);
        // calc the node count & value_sum at this node
        long[] res = new long[2];
        res[0] = left[0] + 1 + right[0]; // node count of this subtree
        res[1] = left[1] + node.val + right[1]; // value sum of this subtree
        // current avg
        double avg = (double) res[1]/ (double)res[0];
        // update max avg if possible
        maxAvg = Math.max(maxAvg, avg);
        return res;
    }
    public double maximumAverageSubtree(TreeNode root) {
        maximumAvgSubtreeHelper(root);
        return maxAvg;
    }






}
