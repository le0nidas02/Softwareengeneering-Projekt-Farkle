package de.htwg.se.farkle.model

object Evaluator {
  
  def evaluate(dice: List[Dice]): Int = {
    val values = dice.map(_.value).sorted

    // KCD-Straßen-Check
    if (values == List(1, 2, 3, 4, 5, 6)) 1500
    else if (values == List(1, 2, 3, 4, 5)) 500
    else if (values == List(2, 3, 4, 5, 6)) 750
    else {
      val counts = dice.groupBy(_.value).map { case (k, v) => (k, v.size) }
      counts.foldLeft(0) { case (score, (value, count)) =>
        score + scoreForGroup(value, count)
      }
    }
  }

  private def scoreForGroup(value: Int, count: Int): Int = {
    if (count >= 3) {
      val baseScore = if (value == 1) 1000 else value * 100
      val multiplier = 1 << (count - 3) 
      baseScore * multiplier
    } else {
      if (value == 1) count * 100
      else if (value == 5) count * 50
      else 0
    }
  }
}