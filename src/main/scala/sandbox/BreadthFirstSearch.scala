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
  private def buildGraph(tile: Tile, marked: Set[Tile] = Set(), currentLevel: Int = 0, levelLimit: Int = 10): Node =
    Node(
      tile,
      currentLevel,
      if (currentLevel > levelLimit)
        Nil
      else {
        val neighbors = board neighborsOf tile filterNot marked.contains
        neighbors map { n => buildGraph(n, marked ++ neighbors + tile, currentLevel + 1, levelLimit) }
      })

  private def findDirection(node: Node): Tile = {
    node.parent match {
      case None => node.value
      case Some(parent) => parent.value

    }
  }

  /**
   * Implement the BFS algorithm and the direction search
   */
  private def searchGraph(queue: Queue[Node]): Option[(Tile,Tile)] = catching(classOf[NoSuchElementException]) opt queue.dequeue match {

      case Some((node, rest)) =>
        if (goal(node.value))
          Some(node.value, findDirection(node))
        else
          searchGraph(rest.enqueue(node.children map { child =>
            //Each time we go through the children, we should
            //keep track of each parent to be able to go back to the
            //root node
            Node(child.value, child.level, child.children, Some(node))
          }))
      case _ => None
    }


  /**
   * Entry point of the API
   *
   * It returns the tile meeting the goal and the direction to go
   * to reach this tile, if any
   */
  def findTileAndDirection(startTile: Tile): Option[(Tile, Tile)] = {
    val graph = buildGraph(startTile, Set(startTile))
    searchGraph(Queue(graph))
  }

}
