package antwar

import annotation.tailrec
import io.Source
import java.io._

class AntsGame(bot: Bot, in: InputStream = System.in, out: OutputStream = System.out) {

  val source = new BufferedSource(in, Source.DefaultBufSize)
  val writer = new BufferedWriter(new OutputStreamWriter(out))

  def run() = {

    def initialize(game: GameNew): Unit = {
      Logger.info("==> New game")
      val newGame = Parser.initialize(source)
      go

      playNextTurn(newGame.toInProgress)
    }

    initialize(GameNew())

  }

  def playNextTurn(game: GameInProgress): Unit = {

    Logger.info("==> Next turn")

    val newGameState = Parser.parse(source, game.parameters, game.board.water)

    if(newGameState.gameOver) {
      Unit
      Logger.info("==> End of game")
    }
    else
      orders(newGameState)

  }

  def orders(newGameState: GameInProgress): Unit = {
    val orders = bot.ordersFrom(newGameState)
    orders.map(_.inServerSpeak).foreach(writer.write)
    go
    playNextTurn(newGameState)
  }

  def go(): Unit = {
    writer.write("go\n")
    writer.flush
  }

}
