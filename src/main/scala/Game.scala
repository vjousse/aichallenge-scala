package antwar

import scala.math.{abs,min,pow}


case class GameNew(turn: Int = 0, parameters: GameParameters = GameParameters()) extends Game {
  val gameOver = false

  def toInProgress() = GameInProgress(turn, parameters, Board(cols = parameters.cols, rows = parameters.rows))
}

case class GameInProgress(turn: Int = 0, parameters: GameParameters, board: Board, gameOver: Boolean = false) extends Game {
  def including[P <: Positionable](positionable: P) = this.copy(board = this.board including positionable)
  def including(p: Positionable*): GameInProgress = p.foldLeft(this){(game, positionable) => game.including(positionable)}

  def distanceFrom(one: Tile) = new {
    def to(another: Tile) = {
      val dRow = abs(one.row - another.row)
      val dCol = abs(one.column - another.column)
      pow(min(dRow, parameters.rows - dRow), 2) + pow(min(dCol, parameters.cols - dCol), 2)
    }
  }

  def directionFrom(one: Tile) = new {
    def to(other: Tile): Set[CardinalPoint] = {
      val ns: Set[CardinalPoint] = if (one.row < other.row) {
        if (other.row - one.row >= parameters.rows / 2) Set(North) else Set(South)
      } else if (one.row > other.row) {
        if (one.row - other.row >= parameters.rows / 2) Set(South) else Set(North)
      } else Set()

      val ew: Set[CardinalPoint] = if (one.column < other.column) {
        if (other.column - one.column >= parameters.cols / 2) Set(West) else Set(East)
      } else if (one.column > other.column) {
        if (one.column - other.column >= parameters.cols / 2) Set(East) else Set(West)
      } else Set()

      ns ++ ew
    }
  }

  def tile(aim: CardinalPoint) = new {
    def of(tile: Tile) = {
      aim match {
        case North => tile.copy(row = if (tile.row == 0) parameters.rows - 1 else tile.row - 1)
        case South => tile.copy(row = (tile.row + 1) % parameters.rows)
        case East => tile.copy(column = (tile.column + 1) % parameters.cols)
        case West => tile.copy(column = if (tile.column == 0) parameters.cols - 1 else tile.column - 1)
      }
    }
  }
}
case class GameOver(turn: Int = 0, parameters: GameParameters, board: Board) extends Game {
  val gameOver = true
}

sealed trait Game {
  val turn: Int
  val gameOver: Boolean
}

