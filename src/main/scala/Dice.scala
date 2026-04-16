case class Dice(value: Int) {
  override def toString: String = {
    value match {
      case 1 => "[  *  ]"
      case 2 => "[*    ]" // Note: Kingdom Come style or standard? Standard 2 is diagonal
      case 3 => "[* * *]"
      case 4 => "[*   *]\n[*   *]" // Example multi-line representation
      case 5 => "[*   *]\n[  *  ]\n[*   *]"
      case 6 => "[* * *]\n[* * *]"
      case _ => "[     ]"
    }
  }
}

object Dice {
  private val random = new scala.util.Random
  def roll(): Dice = Dice(random.between(1, 7))
}
