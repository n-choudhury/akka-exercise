package part2.actors

import akka.actor.{Actor, Props}
import akka.event.Logging
import part2.Main.{End, GetPongSum, ThrowException, maxMessage}

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
//        Future {
          context.parent ! "pong"
//        }
      } else if (sum == maxMessage) context.parent ! End(sum)

    case ThrowException() =>
      throw new Exception()

    case GetPongSum(optInt) =>
      sender() ! GetPongSum(Some(sum))
  }

    def doWork(): Int = {
      Thread sleep 10
      1
    }

}
