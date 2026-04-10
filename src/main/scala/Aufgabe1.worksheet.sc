case class Player(name:String, points:Int) {

}
case class Dice() {
  val random= new scala.util.Random
  def roll():Int = random.between(1,7)
}

case class Round() {

}

val babysFirstDiceRoll = Dice()
babysFirstDiceRoll.roll()