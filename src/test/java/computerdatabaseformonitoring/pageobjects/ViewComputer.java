//Included to package pageobjects in computerdatabaseformonitoring
package computerdatabaseformonitoring.pageobjects;

//Imports of gatling libraries
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

//Created class ViewComputer with one method - load finded computer details by its ID
public final class ViewComputer {

    //Functional action (method) - "OpenFindedComputer"
    public static final ChainBuilder OpenFindedComputer =
            exec(http("Method GET /computers/{ID} - Load founded computer details by its ID")
                    //Exec GET-method with save URL-address in FilterComputer-class
                    .get("#{computerURL}")

                    //Check that http status-code is 200 in response
                    .check(status().is(200)));
}