import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.google.gson.JsonObject;
import org.json.*;
import java.lang.Math.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@WebServlet("/FoodServlet")
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String yelpAPIKey = "JnzOa_GrYSJ41B0PPMxW8ST0ToYuQFrvLbgBcRQ16bCdTch4VfOF7A2ZnEV0-vcexLPCMeFA-uayDSbcxmWMan7ndtAeSi9Crc_CWlPV2vwnsR36jY-bzv0PucK7W3Yx";
    private final String yelpAPIHost = "https://api.yelp.com";
    private final String SEARCH_PATH = "/v3/businesses/search";
    
    public FoodServlet() {
        super();
    }
	
    
    public int getTotalSize(String apiCall) {
    	int resultSize;
    	HttpResponse<JsonNode> res = null;
    	try {
    		res = Unirest.get(apiCall)
    				.header("authorization",
    						"Bearer " + yelpAPIKey)
    				.asJson();
    	} catch (UnirestException e) {
    		e.printStackTrace();
    	}
    	JSONObject responseObj = res.getBody().getObject();
    	resultSize = responseObj.getInt("total");
    	
    	return resultSize;
    }
    
    public int getRandomInt(int maxOff) {
    	int random;
    	random = (int) (Math.random() * maxOff);
    	
    	return random;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cuisine = request.getParameter("cusine").replace(" ", "+");
    	String location = request.getParameter("location").replaceAll(" ", "");
    	int totalResult, maxOffset, limit, randIndex;
    	limit = 50;
    	// MAX POSSIBLE RESULTS IS 1000, so last page would be 950 if it goes over the 1000 cap
    	String apiURL = yelpAPIHost + SEARCH_PATH + "?term=" + cuisine + "&location="+location; // + "&offset=1&limit=50";
    	totalResult = getTotalSize(apiURL);
    	
    	if(totalResult >= 1000) {
    		maxOffset = 950;
    	}else {
    		maxOffset = totalResult - limit ; 
    	}
    	apiURL += "&offset=" + getRandomInt(maxOffset) + "&limit="+ limit;
    	
    	HttpResponse<JsonNode> res = null;
    	try {
    		res = Unirest.get(apiURL)
    				.header("authorization",
    						"Bearer " + yelpAPIKey)
    				.asJson();
    	} catch (UnirestException e) {
    		e.printStackTrace();
    	}
    	JSONObject responseObj = res.getBody().getObject();
    	JSONArray responseArray = responseObj.getJSONArray("businesses");
    	randIndex = getRandomInt(responseArray.length()-1);
    	
      //PrintWriter out = response.getWriter();
      //out.println("<html>");
      //out.println("<head><title>YelpME</title></head>");
      //out.println("<body><p>TOTAL RESULTS: " + totalResult + "<br />RANDOM OFFSET and SELECTOR: " + apiURL + "<p> " + randIndex + "</p></body></html>");
        

    	
    	
    	
    	JSONObject randomResturant = responseArray.getJSONObject(randIndex);
    	
    	
        request.setAttribute("randomRestJSON", randomResturant);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/selectedRestaurant.jsp");
        dispatcher.forward(request, response);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//      response.getWriter().write(randomResturant.toString());

        
	}

}
