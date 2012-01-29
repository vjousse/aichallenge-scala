package antwar

case class GameNew(turn: Int = 0, parameters: GameParameters = GameParameters()) extends Game {
  val gameOver = false

  def toInProgress() = GameInProgress(turn, parameters, Board(cols = parameters.cols, rows = parameters.rows))
}

case class GameInProgress(turn: Int = 0, parameters: GameParameters, board: Board, gameOver: Boolean = false) extends Game {
  def including[P <: Positionable](positionable: P) = this.copy(board = this.board including positionable)
  def including(p: Positionable*): GameInProgress = p.foldLeft(this){(game, positionable) => game.including(positionable)}

}
case class GameOver(turn: Int = 0, parameters: GameParameters, board: Board) extends Game {
  val gameOver = true
}

sealed trait Game {
  val turn: Int
  val gameOver: Boolean
}

