package antwar

trait Bot {
  def ordersFrom(gameState: GameInProgress): Set[Order]
}
