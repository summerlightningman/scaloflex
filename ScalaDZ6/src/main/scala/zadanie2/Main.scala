package zadanie2

object Main extends App {
  def giveMoney(avatar: Avatar) = Avatar(avatar.id, avatar.name, avatar.money + 100)

  def takeMoney(avatar: Avatar) = Avatar(avatar.id, avatar.name, avatar.money - 100)

  def changeName(avatar: Avatar) = Avatar(avatar.id, s"${avatar.name}_old", avatar.money)

  val avatars = Seq(
    Avatar(22, "Dmitry", 332),
    Avatar(23, "Alexey", 332),
    Avatar(24, "NOGEBATORRR888", 332),
    Avatar(25, "OrelDagestana95", 332),
    Avatar(26, "Sergey", 332),
    Avatar(27, "Scalodrom", 332)
  )

  val avatars1 = avatars.takeRight(3)
  val avatars2 = avatars.take(3)
  val avatars3 = for (i <- avatars.indices if (i % 2 == 0)) yield avatars(i)

  implicit val executor: OperationExecutor[Avatar] = new AvatarExecutor[Avatar]()

  println(executor.executeWithLock(avatars1)(giveMoney))
  println(executor.executeWithLock(avatars2)(takeMoney))
  println(executor.executeWithLock(avatars3)(changeName))
}











