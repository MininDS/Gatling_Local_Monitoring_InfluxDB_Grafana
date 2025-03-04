//Included to package pageobjects in computerdatabaseformonitoring
package computerdatabaseformonitoring.pageobjects;

//Imports of gatling libraries
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

//Created class HomePage with one method - Load ComputerDataBase HomePage
public final class HomePage {

    public static final ChainBuilder LoadHomePage =
                    //Load home page with search options for system user
                    exec(http("Method GET /computers - Load ComputerDataBase HomePage")
                            .get("/computers")

                            //Check that http status-code is 200 in response
                            .check(status().is(200)));
}