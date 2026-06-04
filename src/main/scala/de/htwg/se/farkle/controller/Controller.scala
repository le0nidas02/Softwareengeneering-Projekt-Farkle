package de.htwg.se.farkle.controller

import de.htwg.se.farkle.model.{Game, Evaluator}
import de.htwg.se.farkle.util.Observable

class Controller(var game: Game) extends Observable {
  
  def rollDice(): Unit = {
    game = game.rollActive()
    checkBust()
    notifyObservers()
  }

  // Diese Methode können wir jetzt wunderbar deterministisch testen!
  def checkBust(): Unit = {
    if (game.dice.nonEmpty && Evaluator.evaluate(game.dice) == 0) {
      game = game.nextPlayer()
    }
  }

  def keep(indices: List[Int]): Unit = {
    val keptDice = indices.map(i => i - 1).flatMap(i => game.dice.lift(i))
    val points = Evaluator.evaluate(keptDice)
    
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