package de.htwg.se.farkle.controller

import de.htwg.se.farkle.model.Game
import de.htwg.se.farkle.util.Observable

class Controller(var game: Game) extends Observable {
  
  def rollDice(): Unit = {
    // 1. Model verändern
    game = game.rollAll()
    // 2. View benachrichtigen
    notifyObservers()
  }
}