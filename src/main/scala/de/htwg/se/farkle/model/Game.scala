package de.htwg.se.farkle.model

case class Game(
  players: Vector[Player] = Vector(Player("Player 1"), Player("Player 2")),
  currentPlayerIndex: Int = 0,
  dice: List[Dice] = List.empty,
  turnScore: Int = 0,
  activeDice: Int = 6 // Wie viele Würfel darf ich noch in die Hand nehmen?
) {
  
  def currentPlayer: Player = players(currentPlayerIndex)
  
  // Würfelt nur die noch aktiven Würfel neu aus
  def rollActive(): Game = {
    copy(dice = List.fill(activeDice)(Dice.roll()))
  }
  
  // Wechselt den Spieler und resettet die Runde
  def nextPlayer(): Game = {
    copy(
      currentPlayerIndex = (currentPlayerIndex + 1) % players.length,
      turnScore = 0,
      activeDice = 6,
      dice = List.empty
    )
  }
}