package de.htwg.se.farkle

import de.htwg.se.farkle.model._
import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.aview.TUI
import de.htwg.se.farkle.aview.gui.FarkleGUI
import scala.swing.Swing

object Main {
  def main(args: Array[String]): Unit = {
    println("Starte Kingdom Come: Farkle...")
    
    // 1. Model und Controller erstellen
    val game = Game()
    val controller = new Controller(game)
    
    // 2. GUI sicher auf dem Swing-Thread starten
    Swing.onEDT {
      val gui = new FarkleGUI(controller)
      gui.visible = true
    }
    
    // 3. TUI auf dem Main-Thread starten
    val tui = new TUI(controller)
    tui.run()
  }
}