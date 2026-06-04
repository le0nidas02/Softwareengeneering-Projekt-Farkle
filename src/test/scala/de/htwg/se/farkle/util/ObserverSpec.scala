package de.htwg.se.farkle.util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ObserverSpec extends AnyWordSpec {
  "An Observable" should {
    "add an Observer" in {
      val observable = new Observable {}
      val observer = new Observer {
        var updated: Boolean = false
        def update(): Unit = updated = true
      }
      observable.add(observer)
      observable.subscribers should contain (observer)
    }
    
    "remove an Observer" in {
      val observable = new Observable {}
      val observer = new Observer {
        def update(): Unit = ()
      }
      observable.add(observer)
      observable.remove(observer)
      observable.subscribers should not contain (observer)
    }
    
    "notify Observers when changed" in {
      val observable = new Observable {}
      var updated: Boolean = false
      val observer = new Observer {
        def update(): Unit = updated = true
      }
      observable.add(observer)
      observable.notifyObservers()
      updated should be (true)
    }
  }
}