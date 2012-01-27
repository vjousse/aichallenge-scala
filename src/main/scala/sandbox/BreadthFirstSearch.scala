package antwar
package sandbox

import scala.collection.immutable._

case class BreadthFirstSearch(board: Board, goal: Tile => Boolean) {

  case class Node(value: Tile, children: List[Node] = Nil)

  /**
   *  Build a graph for the BFS, with a deep limit to levelLimit
   */
  def graph(tile: Tile, marked: Set[Tile] = Set(), currentLevel: Int = 0, levelLimit: Int = 10): Node = {

    Node(tile,
      if (currentLevel > levelLimit)
        Nil
      else {
        val neighbors = board neighborsOf tile filterNot marked.contains
        neighbors map { n => graph(n, marked ++ neighbors, currentLevel + 1, levelLimit) }
      })

  }

  private def searchGraph(queue: Queue[Node]): Option[Tile] = queue.dequeue match {
    case (node, rest) => {

      if (goal(node.value))
        Some(node.value)
      else
        searchGraph(rest.enqueue(node.children))

    }
    case _ => None
  }

  def findTile(startTile: Tile): Option[Tile] = {
    searchGraph(Queue(graph(startTile)))
  }

}
