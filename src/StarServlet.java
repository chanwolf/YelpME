

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// this annotation maps this Java Servlet Class to a URL
@WebServlet("/stars")
public class StarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StarServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // change this to your own mysql username and password
		String loginUser = "mytestuser";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://ec2-52-14-48-253.us-east-2.compute.amazonaws.com:3306/moviedb";
		
        // set response mime type
        response.setContentType("text/html"); 

        // get the printwriter for writing response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>ZotFlix</title></head>");
        
        
        try {
        		Class.forName("com.mysql.jdbc.Driver").newInstance();
        		// create database connection
        		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        		// declare statement
        		Statement statement = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, 
        				   				ResultSet.CONCUR_READ_ONLY);
        		// prepare query
        		
        		String query = "SELECT movDesc.title,movDesc.year,movDesc.director,movDesc.rating, stars.name AS 'Actor', genres.name AS 'Genre' " + 
        				"FROM(SELECT mov.title, mov.year,mov.director,movRatings.rating, mov.id " + 
        				"FROM movies AS mov, ratings AS movRatings " + 
        				"WHERE mov.id = movRatings.movieId " + 
        				"ORDER BY movRatings.rating DESC " + 
        				"LIMIT 20) AS movDesc, genres, stars, stars_in_movies, genres_in_movies " +  
        				"WHERE movDesc.id = stars_in_movies.movieId AND stars.id = stars_in_movies.starId AND movDesc.id = genres_in_movies.movieId AND  genres.id = genres_in_movies.genreId";
        		// execute query
        		ResultSet resultSet = statement.executeQuery(query);

        		out.println("<body>");
        		out.println("<h1>MovieDB Stars</h1>");
        		
        		out.println("<table border>");
        		
        		// add table header row
        		out.println("<tr>");
        		out.println("<th>Movie Title</th>");
        		out.println("<th>Year</th>");
        		out.println("<th>Director</th>");
        		out.println("<th>List of Genres</th>");
        		out.println("<th>List of Actors</th>");
        		out.println("<th>Rating</th>");
        		out.println("</tr>");
        		
        		// add a row for every star result
        		while (resultSet.next()) {
        			// get a star from result set
        			String movieName = resultSet.getString("title");
        			String movieYear = resultSet.getString("year");
        			String movieDirector = resultSet.getString("director");
        			String movieRating = resultSet.getString("rating");
//        			while(resultSet.next().getString('title') )
        			String movieGenres = resultSet.getString("Genre");
        			String movieActors = resultSet.getString("Actor");
        			
        			
        			
        				
        			
        			
        			out.println("<tr>");
        			out.println("<td>" + movieName + "</td>");
//        			out.println("<td>" + starName + "</td>");
//        			out.println("<td>" + birthYear + "</td>");
        			out.println("</tr>");
        		}
        		
        		
        		
        		out.println("</table>");
        		
        		out.println("</body>");
        		
        		resultSet.close();
        		statement.close();
        		connection.close();
        		
        } catch (Exception e) {
        		/*
        		 * After you deploy the WAR file through tomcat manager webpage,
        		 *   there's no console to see the print messages.
        		 * Tomcat append all the print messages to the file: tomcat_directory/logs/catalina.out
        		 * 
        		 * To view the last n lines (for example, 100 lines) of messages you can use:
        		 *   tail -100 catalina.out
        		 * This can help you debug your program after deploying it on AWS.
        		 */
        		e.printStackTrace();
        		
        		out.println("<body>");
        		out.println("<p>");
        		out.println("Exception in doGet: " + e.getMessage());
        		out.println("</p>");
        		out.print("</body>");
        }
        
        out.println("</html>");
        out.close();
        
	}


}
