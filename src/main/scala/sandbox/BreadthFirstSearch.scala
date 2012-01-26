package antwar
package sandbox

import scala.collection.immutable._

case class BreadthFirstSearch(board: Board, goal: Tile => Boolean) {

  case class Node(tile: Tile, childs: List[Tile], marked: Boolean = false)

  def findTile(startTile: Tile, maxDistance: Int = 10): Option[Tile] = {

    val queue = Queue(startTile)
    searchQueue(queue, 0, Set(startTile))

  }

  def searchQueue(queue: Queue[Tile], distance: Int, marked: Set[Tile] = Set()): Option[Tile] = {

    if (queue.length == 0) {
      println("Queue size of %s and distance of %s, exiting".format(queue.length, distance))
      None
    }
    else {

      val (tile, rest) = queue.dequeue
      //println("Examining Tile " + tile)

      if (goal(tile)) Some(tile)
      else {
        val newQueue = rest.enqueue(board.neighborsOf(tile))
         searchQueue(newQueue, distance+1)
      }
    }

  }

}
