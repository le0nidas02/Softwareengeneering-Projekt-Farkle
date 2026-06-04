package de.htwg.se.farkle.controller

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.model.Game
import de.htwg.se.farkle.util.Observer

class ControllerSpec extends AnyWordSpec {
  "A Controller" should {
    "notify its observers after rolling the dice" in {
      val game = Game()
      val controller = Controller(game)
      
      // Wir bauen uns einen falschen Beobachter, der nur prüfen soll, ob er gerufen wird
      var notified = false
      val testObserver = new Observer {
        override def update(): Unit = notified = true
      }
      controller.add(testObserver)
      
      // Aktion ausführen
      controller.rollDice()
      
      // Assertions (Prüfungen)
      notified should be(true)
      controller.game.dice.length should be(6)
    }
  }
}