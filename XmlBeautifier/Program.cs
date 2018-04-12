using System;
using System.Collections.Generic;
using System.Text;
using System.Xml;
using System.IO;

namespace xmllint
{
    class Program
    {

        static TextReader textReader = Console.In;

        static void Main(string[] args)
        {
            Console.OutputEncoding = System.Text.Encoding.UTF8;
            Console.InputEncoding = System.Text.Encoding.UTF8;

            initialize(args);
            try
            {
                var actual = Console.ForegroundColor;

                Console.ForegroundColor = ConsoleColor.Green;
                XmlWriterSettings settings = new XmlWriterSettings();
                settings.Indent = true;
                settings.Encoding = Encoding.UTF8;
                XmlDocument doc = new XmlDocument();                
                doc.Load(textReader);

                XmlTextWriter writer = new XmlTextWriter(Console.Out);
                writer.Formatting = Formatting.Indented;
                writer.Indentation = 4;                
                doc.WriteContentTo(writer);
                Console.ForegroundColor = actual;
            }
            catch (Exception e)
            {
                Console.WriteLine("Exception : " + e);
            }
        }

        private static void initialize(string[] args)
        {
            if (args.Length == 1)
            {
                String fileName = args[0];
                textReader = new StreamReader(fileName, Encoding.UTF8, true);
                
            }
        }
    }
}