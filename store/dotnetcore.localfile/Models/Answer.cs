namespace dotnetcore.localfile.Models
{
    public class Answer
    {
        public long Id { get; set; }
        public long QuestionId { get; set; }
        public long UpVote { get; set; }
        public string BountyAward { get; set; }
        public string PostContent { get; set; }
        public string AnswerTime { get; set; }
        public string UserLink { get; set; }
        public string UserAvatar { get; set; }
    }
}