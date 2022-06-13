package part2.actors

import akka.actor.{Actor, ActorRef, Props}
import akka.event.{Logging, LoggingAdapter}
import part2.Main.{End, GetPongSum, ThrowException, maxMessage}

class Ping extends Actor {

  val log: LoggingAdapter = Logging(context.system, this)
  val pong: ActorRef = context.actorOf(Props[Pong], "actorPong")

  var sum: Int = 0

  (1 to maxMessage).foreach(_ =>
      pong ! "ping"
  )


  override def receive: Receive = {
    case "Hello" =>
      log.info("Starting actor Ping...")

    case "pong" =>
      //      log.info("pong")
      sum += 1

    case End(pongCount) =>
      log.info(s"Sum in ping: $sum")
      log.info(s"Counter of pong: $pongCount")

      /**
       * Point 11
       */

      pong ! GetPongSum(None)
      pong ! ThrowException()
      pong ! GetPongSum(None)


    case GetPongSum(optInt) =>
        log.info(s"GetPongSum : ${optInt.getOrElse(0)}")
//      context.system.terminate()
  }
}
