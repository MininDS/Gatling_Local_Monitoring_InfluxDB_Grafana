//Package with appropriate load testing classes and materials named "computerdatabaseformonitoring"
package computerdatabaseformonitoring;


//Imports with gatling additional libraries
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


//Imports with pageobjects classes
import computerdatabaseformonitoring.pageobjects.HomePage;
import computerdatabaseformonitoring.pageobjects.FilterComputer;
import computerdatabaseformonitoring.pageobjects.ViewComputer;


//Created public class named "MyComputerSimulationForMonitoring" with load testing Simulation design
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

    //Created class-ScenarioBuilder with field "usersSearch" includes scenario named "Users search" for typical user's actions in the system
    private ScenarioBuilder usersSearch = scenario ("Users search")
            //Scenario "Users" executes typical actions - open homepage, search computer by manufacturer, view finded computer
            .exec(HomePage.LoadHomePage)
            .pause(2)
            .exec(FilterComputer.searchForComputerByManufacturer)
            .pause(2)
            .exec(ViewComputer.OpenFindedComputer)
            .pause(2);

    //Started MyComputerSimulationForMonitoring load testing with needed loading model:
        {
            //setUp(
                //*Open model with usersSearch - use 10 scenarios per sec during 15 second
                //usersSearch.injectOpen(constantUsersPerSec(10).during(15))
            //)
            //*Use http-protocol during simulation
            //.protocols(httpProtocol);

            setUp(
                    usersSearch.injectOpen(rampUsers(10).during(1),
                    nothingFor(1),
                    rampUsers(10).during(1),
                            nothingFor(1),
                            rampUsers(20).during(1),
                            nothingFor(1),
                            rampUsers(30).during(1),
                            nothingFor(1),
                            rampUsers(40).during(1),
                            nothingFor(1),
                            rampUsers(50).during(1),
                            nothingFor(1),
                            rampUsers(60).during(1),
                            nothingFor(1),
                            rampUsers(70).during(1)

            ).protocols(httpProtocol));

            //setUp(
                    //usersSearch.injectOpen(
                            //rampUsersPerSec(1).to(3).during(1),
                            //nothingFor(1),
                            //rampUsersPerSec(3).to(9).during(1),
                            //nothingFor(1),
                            //rampUsersPerSec(9).to(27).during(1),
                            //nothingFor(1),
                            //rampUsersPerSec(27).to(81).during(1)
                    //).protocols(httpProtocol));

            //setUp(
                    //usersSearch.injectOpen(
                            //rampUsersPerSec(0).to(1).during(1),
                            //nothingFor(1),
                            //constantUsersPerSec(1).during(1),
                            //nothingFor(1),
                            //rampUsersPerSec(2).to(4).during(1),
                            //nothingFor(1),
                            //constantUsersPerSec(4).during(1),
                            //nothingFor(1),
                            //rampUsersPerSec(4).to(8).during(1),
                            //nothingFor(1),
                            //constantUsersPerSec(8).during(1)
                    //).protocols(httpProtocol)
            //);
        }
}

