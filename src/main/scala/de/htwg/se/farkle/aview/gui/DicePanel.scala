package de.htwg.se.farkle.aview.gui

import de.htwg.se.farkle.controller.Controller
import scala.swing._
import scala.swing.event.ButtonClicked
import java.awt.Color

class DicePanel(controller: Controller) extends FlowPanel {
  
  val diceButtons: Array[Button] = Array.fill(6)(new Button(""))
  var selectedIndices: Set[Int] = Set()
  
  // Etwas Hintergrundfarbe, damit die weißen Würfel besser poppen
  background = new Color(245, 245, 250) 
  
  diceButtons.zipWithIndex.foreach { case (btn, i) =>
    btn.preferredSize = new Dimension(80, 80) // Schön groß!
    btn.font = new Font("Arial", 1, 36)
    btn.foreground = Color.DARK_GRAY
    contents += btn
    
    listenTo(btn)
    reactions += {
      case ButtonClicked(`btn`) => 
        val index = i + 1
        if (selectedIndices.contains(index)) selectedIndices -= index
        else selectedIndices += index
        redraw()
    }
  }

  def clearSelection(): Unit = {
    selectedIndices = Set()
  }

  def redraw(): Unit = {
    val currentDice = controller.game.dice
    diceButtons.foreach(_.visible = false)
    
    currentDice.zipWithIndex.foreach { case (dice, i) =>
      val index = i + 1
      diceButtons(i).text = dice.value.toString // Der Text bleibt immer nur die Zahl!
      
      if (selectedIndices.contains(index)) {
        // Ausgewählt: Fetter grüner Rahmen und leicht grüner Hintergrund
        diceButtons(i).border = Swing.LineBorder(new Color(46, 204, 113), 4)
        diceButtons(i).background = new Color(230, 255, 230)
      } else {
        // Nicht ausgewählt: Standard-Rahmen, weißer Hintergrund
        diceButtons(i).border = Swing.LineBorder(Color.LIGHT_GRAY, 1)
        diceButtons(i).background = Color.WHITE
      }
      diceButtons(i).visible = true
    }
    
    revalidate()
    repaint()
  }
}