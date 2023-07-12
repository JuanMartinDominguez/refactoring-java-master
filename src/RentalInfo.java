import java.util.HashMap;

public class RentalInfo {

	public String statement(Customer customer) {
	    HashMap<String, Movie> movies = createMoviesMap();
	    
	    double totalAmount = 0;
	    int frequentEnterPoints = 0;
	    StringBuilder result = new StringBuilder("Rental Record for ").append(customer.getName()).append("\n");
	    
	    for (MovieRental r : customer.getRentals()) {
	        double thisAmount = calculateAmount(r, movies);
	        frequentEnterPoints += calculateFrequentEnterPoints(r, movies);

	        //print figures for this rental
	        result.append("\t").append(movies.get(r.getMovieId()).getTitle()).append("\t").append(thisAmount).append("\n");
	        totalAmount += thisAmount;
	    }

	    // add footer lines
	    result.append("Amount owed is ").append(totalAmount).append("\n");
	    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

	    return result.toString();
	}

	private HashMap<String, Movie> createMoviesMap() {
	    HashMap<String, Movie> movies = new HashMap<>();
	    String regular = "regular";
	    movies.put("F001", new Movie("You've Got Mail", regular));
	    movies.put("F002", new Movie("Matrix", regular));
	    movies.put("F003", new Movie("Cars", "childrens"));
	    movies.put("F004", new Movie("Fast & Furious X", "new"));
	    return movies;
	}

	private double calculateAmount(MovieRental rental, HashMap<String, Movie> movies) {
	    Movie movie = movies.get(rental.getMovieId());
	    double thisAmount = 0;

	    if (movie.getCode().equals("regular")) {
	        thisAmount = 2;
	        if (rental.getDays() > 2) {
	            thisAmount += (rental.getDays() - 2) * 1.5;
	        }
	    } else if (movie.getCode().equals("new")) {
	        thisAmount = rental.getDays() * 3.00;
	    } else if (movie.getCode().equals("childrens")) {
	        thisAmount = 1.5;
	        if (rental.getDays() > 3) {
	            thisAmount += (rental.getDays() - 3) * 1.5;
	        }
	    }

	    return thisAmount;
	}

	private int calculateFrequentEnterPoints(MovieRental rental, HashMap<String, Movie> movies) {
	    Movie movie = movies.get(rental.getMovieId());
	    int frequentEnterPoints = 1;

	    if (movie.getCode().equals("new") && rental.getDays() > 2) {
	        frequentEnterPoints++;
	    }

	    return frequentEnterPoints;
	}
}