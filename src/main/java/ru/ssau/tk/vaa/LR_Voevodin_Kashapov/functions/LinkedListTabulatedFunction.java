package ru.ssau.tk.vaa.LR_Voevodin_Kashapov.functions;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {
    private int count;
    private Node head;
    private Node last;

    protected static class Node {
        public Node next;
        public Node prev;
        public double x;
        public double y;
    }

    protected void addNode(double x, double y) {
        Node node = new Node();
        node.x = x;
        node.y = y;
        if (head == null) {
            head = node;
            node.prev = node;
            node.next = node;
        } else {
            last.next = node;
            head.prev = node;
            node.prev = last;
            node.next = head;
        }
        last = node;
        //count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length < 2){
            throw new IllegalArgumentException("length less than 2 points");
        }
        for (int i = 0; i < xValues.length; i++) {
            this.addNode(xValues[i], yValues[i]);
        }
        this.count = xValues.length;
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Length less than 2 points");
        }
        if (xFrom >= xTo) {
            throw new IllegalArgumentException("Incorrect bounds");
        }
        this.count = count;
        double step = (xTo - xFrom) / (count - 1);
        for (int i = 0; i < count; i++) {
            addNode(xFrom, source.apply(xFrom));
            xFrom += step;
        }
    }

    public int getCount() {
        return count;
    }

    public double leftBound() {
        return head.x;
    }

    public double rightBound() {
        return last.x;
    }

    public Node getNode(int index) {
        checkIndex(index);
        Node iNode;
        if (index < count / 2) {
            iNode = head;
            for (int i = 0; i < count / 2; i++) {
                if (i == index) {
                    return iNode;
                } else {
                    iNode = iNode.next;
                }
            }
        } else {
            iNode = last;
            for (int i = count - 1; i >= count / 2; i--) {
                if (i == index) {
                    return iNode;
                } else {
                    iNode = iNode.prev;
                }
            }
        }
        return null;
    }


    @Override
    public double getX(int index) {
        checkIndex(index);
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        checkIndex(index);
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        checkIndex(index);
        getNode(index).y = value;
    }

    public int indexOfX(double x) {
        Node xNode = head;
        for (int i = 0; i < count; i++) {
            if (xNode.x == x) {
                return i;
            }
            xNode = xNode.next;
        }
        return -1;
    }

    public int indexOfY(double y) {
        Node yNode = head;
        for (int i = 0; i < count; i++) {
            if (yNode.y == y) {
                return i;
            }
            yNode = yNode.next;
        }
        return -1;
    }

    public int floorIndexOfX(double x) {
        if (x < head.x) {
            throw new IllegalArgumentException("X is less than the left border");
        }
        Node node = head;
        for (int i = 0; i < count; i++) {
            if (node.x <= x) {
                node = node.next;
            } else {
                return i - 1;
            }
        }
        return getCount();

    }

    protected double extrapolateLeft(double x) {
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    protected double extrapolateRight(double x) {
        return interpolate(x, last.prev.x, last.x, last.prev.y, last.y);
    }

    protected double interpolate(double x, int floorIndex) {
        Node node = getNode(floorIndex);
        Node nodeNext = node.next;
        if (x <  node.x || x > nodeNext.x){
            throw new IllegalArgumentException("x is out of interval boundary");
        }
        return interpolate(x, node.x, nodeNext.x, node.y, nodeNext.y);
    }

    protected Node floorNodeOfX(double x) {
        Node node = head;
        if (x < head.x) {
            throw new IllegalArgumentException("X is less than the left border");
        }
        for (int i = 0; i < count; i++) {
            if (node.x <= x) {
                node = node.next;
            } else {
                return node.prev;
            }
        }
        return last;
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            Node node = floorNodeOfX(x);
            if (node.x == x) {
                return node.y;
            } else {
                return interpolate(x, node.x, node.next.x, node.y, node.next.y);
            }
        }
    }

    @Override
    public void insert(double x, double y) {
        if (indexOfX(x) != -1) {
            setY(indexOfX(x), y);
        }

        if (head == null) {
            addNode(x, y);
            count++;
        } else {
            Node node = new Node();
            node.x = x;
            node.y = y;

            if (head.x > x) {
                node.next = head;
                node.prev = head.prev;
                last.next = node;
                head = node;
            } //else if (floorIndexOfX(x) == 0) {
              //  node.next = head.next;
               // node.prev = head;
               // head.next = node;

            //}
        else if (floorIndexOfX(x) == count) {
                node.next = head;
                node.prev = last;
                head.prev = node;
                last.next = node;
                last = node;

            } else {
                int ind = floorIndexOfX(x);
                Node check = getNode(ind);
                node.next = check.next;
                node.prev = check;
                check.next = node;
            }
            count++;
        }
    }

    @Override
    public void remove(int index) {
        Node delete = floorNodeOfX(getX(index));
        Node prevDelete = delete.prev;
        if (index == 0){
            last.next = delete.next;
            head = delete.next;
        } else{
            prevDelete.next = delete.next;
        }
        count--;

    }

    private void checkIndex(int index){
        if (index < 0 || index > count - 1){
            throw new IndexOutOfBoundsException("The index is out of bounds");
        }
    }

    public Iterator<Point> iterator() {
        Iterator<Point> it = new Iterator<>() {
            Node node = head;

            @Override
            public boolean hasNext() {
                return node != head.prev && node != null;
            }

            @Override
            public Point next() {
                if (!hasNext())
                {
                    throw new NoSuchElementException();
                } else {
                    Point point = new Point(node.x, node.y);
                    if(node == head.prev){
                        node = null;
                    } else{
                        node = node.next;
                    }
                    return point;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}

