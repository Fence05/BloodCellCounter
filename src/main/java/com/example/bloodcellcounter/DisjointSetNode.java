package com.example.bloodcellcounter;


public class DisjointSetNode<T> {

    private DisjointSetNode<T> parent=null;
    private T data;
    private int size = 1;               //proper encapsulation done later for private
    private int x;
    private int y;

    public DisjointSetNode(T data,int x,int y) {
        this.data = data;
        this.x=x;
        this.y=y;
    }

    public DisjointSetNode<T> getParent() {
        return parent;
    }

    public void setParent(DisjointSetNode<T> parent) {
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getSize() {return size;}

    public void setSize(int size) {this.size = size;}

    public int getX() {return x;}

    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
}


