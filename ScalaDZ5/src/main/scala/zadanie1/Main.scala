import zadanie1._

object Main extends App {

  def feedFish[A <: Fish](f: Fish) = println(s"Fish ${f} has fed")

  feedFish(Crucian()) // Fish Crucian has fed
  feedFish(Shark())   // Fish Shark has fed
  feedFish(Salmon())  // Fish Salmon has fed
  //  feedFish(Tiger())        // NOPE
  //  feedFish(Python())       // NOPE
}
