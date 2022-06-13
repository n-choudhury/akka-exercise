package part2.actors

import akka.actor.Actor
import akka.event.Logging

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PongChild extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {

    case "doWork" =>
      val parentActor = context.parent

      val futureInt = Future {
        doWork()
      }
      parentActor ! WorkDone

  }

  def doWork(): Int = {
    Thread sleep 1000
    1
  }

}
