package de.htwg.se.farkle.controller

trait GameState {
  def rollDice(controller: Controller): Unit
  def keep(controller: Controller, indices: List[Int]): Unit
  def bank(controller: Controller): Unit
}

class RollingState extends GameState {
  override def rollDice(controller: Controller): Unit = {
    val oldGame = controller.game
    val oldState = controller.state
    
    var newGame = controller.game.rollActive()
    var newState: GameState = new KeepingState()
    
    // Bust-Check
    if (newGame.dice.nonEmpty && controller.evaluator.evaluate(newGame.dice) == 0) {
      newGame = newGame.nextPlayer()
      newState = new RollingState()
    }
    
    // Änderung über das Command Pattern ausführen!
    controller.undoManager.doStep(new SetCommand(controller, oldGame, oldState, newGame, newState))
    controller.notifyObservers()
  }

  override def keep(controller: Controller, indices: List[Int]): Unit = {
    // Ignorieren im RollingState
  }

  override def bank(controller: Controller): Unit = {
    if (controller.game.turnScore > 0) {
      controller.doBank()
    }
  }
}

class KeepingState extends GameState {
  override def rollDice(controller: Controller): Unit = {
    // Ignorieren, er muss erst Würfel behalten
  }

  override def keep(controller: Controller, indices: List[Int]): Unit = {
    controller.doKeep(indices)
  }

  override def bank(controller: Controller): Unit = {
    // Ignorieren, er muss die liegenden Würfel erst auswerten
  }
}