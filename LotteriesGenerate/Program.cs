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
            Generate("quinta", 0, 80, 5, new int[] { 22, 7 });

            // mega sena
            Generate("mega-sena", 0, 61, 6, new int[] { 22, 7 });

            Console.Read();
        }

        public static void Generate(string name, int min, int max, int maxPerGame, int[] pivos)
        {
            var used = new List<int>();
            var games = new List<List<int>>();

            foreach (var pivo in pivos)
                used.Add(pivo);

            while (used.Count < max)
            {
                var game = new List<int>(pivos);
                games.Add(game);

                while (game.Count < maxPerGame)
                {
                    var next = new Random().Next(0, 81);

                    if (next != 0 && !game.Contains(next) && !used.Contains(next))
                    { 
                        used.Add(next);
                        game.Add(next);

                        if (used.Count == max)
                            break;
                    }
                }
            }

            Console.WriteLine("*** {0} ***", name);
            Console.WriteLine();

            var test = new List<int>();
            foreach(var game in games)
            {
                game.Sort();

                foreach(var number in game)
                { 
                    Console.Write(number + " ");
                    test.Add(number);
                }
                Console.WriteLine();
            }

            Console.WriteLine();
            Console.WriteLine("-- DEBUG -- ");

            test.Sort();

            foreach (var number in test)
                Console.Write(number + " ");

            Console.WriteLine();
            Console.WriteLine();
        }
    }
}
