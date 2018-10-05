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
            IConnection connection = factory.CreateConnection();
            IModel channel = connection.CreateModel();
            channel.BasicQos(0, 1, false);
            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (e, ea) =>
            {
                String json = Encoding.UTF8.GetString(ea.Body);
                QuestionJobResult result = JsonConvert.DeserializeObject<QuestionJobResult>(json);
                Console.WriteLine($"Question Id {result.QuestionId}");

                String fileName = result.QuestionId == null ? DateTime.Now.Ticks + ".json" : result.QuestionId + ".json";
                String filePath = Path.Combine(Environment.CurrentDirectory, "data", fileName);
                File.WriteAllText(filePath, json);
                channel.BasicAck(ea.DeliveryTag, false);
            };
            channel.BasicConsume(queue: "queue2", autoAck: false, consumer: consumer);
            Console.ReadKey();
        }
    }
}
