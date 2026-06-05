package de.htwg.se.farkle.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.model.{Game, Player, Dice, Evaluator, KcdEvaluator}
import de.htwg.se.farkle.util.Observer

class ControllerSpec extends AnyWordSpec {
  "A Controller" should {
    
    "notify its observers after rolling the dice and change to KeepingState" in {
      val controller = new Controller(Game())
      var notified = false
      controller.add(new Observer { override def update(): Unit = notified = true })
      
      controller.rollDice()
      notified should be(true)
      controller.game.dice.length should be(6)
      // Nach einem gültigen Wurf müssen wir im KeepingState sein
      controller.state.isInstanceOf[KeepingState] should be(true)
    }

    "keep selected dice, evaluate them, update the turn score, and transition back to RollingState" in {
      val game = Game(dice = List(Dice(1), Dice(5), Dice(2), Dice(3)))
      val controller = new Controller(game)
      
      // WICHTIG: Wir versetzen den Controller manuell in den passenden Zustand für diesen Test
      controller.state = new KeepingState()
      
      controller.keep(List(1, 2)) // Behalte Würfel 1 und 5
      
      controller.game.turnScore should be(150)
      controller.game.activeDice should be(4)
      // Nach dem Behalten wechselt der Zustand wieder zu RollingState (bereit für den nächsten Wurf oder Bank)
      controller.state.isInstanceOf[RollingState] should be(true)
    }

    "trigger Hot Dice and reset active dice to 6 if all dice are kept" in {
      val game = Game(dice = List(Dice(1)), activeDice = 1)
      val controller = new Controller(game)
      
      controller.state = new KeepingState()
      controller.keep(List(1))
      
      controller.game.activeDice should be(6)
    }

    "bank the current turn score to the player and switch turns" in {
      val game = Game(
        players = Vector(Player("P1", 1000), Player("P2", 0)),
        turnScore = 300,
        currentPlayerIndex = 0
      )
      val controller = new Controller(game)
      
      controller.bank()
      
      controller.game.players(0).score should be(1300)
      controller.game.currentPlayer.name should be("P2")
      controller.game.turnScore should be(0)
      controller.state.isInstanceOf[RollingState] should be(true)
    }

    "switch to the next player if a bust (Farkle) is rolled" in {
      // Dank Strategy Pattern bauen wir einen Test-Evaluator, der IMMER 0 Punkte liefert
      val zeroEvaluator = new Evaluator {
        override def evaluate(dice: List[Dice]): Int = 0
      }
      
      val controller = new Controller(Game(), zeroEvaluator)
      
      // Wenn wir jetzt würfeln, schlägt der zeroEvaluator an -> Bust!
      controller.rollDice()
      
      // Der Zug muss sofort zum nächsten Spieler gewechselt haben
      controller.game.currentPlayerIndex should be(1)
      // Bei einem Bust bleibt das Spiel für den nächsten Spieler im RollingState
      controller.state.isInstanceOf[RollingState] should be(true)
    }

    "ignore keep in RollingState" in {
      val controller = new Controller(Game())
      controller.state = new RollingState()
      controller.keep(List(1))
      // Es darf nichts passiert sein
      controller.game.turnScore should be(0) 
    }

    "ignore bank in RollingState if turnScore is 0" in {
      val controller = new Controller(Game())
      controller.state = new RollingState()
      controller.bank()
      // Spieler darf nicht gewechselt haben, da er keine Punkte zum Sichern hatte
      controller.game.currentPlayer.name should be("Player 1")
    }

    "ignore rollDice in KeepingState" in {
      val controller = new Controller(Game(dice = List(Dice(1))))
      controller.state = new KeepingState()
      controller.rollDice()
      // Würfel dürfen sich nicht verändert haben
      controller.game.dice.length should be(1)
    }

    "ignore bank in KeepingState" in {
      val controller = new Controller(Game())
      controller.state = new KeepingState()
      controller.bank()
      // Spieler darf nicht gewechselt haben, da er im falschen Zustand ist
      controller.game.currentPlayer.name should be("Player 1")
    }
  }
}