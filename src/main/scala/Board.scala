package antwar
import scala.math.{abs,min,pow}

case class Board(rows: Int, cols: Int,
    myAnts: Map[Tile, MyAnt] = Map(),
    enemyAnts: Map[Tile, EnemyAnt] = Map(),
    water: Map[Tile, Water] = Map(),
    food: Map[Tile, Food] = Map(),
    corpses: Map[Tile, Corpse] = Map(),
    myHills: Map[Tile, MyHill] = Map(),
    enemyHills: Map[Tile, EnemyHill] = Map()) {

  lazy val elements = myAnts ++ enemyAnts ++ water ++ food ++ corpses ++ myHills ++ enemyHills

  lazy val boardTiles = {

    val tiles = for{
      r <- (0 to rows)
      c <- (0 to rows)
    } yield Tile(c, r)

    tiles map { t => (t, Nothing(t)) } toMap

  }

  lazy val allTiles = boardTiles ++ elements

  def including[P <: Positionable](positionable: P) = positionable match {
    case friend: MyAnt    => this.copy(myAnts = this.myAnts.updated(friend.tile, friend))
    case enemy: EnemyAnt  => this.copy(enemyAnts = this.enemyAnts.updated(enemy.tile, enemy))
    case puddle: Water    => this.copy(water = this.water.updated(puddle.tile, puddle))
    case crumb: Food      => this.copy(food = this.food.updated(crumb.tile, crumb))
    case corpse: Corpse   => this.copy(corpses = this.corpses.updated(corpse.tile, corpse))
    case friend: MyHill   => this.copy(myHills = this.myHills.updated(friend.tile, friend))
    case enemy: EnemyHill => this.copy(enemyHills = this.enemyHills.updated(enemy.tile, enemy))
  }

  def neighborsOf(aTile: Tile): List[Tile] = {
    for{
      direction <- List(North, East, South, West)
      neighbor = tile(direction) of aTile
      if !water.contains(neighbor)
    } yield neighbor
  }

  def distanceFrom(one: Tile) = new {
    def to(another: Tile) = {
      val dRow = abs(one.row - another.row)
      val dCol = abs(one.column - another.column)
      pow(min(dRow, rows - dRow), 2) + pow(min(dCol, cols - dCol), 2)
    }
  }


  def closestHillFrom(one: Tile): Tile = myHills.values.minBy( h => distanceFrom(one) to (h.tile) ).tile

  def directionFrom(one: Tile) = new {
    def to(other: Tile): Set[CardinalPoint] = {
      val ns: Set[CardinalPoint] = if (one.row < other.row) {
        if (other.row - one.row >= rows / 2) Set(North) else Set(South)
      } else if (one.row > other.row) {
        if (one.row - other.row >= rows / 2) Set(South) else Set(North)
      } else Set()

      val ew: Set[CardinalPoint] = if (one.column < other.column) {
        if (other.column - one.column >= cols / 2) Set(West) else Set(East)
      } else if (one.column > other.column) {
        if (one.column - other.column >= cols / 2) Set(East) else Set(West)
      } else Set()

      ns ++ ew
    }

    def toNeighbor(other: Tile): CardinalPoint = {
      if((one.column + 1) % cols == other.column) East
      else if((one.row + 1) % cols == other.row) South
      else if((if(one.column == 0) (cols-1) else (one.column-1)) == other.column) West
      else North
    }
  }



  def tile(aim: CardinalPoint) = new {
    def of(tile: Tile) = {
      aim match {
        case North => tile.copy(row = if (tile.row == 0) rows - 1 else tile.row - 1)
        case South => tile.copy(row = (tile.row + 1) % rows)
        case East => tile.copy(column = (tile.column + 1) % cols)
        case West => tile.copy(column = if (tile.column == 0) cols - 1 else tile.column - 1)
      }
    }
  }

}
