

def getNumbers(numbers)
  individualNumbers = numbers.split(' ')
  return individualNumbers.map(&:to_i)
end

def getWinningSum(game = '')
  ticketNumbers = game.split(':', 2)[1].split('|', 2)
  winningNumbers = getNumbers ticketNumbers[0]
  scratchNumbers = getNumbers ticketNumbers[1]
  sum = 0
  scratchNumbers.each do |number|
    if winningNumbers.include? number
      if sum == 0
        sum = 1
      else
        sum *= 2
      end
    end
  end
  return sum
end

def challenge1(lines = [])
  sum = 0
  lines.each do |line|
    sum += getWinningSum line
  end
  return sum
end

def getAmountOfWins(game = '')
  wins = 0
  ticketNumbers = game.split(':', 2)[1].split('|', 2)
  winningNumbers = getNumbers ticketNumbers[0]
  scratchNumbers = getNumbers ticketNumbers[1]
  scratchNumbers.each do |number|
    if winningNumbers.include? number
      wins += 1
    end
  end
  return wins
end

def processCards(cardDict, games)
  totalCards = 0
  games.each_with_index do |game, index|
    totalCards += cardDict[index]
    wins = getAmountOfWins game
    wins.times do |i|
      cardDict[index + (i + 1)] += cardDict[index]
    end
  end
  return totalCards
end


def challenge2(lines = [])
  cards = {}
  lines.each_with_index do |item, index|
    cards[index] = 1
  end
  return processCards(cards, lines)
end

def runChallenges(filePath = 'input.txt')
  lines = File.readlines(filePath).map(&:chomp)
  puts challenge1(lines)
  puts challenge2(lines)
end

runChallenges 'input.txt'
