package Simulates

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetResultTest extends Simulation{

  val httpConfig =  http.baseUrls("http://localhost:8080")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val get = http("Get Vote result")
    .get("/api/associate/vote/result/9")

  val getVotes = scenario("Get result of votes for agenda 9")
    .exec(get)
    .pause(1)


  setUp(
    getVotes.inject(
      atOnceUsers(6),
      rampUsers(10) during(10),
      nothingFor(10),
      atOnceUsers(1),
      nothingFor(20),
      rampUsers(40) during(10),
      rampUsersPerSec(20) to 80 during(10),
      atOnceUsers(1000)
    )
  ).protocols(httpConfig)
}
