package part1.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.event.{Logging, LoggingAdapter}

class Ping extends Actor {

  val log: LoggingAdapter = Logging(context.system, this)
  val pong: ActorRef = context.actorOf(Props[Pong], "actorPong")

  pong ! "ping"

  override def receive: Receive = {

    case "pong" =>
      log.info("pong")
  }
}
