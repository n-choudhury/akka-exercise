package part1.actors

import akka.actor.Actor
import akka.event.Logging

class Pong extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case "ping" =>
      log.info("ping")
      context.parent ! "pong"
  }
}
