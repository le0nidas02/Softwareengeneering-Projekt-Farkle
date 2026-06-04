package de.htwg.se.farkle.controller

import de.htwg.se.farkle.model.{Game, Evaluator, KcdEvaluator}
import de.htwg.se.farkle.util.Observable

// Der Controller nimmt jetzt ZWEI Parameter: Das Game und die Strategie (mit Standardwert)
class Controller(var game: Game, val evaluator: Evaluator = new KcdEvaluator()) extends Observable {
  
  def rollDice(): Unit = {
    game = game.rollActive()
    checkBust()
    notifyObservers()
  }

  def checkBust(): Unit = {
    // Hier nutzen wir jetzt die Instanz "evaluator"
    if (game.dice.nonEmpty && evaluator.evaluate(game.dice) == 0) {
      game = game.nextPlayer()
    }
  }

  def keep(indices: List[Int]): Unit = {
    val keptDice = indices.map(i => i - 1).flatMap(i => game.dice.lift(i))
    // Auch hier: Aufruf über die Instanz
    val points = evaluator.evaluate(keptDice)
    
    var newActive = game.activeDice - keptDice.length
    if (newActive <= 0) newActive = 6 
    
    game = game.copy(
      turnScore = game.turnScore + points,
      activeDice = newActive,
      dice = List.empty 
    )
    notifyObservers()
  }

  def bank(): Unit = {
    val currentPlayer = game.currentPlayer
    val updatedPlayer = currentPlayer.addScore(game.turnScore)
    val updatedPlayers = game.players.updated(game.currentPlayerIndex, updatedPlayer)
    
    game = game.copy(players = updatedPlayers).nextPlayer()
    notifyObservers()
  }
}