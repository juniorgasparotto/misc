using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LotteriesGenerate
{
    class Program
    {
        static void Main(string[] args)
        {
            // quina
            Generate("Quina", 1, 80, 5, new int[] { 7, 22, 40 });

            // mega sena
            Generate("Mega-Sena", 1, 60, 6, new int[] { 7, 22, 40 });

            // loto-facil
            Generate("Loto-facil", 1, 25, 15, new int[] { 2, 3, 5, 7, 8, 9, 10, 13, 15, 17, 20, 24, 25 });

            Console.Read();
        }

        public static void Generate(string name, int min, int max, int maxPerGame, int[] pivos)
        {
            Console.WriteLine("***********************************************************");
            Console.WriteLine("-> " + name);
            Console.WriteLine("***********************************************************");

            var random = new Random();
            var used = new List<int>();
            var games = new List<List<int>>();

            Console.WriteLine();
            Console.WriteLine();
            Console.WriteLine("====> Jogos com {0} números fixos: ", pivos.Length);
            Console.WriteLine();

            var pivosSort = pivos.ToList();
            pivosSort.Sort();

            foreach (var pivo in pivosSort)
            { 
                used.Add(pivo);
                Console.Write(pivo.ToString("00") + " ");
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
                            break;
                    }
                }
            }

            Console.WriteLine();
            Console.WriteLine();
            Console.WriteLine("====> Resultados: ");
            Console.WriteLine();

            var test = new List<int>();
            var i = 1;
            foreach(var game in games)
            {
                game.Sort();
                var countGamesChars = new String('0', games.Count.ToString().Length);
                Console.Write("#Jogo " + i++.ToString(countGamesChars) + ":   ");

                foreach(var number in game)
                {
                    Console.Write(number.ToString("00") + " ");
                    test.Add(number);
                }
                Console.WriteLine();
            }
            
            Console.WriteLine();
            Console.WriteLine("====> Ocorrências dos números: ");
            Console.WriteLine();

            test.Sort();

            foreach (var number in test.Distinct())
            {
                var amountStr = "";
                var countNumber = test.Count(f=>f == number);

                if (countNumber > 1)
                    amountStr = string.Format(" ({0}x)", countNumber);

                var write = number.ToString("00") + amountStr + " ";

                if (number % 5 == 0)
                    Console.WriteLine(write);
                else
                    Console.Write(write);
            }

            Console.WriteLine();
        }
    }
}
