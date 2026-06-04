package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class GameSpec extends AnyWordSpec {
  "A Game" should {
    "start with 2 default players and active state" in {
      val game = Game()
      game.players.length should be(2)
      game.currentPlayer.name should be("Player 1")
      game.dice should be(empty)
      game.turnScore should be(0)
      game.activeDice should be(6)
    }
    
    "roll only the active amount of dice" in {
      val game = Game(activeDice = 4)
      val newGame = game.rollActive()
      newGame.dice.length should be(4)
      newGame.dice.foreach(d => d.value should (be >= 1 and be <= 6))
    }
    
    "switch to the next player and reset turn state" in {
      val game = Game(turnScore = 500, activeDice = 2, dice = List(Dice(1)))
      val newGame = game.nextPlayer()
      
      newGame.currentPlayer.name should be("Player 2")
      newGame.turnScore should be(0)
      newGame.activeDice should be(6)
      newGame.dice should be(empty)
    }
    
    "loop back to player 1 when player 2 is done" in {
      val game = Game(currentPlayerIndex = 1)
      val newGame = game.nextPlayer()
      newGame.currentPlayer.name should be("Player 1")
    }
  }
}