namespace MVCSample.Models
{
    public class Post
    {
        public int PostId { get; set; }
        public string Title { get; set; }
        public int BlogId { get; set; }
        public Blog Blog { get; set; }
    }
}
