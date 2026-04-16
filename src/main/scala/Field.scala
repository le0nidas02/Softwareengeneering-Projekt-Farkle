case class Field(dice: List[Dice]) {
  override def toString: String = {
    val diceStrings = dice.map(_.toString)
    val maxLines = diceStrings.map(_.split("\n").length).max
    
    val paddedDice = diceStrings.map { ds =>
      val lines = ds.split("\n")
      val padding = maxLines - lines.length
      lines ++ Array.fill(padding)(" " * lines(0).length)
    }

    (0 until maxLines).map { lineIdx =>
      paddedDice.map(d => d(lineIdx)).mkString("  ")
    }.mkString("\n")
  }
}


// Nur dafür da Ausgabe auf Konsole nicht mehr untereinander, sondern nebeneinander auszugeben