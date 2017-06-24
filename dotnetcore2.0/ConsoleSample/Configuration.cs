using Microsoft.EntityFrameworkCore;
using SysCommand.ConsoleApp;
using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace Teste
{
    public static class ConfigurationManager
    {
        public static IConfigurationRoot Configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json").Build();
                
        public static string GetConnectionString(string connName = "DefaultConnection")
        {
            return Configuration.GetConnectionString(connName);
        }
    }
}
