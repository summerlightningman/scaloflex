package zadanie1

import org.scalatest.Suite
import org.scalatest.concurrent.{Signaler, ThreadSignaler, TimeLimits}
import org.scalatest.flatspec._
import org.scalatest.time.{Seconds, Span}
import zadanie1.Main._
import org.scalatest.time.SpanSugar._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class MainTest extends TimeLimits with Suite {
  feed()
}