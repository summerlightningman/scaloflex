package zadanie3

import zadanie1._


object Main extends App {
  val zoo: Zoo = new Zoo

  val catter = ZooKeeper[Cat]()
  val snaker = ZooKeeper[Snake]()
  val fisher = ZooKeeper[Fish]()

  val animaler = ZooKeeper[Animal]()

  zoo.cleanCat(catter)
//  zoo.cleanCat(fisher)
//  zoo.cleanCat(snaker)

  zoo.cleanSnake(snaker)
//  zoo.cleanSnake(catter)
//  zoo.cleanSnake(fisher)

  zoo.cleanFish(fisher)
  zoo.cleanFish(catter)
  zoo.cleanFish(snaker)

  println("\\/ this is a ZooKeeper[Animal] \\/")

  zoo.cleanCat(animaler)
  zoo.cleanSnake(animaler)
  zoo.cleanFish(animaler)
}
