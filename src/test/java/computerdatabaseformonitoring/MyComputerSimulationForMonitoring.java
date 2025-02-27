//Announced package with appropriate load testing classes and materials named "computerdatabaseformonitoring"
package computerdatabaseformonitoring;



//Announced some imports with gatling additional libraries
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;



//Created public class named "MyComputerSimulationForMonitoring" with load testing Simulation design - variables, methods, etc
public class MyComputerSimulationForMonitoring extends Simulation {


    //Created private class named "HttpProtocolBuilder" with field "httpProtocol" and value "http", some methods
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://computer-database.gatling.io")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .acceptEncodingHeader("gzip, deflate")
            .acceptLanguageHeader("ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
            .upgradeInsecureRequestsHeader("1")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36");


    //Created class-interface FeederBuilder.Batchable with field "searchFeeder" and its function "csv("data/search.csv").random()"
    FeederBuilder.Batchable searchFeeder =
            //Selects strings from search.csv file randomly
            csv("data/search.csv").random();


    //Functional action (method) - "searchForComputer"
    ChainBuilder searchForComputer =
            //Use searchFeeder above in this functional scenario
            feed(searchFeeder)

            //Step 1 - Load home page with search options for system user
            .exec(http("Method GET /computers - Load ComputerDataBase HomePage")
                .get("/computers")

                //Check that http status-code is 200 in response
                .check(status().is(200)))
                .pause(2)

            //Step 2 - Search computer by "searchCriterion" from searchFeeder-csv-file
            .exec(http("Method GET /computers?f=#{searchCriterion} - Filter computer by manufacturer")
                .get("/computers?f=#{searchCriterion}")

                //Check that http status-code is 200 in response
                .check(status().is(200))

                //Check that response has "searchComputerName" value from csv in href
                .check(css("a:contains('#{searchComputerName}')", "href")

                //Save response URL-address as "computerURL" local-variable
                .saveAs("computerURL")))
                .pause(2)

            //Step 3 - Open needed computer named from searchFeeder-csv-file (value of searchComputerName) with URL (computerURL) from step 2
            .exec(http("Method GET #{computerURL} - Load founded computer #{searchComputerName} details by its ID")
                .get("#{computerURL}")

                //Check that http status-code is 200 in response
                .check(status().is(200)))
                .pause(2);




    //Created class-ScenarioBuilder with field "users" includes scenario-variable named "Users" for typical user's actions in the system
    private ScenarioBuilder users = scenario ("Users")
            //Scenario "Users" executes typical action "searchForComputer" which was announced above
            .exec(searchForComputer);


    //Started MyComputerSimulationForMonitoring load testing with needed loading model:
        //***Basic setUp for script-debugging - Open model with 1 admin and 1 user, parallel executing of scenarios
        {
            setUp(
                //*Executes users-scenarios parallel with only 1 user injected in the system (OpenModel)
                users.injectOpen(atOnceUsers(1))
            )
            //*Use http-protocol during simulation
            .protocols(httpProtocol);
        }
}
