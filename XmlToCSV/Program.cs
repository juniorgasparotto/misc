using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using System.Collections;
using System.Text.RegularExpressions;

namespace CsvToSQL
{
    class Program
    {
        public class CsvRow        
        {
            public string Skus {get;set;}
            public string CanCorporateSales { get; set; }
            public string FirstName { get; set; }
            public string LinkId { get; set; }
            public string KeyWords { get; set; }
        };

        static void Main(string[] args)
        {
            #region get parameters
            
            var path = @"C:\Users\glauberj\Desktop\output.csv";
            var sqlWriterName = "sqlserver";
            var tableName = "#CSV";
            var maxBulk = 25;
            var insertStringFormat = SqlInsertStringFormat.None;

            #endregion

            SqlServerWriter sqlWriter = null;
            if (sqlWriterName == "sqlserver")
                sqlWriter = new SqlServerWriter();
            else
                throw new Exception("The parameter 'SqlWriterName' not found");

            var sqlTable = SqlTable.CsvToSqlTable(ReadNormalCSV(path), sqlWriter, true, ";");
            var output = "";
            if (sqlTable != null)
            {
                output = sqlWriter.GenerateTableWithInserts(sqlTable, tableName, maxBulk, insertStringFormat);
            }
            else
            {
                output = "The 'CSV' is empty";
            }

            Console.Write(output);
            //CSVToSQL(ReadFromSqlOutput(path), true, ";");

            //XmlToCSV();
        }

        static TextReader ReadNormalCSV(string path)
        {
            return new StreamReader(path);
        }

        static TextReader ReadFromSqlOutput(string path)
        {
            var fileContent = File.ReadAllText(path);
            var lines = fileContent.Split(new string[] { "\r\n" }, StringSplitOptions.None);
            var strBuilder = new StringBuilder();

            string input = lines[1];
            string pattern = @"-+(\s)?";

            var captures = Regex.Matches(input, pattern);

            var countLine = 0;

            foreach (var line in lines)
            {
                var start = 0;
                var str = "";
                foreach (Match capture in captures)
                {
                    var end = capture.Value.Length;
                    var delimiter = ";";
                    if (start + end > line.Length)
                    {
                        end = line.Length - start;
                        delimiter = "";
                    }
                    else if (start + end == line.Length)
                    { 
                        delimiter = "";
                    }

                    var value = line.Substring(start, end);
                    if (value != capture.Value)
                        str += @"""" + value.Trim().Replace(@"""", "\"\"") + @"""" + delimiter;
                    start += end;
                }

                var testEmpty = str.Replace(@"""", "").Trim();
                if (countLine != 1 && testEmpty == "")
                    break;

                countLine++;

                if (testEmpty != "")
                    strBuilder.AppendLine(str);
            }

            var debu = strBuilder.ToString();
            return new System.IO.StringReader(strBuilder.ToString());
        }


        static void XmlToCSV()
        {
            var pathFrom = @"D:\Junior\Projetos\GITHUB.COM\juniorgasparotto\XmlToCSV\in.xml";
            var pathTo = @"D:\Junior\Projetos\GITHUB.COM\juniorgasparotto\XmlToCSV\out.csv";
            XElement xelm = XElement.Load(pathFrom);
            var docs = xelm.Descendants("doc").ToList();

            var streamWriter = new System.IO.StreamWriter(pathTo);
            var writer = new CsvHelper.CsvWriter(streamWriter);

            writer.WriteRecord(new CsvRow()
            {
                Skus = "skus",
                FirstName = "firstName",
                CanCorporateSales = "canCorporateSales",
                LinkId = "linkId",
                KeyWords = "keyWords"
            });

            foreach (var doc in docs)
            {
                var canCorporateSales = doc.Descendants("bool").ToList().Where(f => f.Attribute("name").Value == "canCorporateSales").FirstOrDefault().Value;
                var firstName = doc.Descendants("str").ToList().Where(f => f.Attribute("name").Value == "firstName").FirstOrDefault().Value;
                var keyWords = doc.Descendants("str").ToList().Where(f => f.Attribute("name").Value == "keyWords").FirstOrDefault().Value;
                var linkId = doc.Descendants("str").ToList().Where(f => f.Attribute("name").Value == "linkId").FirstOrDefault().Value;
                var skusId = doc.Descendants("arr").ToList().Where(f => f.Attribute("name").Value == "skuId").Elements("int").ToList();
                var skus = "";
                foreach (var elm in skusId)
                {
                    if (skus != "")
                        skus += ", " + elm.Value;
                    else
                        skus += elm.Value;
                }

                var obj = new CsvRow() {
                    Skus = skus,
                    FirstName = firstName,
                    CanCorporateSales = canCorporateSales,
                    LinkId = linkId,
                    KeyWords = keyWords
                };

                writer.WriteRecord(obj);
                
            }

            streamWriter.Close();
        }
    }
}
