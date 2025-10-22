import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj2 {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java Proj2 <input file> <number of lines>");
            System.exit(1);
        }

        String filename = args[0];
        int numLines = Integer.parseInt(args[1]);

        // Read dataset into ArrayList
        ArrayList<String> dataList = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            int count = 0;
            while (sc.hasNextLine() && count < numLines) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    dataList.add(line);
                    count++;
                }
            }
        }

        // Create sorted and randomized copies
        ArrayList<String> sortedList = new ArrayList<>(dataList);
        ArrayList<String> randomList = new ArrayList<>(dataList);
        Collections.sort(sortedList);
        Collections.shuffle(randomList);

        // Create trees using your Project 1 BST class
        BST<String> bstSorted = new BST<>();
        BST<String> bstRandom = new BST<>();
        AvlTree<String> avlSorted = new AvlTree<>();
        AvlTree<String> avlRandom = new AvlTree<>();

        // Time insertions
        long start, end;

        start = System.nanoTime();
        for (String s : sortedList) bstSorted.insert(s);
        end = System.nanoTime();
        long bstSortedInsert = end - start;

        start = System.nanoTime();
        for (String s : randomList) bstRandom.insert(s);
        end = System.nanoTime();
        long bstRandomInsert = end - start;

        start = System.nanoTime();
        for (String s : sortedList) avlSorted.insert(s);
        end = System.nanoTime();
        long avlSortedInsert = end - start;

        start = System.nanoTime();
        for (String s : randomList) avlRandom.insert(s);
        end = System.nanoTime();
        long avlRandomInsert = end - start;

        // Time searches
        start = System.nanoTime();
        for (String s : dataList) bstSorted.search(s);
        end = System.nanoTime();
        long bstSortedSearch = end - start;

        start = System.nanoTime();
        for (String s : dataList) bstRandom.search(s);
        end = System.nanoTime();
        long bstRandomSearch = end - start;

        start = System.nanoTime();
        for (String s : dataList) avlSorted.contains(s);
        end = System.nanoTime();
        long avlSortedSearch = end - start;

        start = System.nanoTime();
        for (String s : dataList) avlRandom.contains(s);
        end = System.nanoTime();
        long avlRandomSearch = end - start;

        // Print simple results
        System.out.println("N = " + numLines);
        System.out.println("BST Sorted Insert: " + bstSortedInsert);
        System.out.println("BST Random Insert: " + bstRandomInsert);
        System.out.println("AVL Sorted Insert: " + avlSortedInsert);
        System.out.println("AVL Random Insert: " + avlRandomInsert);
        System.out.println("BST Sorted Search: " + bstSortedSearch);
        System.out.println("BST Random Search: " + bstRandomSearch);
        System.out.println("AVL Sorted Search: " + avlSortedSearch);
        System.out.println("AVL Random Search: " + avlRandomSearch);

        // Append to CSV file
        try (FileWriter fw = new FileWriter("output.txt", true)) {
            fw.write(numLines + "," +
                    bstSortedInsert + "," + bstRandomInsert + "," +
                    avlSortedInsert + "," + avlRandomInsert + "," +
                    bstSortedSearch + "," + bstRandomSearch + "," +
                    avlSortedSearch + "," + avlRandomSearch + "\n");
        }

        System.out.println("Results appended to output.txt");
    }
}
