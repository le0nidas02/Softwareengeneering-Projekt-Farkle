package de.htwg.se.farkle.aview

import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.util.Observer
import scala.io.StdIn.readLine

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  override def update(): Unit = {
    val game = controller.game
    println("\n" + "=" * 50)
    println(s"👑 AKTUELLER SPIELER: ${game.currentPlayer.name}")
    println(s"💰 TOTAL SCORE: ${game.players(0).name} [${game.players(0).score}] vs ${game.players(1).name} [${game.players(1).score}]")
    println(s"🔥 TURN SCORE:  ${game.turnScore}   |   🎲 VERFÜGBAR: ${game.activeDice}")
    println("-" * 50)
    
    if (game.dice.nonEmpty) {
      val lines = game.dice.map(_.toString.split("\n"))
      for (i <- 0 until 3) {
        println(lines.map(_(i)).mkString("  "))
      }
    } else {
      println("Keine Würfel auf dem Tisch. Drücke 'r' zum Würfeln.")
    }
    println("=" * 50)
  }

  def run(): Unit = {
    println("Willkommen bei Kingdom Come: Farkle!")
    println("Befehle: 'r' = roll, 'k 1 2' = behalte Würfel 1 & 2, 'b' = bank (Punkte sichern), 'q' = quit")
    update()
    
    var input: String = ""
    while (input != "q") {
      input = readLine("Befehl: ").toLowerCase
      input.split(" ").toList match {
        case "q" :: Nil => println("Gott befohlen, Heinrich!")
        case "r" :: Nil => controller.rollDice()
        case "b" :: Nil => controller.bank()
        case "k" :: indices => 
          try {
            val idxList = indices.map(_.toInt)
            controller.keep(idxList)
          } catch {
            case _: NumberFormatException => println("Ungültige Eingabe! Bitte z.B. 'k 1 2' verwenden.")
          }
        case _ => println("Unbekannter Befehl! Nutze: 'r', 'k 1 2', 'b' oder 'q'")
      }
    }
  }
}