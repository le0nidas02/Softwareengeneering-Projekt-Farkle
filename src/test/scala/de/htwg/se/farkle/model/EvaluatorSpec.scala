package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class EvaluatorSpec extends AnyWordSpec {
  "The Evaluator" should {
    "score single 1s as 100 and 5s as 50" in {
      Evaluator.evaluate(List(Dice(1))) should be(100)
      Evaluator.evaluate(List(Dice(5))) should be(50)
      Evaluator.evaluate(List(Dice(1), Dice(5))) should be(150)
    }
    
    "return 0 for dice that do not score" in {
      Evaluator.evaluate(List(Dice(2), Dice(3), Dice(4), Dice(6))) should be(0)
    }
    
    "score triplets correctly (KCD rules)" in {
      Evaluator.evaluate(List.fill(3)(Dice(1))) should be(1000)
      Evaluator.evaluate(List.fill(3)(Dice(2))) should be(200)
      Evaluator.evaluate(List.fill(3)(Dice(5))) should be(500)
    }

    "score four, five and six of a kind by doubling the score (KCD rules)" in {
      // 4x 1 = 2000, 5x 1 = 4000, 6x 1 = 8000
      Evaluator.evaluate(List.fill(4)(Dice(1))) should be(2000)
      Evaluator.evaluate(List.fill(5)(Dice(1))) should be(4000)
      Evaluator.evaluate(List.fill(6)(Dice(1))) should be(8000)

      // 4x 2 = 400, 5x 2 = 800, 6x 2 = 1600
      Evaluator.evaluate(List.fill(4)(Dice(2))) should be(400)
      Evaluator.evaluate(List.fill(5)(Dice(2))) should be(800)
      Evaluator.evaluate(List.fill(6)(Dice(2))) should be(1600)
    }

    "score KCD straights correctly regardless of input order" in {
      // Kleine Straße 1-5 = 500
      Evaluator.evaluate(List(Dice(1), Dice(2), Dice(3), Dice(4), Dice(5))) should be(500)
      // Kleine Straße 2-6 = 750
      Evaluator.evaluate(List(Dice(2), Dice(3), Dice(4), Dice(5), Dice(6))) should be(750)
      // Große Straße 1-6 = 1500
      Evaluator.evaluate(List(Dice(1), Dice(2), Dice(3), Dice(4), Dice(5), Dice(6))) should be(1500)
      
      // Die Sortierung darf keine Rolle spielen
      Evaluator.evaluate(List(Dice(6), Dice(1), Dice(5), Dice(2), Dice(4), Dice(3))) should be(1500)
    }
    
    "score mixed combinations correctly" in {
      // Drei 2er (200) + eine 1 (100) + eine 5 (50) = 350
      val dice = List(Dice(2), Dice(2), Dice(2), Dice(1), Dice(5), Dice(4))
      Evaluator.evaluate(dice) should be(350)
    }
  }
}