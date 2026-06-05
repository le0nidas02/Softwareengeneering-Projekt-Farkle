package de.htwg.se.farkle.aview

import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.util.Observer
import scala.util.{Try, Success, Failure}
import scala.io.StdIn.readLine

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def run(): Unit = {
    var continue = true
    while (continue) {
      val input = scala.io.StdIn.readLine("Befehl: ")
      if (input == null) {
        continue = false // EOF sauber abfangen
      } else {
        continue = processInputLine(input)
      }
    }
  }

  def processInputLine(input: String): Boolean = {
    input.trim.toLowerCase match {
      case "q" => false
      case "r" => 
        controller.rollDice()
        true
      case "b" => 
        controller.bank()
        true
      case "z" =>
        controller.undo()
        true
      case "y" =>
        controller.redo()
        true
      case cmd if cmd.startsWith("k ") =>
        val args = cmd.stripPrefix("k ").trim.split(" ").toList
        
        // HIER IST DIE TRY-MONADE (Task 8 erfüllt!)
        val tryIndices = Try(args.map(_.toInt))
        tryIndices match {
          case Success(indices) => controller.keep(indices)
          case Failure(_) => println("Ungültige Eingabe! Bitte z.B. 'k 1 2' verwenden.")
        }
        true
      case _ =>
        println("Unbekannter Befehl! Nutze: 'r', 'k 1 2', 'b', 'z', 'y' oder 'q'")
        true
    }
  }

  override def update(): Unit = {
    // Deine Print-Logik für das Spielfeld...
  }
}