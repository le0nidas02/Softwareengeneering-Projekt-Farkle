package de.htwg.se.farkle.controller

import de.htwg.se.farkle.model.{Game, Evaluator, KcdEvaluator}
import de.htwg.se.farkle.util.{Observable, UndoManager}

class Controller(var game: Game, val evaluator: Evaluator = new KcdEvaluator()) extends Observable {
  
  var state: GameState = new RollingState()
  val undoManager = new UndoManager()

  // --- Delegation an das State Pattern ---
  def rollDice(): Unit = state.rollDice(this)
  def keep(indices: List[Int]): Unit = state.keep(this, indices)
  def bank(): Unit = state.bank(this)

  // --- Neue Undo/Redo Schnittstelle ---
  def undo(): Unit = {
    undoManager.undoStep()
    notifyObservers()
  }

  def redo(): Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  // --- Hilfsmethoden für die Zustände (Command Pattern Integration) ---
  def doKeep(indices: List[Int]): Unit = {
    val keptDice = indices.map(i => i - 1).flatMap(i => game.dice.lift(i))
    val points = evaluator.evaluate(keptDice)
    
    var newActive = game.activeDice - keptDice.length
    if (newActive <= 0) newActive = 6 
    
    val newGame = game.copy(
      turnScore = game.turnScore + points,
      activeDice = newActive,
      dice = List.empty 
    )
    val newState = new RollingState()
    
    undoManager.doStep(new SetCommand(this, game, state, newGame, newState))
    notifyObservers()
  }

  def doBank(): Unit = {
    val currentPlayer = game.currentPlayer
    val updatedPlayer = currentPlayer.addScore(game.turnScore)
    val updatedPlayers = game.players.updated(game.currentPlayerIndex, updatedPlayer)
    
    val newGame = game.copy(players = updatedPlayers).nextPlayer()
    val newState = new RollingState()

    undoManager.doStep(new SetCommand(this, game, state, newGame, newState))
    notifyObservers()
  }
}