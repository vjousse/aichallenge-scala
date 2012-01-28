package antwar
package test

import sandbox._

import org.specs2.mutable._

class BreadthSarchTest extends Specification with BfsFixtures {

  "My BFS algorithm" should {
    "Find the closest ant without using the torus particularity" in {
      search.findTileAndDirection(food.tile) must beSome((myAnt.tile, Tile(6,1)))
    }

    "Find the closest ant using the torus particularity of the board" in {
      searchTorus.findTileAndDirection(food.tile) must beSome((myCloserAnt.tile, Tile(0,3)))
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
