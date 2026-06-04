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
      
      val in = new ByteArrayInputStream("r\nq\n".getBytes)
      Console.withIn(in) {
        tui.run()
      }
      
      controller.game.dice.length should be(6)
    }
    
    "handle unknown input correctly and not change the game state" in {
      val game = Game()
      val controller = Controller(game)
      val tui = TUI(controller)
      
      val in = new ByteArrayInputStream("blabla\nq\n".getBytes)
      Console.withIn(in) {
        tui.run()
      }
      
      controller.game.dice should be(empty)
    }
  }
}