package de.htwg.se.farkle.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.model.Game
import java.io.ByteArrayInputStream

class TuiSpec extends AnyWordSpec {
  "A TUI" should {
    "process 'r' to roll dice and 'q' to quit" in {
      val controller = Controller(Game())
      val tui = TUI(controller)
      val in = new ByteArrayInputStream("r\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      controller.game.dice.length should be(6)
    }
    
    "process 'b' to bank the score" in {
      val controller = Controller(Game())
      val tui = TUI(controller)
      val in = new ByteArrayInputStream("b\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      // Nach einem Bank-Befehl ist sofort Player 2 dran
      controller.game.currentPlayer.name should be("Player 2")
    }

    "process 'k 1 2' to keep dice" in {
      val controller = Controller(Game())
      val tui = TUI(controller)
      // Wir geben gültige Indizes ein
      val in = new ByteArrayInputStream("k 1 2\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      // Da das Game leer war, ändert sich der Score nicht, aber die Logik läuft fehlerfrei durch
      controller.game.turnScore should be(0)
    }

    "handle invalid keep input like 'k a b' without crashing" in {
      val controller = Controller(Game())
      val tui = TUI(controller)
      // Falsche Eingabe provoziert NumberFormatException, die abgefangen wird
      val in = new ByteArrayInputStream("k a b\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      // Zustand sollte unverändert sein
      controller.game.dice should be(empty)
    }
    
    "handle completely unknown input correctly" in {
      val controller = Controller(Game())
      val tui = TUI(controller)
      val in = new ByteArrayInputStream("blabla\nq\n".getBytes)
      Console.withIn(in) { tui.run() }
      controller.game.dice should be(empty)
    }
  }
}