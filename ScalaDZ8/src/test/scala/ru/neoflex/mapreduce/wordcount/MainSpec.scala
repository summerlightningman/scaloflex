package ru.neoflex.mapreduce.wordcount

import akka.actor.ActorSystem
import akka.testkit.TestKit
import ru.neoflex.mapreduce.MapReduce

class MainSpec extends TestKit(ActorSystem("word-count")){
  val mapreduce = new MapReduce[String, String, WordCount]

}
