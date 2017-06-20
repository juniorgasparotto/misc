﻿using Microsoft.EntityFrameworkCore;
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
                var all = context.Posts.ToArrayAsync();
                var post = new Post {
                    Blog = new Blog() {
                        Url = "teste"
                    },
                };

                context.Add(post);
                context.SaveChangesAsync();
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
            // optionsBuilder.UseSqlite(@"DataSource=.\\data.db");
            optionsBuilder.UseSqlServer(@"Data Source=(localdb)\MSSQLLocalDB;Initial Catalog=ConsoleSample;Integrated Security=True;Connect Timeout=30;Encrypt=False;TrustServerCertificate=True;ApplicationIntent=ReadWrite;MultiSubnetFailover=False");
        }
    }

    public class Blog
    {
        public int BlogId { get; set; }
        public string Url { get; set; }

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
