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
    
    "score mixed combinations correctly" in {
      // Drei 2er (200) + eine 1 (100) + eine 5 (50) = 350
      val dice = List(Dice(2), Dice(2), Dice(2), Dice(1), Dice(5), Dice(4))
      Evaluator.evaluate(dice) should be(350)
    }

    "score four or five of a kind correctly including remainders" in {
      // 4 Einsen = 1000 (für 3) + 100 (für die 4.) = 1100
      Evaluator.evaluate(List.fill(4)(Dice(1))) should be(1100)
      // 5 Fünfen = 500 (für 3) + 100 (für die restlichen 2) = 600
      Evaluator.evaluate(List.fill(5)(Dice(5))) should be(600)
      // 4 Zweien = 200 (für 3). Die 4. Zwei gibt keine extra Punkte!
      Evaluator.evaluate(List.fill(4)(Dice(2))) should be(200)
    }
  }
}