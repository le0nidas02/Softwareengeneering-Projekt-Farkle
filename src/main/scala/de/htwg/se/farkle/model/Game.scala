package de.htwg.se.farkle.model

// Die Klasse ist immutable. Wenn wir würfeln, erstellen wir ein neues Game-Objekt.
case class Game(dice: List[Dice] = List.empty) {
  
  def rollAll(): Game = {
    // Erstellt 6 neue Würfel und kopiert sie in einen neuen Game-State
    copy(dice = List.fill(6)(Dice.roll()))
  }
}