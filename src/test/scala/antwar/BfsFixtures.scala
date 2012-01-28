package antwar
package test

import sandbox._

trait BfsFixtures {

  val myAnt = new MyAnt(Tile(7, 1))
  val myOtherAnt = new MyAnt(Tile(9, 0))
  //Closest ant using Torus board
  val myCloserAnt = new MyAnt(Tile(9, 3))

  val food = new Food(Tile(3, 3))
  val water = new Water(Tile(3, 4))

  val board = new Board(
    10, 10,
    myAnts = Map(
      myAnt.tile -> myAnt,
      myOtherAnt.tile -> myOtherAnt
      ),
    food = Map(food.tile -> food),
    water = Map(water.tile -> water)
    )

  val torusBoard = new Board(
    10, 10,
    myAnts = Map(
      myAnt.tile -> myAnt,
      myOtherAnt.tile -> myOtherAnt,
      myCloserAnt.tile -> myCloserAnt
      ),
    food = Map(food.tile -> food),
    water = Map(water.tile -> water)
    )

  val search = new BreadthFirstSearch(board, isAnt)
  val searchTorus = new BreadthFirstSearch(torusBoard, isAntTorus)

  def isAnt(tile: Tile): Boolean = board.myAnts.contains(tile)
  def isAntTorus(tile: Tile): Boolean = torusBoard.myAnts.contains(tile)

}
