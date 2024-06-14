package kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel

data class YouTubeModel(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result1>
)


/*
{
 "count": 123,
 "next": "http://api.example.org/accounts/?page=4",
 "previous": "http://api.example.org/accounts/?page=2",
 "results": [
   {
     "id": 0,
     "title": "string",
     "url": "string",
     "created_at": "2024-04-24T09:59:13.418Z"
   }
 ]
}
 */