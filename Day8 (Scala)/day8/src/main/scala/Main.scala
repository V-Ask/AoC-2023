import scala.io.Source
import scala.collection.immutable.Queue
import scala.compiletime.ops.int

def getInstructions(instStr: String): Queue[Char] =
  return instStr.toCharArray()
    .foldLeft(Queue.empty)((acc, c) => acc.appended(c))

def getCoordinates(coordLines: List[String]): Map[String, (String, String)] =
  def getToCoords(line: String): (String, String) =
    val toCoords = line.substring(0, line.length() - 1).split("\\(").apply(1).split(", ")
    return (toCoords.apply(0), toCoords.apply(1))
  return coordLines
    .foldLeft(Map.empty[String, (String, String)])((mappedCoords, coord) =>
      val from = coord.substring(0, 3)
      val toCoords = getToCoords(coord)
      mappedCoords + (from -> toCoords)
    )
    

def traverseInstruction(currentLoc: String, instructions: Queue[Char], 
  map: Map[String, (String, String)]): (String, Queue[Char]) =
  val (inst, newQueue) = instructions.dequeue
  val (l_to, r_to) = map.apply(currentLoc)
  ((inst match 
    case 'L' => l_to
    case 'R' => r_to), newQueue.appended(inst))

def traverseInstructions(currentLoc: String, instructions: Queue[Char], 
  map: Map[String, (String, String)], steps: Int, endPoint: String => Boolean): Int =
  if(endPoint(currentLoc)) return steps
  val (nextLoc, newQueue) = traverseInstruction(currentLoc, instructions, map)
  return traverseInstructions(nextLoc, newQueue, map, steps + 1, endPoint)
  

def challenge1(lines: List[String]): Int = 
  val instructions = getInstructions(lines apply 0)
  val mappings = getCoordinates(lines drop 2)
  val pathSteps = traverseInstructions("AAA", instructions, mappings, 0, (s => s == "ZZZ"))
  return pathSteps

def GCD(a: Long, b: Long): Long =
  return if (b == 0) a else GCD(b, a % b)

def LCM(a: Long, b: Long): Long =
  return if (a == 0 || b == 0) 0 else a * b / GCD(a, b)

def LCM(numbers: List[Long]): Long = 
  return numbers.reduce(LCM)

def challenge2(lines: List[String]): Long = 
  val instructions = getInstructions(lines apply 0)
  val mappings = getCoordinates(lines drop 2)
  val startPoints = mappings.keySet.filter((from => from.charAt(2) == 'A')).toList
  def end(s: String): Boolean =
    return s.charAt(2) == 'Z'
  val movesTillDestination = startPoints.foldLeft(List.empty[Long])((acc, point) =>
    acc.appended(traverseInstructions(point, instructions, mappings, 0, end).toLong) 
  )
  println(LCM(List(21883, 13019, 19667, 14681)))
  return LCM(movesTillDestination)

def runChallenges(lines: List[String]): Unit = 
  //println(challenge1(lines))
  println(challenge2(lines))

def readInput(filepath: String): List[String] = 
  return Source.fromResource(filepath).getLines().toList

@main def hello: Unit =
  val inputLines = readInput("input.txt")
  runChallenges(inputLines)
