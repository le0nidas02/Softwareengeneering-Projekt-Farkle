package de.htwg.se.farkle.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class EvaluatorSpec extends AnyWordSpec {
  "A KcdEvaluator" should {
    val evaluator = new KcdEvaluator()

    "score single 1s as 100 and 5s as 50" in {
      evaluator.evaluate(List(Dice(1))) should be(100)
      evaluator.evaluate(List(Dice(5))) should be(50)
      evaluator.evaluate(List(Dice(1), Dice(5))) should be(150)
    }

    "return 0 for dice that do not score" in {
      evaluator.evaluate(List(Dice(2), Dice(3), Dice(4), Dice(6))) should be(0)
    }

    "score triplets correctly (KCD rules)" in {
      evaluator.evaluate(List.fill(3)(Dice(1))) should be(1000)
      evaluator.evaluate(List.fill(3)(Dice(2))) should be(200)
      evaluator.evaluate(List.fill(3)(Dice(5))) should be(500)
    }

    "score four, five and six of a kind by doubling the score" in {
      evaluator.evaluate(List.fill(4)(Dice(2))) should be(400)
      evaluator.evaluate(List.fill(5)(Dice(1))) should be(4000)
      evaluator.evaluate(List.fill(6)(Dice(1))) should be(8000)
    }
    
    "score KCD straights correctly regardless of input order" in {
      evaluator.evaluate(List(1, 2, 3, 4, 5, 6).map(Dice(_))) should be(1500)
      evaluator.evaluate(List(1, 2, 3, 4, 5).map(Dice(_))) should be(500)
      evaluator.evaluate(List(2, 3, 4, 5, 6).map(Dice(_))) should be(750)
    }
    
    "score mixed combinations correctly" in {
      evaluator.evaluate(List(Dice(2), Dice(2), Dice(2), Dice(5))) should be(250)
    }
  }
}