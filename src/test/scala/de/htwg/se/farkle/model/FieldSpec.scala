package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FieldSpec extends AnyWordSpec {
  "A Field" should {
    "format dice correctly in a row" in {
      val field = Field(List(Dice(1), Dice(2)))
      val output = field.toString
      output should include("[  * ]") 
      output should include("[    *]") 
    }
    
    "handle multi-line dice representation" in {
      val field = Field(List(Dice(5)))
      val lines = field.toString.split("\n")
      lines.length should be(3)
    }
    
    "correctly pad dice with different line heights" in {
      val unevenField = Field(List(Dice(1), Dice(99))) 
      val result = unevenField.toString
      result should include ("[     ]") 
    }
  }
}