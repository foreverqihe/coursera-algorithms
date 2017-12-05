import java.util.HashSet;
import java.util.Set;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        Set<String> printed = new HashSet<>();

        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        String input = null;

        try {
            input = StdIn.readString();
            while (input != null) {
                randomQueue.enqueue(input);
                input = StdIn.readString();
            }
        } catch (NoSuchElementException e) {
            // e.printStackTrace();
        }

        for (int i = 0; i < k;) {
            input = randomQueue.dequeue();
            if (printed.contains(input)) {
                continue;
            }

            printed.add(input);
            i++;
            System.out.println(input);
        }
    }

}
