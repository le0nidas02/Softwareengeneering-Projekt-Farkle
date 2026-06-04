package de.htwg.se.farkle.aview

import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.util.Observer
import scala.io.StdIn.readLine

class TUI(controller: Controller) extends Observer {
  // TUI meldet sich sofort beim Controller als Beobachter an
  controller.add(this)

  // Wird automatisch vom Controller aufgerufen, wenn notifyObservers() feuert
  override def update(): Unit = {
    println("\n--- Farkle Dice Roll ---")
    if (controller.game.dice.nonEmpty) {
      val lines = controller.game.dice.map(_.toString.split("\n"))
      for (i <- 0 until 3) {
        println(lines.map(_(i)).mkString("  "))
      }
    } else {
      println("Keine Würfel auf dem Tisch. Drücke 'r' zum Würfeln.")
    }
  }

  // Die Endlos-Schleife, die auf deine Eingaben wartet
  def run(): Unit = {
    println("Willkommen bei Farkle! ('r' = roll, 'q' = quit)")
    update() // Einmal initial zeichnen
    
    var input: String = ""
    while (input != "q") {
      input = readLine("Befehl: ").toLowerCase
      input match {
        case "r" => controller.rollDice()
        case "q" => println("Gott befohlen, Heinrich!")
        case _   => println("Unbekannter Befehl.")
      }
    }
  }
}