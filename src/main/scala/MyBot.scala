package antwar
import sandbox.BreadthFirstSearch

import scala.util.Random
import scala.collection.immutable._

object MyBot extends App {

  Logger.info("New game")

  new AntsGame(bot = new MyBot).run()
}

class MyBot extends Bot {

  def ordersFrom(game: GameInProgress): Set[Order] = {

    val randomOrders = randomOrder(game)
    val foodOrders = collectFood(game)

    //Filter orders to be sure they are unique
    val foodOrdersMap = (foodOrders map { order => (order.tile, order) }).toMap
    val randomOrdersMap = (randomOrders map { order => (order.tile, order) }).toMap

    val allOrdersMap = randomOrdersMap ++ foodOrdersMap
    (for {
      (tile, order) <- allOrdersMap
    } yield order).toSet
  }

  /**
   * Should give back an order if a food is not too far
   * away from an ant
   *
   * TODO: Put it somewhere else
   */
  def antOrderForFood(food: Food, game: GameInProgress): Option[Order] = {

    def isAnt(tile: Tile): Boolean = game.board.myAnts.contains(tile)

    val search = new BreadthFirstSearch(game.board, isAnt)
    search.findTileAndDirection(food.tile) match {
      case Some((targetTile, directionTile)) => Some(Order(targetTile, game.board directionFrom targetTile toNeighbor directionTile))
      case None => None
    }

  }

  /**
   * Try to move each ant in the direction of a food
   */
  def collectFood(game: GameInProgress): Set[Order] = {
    //For each food of the map, try to see if an ant
    //can be moved toward it

    val orders = for {
      (tile, food) <- game.board.food
      order <- antOrderForFood(food, game)
    } yield order

    orders.toSet
  }

  def randomOrder(game: GameInProgress): Set[Order] = {

    val directions = List(North, East, South, West)
    val ants = game.board.myAnts.values

    ants.flatMap{ant =>
      // for this ant, find the first direction which is not water, if any
      val direction = Random.shuffle(directions).find{aim =>
        val targetTile = game.board.tile(aim).of(ant.tile)
        !game.board.water.contains(targetTile)
      }
      // convert this (possible) direction into an order for this ant
      direction.map{d => Order(ant.tile, d)}
    }.toSet

  }
}
