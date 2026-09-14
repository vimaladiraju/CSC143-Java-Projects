public int depthSum(TreeNode root) {
    return depthSum(root, 1);
}

private int depthSum(TreeNode node, int depth) {
    if (node == null) {
        return 0;
    }

    return (node.data * depth) + depthSum(node.left, depth + 1) + depthSum(node.right, depth + 1);
}
