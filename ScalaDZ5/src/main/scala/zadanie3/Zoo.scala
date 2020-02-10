package zadanie3

import zadanie1._

class Zoo {

  def cleanFish[A <: Fish](zk: ZooKeeper[A]): Unit = println("Fishes was cleaned")

  def cleanSnake(zk: ZooKeeper[Snake]): Unit = println("Snakes was cleaned")

  def cleanCat(zk: ZooKeeper[Cat]): Unit = println("Cats was cleaned")

}
