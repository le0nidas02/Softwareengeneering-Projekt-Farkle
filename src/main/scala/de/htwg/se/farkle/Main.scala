package de.htwg.se.farkle

// Hier importieren wir die Klassen aus den anderen Ordnern!
import de.htwg.se.farkle.model._  // Das _ bedeutet: "Importiere ALLES aus dem model-Ordner"
import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.aview.TUI

object Main {
  def main(args: Array[String]): Unit = {
    println("Starte Kingdom Come: Farkle...")
    
    // 1. Model erstellen (Unsere reinen Daten)
    val game = Game()
    
    // 2. Controller erstellen und Model übergeben (Das Gehirn)
    val controller = Controller(game)
    
    // 3. TUI erstellen und Controller übergeben (Die Augen)
    val tui = TUI(controller)
    
    // Spiel-Schleife der TUI starten
    tui.run()
  }
}