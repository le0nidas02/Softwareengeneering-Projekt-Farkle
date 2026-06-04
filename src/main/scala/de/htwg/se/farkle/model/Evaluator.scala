package de.htwg.se.farkle.model

object Evaluator {
  
  def evaluate(dice: List[Dice]): Int = {
    // Gruppiert die Würfel nach ihrem Wert und zählt, wie oft jeder vorkommt.
    // Beispiel: List(Dice(2), Dice(2), Dice(5)) wird zu Map(2 -> 2, 5 -> 1)
    val counts = dice.groupBy(_.value).map { case (k, v) => (k, v.size) }
    
    // foldLeft summiert die Punkte aller Gruppen funktional auf
    counts.foldLeft(0) { case (score, (value, count)) =>
      score + scoreForGroup(value, count)
    }
  }

  private def scoreForGroup(value: Int, count: Int): Int = {
    if (count >= 3) {
      val baseScore = if (value == 1) 1000 else value * 100
      val remainder = count - 3
      val remainderScore = if (value == 1) remainder * 100 else if (value == 5) remainder * 50 else 0
      baseScore + remainderScore
    } else {
      if (value == 1) count * 100
      else if (value == 5) count * 50
      else 0
    }
  }
}