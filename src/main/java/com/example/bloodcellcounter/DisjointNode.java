package com.example.bloodcellcounter;


public class DisjointNode<T> {

    private DisjointNode<T> parent=null;
    private T data;
    private int width = 1;
    private int size = 1;
    private int x;
    private int y;

    public DisjointNode(T data,int x,int y) {
        this.data = data;
        this.x=x;
        this.y=y;
    }

    public DisjointNode<T> getParent() {
        return parent;
    }

    public void setParent(DisjointNode<T> parent) {
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getWidth() {return width;}

    public void setWidth(int width) {this.width = width;}

    public int getSize() {return size;}

    public void setSize(int size) {this.size = size;}

    public int getX() {return x;}

    public void setX(int x) {this.x = x;}

    public int getY() {return y;}
}


