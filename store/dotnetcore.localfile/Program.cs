using System;
using System.IO;
using Newtonsoft.Json;
using System.Text;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using dotnetcore.localfile.Models;

namespace dotnetcore.localfile
{
    class Program
    {
        static void Main(string[] args)
        {
            ConnectionFactory factory = new ConnectionFactory();
            factory.Uri = new Uri("amqp://root:654231@47.52.157.46:32825/FILES");
            IConnection connection = factory.CreateConnection("dotnetcore.localfile");
            IModel channel = connection.CreateModel();
            var consumer = new EventingBasicConsumer(channel);
            String queueName = channel.QueueDeclare();
            channel.QueueBind(queue: queueName, exchange: "/question/publish", routingKey: "", arguments: null);
            consumer.Received += (e, ea) =>
            {
                String json = Encoding.UTF8.GetString(ea.Body);
                QuestionJobResult result = JsonConvert.DeserializeObject<QuestionJobResult>(json);
                Console.WriteLine($"Question Id {result.QuestionId}");

                String fileName = result.QuestionId == null ? DateTime.Now.Ticks + ".json" : result.QuestionId + ".json";
                String filePath = Path.Combine(Environment.CurrentDirectory, "bin", fileName);
                File.WriteAllText(filePath, json);
            };
            channel.BasicConsume(queue: queueName, autoAck: true, consumer: consumer);
            // Console.ReadKey();
        }
    }
}
