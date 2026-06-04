package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PlayerSpec extends AnyWordSpec {
  "A Player" should {
    "have a name and default score 0" in {
      val player = Player("Heinrich")
      player.name should be("Heinrich")
      player.score should be(0)
    }
    "be able to add score to his total returning a new Player object" in {
      val player = Player("Heinrich")
      val updatedPlayer = player.addScore(1000)
      updatedPlayer.score should be(1000)
      player.score should be(0) // Der alte Spieler bleibt unverändert (Immutable!)
    }
  }
}