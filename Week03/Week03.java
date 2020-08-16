//1.二叉树的最近公共祖先，使用递归
class Solution {

    private TreeNode ans;

    public Solution() {
        this.ans = null;
    }

    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return false;
        boolean lson = dfs(root.left, p, q);
        boolean rson = dfs(root.right, p, q);
        if ((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))) {
            ans = root;
        } 
        return lson || rson || (root.val == p.val || root.val == q.val);
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        this.dfs(root, p, q);
        return this.ans;
    }
}

//2.从前序与中序遍历构造二叉树，使用递归

class Solution {
    private Map<Integer, Integer> indexMap;

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);
        
        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }
}

//3.组合，使用回溯
class Solution {
  List<List<Integer>> output = new LinkedList();
  int n;
  int k;

  public void backtrack(int first, LinkedList<Integer> curr) {
    // if the combination is done
    if (curr.size() == k)
      output.add(new LinkedList(curr));

    for (int i = first; i < n + 1; ++i) {
      // add i into the current combination
      curr.add(i);
      // use next integers to complete the combination
      backtrack(i + 1, curr);
      // backtrack
      curr.removeLast();
    }
  }

  public List<List<Integer>> combine(int n, int k) {
    this.n = n;
    this.k = k;
    backtrack(1, new LinkedList<Integer>());
    return output;
  }
}

//4.全排列，使用搜索回溯
class Solution {
  public void backtrack(int n,
                        ArrayList<Integer> output,
                        List<List<Integer>> res,
                        int first) {
    // 所有数都填完了
    if (first == n)
      res.add(new ArrayList<Integer>(output));
    for (int i = first; i < n; i++) {
      // 动态维护数组
      Collections.swap(output, first, i);
      // 继续递归填下一个数
      backtrack(n, output, res, first + 1);
      // 撤销操作
      Collections.swap(output, first, i);
    }
  }

  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new LinkedList();

    ArrayList<Integer> output = new ArrayList<Integer>();
    for (int num : nums)
      output.add(num);

    int n = nums.length;
    backtrack(n, output, res, 0);
    return res;
  }
}
