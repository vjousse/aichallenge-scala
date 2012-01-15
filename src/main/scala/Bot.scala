package antwar

trait Bot {
  def ordersFrom(gameState: Game): Set[Order]
}
