import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int curr;
    private int capacity;

    public RandomizedQueue() {
        // construct an empty randomized queue
        curr = 0;
        capacity = 1;
        queue = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        // is the queue empty?
        return curr == 0;
    }

    public int size() {
        // return the number of items on the queue
        return curr;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null)
            throw new java.lang.NullPointerException();
        if (curr + 1 > capacity) {
            resizePlus();
        }
        queue[curr++] = item;
    }

    private void resizePlus() {
        capacity *= 2;
        Item[] newQueue = (Item[]) new Object[capacity];
        int index = 0;
        for (Item i : queue) {
            newQueue[index++] = i;
        }
        queue = newQueue;
    }

    private void resizeMinus() {
        capacity /= 2;
        Item[] newQueue = (Item[]) new Object[capacity];
        int index = 0;
        for (int i = 0; i < capacity; i++) {
            newQueue[index++] = queue[i];
        }
        queue = newQueue;
    }

    public Item dequeue() {
        // delete and return a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int i = StdRandom.uniform(curr);
        Item ret = queue[i];
        queue[i] = queue[--curr];
        queue[curr] = null;
        if (capacity / 4 > curr) {
            resizeMinus();
        }
        return ret;
    }

    public Item sample() {
        // return (but do not delete) a random item
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return queue[StdRandom.uniform(curr)];
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = 0;
        private int[] shuffledIndexes = new int[curr];

        public boolean hasNext() {
            if (current == 0) {
                for (int i = 0; i < curr; i++)
                    shuffledIndexes[i] = i;
                StdRandom.shuffle(shuffledIndexes);
            }
            return current < curr;
        }

        public Item next() {
            if (current == 0) {
                for (int i = 0; i < curr; i++)
                    shuffledIndexes[i] = i;
                StdRandom.shuffle(shuffledIndexes);
            }
            if (current >= curr || size() == 0)
                throw new java.util.NoSuchElementException();
            return queue[shuffledIndexes[current++]];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}