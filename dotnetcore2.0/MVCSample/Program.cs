using SysCommand.ConsoleApp;
using System;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.Extensions.Configuration;
using System.IO;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore;
using SysCommand.Mapping;

namespace MVCSample
{
    public class Program : Command
    {
        static void Main(string[] args)
        {
            BuildWebHost(args).Run();
            App.RunApplication();
        }

        [Action(Ignore=true)]
        public static IWebHost BuildWebHost(string[] args) =>
                    WebHost.CreateDefaultBuilder(args)
                        .UseStartup<Startup>()
                        .Build();

        public void AddPost()
        {
            using (var context = new BloggingContext())
            {
                var post = new Post
                {
                    Blog = new Blog()
                    {
                        Url2 = "teste"
                    },
                };

                context.Add(post);
                context.SaveChanges();
            }
        }
    }
}
