import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class DiceSpec extends AnyWordSpec {
  "A Dice" should {
    "have a value between 1 and 6" in {
      val d = Dice.roll()
      d.value should (be >= 1 and be <= 6)
    }
    "Each Value should have a String representation" in {
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

class FieldSpec extends AnyWordSpec {
  "A Field" should {
    "format dice correctly in a row" in {
      val field = Field(List(Dice(1), Dice(2)))
      val output = field.toString
      output should include("[  *  ]")
      output should include("[*    ]")
    }
    "handle multi-line dice representation" in {
      val field = Field(List(Dice(5)))
      val lines = field.toString.split("\n")
      lines.length should be(3)
    }
  }
}
