using System.Collections.Generic;

namespace MVCSample
{
    public class Blog
    {
        public int BlogId { get; set; }
        public string Url2 { get; set; }
        public List<Post> Posts { get; set; }
    }
}
