package com.json.fix

import akka.actor.{ActorSystem, Props}
import com.json.fix.util.{AppLog, ApplicationLifecycle}


object Main extends ApplicationLifecycle with AppLog {
  private val applicationName = "test-spray"
  implicit val actorSystem    = ActorSystem(s"$applicationName-system")

  private[this] var started: Boolean = false

  def main(args:Array[String]) {
    start()
  }

  def start() {
    log.info(s"Starting $applicationName Service")

    if (!started) {
      val boot = actorSystem.actorOf(Props[ServiceRequest], "test-spray-service")

      boot ! Poll
      started = true
    }
  }

  def stop() {
    log.info(s"Stopping $applicationName Service")

    if (started) {
      started = false
      actorSystem.shutdown()
    }
  }
}
