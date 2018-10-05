using System.Collections.Generic;

namespace dotnetcore.localfile.Models
{
    public class Question
    {
        public long? Id { get; set; }
        public string Name { get; set; }
        public string Link { get; set; }
        public string Asked { get; set; }
        public string Viewed { get; set; }
        public long UpVote { get; set; }
        public long Favorite { get; set; }
        public string PostContent { get; set; }
        public IEnumerable<string> Tags { get; set; }
    }
}