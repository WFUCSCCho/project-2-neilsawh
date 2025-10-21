/*
@file: Parser.java
@description: Handles reading commands from the input file, executing BST operations, and writing results to result.txt
@author: Neil Sawhney
@date: September 25, 2025
 */
import java.io.*;
import java.util.Scanner;

public class Parser {

    //Create a BST tree of Integer type
    private BST<Movie> mybst = new BST<>();

    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename));
    }

    // Implemented the process method
    // Removed redundant spaces for each input command
    public void process(File input) throws FileNotFoundException {
        Scanner scanner = new Scanner(input);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
                continue;
            else if (line.startsWith("insert")) {
                String movieData = line.substring(6).trim(); // after "insert "
                operate_BST(new String[]{"insert", movieData});
            } else if (line.startsWith("search")) {
                String rank = line.substring(6).trim(); // after "search "
                operate_BST(new String[]{"search", rank});
            } else if (line.startsWith("print")) {
                operate_BST(new String[]{"print"});
            } else {
                writeToFile("Invalid Command", "./result.txt");
            }

        }
        scanner.close();
    }

    // Execute BST operations based on parsed commands (insert, search, remove, print)
    public void operate_BST(String[] command) {
        try {
            switch (command[0]) {
                case "insert": {
                    Movie movie = parseMovie(command[1]);
                    mybst.insert(movie);
                    writeToFile("insert " + movie, "./result.txt");
                    break;
                }
                case "search": {
                    int rank = Integer.parseInt(command[1]);
                    Movie dummyMovie = new Movie(rank, "", 0, 0.0);
                    Node<Movie> node = mybst.search(dummyMovie);

                    if (node != null && node.getValue().getRank() == rank)
                        writeToFile("found " + rank, "./result.txt");
                    else
                        writeToFile("not found " + rank, "./result.txt");
                    break;
                }
                case "print":
                    writeToFile(mybst.inorder(), "./result.txt");
                    break;
                default:
                    writeToFile("Invalid Command", "./result.txt");
                    break;
            }
        } catch (Exception e) {
            writeToFile("Invalid Command", "./result.txt");
        }
    }

    // Implemented the writeToFile method
    // Generated the result file
    public void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Parses a CSV string into a Movie object.
    private Movie parseMovie(String movieString) {
        String[] parts = movieString.split(",", 4);
        int rank = Integer.parseInt(parts[0]);
        String title = parts[1];
        int year = Integer.parseInt(parts[2]);
        double rating = Double.parseDouble(parts[3]);
        return new Movie(rank, title, year, rating);
    }

}
