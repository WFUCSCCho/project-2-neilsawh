import java.io.*;
import java.util.*;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java Proj2 <dataset-file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // Read dataset into ArrayList
        ArrayList<Movie> dataset = new ArrayList<>();
        FileInputStream fis = new FileInputStream(inputFileName);
        Scanner scanner = new Scanner(fis);

        // Skip header
        if (scanner.hasNextLine()) scanner.nextLine();

        int count = 0;
        while (scanner.hasNextLine() && count < numLines) {
            String line = scanner.nextLine();
            String[] parts = line.split(",", 4);
            int rank = Integer.parseInt(parts[0]);
            String title = parts[1];
            int year = Integer.parseInt(parts[2]);
            double rating = Double.parseDouble(parts[3]);
            dataset.add(new Movie(rank, title, year, rating));
            count++;
        }
        scanner.close();

        // Prepare sorted and randomized datasets
        ArrayList<Movie> sortedDataset = new ArrayList<>(dataset);
        sortedDataset.sort(Comparator.naturalOrder());

        ArrayList<Movie> shuffledDataset = new ArrayList<>(dataset);
        Collections.shuffle(shuffledDataset);

        // Create trees
        BST<Movie> bstSorted = new BST<>();
        BST<Movie> bstShuffled = new BST<>();
        AvlTree<Movie> avlSorted = new AvlTree<>();
        AvlTree<Movie> avlShuffled = new AvlTree<>();

        // --- Timing insertions ---
        long start, end;

        // BST Sorted Insert
        start = System.nanoTime();
        for (Movie m : sortedDataset) bstSorted.insert(m);
        end = System.nanoTime();
        long bstSortedInsertTime = end - start;

        // BST Shuffled Insert
        start = System.nanoTime();
        for (Movie m : shuffledDataset) bstShuffled.insert(m);
        end = System.nanoTime();
        long bstShuffledInsertTime = end - start;

        // AVL Sorted Insert
        start = System.nanoTime();
        for (Movie m : sortedDataset) avlSorted.insert(m); // Corrected
        end = System.nanoTime();
        long avlSortedInsertTime = end - start;

        // AVL Shuffled Insert
        start = System.nanoTime();
        for (Movie m : shuffledDataset) avlShuffled.insert(m); // Corrected
        end = System.nanoTime();
        long avlShuffledInsertTime = end - start;

        // --- Timing searches ---
        // BST Sorted Search
        start = System.nanoTime();
        for (Movie m : dataset) bstSorted.search(m);
        end = System.nanoTime();
        long bstSortedSearchTime = end - start;

        // BST Shuffled Search
        start = System.nanoTime();
        for (Movie m : dataset) bstShuffled.search(m);
        end = System.nanoTime();
        long bstShuffledSearchTime = end - start;

        // AVL Sorted Search
        start = System.nanoTime();
        for (Movie m : dataset) avlSorted.contains(m);
        end = System.nanoTime();
        long avlSortedSearchTime = end - start;

        // AVL Shuffled Search
        start = System.nanoTime();
        for (Movie m : dataset) avlShuffled.contains(m);
        end = System.nanoTime();
        long avlShuffledSearchTime = end - start;

        // Print results
        System.out.println("N = " + numLines);
        System.out.println("BST Sorted Insert Time (ns): " + bstSortedInsertTime);
        System.out.println("BST Shuffled Insert Time (ns): " + bstShuffledInsertTime);
        System.out.println("AVL Sorted Insert Time (ns): " + avlSortedInsertTime);
        System.out.println("AVL Shuffled Insert Time (ns): " + avlShuffledInsertTime);

        System.out.println("BST Sorted Search Time (ns): " + bstSortedSearchTime);
        System.out.println("BST Shuffled Search Time (ns): " + bstShuffledSearchTime);
        System.out.println("AVL Sorted Search Time (ns): " + avlSortedSearchTime);
        System.out.println("AVL Shuffled Search Time (ns): " + avlShuffledSearchTime);

        // Write CSV results to output.txt
        try {
            FileWriter writer = new FileWriter("output.txt", true); // true = append
            writer.write(numLines + "," + bstSortedInsertTime + "," + bstShuffledInsertTime + ","
                    + avlSortedInsertTime + "," + avlShuffledInsertTime + ","
                    + bstSortedSearchTime + "," + bstShuffledSearchTime + ","
                    + avlSortedSearchTime + "," + avlShuffledSearchTime + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to output.txt: " + e.getMessage());
        }
    }
}
