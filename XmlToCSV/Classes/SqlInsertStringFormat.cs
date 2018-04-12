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
    public enum SqlInsertStringFormat
    {
        None,
        BreakLineForEachColumn,
        BreakLineAndShowColumnNameForEachColumn
    }
}