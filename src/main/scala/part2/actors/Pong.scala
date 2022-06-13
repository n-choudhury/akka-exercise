package part2.actors

import akka.actor.{Actor, Props}
import akka.event.Logging
import part2.Main.{End, maxMessage}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class WorkDone(num: Int)

class Pong extends Actor {
  val log = Logging(context.system, this)
  var sum: Int = 0


  override def receive: Receive = {
    case "ping" =>
//      log.info("ping")
//      sum += 1

    val selfActor = self
      Future {
        doWork()
      }.map { num =>
        selfActor ! WorkDone(num)
      }
      // Point 7
//      Future {
//        sum += doWork()
//      }
//      if (sum < maxMessage) {
//        context.parent ! "pong"
//      } else if (sum == maxMessage) context.parent ! End(sum)


    case WorkDone(num) =>
      sum += num
      //      log.info(s"[WorkDone] sum = $sum")
      if (sum < maxMessage) {
        context.parent ! "pong"
      } else if (sum == maxMessage) context.parent ! End(sum)
  }

    def doWork(): Int = {
      Thread sleep 1000
      1
    }


  /**
   * ------------------------------- Point No. 7 explanation ----------------------------
   * Incrementing sum inside a Future block executes in a separate thread(outside of the scope of the actor)
   * By the time all ping messages been processed by the actor, sum value won't be synchronized and will not
   * get the final value to reach END.
   *
   * ------------------------------- Possible solutions: --------------------------------
   * a. Performing heavy computation in a separate and avoid blocking the thread where the actor being executed.
   *    mutating the state in a message handler (within actor scope)
   * b. Passing sum as method parameter in actor state instead of mutable sate.
   *
   */
}
