package de.htwg.se.farkle.aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.model.Game
import java.io.ByteArrayInputStream

class TuiSpec extends AnyWordSpec {
  "A TUI" should {
    "process 'r' to roll dice and 'q' to quit" in {
      val game = Game()
      val controller = Controller(game)
      val tui = TUI(controller)
      
      // Wir simulieren die Eingabe "r" gefolgt von "q" (mit Zeilenumbrüchen)
      val in = new ByteArrayInputStream("r\nq\n".getBytes)
      Console.withIn(in) {
        tui.run()
      }
      
      // Nach dem "r" sollte der Controller gewürfelt haben
      controller.game.dice.length should be(6)
    }
    
    "handle unknown input correctly and not change the game state" in {
      val game = Game()
      val controller = Controller(game)
      val tui = TUI(controller)
      
      // Wir simulieren eine falsche Eingabe und dann "q"
      val in = new ByteArrayInputStream("blabla\nq\n".getBytes)
      Console.withIn(in) {
        tui.run()
      }
      
      // Nichts sollte sich am Spielzustand geändert haben
      controller.game.dice should be(empty)
    }
  }
}