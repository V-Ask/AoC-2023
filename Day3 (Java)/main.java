import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

class Coordinates {
    private int row;
    private int col;
    private char symbol;

    public Coordinates(int row, int col, char symbol) {
        this.row = row;
        this.col = col;
        this.symbol = symbol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getSym() {
        return symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates)) {
            return false;
        }
        Coordinates other = (Coordinates)obj;
        return (other.row == row && other.col == col && other.symbol == symbol);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.hashCode(row) + Integer.hashCode(col) + Character.hashCode(symbol);
        return result;
    }
}

class Day3 {
    
    private static final Character[] NON_SYMBOLS = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.'};

    private static List<Character> NON_SYMBOL_ARRAYLIST() {
        return Arrays.asList(NON_SYMBOLS);
    }

    private List<String> loadFile(String path) {
        List<String> output = new ArrayList<>();
        try {
            Path filePath = Paths.get(path);
            output = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    private Optional<Integer> getNumeric(String string) {
        Optional<Integer> output = Optional.empty();
        try {
            output = Optional.of(Integer.parseInt(string));
        } catch (NumberFormatException nfe) {
            return output;
        }
        return output;
    }

    private Coordinates gridChar(int row, int col, List<String> lines) {
        return new Coordinates(row, col, lines.get(row).charAt(col));
    }

    private Optional<Coordinates> symbolAtGridChar(int row, int col, List<String> lines) {
        Optional<Coordinates> possibleOutput = Optional.of(gridChar(row, col, lines));
        if (NON_SYMBOL_ARRAYLIST().contains(possibleOutput.get().getSym())) return Optional.empty();
        return possibleOutput;
    }

    private List<Coordinates> isSymbolAdjacent(int row, int col, List<String> lines) {
        List<Coordinates> output = new ArrayList<>();
        if (row > 0) {
            var upSym = symbolAtGridChar(row - 1, col, lines);
            if(upSym.isPresent()) output.add(upSym.get());
            if(col > 0) {
                var upLeftSym = symbolAtGridChar(row - 1, col - 1, lines);
                if(upLeftSym.isPresent()) output.add(upLeftSym.get());
            }
            if(col < lines.get(row).length() - 1) {
                var upRightSym = symbolAtGridChar(row - 1, col + 1, lines);
                if(upRightSym.isPresent()) output.add(upRightSym.get());
            }
        }
        if (row < lines.size() - 1) {
            var downSym = symbolAtGridChar(row + 1, col, lines);
            if(downSym.isPresent()) output.add(downSym.get());
            if(col > 0) {
                var downLeftSym = symbolAtGridChar(row + 1, col - 1, lines);
                if(downLeftSym.isPresent()) output.add(downLeftSym.get());
            }
            if (col < lines.get(row).length() - 1) {
                var downRightSym = symbolAtGridChar(row + 1, col + 1, lines);
                if(downRightSym.isPresent()) output.add(downRightSym.get());
            }
        }
        if (col > 0) {
            var leftSym = symbolAtGridChar(row, col - 1, lines);
            if(leftSym.isPresent()) output.add(leftSym.get());
        }
        if (col < lines.get(row).length() - 1) {
            var rightSym = symbolAtGridChar(row, col + 1, lines);
            if(rightSym.isPresent()) output.add(rightSym.get());
        }
        return output;
    } 



    private String challenge1(List<String> inputLines) {
        BigInteger finalResult = BigInteger.valueOf(0);
        for (int i = 0; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            int curNumber = 0;
            boolean symbolAdjacent = false;
            for (int j = 0; j < line.length(); j++) {
                String charString = Character.toString(line.charAt(j));
                Optional<Integer> possibleInteger = getNumeric(charString);
                if (possibleInteger.isPresent()) {
                    curNumber = curNumber * 10 + possibleInteger.get();
                    if (!symbolAdjacent) {
                        symbolAdjacent = !isSymbolAdjacent(i, j, inputLines).isEmpty();
                    }
                } else {
                    if (symbolAdjacent) {
                        finalResult = finalResult.add(BigInteger.valueOf(curNumber));
                        symbolAdjacent = false;
                    }
                    curNumber = 0;
                }
                
            }
            if(symbolAdjacent) {
                finalResult = finalResult.add(BigInteger.valueOf(curNumber));
            }
        }
        return finalResult.toString();
    }

    private String challenge2(List<String> inputLines) {
        HashMap<Coordinates, List<Integer>> symbolMap = new HashMap<>();
        int curNumber = 0;
        HashSet<Coordinates> uniqueCoordinates = new HashSet<>();
        for (int i = 0; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            curNumber = 0;
            uniqueCoordinates = new HashSet<>();
            for (int j = 0; j < line.length(); j++) {
                String charString = Character.toString(line.charAt(j));
                Optional<Integer> possibleInteger = getNumeric(charString);
                if (possibleInteger.isPresent()) {
                    curNumber = curNumber * 10 + possibleInteger.get();
                    List<Coordinates> adjacentSymbols = isSymbolAdjacent(i, j, inputLines);
                    uniqueCoordinates.addAll(adjacentSymbols);
                } else if (curNumber != 0) {
                    for (Coordinates symbol : uniqueCoordinates) {
                        List<Integer> existingNums;
                        if(symbolMap.containsKey(symbol)) {
                            existingNums = symbolMap.get(symbol);
                        } else {
                            existingNums = new ArrayList<>();
                        }
                        existingNums.add(curNumber);

                        symbolMap.put(symbol, existingNums);
                    }
                    curNumber = 0;
                    uniqueCoordinates = new HashSet<>();
                }  
            }
            for (Coordinates symbol : uniqueCoordinates) {
                List<Integer> existingNums;
                if(symbolMap.containsKey(symbol)) {
                    existingNums = symbolMap.get(symbol);
                } else {
                    existingNums = new ArrayList<>();
                }
                existingNums.add(curNumber);

                symbolMap.put(symbol, existingNums);
            }
        }
        var wrapper = new Object(){ int finalResult = 0; };
        symbolMap.forEach((key, value) -> {
            if(key.getSym() == '*' && value.size() == 2) {
                wrapper.finalResult += value.get(0) * value.get(1);
            }
        });
        return String.valueOf(wrapper.finalResult);
    }

    public void runChallenges(String inputPath) {
        var inputLines = loadFile(inputPath);
        System.out.println("Challenge 1: " + challenge1(inputLines));
        System.out.println("Challenge 2: " + challenge2(inputLines));
    }
}

class Main {
    public static void main(String[] args) {
        var day3 = new Day3();
        day3.runChallenges("input.txt");
    }
}