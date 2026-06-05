package de.htwg.se.farkle.controller

import de.htwg.se.farkle.model.{Game, Evaluator, KcdEvaluator}
import de.htwg.se.farkle.util.Observable

class Controller(var game: Game, val evaluator: Evaluator = new KcdEvaluator()) extends Observable {
  
  // Startzustand: Der erste Spieler muss würfeln
  var state: GameState = new RollingState()

  // --- Delegation an das State Pattern ---
  def rollDice(): Unit = state.rollDice(this)
  def keep(indices: List[Int]): Unit = state.keep(this, indices)
  def bank(): Unit = state.bank(this)

  // --- Hilfsmethoden für die Zustände (Hier passiert die echte Magie) ---
  def doKeep(indices: List[Int]): Unit = {
    val keptDice = indices.map(i => i - 1).flatMap(i => game.dice.lift(i))
    val points = evaluator.evaluate(keptDice)
    
    var newActive = game.activeDice - keptDice.length
    if (newActive <= 0) newActive = 6 // Hot Dice
    
    game = game.copy(
      turnScore = game.turnScore + points,
      activeDice = newActive,
      dice = List.empty 
    )
    notifyObservers()
  }

  def doBank(): Unit = {
    val currentPlayer = game.currentPlayer
    val updatedPlayer = currentPlayer.addScore(game.turnScore)
    val updatedPlayers = game.players.updated(game.currentPlayerIndex, updatedPlayer)
    
    game = game.copy(players = updatedPlayers).nextPlayer()
    notifyObservers()
  }
}