package de.htwg.se.farkle.aview.gui

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.model.{Game, Dice}

class DicePanelSpec extends AnyWordSpec {
  "A DicePanel" should {
    "initialize with 6 hidden buttons" in {
      val controller = new Controller(Game())
      val panel = new DicePanel(controller)
      panel.diceButtons.length should be(6)
    }
    "redraw correctly based on game dice and toggle selection" in {
      val controller = new Controller(Game(dice = List(Dice(1), Dice(5))))
      val panel = new DicePanel(controller)
      panel.redraw()
      panel.diceButtons(0).visible should be(true)
      panel.diceButtons(0).text should be("1")
      
      // 1. Klick: Würfel auswählen
      panel.diceButtons(0).peer.doClick()
      panel.selectedIndices should contain(1)
      // Der Text bleibt gleich, aber das Set ändert sich!
      panel.diceButtons(0).text should be("1") 
      
      // 2. Klick: Würfel wieder abwählen
      panel.diceButtons(0).peer.doClick()
      panel.selectedIndices should not contain(1)
      panel.diceButtons(0).text should be("1")
    }
    "clear its selection cleanly" in {
      val controller = new Controller(Game())
      val panel = new DicePanel(controller)
      panel.selectedIndices += 1
      panel.clearSelection()
      panel.selectedIndices should be(empty)
    }
  }
}