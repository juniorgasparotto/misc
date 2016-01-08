using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LotteriesGenerate
{
    class Program
    {
        static Random random = new Random();
        static List<List<int>> allGamesSorted = new List<List<int>>();
        static List<List<int>> currentChoosed = new List<List<int>>();
        static Dictionary<int, int> countNumbers = new Dictionary<int, int>();
        static Dictionary<int, int> countHits = new Dictionary<int, int>();

        static void Main(string[] args)
        {
            // ** mega sena
            //Generate(false, "Mega-Sena", 1, 60, 6, new int[] { 7, 22, 40 });
            // ** quina
            //Generate(false, "Quina", 1, 80, 5, new int[] { 7, 22, 40 });
            // ** loto-facil
            //Generate(false, "Loto-facil", 1, 25, 15, new int[] { 2, 3, 5, 7, 8, 9, 10, 13, 15, 17, 20, 24, 25 });

            // mega-sena
            while (true)
            {
                //var winningNumbers = new int[] { 4, 5, 6 };
                var winningNumbers = new int[] { 5, 6 };
                var pivos = new int[] { 7, 22 };
                TestAttemps("Mega-Sena", 1, 60, 6, winningNumbers, pivos);
                Console.WriteLine();
                Console.ReadKey();
            }
        }

        public static void TestAttemps(string name, int min, int max, int maxPerGame, int[] winningNumbers, int[] pivos)
        {
            if (currentChoosed.Count == 0)
                currentChoosed = Generate(true, name, min, max, maxPerGame, pivos);

            Console.WriteLine("***********************************************************");
            Console.WriteLine("-> " + name);
            Console.WriteLine("***********************************************************");

            Console.WriteLine();
            Console.WriteLine("Aguardando prêmios...");
            Console.WriteLine();

            var i = 1;
            while(true)
            {
                var gamesHits = new Dictionary<List<int>, int>();
                var gameSorted = SortNumber(random, min, max, maxPerGame);

                // caso duplique, tenta gerar um número que ainda não gerou apenas uma vez para não gerar loop infinito
                while (IsDuplicate(gameSorted))
                {
                    gameSorted = SortNumber(random, min, max, maxPerGame);
                    break;
                }

                // conta ocorrencias dos números
                foreach(var n in gameSorted)
                {
                    if (!countNumbers.ContainsKey(n))
                        countNumbers.Add(n, 0);

                    countNumbers[n]++;
                }

                allGamesSorted.Add(gameSorted);
                gameSorted.Sort();

                foreach (var choosed in currentChoosed)
                {
                    foreach (var numberChoosed in choosed)
                    {
                        if (gameSorted.Contains(numberChoosed))
                        {
                            if (!gamesHits.ContainsKey(choosed))
                                gamesHits.Add(choosed, 0);
                            gamesHits[choosed] += 1;
                        }
                    }
                }

                foreach (var keyValue in gamesHits)
                {
                    if (winningNumbers.Contains(keyValue.Value))
                    {
                        if (!countHits.ContainsKey(keyValue.Value))
                            countHits.Add(keyValue.Value, 0);
                        countHits[keyValue.Value]++;

                        Console.WriteLine();
                        Console.WriteLine("***********************************************************");
                        Console.WriteLine("Párabens!!! Você acertou: " + keyValue.Value + " números");
                        Console.WriteLine("***********************************************************");
                        Console.WriteLine();
                        Console.WriteLine("#Concurso: {0}", i);
                        Console.WriteLine("#Data: {0}", DateTime.Now);
                        Console.WriteLine("#Sorteados: {0}", GetStringNumbers(gameSorted));
                        Console.WriteLine("#Jogado   : {0}", GetStringNumbers(keyValue.Key));
                            
                        Console.WriteLine();
                    }

                    // para somente quando acertar o máximo
                    if (keyValue.Value == maxPerGame)
                    {
                        Console.WriteLine("+++++++++++ Párabens!!! Você acertou o prémio MÁXIMO!!! ++++++++++");
                        Console.WriteLine();
                        PrintResume(gameSorted, i);
                        return;
                    }
                }

                i++;

                if (Console.KeyAvailable)
                {
                    PrintResume(gameSorted, i);

                    Console.WriteLine();
                    Console.WriteLine("Aguardando prêmios...");
                    Console.WriteLine();
                    // o primeiro key press não irá parar o código, pois precisa a tecla já foi digitada
                    Console.ReadKey();

                    // esse para para mostrar as informações
                    Console.ReadKey();
                }
            }
        }

        private static void PrintResume(List<int> gameSorted, int iCountConcourse)
        {
            Console.WriteLine();
            Console.WriteLine("***********************************************************");
            Console.WriteLine("-> Lista dos números jogados");
            Console.WriteLine("***********************************************************");
            Console.WriteLine();

            foreach (var game in currentChoosed)
            {
                var win = GetStringNumbers(game) == GetStringNumbers(gameSorted) ? " * Game WIN!!! " : "";
                Console.WriteLine(GetStringNumbers(game) + win);
            }

            Console.WriteLine();
            Console.WriteLine("***********************************************************");
            Console.WriteLine("-> Lista dos últimos 5 sorteios");
            Console.WriteLine("***********************************************************");
            Console.WriteLine();

            var reverse = allGamesSorted.ToList();
            reverse.Reverse();

            foreach (var lastGame in reverse.Take(5))
            {
                var win = GetStringNumbers(lastGame) == GetStringNumbers(gameSorted) ? " * last " : "";
                Console.WriteLine(GetStringNumbers(lastGame) + win);
            }

            Console.WriteLine();
            Console.WriteLine("***********************************************************");
            Console.WriteLine("-> Ocorrências dos números");
            Console.WriteLine("***********************************************************");
            Console.WriteLine();

            var countPrint = 1;
            var order = countNumbers.OrderByDescending(f => f.Value).ToList();
            int maxRepeat = order.First().Value.ToString().Length;
            foreach (var keyValue in order)
            {
                var print = keyValue.Key.ToString("00") + " (" + keyValue.Value.ToString(new string('0', maxRepeat)) + "x) | ";
                if (countPrint % 10 == 0)
                    Console.WriteLine(print);
                else
                    Console.Write(print);
                countPrint++;
            }

            Console.WriteLine();
            Console.WriteLine();
            Console.WriteLine("***********************************************************");
            Console.WriteLine("-> Você acertou:");
            Console.WriteLine("***********************************************************");
            Console.WriteLine();
            Console.WriteLine("#Total de concursos: " + iCountConcourse);
            Console.WriteLine("#Total de jogos efetuados: " + iCountConcourse * currentChoosed.Count);
            Console.WriteLine("#Total em reais jogados: " + ((iCountConcourse * currentChoosed.Count) * 3.5m));
            //Console.WriteLine("#Total em reais em prêmio estimado: " + ((i * currentChoosed.Count) * 3.5m));

            if (countHits.Count == 0)
                Console.WriteLine("0 acertos ;(");

            foreach (var keyValue in countHits)
                Console.WriteLine("Acertou {0} jogos com #{1} números", keyValue.Value, keyValue.Key);
        }

        public static string GetStringNumbers(List<int> numbers)
        {
            var output = "";
            foreach (var n in numbers)
                output += n.ToString("00") + " ";

            return output;
        }

        public static List<List<int>> Generate(bool ommitOutput, string name, int min, int max, int maxPerGame, int[] pivos)
        {
            if (!ommitOutput)
            { 
                Console.WriteLine("***********************************************************");
                Console.WriteLine("-> " + name);
                Console.WriteLine("***********************************************************");
            }

            var random = new Random();
            var used = new List<int>();
            var games = new List<List<int>>();

            if (!ommitOutput)
            {
                Console.WriteLine();
                Console.WriteLine();
                Console.WriteLine("====> Jogos com {0} números fixos: ", pivos.Length);
                Console.WriteLine();
            }

            var pivosSort = pivos.ToList();
            pivosSort.Sort();

            foreach (var pivo in pivosSort)
            { 
                used.Add(pivo);
                if (!ommitOutput)
                {
                    Console.Write(pivo.ToString("00") + " ");
                }
            }

            while (used.Count < max)
            {
                var game = new List<int>(pivos);
                games.Add(game);

                while (game.Count < maxPerGame)
                {
                    var next = random.Next(min, max + 1);

                    if (!game.Contains(next) && !used.Contains(next))
                    { 
                        used.Add(next);
                        game.Add(next);

                        if (used.Count == max)
                        {
                            if (game.Count < maxPerGame)
                                game.AddRange(SortNumber(random, min, max, maxPerGame - game.Count));

                            break;
                        }
                    }
                }
            }

            if (!ommitOutput)
            {
                Console.WriteLine();
                Console.WriteLine();
                Console.WriteLine("====> Resultados: ");
                Console.WriteLine();
            }

            var test = new List<int>();
            var i = 1;
            foreach(var game in games)
            {
                game.Sort();
                var countGamesChars = new String('0', games.Count.ToString().Length);

                if (!ommitOutput)
                {
                    Console.Write("#Jogo " + i++.ToString(countGamesChars) + ":   ");
                }

                foreach(var number in game)
                {
                    if (!ommitOutput)
                    {
                        Console.Write(number.ToString("00") + " ");
                    }
                    test.Add(number);
                }

                if (!ommitOutput)
                {
                    Console.WriteLine();
                }
            }

            if (!ommitOutput)
            {
                Console.WriteLine();
                Console.WriteLine("====> Ocorrências dos números: ");
                Console.WriteLine();
            }

            test.Sort();

            foreach (var number in test.Distinct())
            {
                var amountStr = "";
                var countNumber = test.Count(f=>f == number);

                if (countNumber > 1)
                    amountStr = string.Format(" ({0}x)", countNumber);

                var write = number.ToString("00") + amountStr + " ";

                if (!ommitOutput)
                {
                    if (number % 5 == 0)
                        Console.WriteLine(write);
                    else
                        Console.Write(write);
                }
            }

            if (!ommitOutput)
            {
                Console.WriteLine();
            }

            return games;
        }

        private static bool IsDuplicate(List<int> game)
        {
            foreach(var sorted in allGamesSorted)
            {
                var count = 0;
                foreach (var numberSorted in sorted)
                {
                    if (game.Contains(numberSorted))
                        count++;
                }

                if (count == game.Count)
                    return true;
            }

            return false;
        }

        private static List<int> SortNumber(Random random, int min, int max, int maxPerGame)
        {
            var game = new List<int>();
            while (game.Count < maxPerGame)
            {
                var next = random.Next(min, max + 1);

                if (!game.Contains(next))
                    game.Add(next);
            }

            return game;
        }
    }
}
