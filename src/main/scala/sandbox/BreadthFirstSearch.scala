package antwar
package sandbox

import scala.collection.immutable._

case class BreadthFirstSearch(board: Board) {

  case class Node(tile: Tile, childs: List[Tile], marked: Boolean = false)

  def findTile(startTile: Tile, board: Board, goal: Tile => Boolean, maxDistance: Int = 10): Option[Tile] = {

    val queue = Queue(startTile)
    None
  }
}
