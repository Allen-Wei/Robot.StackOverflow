using System;

namespace MongoDBStore
{
    class Program
    {
        static void Main(string[] args)
        {
            // To directly connect to a single MongoDB server
            // (this will not auto-discover the primary even if it's a member of a replica set)

            // or use a connection string

            // or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
            var client = new MongoClient("mongodb://localhost:27017");
        }
    }
}
