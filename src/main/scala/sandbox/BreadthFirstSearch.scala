package antwar
package sandbox

import scala.collection.immutable._

case class BreadthFirstSearch(board: Board, goal: Tile => Boolean) {

  def findTile(startTile: Tile): Option[Tile] = searchQueue(Queue(startTile), Set(startTile))

  private def searchQueue(queue: Queue[Tile], marked: Set[Tile]): Option[Tile] = queue.dequeue match {
    case (tile, rest) => {

      if (goal(tile)) Some(tile)
      else {
        val neighbors = board neighborsOf tile filterNot marked.contains
        val newQueue = rest.enqueue(neighbors)
        searchQueue(newQueue, marked ++ neighbors)
      }

    }
    case _ => None
  }

}
