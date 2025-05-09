package com.example.bloodcellcounter;


public class DisjointSetController <T> {

    public DisjointSetNode<T> findRoot(DisjointSetNode<T> node) {
        while (node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }
//find disjoint sets root/parent


    public DisjointSetNode<T> findNodeParent(DisjointSetNode<T> node) {
        return node.getParent() == null ? node : findNodeParent(node.getParent());
    }

//from notes find parent

    public void unionBySize(DisjointSetNode<T> node1, DisjointSetNode<T> node2) {
        DisjointSetNode<T> rootNode1 =findNodeParent(node1);
        DisjointSetNode<T> rootNode2 =findNodeParent(node2);

        if (rootNode1 == rootNode2) return;

        DisjointSetNode<T> biggerRoot = rootNode1.getSize()>=rootNode2.getSize() ? rootNode1 : rootNode2;
        DisjointSetNode<T> smallerRoot = biggerRoot==rootNode1 ? rootNode2 : rootNode1;

        smallerRoot.setParent(biggerRoot);
        biggerRoot.setSize(biggerRoot.getSize() + smallerRoot.getSize());
        //adds 2 roots and puts into biggerRoot, size update
    }
//union function

}
