package part2

import akka.actor.{ActorSystem, Props}
import part2.actors.Ping

object Main extends App {
  val actorSystem = ActorSystem("PingPong")
  val ping = actorSystem.actorOf(Props[Ping], "actorPing")
  val maxMessage = 10000

  case class End(receivedPings : Int)
  case class GetPongSum(sum: Option[Int])

  ping ! "Hello"
}
