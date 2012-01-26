package antwar
package test

import sandbox._

import org.specs2.mutable._

class BreadthSarchTest extends Specification with BfsFixtures {

  "My BFS algorithm" should {
    "Find the closest ant" in {
      search.findTile(food.tile) must beSome(myAnt.tile)
    }
  }

  "Food" should {
    "Have 3 neighbors" in {
      board.neighborsOf(food.tile).length must beEqualTo(3)
    }
  }

  "My ant" should {
    "Have 4 neighbors" in {
      board.neighborsOf(myAnt.tile).length must beEqualTo(4)
    }
  }

}
