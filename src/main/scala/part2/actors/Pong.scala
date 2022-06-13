package part2.actors

import akka.actor.{Actor, Props}
import akka.event.Logging
import part2.Main.{End, maxMessage}
import part2.actors.{PongChild, WorkDone}

import scala.concurrent.Future

case object WorkDone

class Pong extends Actor {
  val log = Logging(context.system, this)
  var sum: Int = 0


  override def receive: Receive = {
    case "ping" =>
//      log.info("ping")
      sum += 1
      if (sum < maxMessage) {
        context.parent ! "pong"
      }
      else if (sum == maxMessage) context.parent ! End(sum)

//      val pongChild = context.actorOf(Props[PongChild], s"pongChild_${System.nanoTime()}")
//      pongChild ! "doWork"

//    case WorkDone =>
//      //      log.info(s"[WorkDone] sum = $sum")
//      sum += 1
//      if (sum < maxMessage) {
//        context.parent ! "pong"
//      }
//      else if (sum == maxMessage) context.parent ! End(sum)
  }

  //  def doWork(): Int = {
  //    Thread sleep 1000
  //    1
  //  }


  /**
   * ------------------------------- Point No. 7 explanation ----------------------------
   * Incrementing sum inside a Future block executes in a separate thread(outside of the scope of the actor)
   * The thread sleeps for a second (until then sum does not get incremented) by the time all ping messages
   * most likely been processed by the actor and hence could not get END.
   *
   * ------------------------------- Possible solutions: --------------------------------
   * a. Using another actor to delegate heavy computation and avoid blocking the thread where the actor being executed.
   * b. Passing sum as method parameter in actor state instead of mutable sate.
   *
   */
}
