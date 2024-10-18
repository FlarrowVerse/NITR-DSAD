package com.dsad.music.core;

import java.io.Serializable;

public class DoublyLinkedList<T extends Comparable<T>> implements Serializable {

    /*---------------------------------PROPERTIES----------------------------------------------------------------- */
    private static final long serialVersionUID = 1L; // for versioning

    /**
     * Inner class for handling the individual nodes
     */
    private class Node implements Serializable {
        private static final long serialVersionUID = 1L; // for versioning
        T data; // actual data
        Node next, prev; // pointers
        
        /**
         * Parameterized constructor
         * @param data
         */
        Node(T data) {
            this.data = data;
            next = null;
            prev = null;
        }

        /**
         * Prints the node
         * @return String that represents the node
         */
        @Override
        public String toString() {
            return String.format("%s", data);
        }
        
    }

    // instance members
    private long size;
    private Node head, tail;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Default constructor
     */
    public DoublyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    /**
     * Returns the size of the linked list
     * @return size of linked list
     */
    public long getSize() {
        return this.size;
    }

    /**
     * Adds a data to the end of the list
     * @param data
     */
    public void add(T data) {
        Node newNode = new Node(data); // create the node first

        if (tail == null) {
            // playlist is empty
            head = newNode;
            tail = head;
        } else { // playlist has some songs
            tail.next = newNode;
            newNode.prev = tail;
            tail = tail.next;
        }

        this.size++; // increase the playlist's size
    }

    /**
     * Insert new data in a specific position
     * @param data
     * @param index
     */
    public void insert(T data, long index) {
        if (index >= this.size) {
            // add to the end
            this.add(data);
        } else {
            Node newNode = new Node(data); // create the node
            
            if (index == 1) { // enter at the first position
                this.head.prev = newNode;
                newNode.next = this.head;
                this.head = newNode;
            } else { // somewhere in the middle
                Node node = getNode(index);

                Node temp = node.prev;
                // handling next node
                temp.next.prev = newNode;
                newNode.next = temp.next;
                //handling previous node
                temp.next = newNode;
                newNode.prev = temp;
            }
        }
    }

    /**
     * Returns index of a data
     * @param data
     * @return -1/index of data
     */
    public long indexOf(T data) {
        Node front = this.head, back = this.tail;
        long frontIdx = 1, backIdx = this.size;
        boolean found = false;

        while (front != null && back != null && front.prev != back) {
            if (front.data.compareTo(data) == 0 || back.data.compareTo(data) == 0) {
                // found the data
                found = true;
                break;                
            }
            front = front.next; frontIdx++;
            back = back.next; backIdx--;
        }
        if (found) {
            return ((front.data.compareTo(data) == 0)? frontIdx: backIdx);
        }
        return -1;
    }

    /**
     * Search for a given data in the list
     * @param data
     * @return node that was searched
     */
    public Node search(T data) {
        Node front = this.head, back = this.tail;
        boolean found = false;

        while (front != null && back != null && front.prev != back) {
            if (front.data.compareTo(data) == 0 || back.data.compareTo(data) == 0) {
                // found the data
                found = true;
                break;                
            }
            front = front.next;
            back = back.next;
        }
        if (found) {
            return ((front.data.compareTo(data) == 0)? front: back);
        }
        return null;
    }

    /**
     * Search for a given data in the list by its index
     * @param index
     * @return node of the index that was searched
     */
    public Node getNode(long index) {
        Node front = head, back = tail;
        long idxFront = 1, idxBack = this.size;

        // find the position to insert either from front or back, which ever is faster
        while (idxFront < index && idxBack > index) {
            idxFront++;
            idxBack--;
            front = front.next;
            back = back.prev;
        }
        return ((idxFront == index)? front: back);
    }

    /**
     * Searches an object and De-links it from the list
     * @param data index/Song
     * @return true/false
     */
    @SuppressWarnings("unchecked")
    public boolean deleteNode(Object data) {
        Node node = null;
        if (data instanceof Long && (Long)data >= 1 &&  (Long)data <= this.size) {
            node = getNode((Long) data); // if searching by index
        } else {
            node = search((T) data); // if searching by data
        }
        if (node == null) return false; // node not found
        removeNode(node);
        return true;
    }

    /**
     * Given a node removes it from the list by de-linking
     * @param node
     */
    public void removeNode(Node node) {
        // remove it
        Node prev = node.prev;
        prev.next = node.next;
        node.next.prev = prev;        
    }

    /**
     * For easy printing to console
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        long index = 1; // position
        Node curr = this.head;
        while (curr != null) {
            s.append(index).append(") ").append(curr).append("\n"); // <index>) <data>
            index++;
            curr = curr.next;
        }

        return s.toString();
    }

    /**
     * Move a node from one position to another
     * @param data
     * @param index
     */
    @SuppressWarnings("unchecked")
    public void moveNode(Object data, long index) {
        Node node = null;
        if (data instanceof Long  && (Long)data >= 1 &&  (Long)data <= this.size) {
            node = getNode((Long)data);
        } else {
            node = search((T) data);
        }

        // if correct node mentioned, remove it from original place and create
        if (node != null && index >= 1 && index <= this.size) {
            T value = node.data;
            removeNode(node);
            insert(value, index);
        }
    }

    /**
     * reversing the whole list
     */
    public void reverseList() {
        Node curr = head, next = head.next;

        while (curr != tail) {
            // swapping the current node's pointers
            Node temp = curr.prev;
            curr.prev = next;
            curr.next = temp;

            // moving the pair
            curr = next;
            next = curr.next;
        }
    }
}
