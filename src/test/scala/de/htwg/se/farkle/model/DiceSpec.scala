package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class DiceSpec extends AnyWordSpec {
  "A Dice" should {
    "have a value between 1 and 6" in {
      val d = Dice.roll()
      d.value should (be >= 1 and be <= 6)
    }
    "have a String representation for each value" in {
      Dice(1).toString should not be empty
      Dice(2).toString should not be empty
      Dice(3).toString should not be empty
      Dice(4).toString should not be empty
      Dice(5).toString should not be empty
      Dice(6).toString should not be empty
      Dice(0).toString should be("[     ]")
    }
  }
}