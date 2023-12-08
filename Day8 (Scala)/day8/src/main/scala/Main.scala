import scala.io.Source
import scala.collection.immutable.Queue

def getInstructions(instStr: String): Queue[Char] =
  return instStr.toCharArray()
    .foldLeft(Queue.empty)((acc, c) => acc.appended(c))

def getCoordinates(coordLines: List[String]): Map[String, (String, String)] =
  return coordLines
    .foldLeft(Map.empty[String, (String, String)])((mappedCoords, coord) =>
      val from = coord.substring(0, 3)
      
    )

def challenge1(lines: List[String]): Int = 
  val instructions = getInstructions(lines.apply(0))

  println(instructions)
  return 0

def challenge2(lines: List[String]): Int = 
  return 1

def runChallenges(lines: List[String]): Unit = 
  println(challenge1(lines))
  println(challenge2(lines))

def readInput(filepath: String): List[String] = 
  return Source.fromResource(filepath).getLines().toList

@main def hello: Unit =
  val inputLines = readInput("input.txt")
  runChallenges(inputLines)

def msg = "I was compiled by Scala 3. :)"
