using System;
using System.Linq;
using System.Text;
using Newtonsoft.Json;
using RabbitMQ.Client;

namespace JobDispatcher
{
    class Program
    {
        private const String EXCHANGE_NAME = "/question/fetch";
        private const String QUEUE_NAME = "queue-fetch";
        static void Main(string[] args)
        {

            ConnectionFactory factory = new ConnectionFactory();
            factory.Uri = new Uri("amqp://root:654231@47.52.157.46:32825/FILES");
            IConnection connection = factory.CreateConnection();
            IModel channel = connection.CreateModel();
            channel.ExchangeDeclare(exchange: EXCHANGE_NAME, type: "direct", durable: false, autoDelete: false, arguments: null);
            Enumerable.Range(1, 100000).ToList().ForEach(id =>
            {
                String body = JsonConvert.SerializeObject(new { questionId = id });
                channel.BasicPublish(exchange: EXCHANGE_NAME, routingKey: QUEUE_NAME, body: Encoding.UTF8.GetBytes(body));
            });
        }
    }
}
