/*
@file: Movie.java
@description: Represents a movie with a rank, title, release year, and rating, and compares based on rank
@author: Neil Sawhney
@date: September 25, 2025
 */
public class Movie implements Comparable<Movie> {
    private int rank;
    private String title;
    private int year;
    private double rating;

    // Default constructor
    public Movie() {

    }

    // Initializes a Movie with given rank, title, year, and rating.
    public Movie(int rank, String title, int year, double rating) {
        this.rank = rank;
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    // Creates a new Movie by copying another Movie object
    public Movie(Movie other) {
        this.rank = other.rank;
        this.title = other.title;
        this.year = other.year;
        this.rating = other.rating;
    }

    // Getter methods to return each value
    public int getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    // Prints the string rep of the movie
    public String toString() {
        return "#" + rank + " " + title + " " + year + ", IMBD Rating: " + rating;
    }

    // Compares to see if the movie is the same or not
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        return this.rank == other.rank &&
                this.year == other.year &&
                Double.compare(this.rating, other.rating) == 0 &&
                this.title.equals(other.title);
    }

    // Compares based on rank
    public int compareTo(Movie other) {
        return Integer.compare(this.rank, other.rank);
    }
}
