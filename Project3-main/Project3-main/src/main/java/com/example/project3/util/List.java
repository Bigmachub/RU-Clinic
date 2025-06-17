package com.example.project3.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of a dynamically resizing array-based list with basic functionality.
 * Supports adding, removing, accessing, and iterating over elements.
 * @author Shreyeut
 * @author Andy
 * @param <E> the type of elements in this list
 */
public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size;
    private static final int INITIAL_CAPACITY = 4;


    /**
     * Constructs an empty list with an initial capacity of 4.
     */
    @SuppressWarnings("unchecked")
    public List() {
        objects = (E[]) new Object[INITIAL_CAPACITY]; // Initialize with capacity 4
        size = 0;
    }

    /**
     * Adds an element to the end of the list, growing the list if necessary.
     *
     * @param e the element to add to the list
     */
    public void add(E e) {
        if (size == objects.length) {
            grow();
        }
        objects[size++] = e;
    }

    /**
     * Removes the first occurrence of the specified element from the list, if it is present.
     *
     * @param e the element to be removed from the list
     */
    public void remove(E e) {
        int index = find(e);
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                objects[i] = objects[i + 1];
            }
            objects[--size] = null; // Clear the last element
        }
    }

    /**
     * Checks if the list contains the specified element.
     *
     * @param e the element to check for
     * @return true if the list contains the element, false otherwise
     */
    public boolean contains(E e) {
        return find(e) != -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element, or -1 if not found.
     *
     * @param e the element to search for
     * @return the index of the element, or -1 if not found
     */
    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if ((objects[i] == null && e == null) || (objects[i] != null && objects[i].equals(e))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */    public int size() {
        return size;
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return objects[index];
    }

    /**
     * Replaces the element at the specified position in the list with the specified element.
     *
     * @param index the index of the element to replace
     * @param e     the element to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        objects[index] = e;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list, or -1 if the list does not contain the element.
     *
     * @param e the element to search for
     * @return the index of the first occurrence of the specified element, or -1 if not found
     */
    public int indexOf(E e) {
        return find(e);
    }

    /**
     * Returns an iterator over the elements in the list, in proper sequence.
     *
     * @return an iterator over the elements in the list
     */    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Doubles the capacity of the list when the current capacity is reached.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length * 2];
        System.arraycopy(objects, 0, newObjects, 0, size);
        objects = newObjects;
    }

    /**
     * Inner class that implements an iterator over the elements in the list.
     */
    private class ListIterator implements Iterator<E> {
        private int currentIndex = 0;

        /**
         * Checks if there is a next element in the list.
         *
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        /**
         * Returns the next element in the list.
         *
         * @return the next element in the list
         * @throws NoSuchElementException if there is no next element
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return objects[currentIndex++];
        }
    }

    /**
     * Clears all elements from the list, setting each element to null and resetting the size to zero.
     */    public void clear() {
        for (int i = 0; i < size; i++) {
            objects[i] = null; // Set each element to null
        }
        size = 0; // Reset the size to zero, effectively emptying the list
    }
}
