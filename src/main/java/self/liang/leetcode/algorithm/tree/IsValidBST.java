package self.liang.leetcode.algorithm.tree;

import org.apache.poi.ss.formula.functions.T;

public class IsValidBST {
    //
//    给定一个二叉树，判断其是否是一个有效的二叉搜索树。
//
//    假设一个二叉搜索树具有如下特征：
//
//    节点的左子树只包含小于当前节点的数。
//    节点的右子树只包含大于当前节点的数。
//    所有左子树和右子树自身必须也是二叉搜索树。
//    示例 1:
//
//    输入:
//            2
//            / \
//            1   3
//    输出: true
//    示例 2:
//
//    输入:
//            5
//            / \
//            1   4
//                 / \
//                3   6
//    输出: false
//    解释: 输入为: [5,1,4,null,null,3,6]。
//                 根节点的值为 5 ，但是其右子节点值为 4 。
    public static void main(String[] args) {

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(20);
        boolean validBST = isValidBST(root);
        System.out.println(validBST);
    }

    //错误
    public static boolean isValidBST(TreeNode root) {

        //当前节点是否满足
        if (root == null) return true;
        if (root.left != null && root.left.val >= root.val) return false;
        if (root.right != null && root.right.val <= root.val) return false;


        //子节点是否满足
        boolean leftOk = isValidBST(root.left);
        boolean rightOk = isValidBST(root.right);

        if (leftOk && rightOk) return true;
        else return false;
    }


    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


}
