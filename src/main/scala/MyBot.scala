package antwar

import scala.util.Random

object MyBot extends App {

  Logger.info("New game")

  new AntsGame(bot = new MyBot).run()
}

class MyBot extends Bot {

  def ordersFrom(game: GameInProgress): Set[Order] = basicRandomExample(game)

  /**
   * Should give back an order if a food is not too far
   * away from an ant
   *
   * TODO: Put it somewhere else
   */
  def antOrderForFood(food: Food): Option[Order] = {
    //Make it compile
    //TODO: Find a way to have the neighbors of this Tile
    //TODO: Use breadth first search to find a close ant
    None
  }

  /**
   * Try to move each ant in the direction of a food
   */
  def collectFood(game: GameInProgress): Set[Order] = {
    //For each food of the map, try to see if an ant
    //can be moved toward it
    val orders = for {
      (tile, food) <- game.board.food
      order <- antOrderForFood(food)
    } yield order

    orders.toSet
  }

  def basicRandomExample(game: GameInProgress): Set[Order] = {

    val directions = List(North, East, South, West)
    val ants = game.board.myAnts.values

    ants.flatMap{ant =>
      // for this ant, find the first direction which is not water, if any
      val direction = Random.shuffle(directions).find{aim =>
        val targetTile = game.tile(aim).of(ant.tile)
        !game.board.water.contains(targetTile)
      }
      // convert this (possible) direction into an order for this ant
      direction.map{d => Order(ant.tile, d)}
    }.toSet

  }
}
