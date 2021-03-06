using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication.OpenIdConnect;
using Microsoft.AspNetCore.Mvc;
using MVCSample.Models;

namespace MVCSample.Controllers
{
    public class TestController : Controller
    {
        //
        // GET: /Account/SignIn
        [HttpGet]
        public IActionResult Index([FromServices ]ApplicationContext context)
        {
            var post = new Post();
            post.Blog = new Blog();
            post.Blog.Url2 = "URL";
            post.Title = "title";
            context.Posts.Add(post);
            context.SaveChanges();
            return RedirectToAction("Index", "Manage", new { area = "IdentityService" });
        }
    }
}
