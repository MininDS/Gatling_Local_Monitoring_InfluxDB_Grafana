//Included to package pageobjects in computerdatabaseformonitoring
package computerdatabaseformonitoring.pageobjects;

//Imports of gatling libraries
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

//Created class FilterComputer with one method - Filter computer by manufacturer
public final class FilterComputer {

    public static final FeederBuilder.Batchable<String> searchFeeder =
            //Selects strings from search.csv file randomly
            csv("data/search.csv").random();

    //Functional action (method) - "searchForComputerByManufacturer"
    public static final ChainBuilder searchForComputerByManufacturer =
            //Use searchFeeder above in this functional scenario
            feed(searchFeeder)

                    //Search computer by "searchCriterion" from searchFeeder-csv-file
                    .exec(http("Method GET /computers?f={computerManufacturerName} - Filter computer by manufacturer")
                            .get("/computers?f=#{searchCriterion}")

                            //Check that http status-code is 200 in response
                            .check(status().is(200))

                            //Check that response has "searchComputerName" value from csv in href
                            .check(css("a:contains('#{searchComputerName}')", "href")

                            //Save response URL-address as "computerURL" local-variable
                            .saveAs("computerURL")));
}