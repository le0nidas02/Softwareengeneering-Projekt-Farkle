package de.htwg.se.farkle.aview.gui

import de.htwg.se.farkle.controller.Controller
import de.htwg.se.farkle.util.Observer
import scala.swing._
import scala.swing.event.ButtonClicked
import java.awt.Color

class FarkleGUI(controller: Controller) extends MainFrame with Observer {
  controller.add(this)
  
  title = "Kingdom Come: Farkle"
  preferredSize = new Dimension(600, 420)

  val exitAction = Action("Beenden") { dispose() }
  val undoAction = Action("Undo (z)") { controller.undo() }
  val redoAction = Action("Redo (y)") { controller.redo() }

  menuBar = new MenuBar {
    contents += new Menu("Spiel") { contents += new MenuItem(exitAction) }
    contents += new Menu("Bearbeiten") {
      contents += new MenuItem(undoAction)
      contents += new MenuItem(redoAction)
    }
  }

  val turnLabel = new Label("Am Zug: Player 1")
  turnLabel.font = new Font("Arial", 1, 20)
  
  val scoreLabel = new Label("Turn Score: 0 | Total: P1 [0] vs P2 [0]")
  scoreLabel.font = new Font("Arial", 0, 14)
  scoreLabel.foreground = Color.DARK_GRAY

  // NEU: Das Info-Label für Hot Dice und Fehlermeldungen!
  val infoLabel = new Label(" ")
  infoLabel.font = new Font("Arial", 1, 14)
  
  val dicePanel = new DicePanel(controller)
  
  val rollButton = new Button("Würfeln (r)")
  val keepButton = new Button("Auswahl behalten (k)")
  val bankButton = new Button("Bank (b)")

  val buttons = List(rollButton, keepButton, bankButton)
  buttons.foreach { btn =>
    btn.font = new Font("Arial", 1, 14)
    btn.preferredSize = new Dimension(160, 45)
  }

  listenTo(rollButton, keepButton, bankButton)
  reactions += {
    case ButtonClicked(`rollButton`) => 
      infoLabel.text = " " // Fehler beim Würfeln löschen
      controller.rollDice()
    case ButtonClicked(`keepButton`) => 
      val oldScore = controller.game.turnScore
      controller.keep(dicePanel.selectedIndices.toList.sorted)
      // Wenn der Score sich nicht verändert hat, hat das Backend den Zug blockiert!
      if (controller.game.turnScore == oldScore && dicePanel.selectedIndices.nonEmpty) {
        infoLabel.text = "❌ Ungültige Auswahl! Nur Würfel mit Punkten sind erlaubt."
        infoLabel.foreground = Color.RED
      }
    case ButtonClicked(`bankButton`) => 
      infoLabel.text = " "
      controller.bank()
  }

  val statusPanel = new BoxPanel(Orientation.Vertical) {
    contents += turnLabel
    contents += Swing.VStrut(5) 
    contents += scoreLabel
    contents += Swing.VStrut(10)
    contents += infoLabel // NEU hinzugefügt
    border = Swing.EmptyBorder(20, 20, 10, 20)
    background = new Color(245, 245, 250)
  }

  val actionPanel = new FlowPanel {
    contents += rollButton
    contents += keepButton
    contents += bankButton
    border = Swing.EmptyBorder(10, 20, 20, 20)
    background = new Color(245, 245, 250)
  }

  contents = new BorderPanel {
    add(statusPanel, BorderPanel.Position.North)
    add(dicePanel, BorderPanel.Position.Center)
    add(actionPanel, BorderPanel.Position.South)
  }

  override def update(): Unit = {
    val game = controller.game
    val p1Score = game.players(0).score
    val p2Score = game.players(1).score
    
    turnLabel.text = s"👑 Am Zug: ${game.currentPlayer.name}"
    scoreLabel.text = s"🔥 Turn Score: ${game.turnScore}   |   💰 Total: ${game.players(0).name} [ $p1Score ] vs ${game.players(1).name} [ $p2Score ]"
    
    // NEU: Hot Dice erkennen und dem Spieler mitteilen
    if (game.dice.isEmpty && game.turnScore > 0) {
      infoLabel.text = "🔥 HOT DICE! Du hast alle Würfel abgeräumt. Würfle neu!"
      infoLabel.foreground = new Color(220, 100, 0)
    } else {
      infoLabel.text = " "
    }
    
    dicePanel.clearSelection()
    dicePanel.redraw()
  }

  update()
}