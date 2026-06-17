package videogamedb;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class VideoGameDbSimulation extends Simulation {
    // Http configuration
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://videogamedb.uk/api")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Feeder for test data
    private static FeederBuilder.FileBased<Object> jsonFeeder = jsonFile("data/gameJsonFile.json").random();

    // HTTP calls
    private static ChainBuilder getAllGames =
            exec(http("Get all games")
                .get("/videogame"));

    private static ChainBuilder authenticate =
            exec(http("Authenticate")
                    .post("/authenticate")
                    .body(StringBody("{\n" +
                            "  \"password\": \"admin\",\n" +
                            "  \"username\": \"admin\"\n" +
                            "}"))
                    .check(jmesPath("token").saveAs("jwToken")));

    private static ChainBuilder createGame =
            feed(jsonFeeder)
                    .exec(http("Create a new game - #{name}")
                    .post("/videogame")
                    .header("Authorization", "Bearer #{jwToken}")
                    .body(ElFileBody("bodies/newGameTemplate.json")).asJson());

    private static ChainBuilder getLastGame =
            exec(http("Get last game - #{name}")
                    .get("/videogame/#{id}")
                    .check(jmesPath("name").isEL("#{name}")));

    private static ChainBuilder deleteLastGame =
            exec(http("Delete game - #{name}")
                    .delete("/videogame/#{id}")
                    .header("Authorization", "Bearer #{jwToken}")
                    .check(bodyString().is("Video game deleted")));


    // Scenario Definition
    // 1. Get all video games
    // 2. Authenticate
    // 3. Create a new game
    // 4 Get details of newly created game
    // 5. Delete newly created game
    private ScenarioBuilder scn = scenario("Video Game BD Stress Test")
            .exec(getAllGames)
            .pause(2)
            .exec(authenticate)
            .pause(2)
            .exec(createGame)
            .pause(2)
            .exec(getLastGame)
            .pause(2)
            .exec(deleteLastGame);

    // Load Simulation
    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
