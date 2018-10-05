using System;
using HtmlAgilityPack;
using System.Net.Http;
using RabbitMQ.Client;

namespace dotnetcore.parser
{
    class Program
    {
        static void Main(string[] args)
        {
            HttpClientHandler handler = new HttpClientHandler();
            handler.AllowAutoRedirect = true;
            HttpClient client = new HttpClient(handler);
            String response = client.GetStringAsync("https://stackoverflow.com/questions/5").Result;
            HtmlDocument doc = new HtmlDocument();
            doc.LoadHtml(response);
            HtmlNode node = doc.DocumentNode.SelectSingleNode("//*[@id=\"question-header\"]/h1/a");
            String text = node.InnerText;
            Console.WriteLine(text);
        }
    }
}
