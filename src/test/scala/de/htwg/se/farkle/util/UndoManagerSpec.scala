package de.htwg.se.farkle.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class UndoManagerSpec extends AnyWordSpec {
  "An UndoManager" should {
    "have a do, undo and redo step" in {
      val undoManager = new UndoManager()
      
      var testState = 0
      val command = new Command {
        override def doStep(): Unit = testState += 1
        override def undoStep(): Unit = testState -= 1
        override def redoStep(): Unit = testState += 1
      }

      // Test doStep
      undoManager.doStep(command)
      testState should be(1)

      // Test undoStep
      undoManager.undoStep()
      testState should be(0)

      // Test leeres undoStep
      undoManager.undoStep()
      testState should be(0)

      // Test redoStep
      undoManager.redoStep()
      testState should be(1)

      // Test leeres redoStep
      undoManager.redoStep()
      testState should be(1)
    }
  }
}