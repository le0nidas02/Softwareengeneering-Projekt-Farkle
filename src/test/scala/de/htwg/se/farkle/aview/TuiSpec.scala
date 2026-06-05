package de.htwg.se.farkle.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.controller.{Controller, KeepingState}
import de.htwg.se.farkle.model.{Game, Dice}
import java.io.ByteArrayInputStream

class TuiSpec extends AnyWordSpec {
  "A TUI" should {
    
    "process 'r' to roll dice and 'q' to quit" in {
      val controller = new Controller(Game())
      val tui = TUI(controller)
      val in = new ByteArrayInputStream("r\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      controller.game.dice.nonEmpty should be(true)
    }

    "process 'b' to bank the score" in {
      val controller = new Controller(Game())
      val tui = TUI(controller)
      
      // FIX: Wir geben dem Spieler 100 Punkte, damit das State Pattern das Sichern erlaubt!
      controller.game = controller.game.copy(turnScore = 100)
      
      val in = new ByteArrayInputStream("b\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      // Nach einem Bank-Befehl ist sofort Player 2 dran
      controller.game.currentPlayer.name should be("Player 2")
    }

    "process 'k 1 2' to keep dice" in {
      val controller = new Controller(Game())
      val tui = TUI(controller)
      
      // FIX: Damit 'keep' funktioniert, müssen wir im KeepingState sein und Würfel haben
      controller.state = new KeepingState()
      controller.game = controller.game.copy(dice = List(Dice(1), Dice(5), Dice(2)))
      
      val in = new ByteArrayInputStream("k 1 2\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      // Score sollte sich auf 150 aktualisieren
      controller.game.turnScore should be(150)
    }

    "handle invalid keep input like 'k a b' without crashing" in {
      val controller = new Controller(Game())
      val tui = TUI(controller)
      val in = new ByteArrayInputStream("k a b\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
    }

    "handle completely unknown input correctly" in {
      val controller = new Controller(Game())
      val tui = TUI(controller)
      val in = new ByteArrayInputStream("blabla\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
    }
  }
}