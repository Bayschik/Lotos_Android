package kg.geekspro.android_lotos.models.aboutusmodels.contactsmodels

data class WhatsAppModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ResultModel>
)

data class ResultModel(
    val id: Int,
    val title: String,
    val url: String
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
      "url": "string"
    }
  ]
}
 */
