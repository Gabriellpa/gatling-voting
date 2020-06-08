package Simulates

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class VoteSimulateTest extends Simulation{

  val jsonCpfs = jsonFile("data/cpfs.json")

  val httpConfig =  http.baseUrls("http://localhost:8080")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val voteHttp = http("Vote in an Agenda")
    .post("/api/associate/vote")
    .body(StringBody(
      """{
      "agendaId":  9,
      "vote":  "SIM",
      "cpf":  "${cpf}"
  }"""
    )).asJson
    .check(status.is(200))

  val voteIn = scenario("Scenario to vote in agenda !IMPORTANT THE SESSIONS MUST BE OPEN")
    .feed(jsonCpfs)
    .exec(voteHttp)
    .pause(3)
  setUp(
    voteIn.inject(atOnceUsers(100))
  ).protocols(httpConfig)

}
