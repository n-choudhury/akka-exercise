package part2

import akka.actor.{ActorSystem, Props}
import part2.actors.Ping

object Main extends App {
  val actorSystem = ActorSystem("PingPong")
  val ping = actorSystem.actorOf(Props[Ping], "actorPing")
  val maxMessage = 10000

  case class End(receivedPings : Int)
  case class GetPongSum(sum: Option[Int])
  case class ThrowException()

  ping ! "Hello"
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

/**
 * ------------------------------- Point No. 8 explanation ----------------------------
 * Wrapping in Future causes to execute from a different thread than the actor being executed.
 * The state of the parent actor inside the Future block is not deterministic.
 */

/**
 * ------------------------------- Point No. 11(g) explanation ----------------------------
 * When Pong throws exception, it restarts itself, therefore Pong state is reinitialized.
 * the pong actorRef is still available with Ping and is mapped to restarted state of the Pong.
 * That's why the sum is not 10000 and also, Pong actor is active.
 * */