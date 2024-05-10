package kg.geekspro.android_lotos.models.aboutusmodel.youtubemodel

data class YouTubeVideo(
    val id: Int,
    val title: String,
    val url: String,
    val created_at: Long,
)

/*
check
 */
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