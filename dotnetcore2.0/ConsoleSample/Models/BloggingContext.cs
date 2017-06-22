using Microsoft.EntityFrameworkCore;

namespace asdas
{
    public class BloggingContext : DbContext
    {
        public DbSet<Blog> Blogs { get; set; }

        public DbSet<Post> Posts { get; set; }

        public int TenantId { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            // optionsBuilder.UseSqlite(@"DataSource=.\\data.db");
            optionsBuilder.UseSqlServer(ConfigurationManager.GetConnectionString());
        }
    }
}
