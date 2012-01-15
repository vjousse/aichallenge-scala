package antwar

import scala.util.Random

object MyBot extends App {
  new AntsGame().run(new MyBot)
}

class MyBot extends Bot with Loggable {

  def ordersFrom(game: Game): Set[Order] = {

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
