package de.htwg.se.farkle.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.model.{Game, Player, Dice}
import de.htwg.se.farkle.util.Observer

class ControllerSpec extends AnyWordSpec {
  "A Controller" should {
    "notify its observers after rolling the dice" in {
      val controller = Controller(Game())
      var notified = false
      controller.add(new Observer { override def update(): Unit = notified = true })
      
      controller.rollDice()
      notified should be(true)
      controller.game.dice.length should be(6)
    }

    "keep selected dice, evaluate them, and update the turn score" in {
      // Wir bauen ein Spiel mit absichtlichen Würfeln: 1 (Index 0) und 5 (Index 1)
      val game = Game(dice = List(Dice(1), Dice(5), Dice(2), Dice(3)))
      val controller = Controller(game)
      
      // Wir sagen dem Controller: Behalte Würfel 1 und 2 (also Index 0 und 1)
      controller.keep(List(1, 2))
      
      // Eine 1 (100) + eine 5 (50) = 150 Punkte
      controller.game.turnScore should be(150)
      controller.game.activeDice should be(4) // 6 minus 2 behaltene Würfel
    }

    "trigger Hot Dice and reset active dice to 6 if all dice are kept" in {
      val game = Game(dice = List(Dice(1)), activeDice = 1)
      val controller = Controller(game)
      
      controller.keep(List(1))
      
      // Hot Dice! Die activeDice müssen wieder auf 6 springen
      controller.game.activeDice should be(6)
    }

    "bank the current turn score to the player and switch turns" in {
      val game = Game(
        players = Vector(Player("P1", 1000), Player("P2", 0)),
        turnScore = 300,
        currentPlayerIndex = 0
      )
      val controller = Controller(game)
      
      controller.bank()
      
      // P1 sollte jetzt 1300 Punkte haben
      controller.game.players(0).score should be(1300)
      // Der Zug geht an P2
      controller.game.currentPlayer.name should be("P2")
      // Turn Score resettet
      controller.game.turnScore should be(0)
    }

    "switch to the next player if a bust is rolled (checkBust)" in {
      // Wir zwingen das Spiel in einen Bust-Zustand (nur 2er, 3er, 4er, 6er)
      val game = Game(dice = List(Dice(2), Dice(3), Dice(4), Dice(6)))
      val controller = Controller(game)
      
      controller.checkBust()
      
      // Der Spieler muss gewechselt haben, weil der Wurf 0 Punkte wert war
      controller.game.currentPlayer.name should be("Player 2")
    }
  }
}