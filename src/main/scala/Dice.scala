case class Dice(value: Int) {
  override def toString: String = {
    value match {
      case 1 => "[     ]\n[  *  ]\n[     ]"

      case 2 => "[*    ]\n[     ]\n[    *]"

      case 3 => "[*    ]\n[  *  ]\n[    *]"

      case 4 => "[*   *]\n[     ]\n[*   *]"

      case 5 => "[*   *]\n[  *  ]\n[*   *]"

      case 6 => "[* * *]\n[     ]\n[* * *]"
      
      case _ => "[     ]"
    }
  }
}

object Dice {
  private val random = new scala.util.Random
  def roll(): Dice = Dice(random.between(1, 7))
}
