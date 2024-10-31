package com.dsad.music.core;

import java.io.Serializable;
import java.util.function.Predicate;
import java.util.Comparator;

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
    private Node head, tail, current;

    /*---------------------------------FUNCTIONS------------------------------------------------------------------ */
    /**
     * Default constructor
     */
    public DoublyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
        this.current = null;
    }

    /**
     * Returns the size of the linked list
     * @return size of linked list
     */
    public long getSize() {
        return this.size;
    }

    /**
     * Returns the data of a Node
     * @param position
     * @return
     */
    public T getNodeData(long position) {
        return this.getNode(position).data;
    }

    /**
     * Returns the data of the current node's data
     * @return data
     */
    public T getCurrentNode() {
        return (this.current == null)? null: this.current.data;
    }

    /**
     * moves the current node back based on whether the list is circular or not
     * @param circular true/false
     */
    public void moveBack(boolean circular) {
        if (this.current.prev == null && circular) {
            this.current = this.tail; // move the current pointer to the 
        } else if (this.current.prev != null) {
            this.current = this.current.prev;
        }
    }

    /**
     * moves the current node forward based on whether the list is circular or not
     * @param circular true/false
     */
    public void moveForward(boolean circular) {
        if (this.current.next == null && circular) {
            this.current = this.head;
        } else if (this.current.next != null) {
            this.current = this.current.next;
        }
    }

    /**
     * Adds a data to the end of the list
     * @param data
     */
    public void append(T data) {
        Node newNode = new Node(data); // create the node first
        placeNode(newNode, this.size);
        // not assigning the newly added song as the current
        this.size++; // increase the list's size
    }

    /**
     * Insert data at any other index other than last place
     * @param data
     * @param index
     */
    public void insert(T data, long index) {
        Node newNode = new Node(data);
        placeNode(newNode, index);
        // not assigning the newly added song as the current
        this.size++; // increase size
    }

    /**
     * Places given data in a specific position
     * Reusing this code wherever node needs to be inserted
     * @param data
     * @param index
     */
    @SuppressWarnings("exports")
    public void placeNode(Node node, long index) {

        if (this.head == null) { // handle empty list case first, index not a concern
            this.head = node;
            this.tail = node;
            this.current = node;
        }else if (index <= 1) { // place at start
            node.next = this.head;
            this.head.prev = node;
            this.head = node; // new head
        } else if (index >= this.size) { // place at last
            node.prev = this.tail;
            this.tail.next = node;
            this.tail = node; // new tail
        } else { // place at some place in between two nodes
            // find the previous node of the required index
            Node prev = getNode(index-1);
            // connect node to the prev and its next first
            node.next = prev.next;
            node.prev = prev;
            // connect prev and its next to node next
            prev.next.prev = node;
            prev.next = node;
        }
    }

    /**
     * Returns index of a data
     * @param data
     * @return -1/index of data
     */
    public long indexOf(Predicate<T> predicate) {
        Node front = this.head, back = this.tail;
        long frontIdx = 1, backIdx = this.size;
        boolean found = false;

        while (front != null && back != null && front.prev != back) {
            if (predicate.test(front.data) || predicate.test(back.data)) {
                // found the data
                found = true;
                break;                
            }
            front = front.next; frontIdx++;
            back = back.prev; backIdx--;
        }
        if (found) {
            return ((predicate.test(front.data))? frontIdx: backIdx);
        }
        return -1;
    }

    /**
     * Search for a given data in the list
     * @param data
     * @return node that was searched
     */
    @SuppressWarnings("exports")
    public Node search(Predicate<T> predicate) {
        // if the current node matches the criteria, no need to search the entire list
        if (this.current != null && predicate.test(this.current.data)) {
            return this.current;
        }

        Node front = this.head, back = this.tail;
        boolean found = false;

        while (front != null && back != null && front.prev != back) {
            if (predicate.test(front.data) || predicate.test(back.data)) {
                // found the data
                found = true;
                break;                
            }
            front = front.next;
            back = back.prev;
        }
        if (found) {
            return ((predicate.test(front.data))? front: back);
        }
        return null;
    }

    /**
     * Search for a given data in the list by its index
     * @param index
     * @return node of the index that was searched
     */
    @SuppressWarnings("exports")
    public Node getNode(long index) {
        if (index <= 1) return this.head;
        if (index >= this.size) return this.tail;

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
    public T deleteNode(long index, Predicate<T> predicate) {
        Node node = null;
        if (predicate == null && index >= 1 &&  index <= this.size) {
            node = getNode(index); // if searching by index
        } else {
            node = search(predicate); // if searching by data
        }
        if (node == null) return null; // node not found
        else if (this.current == node) {
            // if current node is being removed, shift current pointer
            this.current = (node.prev != null)? node.prev: node.next;
        }
        removeNode(node);

        this.size--;
        return node.data;
    }

    /**
     * Given a node removes it from the list by de-linking
     * @param node
     */
    @SuppressWarnings("exports")
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
     * Reorder the given node by removing its old connections and placing it at given index
     * @param node
     * @param index
     */
    @SuppressWarnings("exports")
    public void reorder(Node node, long index) {
        if (node != null && node.prev == null) { // first node
            node.next.prev = null;
            this.head = node.next;
            node.next = null;
        } else if (node != null && node.next == null) { // last node
            node.prev.next = null;
            this.tail = node.prev;
            node.prev = null;
        } else { // middle node
            // remove node's old connections
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        if (node != null) placeNode(node, index); // place the node at the correct place
    }

    /**
     * Move a node from one position to another
     * @param src original index -1 if not needed
     * @param dest target index -1 if not needed
     */
    public void moveNode(long src, long dest, Predicate<T> predicate) {

        // getting the actual node we are looking for
        Node node = null;
        if (predicate == null && src >= 1 && src <= this.size) {
            node = getNode(src); // incase searching with index
        } else {
            node = search(predicate); // in case searching with data
        }
        // handling all the connections
        if (dest < 1) {
            dest = 1; // resetting to 1
        } else if (dest > this.size) {
            dest = this.size; // resetting to last position
        }
        // handling all the connections
        reorder(node, dest);
    }

    /**
     * reversing the whole list
     */
    public void reverseList() {
        if (this.head == null || this.tail == null || this.head.next == null || this.tail.prev == null) return;
        Node curr = head, next = head.next;

        while (curr != null) {
            // swapping the current node's pointers
            Node temp = curr.prev;
            curr.prev = next;
            curr.next = temp;

            // moving the pair
            curr = next;
            next = (next == null)? null: next.next;
        }
        // swapping the head and tail pointers
        Node temp = this.head;
        this.head = this.tail;
        this.tail = temp;

        this.current = this.head; // changing the current to the current head
    }

    /**
     * Sorts the list according to the criteria provided
     * using Insertion sort
     * @param comparator
     */
    public void sort(Comparator<T> comparator) {
        if (this.head == null) return;
        Node curr = this.head.next; // current node to be sorted
        Node lastSorted = this.head, firstSorted = this.head;
        long lastIdx = 1, firstIdx = 1, currIdx = 2;

        while (curr != null) { // sort all the nodes

            // find the correct place to place the curr node
            while (comparator.compare(curr.data, firstSorted.data) >= 0 || 
                comparator.compare(curr.data, lastSorted.data) < 0 && lastSorted != firstSorted) {
                // as long as curr is smaller than lastSorted and larger than firstSorted
                firstSorted = firstSorted.next; firstIdx++;
                lastSorted = lastSorted.prev; lastIdx--;
            }

            if (comparator.compare(curr.data, firstSorted.data) < 0 && firstIdx != currIdx) {
                // current is less than the firstSorted
                reorder(curr, firstIdx);
            } else if (comparator.compare(curr.data, lastSorted.data) >= 0 && lastIdx != currIdx) {
                // current is more than lastSorted
                reorder(curr, lastIdx);
            }

            // moving to next node
            currIdx++;            
            curr = curr.next;
            // resetting the lastSorted
            lastIdx = currIdx - 1;
            lastSorted = curr.prev;
            // resetting the firstSorted
            firstIdx = 1;
            firstSorted = this.head;
        }
    }

    /**
     * Sets the current to the node pointed by the params
     * @param predicate to test the data
     * @param index
     * @return true/false
     */
    public boolean setCurrent(Predicate<T> predicate, long index) {
        Node query = null;
        if (index != -1) {
            query = this.getNode(index);
        } else {
            query = this.search(predicate); // if setting by condition
        }

        if (query == null) return false;
        else {
            this.current = query;
            return true;
        }
    }

    /**
     * Clears the list
     */
    public void clear() {
        Node curr = this.head;
        while (curr != null) {
            Node next = curr.next;
            curr.prev = null; curr.next = null;
            curr = next;            
        }
        this.head = null; this.tail = null;
    }
}
