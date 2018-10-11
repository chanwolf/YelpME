/**
 * Handle the data returned by FoodServlet
 * @param resultDataString jsonObject
 */
function handleSearchResults(resultDataString) {
    resultDataJson = JSON.parse(resultDataString);

    console.log("handle login response");
    console.log(resultDataJson);
    console.log(resultDataJson["alias"]);

    // If login success, redirect to index.html page
    if (resultDataJson["price"] ==  "$$") {
    	
    	
    	jQuery.get("randomRestaurant.html", function(data) {
    	    var $doc = $(data);
    	    var $d = $doc.find;
    	});
    	
        window.location.replace("randomRestaurant.html");
    }
    // If login fail, display error message on <div> with id "login_error_message"
    else {
    
        console.log("show error message");
        jQuery("#test").text("LOL THIS DOES NO WORK AT ALL");
    }
}



/**
 * Submit the form content with POST method
 * @param formSubmitEvent
 */
function submitSearchForm(formSubmitEvent) {
    console.log("submitting search form");

    // Important: disable the default action of submitting the form
    //   which will cause the page to refresh
    //   see jQuery reference for details: https://api.jquery.com/submit/
    formSubmitEvent.preventDefault();

    jQuery.get(
        "api/food",
        // Serialize the login form to the data sent by POST request
        jQuery("#indexSearch").serialize(),
        (resultDataString) => handleSearchResults(resultDataString));

}





// Bind the submit action of the form to a handler function
jQuery("#indexSearch").submit((event) => submitSearchForm(event));