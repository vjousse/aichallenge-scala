package antwar
package sandbox

import scala.collection.immutable._

case class BreadthFirstSearch(board: Board, goal: Tile => Boolean) {

  case class Node(tile: Tile, childs: List[Tile], marked: Boolean = false)

  def findTile(startTile: Tile, maxDistance: Int = 10): Option[Tile] = {

    val queue = Queue(startTile)
    searchQueue(queue, 0, Set(startTile))

  }

  def searchQueue(queue: Queue[Tile], distance: Int, marked: Set[Tile] = Set()): Option[Tile] = queue.dequeue match {
    case (tile, rest) => {

      val (tile, rest) = queue.dequeue
      println("Examining Tile " + tile)

      goal(tile) match {
        case true => Some(tile)
        case _ => {
          val neighbors = for {
            nTile <- board.neighborsOf(tile)
            if (!marked.contains(nTile))
          } yield nTile

          val newQueue = rest.enqueue(neighbors)
          searchQueue(newQueue, distance + 1, marked ++ neighbors)
        }
      }

    }

    case _ => None
  }

}
