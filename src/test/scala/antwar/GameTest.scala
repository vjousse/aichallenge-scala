package antwar
package test

import sandbox._

import org.specs2.mutable._

class GameTest extends Specification with BfsFixtures {

  "Game" should {
    "Should find north direction from food" in {
      torusBoard directionFrom food.tile toNeighbor Tile(3,2) must beEqualTo(North)
    }

    "Should find south direction from food" in {
      torusBoard directionFrom food.tile toNeighbor Tile(3,4) must beEqualTo(South)
    }

    "Should find east direction from food" in {
      torusBoard directionFrom food.tile toNeighbor Tile(4,3) must beEqualTo(East)
    }

    "Should find west direction from food" in {
      torusBoard directionFrom food.tile toNeighbor Tile(2,3) must beEqualTo(West)
    }


    "Should find west direction from border tile" in {
      torusBoard directionFrom Tile(0,0) toNeighbor Tile(9,0) must beEqualTo(West)
    }

    "Should find north direction from border tile" in {
      torusBoard directionFrom Tile(0,0) toNeighbor Tile(0,9) must beEqualTo(North)
    }

    "Should find east direction from border tile" in {
      torusBoard directionFrom Tile(9,9) toNeighbor Tile(0,9) must beEqualTo(East)
    }

    "Should find South direction from border tile" in {
      torusBoard directionFrom Tile(9,9) toNeighbor Tile(9,0) must beEqualTo(South)
    }
  }

}
