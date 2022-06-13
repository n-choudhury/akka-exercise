package part1

import akka.actor.{ActorSystem, Props}
import part1.actors.Ping

object Main extends App {

  val actorSystem = ActorSystem("PingPong")
  val ping = actorSystem.actorOf(Props[Ping], "actorPing")

  ping ! "Hello"
}
