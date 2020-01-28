package ru.example.blog

import akka.actor.ActorRef
import akka.stream.KillSwitch

case class WsCommentSource(source: ActorRef, killSwitch: KillSwitch)
