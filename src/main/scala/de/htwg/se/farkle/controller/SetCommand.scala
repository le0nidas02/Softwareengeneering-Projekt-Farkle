package de.htwg.se.farkle.controller

import de.htwg.se.farkle.util.Command
import de.htwg.se.farkle.model.Game

class SetCommand(controller: Controller, oldGame: Game, oldState: GameState, newGame: Game, newState: GameState) extends Command {
  override def doStep(): Unit = {
    controller.game = newGame
    controller.state = newState
  }
  
  override def undoStep(): Unit = {
    controller.game = oldGame
    controller.state = oldState
  }
  
  override def redoStep(): Unit = {
    controller.game = newGame
    controller.state = newState
  }
}