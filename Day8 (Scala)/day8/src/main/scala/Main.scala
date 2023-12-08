import scala.io.Source
def challenge1(lines: List[String]): Int = 
  return 0

def challenge2(lines: List[String]): Int = 
  return 1

def runChallenges(lines: List[String]): Unit = 
  println(challenge1(lines))
  println(challenge2(lines))

def readInput(filepath: String): List[String] = 
  return Source.fromFile(filepath).getLines().toList

@main def hello: Unit =
  val inputLines = readInput("/input.txt")
  println(inputLines)
  runChallenges(inputLines)

def msg = "I was compiled by Scala 3. :)"
