package de.htwg.se.farkle.model

trait Evaluator {
  def evaluate(dice: List[Dice]): Int
}