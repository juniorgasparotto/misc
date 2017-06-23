using SysCommand.ConsoleApp;
using System;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace MVCSample
{
    public class Program : Command
    {
        static void Main(string[] args)
        {
            App.RunApplication();
        }

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
