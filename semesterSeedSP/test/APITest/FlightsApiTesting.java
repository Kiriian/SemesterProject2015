package APITest;

import static com.jayway.restassured.RestAssured.basePath;
import static com.jayway.restassured.RestAssured.baseURI;
import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.when;
import com.jayway.restassured.parsing.Parser;
import static org.hamcrest.Matchers.equalTo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FlightsApiTesting
{



    public FlightsApiTesting()
    {
        
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        baseURI = "http://localhost:8080";
        defaultParser = Parser.JSON;
        basePath = "semesterSeedSP/api";
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
      
    }

    @Test
    public void getFlights()
    {
            
        when()
            .get("/request/CPH/2016-01-04T00:00:00.000Z/1")
        .then().
            statusCode(200).
            body("[0].destination", equalTo("SXF"));       
    }    
    @Test
    public void getFlights2()
    {
        when()
            .get("/request/CPH/2017-01-04T00:00:00.000Z/1")
        .then().
            statusCode(404);
    }    
     @Test
    public void getFlightsMPJ()
    {
        when()
            .get("/flightinfo/CPH/MAD/2015-12-28T00:00:00.000Z/1")
        .then().
            statusCode(200).
        body("flights[0].flightID", equalTo("MPJx103x2015-12-28T16:00:00.000Z")); 
    } 
     @Test
    public void getFlightsMPJError()
    {
        when()
            .get("/flightinfo/CPH/MAD/2016-02-26T00:00:00.000Z/1")
        .then().
            statusCode(404);
        
    }
    
} 
    
