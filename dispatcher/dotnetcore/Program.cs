using System;
using RabbitMQ.Client;

namespace JobDispatcher
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            ConnectionFactory factory = new ConnectionFactory();
            factory.Uri = new Uri("amqp://root:654231@47.52.157.46:32825/FILES");
            IConnection connection = factory.CreateConnection();
            IModel channel = connection.CreateModel();
            channel.ExchangeDeclare(exchange: "/question/consume", type: "direct", durable: false, autoDelete: false, arguments: null);
            // channel.BasicPublish()
        }
    }
}
