package de.htwg.se.farkle.model

case class Player(name: String, score: Int = 0) {
  // Eine Hilfsmethode, um dem Spieler leicht Punkte gutzuschreiben (erzeugt eine Kopie)
  def addScore(points: Int): Player = copy(score = score + points)
}