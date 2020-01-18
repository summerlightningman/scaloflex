import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, MustMatchers}

import zadanie1._

class FilterSpec extends TestKit(ActorSystem("cars"))
  with FlatSpecLike
  with BeforeAndAfterAll
  with MustMatchers
  with ImplicitSender {

  "FilterActor" should "receive line" {
    val stream = new Stream
    val mappers = Seq(TestProbe().ref, TestProbe().ref)
    val mapShuffle = TestProbe()
    val filterActor = system.actorOf(stream.FilterActor.props(myFilter, mapShuffle.ref))
    val line = "01.09.2011,00001,FORD,FOCUS,2010,Т961ВН197,Другой цвет"
    filterActor ! line
    mapShuffle.expectMsg(line)
  }

  def myFilter(line: String): Boolean = line.split(',').toSeq.length == 7

  class Stream extends Filter with Mapper with Reducer with MapShuffle with ReduceShuffle with Master {
    override type Line = String

  }

}