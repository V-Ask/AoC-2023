struct Translation {
    public readonly long from;
    public readonly long sourceDestination;
    public readonly long range;

    public Translation(long from, long sourceDestination, long range) {
        this.from = from;
        this.sourceDestination = sourceDestination;
        this.range = range;
    }
}

class Challenge {

    static void Puts_l(List<int> line) {
        Console.Write("[");
        line.ForEach(delegate (int item) {
            Console.Write(item + ",");
        });
        Console.WriteLine("]");
        
    }
    static void Puts(object line) {
        Console.WriteLine(line);
    }

    static string[] GetLines(string filepath) {
        return File.ReadAllLines(filepath);
    }

    static List<long> getSeeds(string line) {
        List<long> seeds = [];
        string[] words = line.Split(" ");
        words.Skip(1).ToList().ForEach(delegate(string word) {
                seeds.Add(long.Parse(word));
        });
        return seeds;
    }

    static List<long> getSeedPairs(string line) {
        List<long> seeds = [];
        string[] words = line.Split(" ").Skip(1).ToArray();
        List<(string x, string y)> pairList = new List<(string x, string y)>();
        for (int i = 0; i < words.Length; i += 2)
        {
            var pair = (words[i], words[i + 1]);
            pairList.Add(pair);
        }
        pairList.ForEach(delegate((string x, string y) pairs) {
            long from = long.Parse(pairs.x);
            long amount = long.Parse(pairs.y);
            for(long i = from; i < from + amount; i ++) {
                seeds.Add(i);
            }
        });
        return seeds;
    }

    static long TranslateValues(long value, List<Translation> map) {
        var index = Array.BinarySearch(map.Select(trans => trans.from).ToArray(), value);
        var newVal = value;
        if (index < 0)
        {
            index = ~index - 1;
        }
        if (index >= 0)
        {
            var result = map[index];
            if (result.from + (result.range - 1) >= value) {
                long diff = value - result.from;
                newVal = result.sourceDestination + diff;
            }
        }
        return newVal;
    }

    static long Challenge1(string[] lines) {
        List<List<Translation>> maps = [];
        List<long>? seeds = [];
        int curMap = -1;
        foreach (var line in lines) {
            switch (line)
        {
            case "":
                curMap++;
                maps.Add([]);
                break;
            default:
                string[] words = line.Split(" ");
                if (words[0] == "seeds:") seeds = getSeeds(line);
                else if (words[^1] == "map:") break;
                else
                {
                    long destination = long.Parse(words[0]);
                    long location = long.Parse(words[1]);
                    long range = long.Parse(words[2]);
                    maps[curMap].Add(new Translation(location, destination, range));
                }
                break;
            }
        }
        foreach (var map in maps) {
            map.Sort((t1, t2) => t1.from.CompareTo(t2.from));
        }
        var locations = new List<long>();
        foreach (var seed in seeds) {
            long loc = seed;
            foreach(var transMap in maps) {
                loc = TranslateValues(loc, transMap);
            }
            locations.Add(loc);
        }
        return locations.Min();
    }

    static long Challenge2(string[] lines) {
        List<List<Translation>> maps = [];
        List<long>? seeds = [];
        int curMap = -1;
        foreach (var line in lines) {
            switch (line)
        {
            case "":
                curMap++;
                maps.Add([]);
                break;
            default:
                string[] words = line.Split(" ");
                if (words[0] == "seeds:") seeds = getSeedPairs(line);
                else if (words[^1] == "map:") break;
                else
                {
                    long destination = long.Parse(words[0]);
                    long location = long.Parse(words[1]);
                    long range = long.Parse(words[2]);
                    maps[curMap].Add(new Translation(location, destination, range));
                }
                break;
            }
        }
        foreach (var map in maps) {
            map.Sort((t1, t2) => t1.from.CompareTo(t2.from));
        }
        var locations = new List<long>();
        foreach (var seed in seeds) {
            long loc = seed;
            foreach(var transMap in maps) {
                loc = TranslateValues(loc, transMap);
            }
            locations.Add(loc);
        }
        return locations.Min();
    }

    public static void runChallenges(string filepath = "input.txt") {
        var lines = GetLines(filepath);
        Puts(Challenge1(lines));
        Puts(Challenge2(lines));
    }
}