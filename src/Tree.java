import org.omg.PortableInterceptor.INACTIVE;

import java.awt.image.AreaAveragingScaleFilter;
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
        System.out.println("Creating node "+ node.data + "[" + low +"," + high + "] " + mid);
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
        TreeNode lastNode = null;
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
                        node.right == lastNode) {
                    lastNode = stack.pop();
                    rList.add(lastNode.data);
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
            List<TreeNode> leftSubTreeList = genBSTree(start, i -1);
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
        boolean isFirst = true;
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

    // LeetCode :: 104. Maximum Depth of Binary Tree
    // Get the max height of the tree recursively
    public int maxDepth(TreeNode root) {
        // null nodes are not counted
        if (root == null)
            return 0;
        // return 1 + the max of left & right child
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));

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
    // Check the version 1 its very interesting little complicated
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









}
