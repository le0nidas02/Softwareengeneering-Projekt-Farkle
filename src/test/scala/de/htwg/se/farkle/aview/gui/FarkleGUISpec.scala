package de.htwg.se.farkle.aview.gui

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.controller.{Controller, KeepingState}
import de.htwg.se.farkle.model.{Game, Dice}

class FarkleGUISpec extends AnyWordSpec {
  "A FarkleGUI" should {
    "update labels correctly" in {
      val controller = new Controller(Game())
      val gui = new FarkleGUI(controller)
      gui.update()
      gui.turnLabel.text should include("Player 1")
    }

    "trigger roll on button click" in {
      // Wir bauen einen falschen Evaluator, der immer 100 Punkte gibt (verhindert zufälligen Bust!)
      val winEvaluator = new de.htwg.se.farkle.model.Evaluator {
        override def evaluate(dice: List[Dice]): Int = 100
      }
      val controller = new Controller(Game(), winEvaluator)
      val gui = new FarkleGUI(controller)
      
      gui.rollButton.peer.doClick()
      
      // Jetzt liegen garantiert immer 6 Würfel auf dem Tisch
      controller.game.dice.length should be(6)
    }

    "trigger keep on button click for all selected dice" in {
      val controller = new Controller(Game(dice = List(Dice(1), Dice(5), Dice(2))))
      controller.state = new KeepingState()
      val gui = new FarkleGUI(controller)
      
      // Wir wählen Würfel 1 (Wert 1) und Würfel 2 (Wert 5) manuell aus
      gui.dicePanel.selectedIndices += 1
      gui.dicePanel.selectedIndices += 2
      
      // Klick auf den Keep-Button schickt beide gleichzeitig ab
      gui.keepButton.peer.doClick()
      
      // 100 Punkte (für die 1) + 50 Punkte (für die 5) = 150 Punkte!
      controller.game.turnScore should be(150)
    }

    "trigger bank on button click" in {
      val controller = new Controller(Game())
      controller.game = controller.game.copy(turnScore = 100)
      val gui = new FarkleGUI(controller)
      gui.bankButton.peer.doClick()
      controller.game.currentPlayer.name should be("Player 2")
    }

    "trigger undo and redo via menu actions" in {
      val controller = new Controller(Game())
      val gui = new FarkleGUI(controller)
      gui.undoAction.apply()
      gui.redoAction.apply()
      controller.game.turnScore should be(0)
    }

    "trigger exit via menu action without crashing" in {
      val controller = new Controller(Game())
      val gui = new FarkleGUI(controller)
      gui.exitAction.apply()
      gui.visible should be(false)
    }

    "show error label on invalid keep" in {
        val controller = new Controller(Game(dice = List(Dice(1), Dice(2))))
        controller.state = new KeepingState()
        val gui = new FarkleGUI(controller)

        gui.dicePanel.selectedIndices += 1 // Dice(1) -> valid
        gui.dicePanel.selectedIndices += 2 // Dice(2) -> invalid (blinder Passagier)

        gui.keepButton.peer.doClick()
        gui.infoLabel.text should include("Ungültig")
    }

    "show hot dice label when all active dice are kept" in {
        val controller = new Controller(Game(dice = List(Dice(1)), activeDice = 1))
        controller.state = new KeepingState()
        val gui = new FarkleGUI(controller)

        gui.dicePanel.selectedIndices += 1
        gui.keepButton.peer.doClick()
        gui.infoLabel.text should include("HOT DICE")
    }
    "do nothing and show no error if keep is clicked without selection" in {
      val controller = new Controller(Game())
      val gui = new FarkleGUI(controller)
      
      // Wir klicken auf Keep, ohne vorher Würfel auszuwählen
      gui.keepButton.peer.doClick()
      
      // Es soll kein Error-Text erscheinen
      gui.infoLabel.text should be(" ")
    }
  }
}