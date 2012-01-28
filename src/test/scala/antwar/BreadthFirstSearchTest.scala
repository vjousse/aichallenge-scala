package antwar
package test

import sandbox._

import org.specs2.mutable._

class BreadthSarchTest extends Specification with BfsFixtures {

  "My BFS algorithm" should {
    "Find the closest ant without using the torus particularity" in {
      search.findTile(food.tile) must beSome(myAnt.tile)
    }

    "Find the closest ant using the torus particularity of the board" in {
      searchTorus.findTile(food.tile) must beSome(myCloserAnt.tile)
    }
  }

  "Food" should {
    "Have 3 neighbors" in {
      board.neighborsOf(food.tile) must have size(3)
    }
  }

  "My ant" should {
    "Have 4 neighbors" in {
      board.neighborsOf(myAnt.tile) must have size(4)
    }
  }

}
