package antwar
package test

import sandbox._

import org.scalatest._

class BreadthSarchTest extends FunSuite {

  test("Find closest ant to Food 3,3") {

    val myAnt = new MyAnt(Tile(7, 1))
    val myOtherAnt = new MyAnt(Tile(9, 9))

    val food = new Food(Tile(3, 3))
    val board = new Board(
      10, 10,
      myAnts = Map(myAnt.tile -> myAnt, myOtherAnt.tile -> myOtherAnt),
      food = Map(food.tile -> food)
    )

    val search = new BreadthFirstSearch(board)

    def isAnt(tile: Tile): Boolean = board.myAnts.contains(tile)

    val antFound = search.findTile(food.tile, isAnt)

    assert(antFound === Some(myAnt))
  }
}
