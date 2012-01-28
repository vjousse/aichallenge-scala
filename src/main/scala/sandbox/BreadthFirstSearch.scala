package antwar
package sandbox

import scala.collection.immutable._
import scala.util.control.Exception._

case class BreadthFirstSearch(board: Board, goal: Tile => Boolean) {

  case class Node(value: Tile, level: Int, children: List[Node] = Nil, parent: Option[Node] = None) {
    override def toString = "Tree(" + value.toString + " {" + children.map(_.toString).mkString(",") + "})\n"
  }

  /**
   *  Build a graph for the BFS, with a deep limit to levelLimit
   */
  def buildGraph(tile: Tile, marked: Set[Tile] = Set(), currentLevel: Int = 0, levelLimit: Int = 10): Node =
    Node(
      tile,
      currentLevel,
      if (currentLevel > levelLimit)
        Nil
      else {
        val neighbors = board neighborsOf tile filterNot marked.contains
        neighbors map { n => buildGraph(n, marked ++ neighbors + tile, currentLevel + 1, levelLimit) }
      })

  private def searchGraph(queue: Queue[Node]): Option[Tile] = catching(classOf[NoSuchElementException]) opt queue.dequeue match {
      case Some((node, rest)) =>
        if (goal(node.value))
          Some(node.value)
        else
          searchGraph(rest.enqueue(node.children))
      case _ => None
    }

  def findTile(startTile: Tile): Option[Tile] = {
    val graph = buildGraph(startTile, Set(startTile))
    searchGraph(Queue(graph))
  }

}
