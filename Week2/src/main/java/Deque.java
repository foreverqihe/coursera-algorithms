import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private Node head = null;
    private Node tail = null;
    private int size = 0;
    
    private class Node {
        private Item item = null;
        private Node next = null;
        private Node prev = null;
    }

    public Deque() {
        // construct an empty deque

    }

    public boolean isEmpty() {
        // is the deque empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        checkParam(item);
        size++;
        if (head == null) {
            Node node = new Node();
            node.item = item;
            head = node;
            tail = node;
        } else {
            Node node = new Node();
            node.item = item;
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    private void checkParam(Item item) {
        if (null == item)
            throw new IllegalArgumentException();
    }

    public void addLast(Item item) {
        // add the item to the end
        checkParam(item);
        size++;
        if (tail == null) {
            Node node = new Node();
            node.item = item;
            head = node;
            tail = node;
        } else {
            Node node = new Node();
            node.item = item;
            tail.next = node;
            tail = node;
        }
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (head == null) {
            throw new java.util.NoSuchElementException();
        }
        size--;
        Node result = head;
        head = result.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }

        return result.item;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (tail == null) {
            throw new java.util.NoSuchElementException();
        }
        size--;
        Node result = tail;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }

        return result.item;

    }


    private class DequeIterator implements Iterator<Item> {

        private Node current;

        public DequeIterator(Deque<Item> deque) {
            current = deque.head;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item result = current.item;
            current = current.next;
            return result;
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException();
        }
        

    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new DequeIterator(this);
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<String> deque = new Deque<>();
        deque.addFirst("3");
        deque.addFirst("2");
        deque.addLast("1");

        for(Iterator<String> it = deque.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }

}
