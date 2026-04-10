import scala.util.Random

case class Player(name:String, points:Int) {

}
case class Dice() {
  val random= new scala.util.Random
  random.between(1,6)
}

case class Round() {

}