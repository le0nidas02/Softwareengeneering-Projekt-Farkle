package de.htwg.se.farkle.controller

trait GameState {
  def rollDice(controller: Controller): Unit
  def keep(controller: Controller, indices: List[Int]): Unit
  def bank(controller: Controller): Unit
}

class RollingState extends GameState {
  override def rollDice(controller: Controller): Unit = {
    controller.game = controller.game.rollActive()
    
    // Bust-Check (Farkle)
    if (controller.game.dice.nonEmpty && controller.evaluator.evaluate(controller.game.dice) == 0) {
      controller.game = controller.game.nextPlayer()
      // Bei einem Bust bleibt der nächste Spieler im RollingState
    } else {
      // Gültiger Wurf -> Zustand wechseln! Er muss jetzt Würfel behalten
      controller.state = new KeepingState()
    }
    controller.notifyObservers()
  }

  override def keep(controller: Controller, indices: List[Int]): Unit = {
    // Ignorieren, da noch nicht gewürfelt wurde
  }

  override def bank(controller: Controller): Unit = {
    if (controller.game.turnScore > 0) {
      controller.doBank()
      // Nach dem Sichern ist der nächste Spieler dran und muss würfeln
      controller.state = new RollingState()
    }
  }
}

class KeepingState extends GameState {
  override def rollDice(controller: Controller): Unit = {
    // Ignorieren, er muss erst Würfel behalten
  }

  override def keep(controller: Controller, indices: List[Int]): Unit = {
    controller.doKeep(indices)
    // Nachdem er Würfel behalten hat, darf er wieder entscheiden: roll oder bank
    controller.state = new RollingState() 
  }

  override def bank(controller: Controller): Unit = {
    // Ignorieren, er muss die liegenden Würfel erst auswerten
  }
}