<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*"%>
<%@ page import="java.io.*" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="com.google.gson.JsonParser" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="org.json.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Random Restaurant Picker</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.0/css/bootstrap.min.css" >
	
	<link rel="stylesheet" type="text/css" href="main.css">	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
	<!--  Creating the Navbar for the webpage -->
	<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #ff5050;">
	    <div class="d-flex flex-grow-1">
	        <span class="w-100 d-lg-none d-block"><!-- hidden spacer to center brand on mobile --></span>
	        <a class="navbar-brand d-none d-lg-inline-block" href="index.html">
	            YelpME
	        <i class="material-icons food">fastfood</i></a>
	        
	        <a class="navbar-brand-two mx-auto d-lg-none d-inline-block" href="#">
	            <img src="//placehold.it/40?text=LOGO" alt="logo">
	        </a>
	        <div class="w-100 text-right">
	            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#myNavbar">
	                <span class="navbar-toggler-icon"></span>
	            </button>
	        </div>
	    </div>
	    <div class="collapse navbar-collapse flex-grow-1 text-right" id="myNavbar">
	        <ul class="navbar-nav ml-auto flex-nowrap">
				<li class="nav-item">
			        <a class="nav-link" href="index.html">Home</a>
		       	</li>
		       	<li class="nav-item">
		      		<a class="nav-link" href="#">About Us</a>
			    </li>
			    <li class="nav-item">
		        	<a class="nav-link" href="#">Contact</a>
		      	</li>
	        </ul>
	    </div>
	</nav>
	
	
	
	<%
		JSONObject randomR = (JSONObject) request.getAttribute("randomRestJSON");
		String restName = randomR.getString("name");
		String restPrice = randomR.optString("price");
		String restPhone = randomR.optString("phone");
		String restURL = randomR.optString("image_url");
		double rating = randomR.getDouble("rating");
		Boolean closed = randomR.getBoolean("is_closed");
		JSONObject location = randomR.getJSONObject("location");
		String address = location.optString("address1") + ", " + location.optString("city") + ", " + location.optString("state") + " " + location.optString("zip_code");
		String yelpURL = randomR.optString("url");
		
		JSONArray category= randomR.getJSONArray("categories");
		
		StringBuilder categories = new StringBuilder();
		//ArrayList<String> categories = new ArrayList<String>();
		for(int i=0; i<category.length(); ++i){
			//categories.add(category.getJSONObject(i).getString("title"));
			categories.append(category.getJSONObject(i).getString("title"));
			if(i != category.length()-1)
				categories.append(", ");
		}
		
	%>
	<button class="refresh" onClick="window.location.reload()"><i class="fas fa-redo-alt fa-4x reload"></i></button>
	
	<!-- Card Dark -->
	<div class="card border-dark mb-3">
	
		<!-- Card Header -->
		<div class="card-header">
    		<%=restName %>
		</div>
		
		<!-- Card image -->
	  	<div class="view overlay">
	    	<img class="card-img-top" src="<%=restURL %>" alt="Random Restaurants Picture" >
	    <a>
	      <div class="mask rgba-white-slight"></div>
	    </a>
	  </div>
	
	  <!-- Card content -->
	  <div class="card-body ">
	
	    
	    <!-- Title -->
	    <div class="card-title-rating">
   		    <h6 class="card-title"><%=restPrice + " " +categories %> </h6>
   		    <%
   		    	double temp = rating;
   		    	int wholeRating = (int) temp; 
   		    	temp -= wholeRating;
   		    	if(temp == 0.0)
   		    		out.println("<span><i class='fa fa-star' aria-hidden='true'></i></span>");
   		    	else if(temp == 0.5)
   		    		out.println("<span><i class='fas fa-star-half-alt'></i></span>");
   		    	
   		    	
   		    	for(int i=0; i<rating-1 ; ++i){
   		    		out.println("<span><i class='fa fa-star' aria-hidden='true'></i></span>");
   		    	}
   		    	


   		    %>
	    	
	    </div>
	    <hr class="hr-light">
	    <!-- Text -->
	    <p class="card-text white-text mb-4">
	    	Address: <%=address %>
	    	<br />
	    	Phone: <%=restPhone %>
	    </p>
	    <!-- Link -->
	    <a href="<%=yelpURL %>" class="white-text d-flex justify-content-end"><h5>Read more <i class="fa fa-angle-double-right"></i></h5></a>
	
	  </div>
	</div>
	<!-- Card Dark Outline -->
	

	


</body>

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>