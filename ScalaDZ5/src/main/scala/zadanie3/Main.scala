package zadanie3

object Main extends App {
  def clearFish(zk: ZooKeeper[Fish]) = println("FishRoom has cleared")

  def clearSnake(zk: ZooKeeper[Snake]) = println("SnakeRoom has cleared")

  def clearCat(zk: ZooKeeper[Cat]) = println("CatRoom has cleared")

  val zkA: ZooKeeper[Animal] = new ZooKeeper[Animal] {}
  val zkF: ZooKeeper[Fish] = new ZooKeeper[Fish] {}
  val zkC: ZooKeeper[Cat] = new ZooKeeper[Cat] {}
  val zkS: ZooKeeper[Snake] = new ZooKeeper[Snake] {}

  clearFish(zkF)      // has cleared
  clearFish(zkA)      // has cleared
//  clearFish(zkS)      // NOPE
//  clearFish(zkC)      // NOPE

  clearCat(zkC)       // has cleared
  clearCat(zkA)       // has cleared
//  clearCat(zkF)       // NOPE
//  clearCat(zkS)       // NOPE

  clearSnake(zkS)     // has cleared
  clearSnake(zkA)     // has cleared
//  clearSnake(zkF)     // NOPE
//  clearSnake(zkC)     // NOPE
}

trait ZooKeeper[-A]
