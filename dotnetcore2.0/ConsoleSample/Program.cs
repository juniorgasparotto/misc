using Microsoft.EntityFrameworkCore;
using SysCommand.ConsoleApp;
using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Infrastructure;

namespace ConsoleSample
{
    public class Program : Command
    {
        static void Main(string[] args)
        {
            App.RunApplication();
        }

        public void Run()
        {
            using (var context = new BloggingContext())
            {
                context.Database.EnsureCreated();

                var all = context.Posts.ToArrayAsync();
                var post = new Post {
                    Blog = new Blog() {
                        Url2 = "teste"
                    },
                };

                context.Add(post);
                context.SaveChanges();
            }
        }
    }

    public class BloggingContext : DbContext
    {
        public DbSet<Blog> Blogs { get; set; }

        public DbSet<Post> Posts { get; set; }

        public int TenantId {get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
           
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlite(@"DataSource=.\\data.db");
        }
    }

    public class Blog
    {
        public int BlogId { get; set; }
        public string Url2 { get; set; }

        public List<Post> Posts { get; set; }
    }

    public class Post
    {
        public int PostId { get; set; }
        public string Title { get; set; }
        public int BlogId { get; set; }
        public Blog Blog { get; set; }
    }
}
