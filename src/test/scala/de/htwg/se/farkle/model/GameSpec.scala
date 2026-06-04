package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameSpec extends AnyWordSpec {
  "A Game" should {
    "be empty when created without dice" in {
      val game = Game()
      game.dice should be (empty)
    }
    
    "roll 6 new dice when rollAll() is called" in {
      val game = Game()
      val newGame = game.rollAll()
      
      newGame.dice.length should be (6)
      newGame.dice.foreach(d => d.value should (be >= 1 and be <= 6))
    }
  }
}