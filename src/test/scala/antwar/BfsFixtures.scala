package antwar
package test

import sandbox._

trait BfsFixtures {

  val myAnt = new MyAnt(Tile(7, 1))
  val myOtherAnt = new MyAnt(Tile(9, 9))

  val food = new Food(Tile(3, 3))
  val water = new Water(Tile(3, 4))
  val board = new Board(
    10, 10,
    myAnts = Map(myAnt.tile -> myAnt, myOtherAnt.tile -> myOtherAnt),
    food = Map(food.tile -> food),
    water = Map(water.tile -> water)
    )

  val search = new BreadthFirstSearch(board, isAnt)

  def isAnt(tile: Tile): Boolean = board.myAnts.contains(tile)

}
