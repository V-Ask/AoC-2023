package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type cubeset struct {
	reds   int
	greens int
	blues  int
}

func max_int(i int, other int) int {
	if i < other {
		return other
	}
	return i
}

func (cs cubeset) Max(other *cubeset) *cubeset {
	cs.blues = max_int(cs.blues, other.blues)
	cs.greens = max_int(cs.greens, other.greens)
	cs.reds = max_int(cs.reds, other.reds)
	return &cs
}

func (cs cubeset) SetCubesetVal(colorString string, value int) *cubeset {
	switch colorString {
	case "blue":
		cs.blues = value
	case "green":
		cs.greens = value
	case "red":
		cs.reds = value
	}
	return &cs
}

func createSet(reds int, greens int, blues int) *cubeset {
	set := cubeset{reds: reds, greens: greens, blues: blues}
	return &set
}

func readFile(filepath string) []string {
	file, err := os.Open(filepath)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()
	var output []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		output = append(output, scanner.Text())
	}
	return output
}

func compareColor(amount int, colorName string, limits *cubeset) bool {
	switch colorName {
	case "red":
		return amount <= limits.reds
	case "green":
		return amount <= limits.greens
	case "blue":
		return amount <= limits.blues
	}
	return false
}

func isSetLegal(set string, limits *cubeset) bool {
	draws := strings.Split(set, ",")
	for _, draw := range draws {
		words := strings.Split(strings.Trim(draw, " "), " ")
		amount, err := strconv.Atoi(words[0])
		if err != nil {
			log.Fatal(err)
		}
		if !compareColor(amount, words[1], limits) {
			return false
		}
	}
	return true
}

func isGameLegal(line string, limits *cubeset) bool {
	sets := strings.Split(line, ";")
	for _, set := range sets {
		if !isSetLegal(set, limits) {
			return false
		}
	}
	return true
}

func chal1(inputlines []string) int {
	var sum = 0
	for i, line := range inputlines {
		game := strings.Trim(strings.Split(line, ":")[1], " ")
		if isGameLegal(game, createSet(12, 13, 14)) {
			sum += (i + 1)
		}
	}
	return sum
}

func getSetCubes(set string) *cubeset {
	var set_cubes *cubeset
	set_cubes = createSet(0, 0, 0)
	draws := strings.Split(set, ",")
	for _, draw := range draws {
		words := strings.Split(strings.Trim(draw, " "), " ")
		value, err := strconv.Atoi(words[0])
		if err != nil {
			log.Fatal(err)
		}
		set_cubes = set_cubes.SetCubesetVal(words[1], value)
	}
	return set_cubes
}

func getPower(game string) int {
	sets := strings.Split(game, ";")
	var max_set *cubeset
	max_set = createSet(0, 0, 0)
	for _, set := range sets {
		set_cubes := getSetCubes(set)
		max_set = max_set.Max(set_cubes)
	}

	return max_set.reds * max_set.greens * max_set.blues
}

func chal2(inputlines []string) int {
	var sum = 0
	for _, line := range inputlines {
		game := strings.Trim(strings.Split(line, ":")[1], " ")
		sum += getPower(game)
	}
	return sum
}

func main() {
	fmt.Println("Challenge 1: ", chal1(readFile("input.txt")))
	fmt.Println("Challenge 2: ", chal2(readFile("input.txt")))
}
