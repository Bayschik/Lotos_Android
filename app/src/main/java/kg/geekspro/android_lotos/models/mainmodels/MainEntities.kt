package kg.geekspro.android_lotos.models.mainmodels

data class MainEntities(
    val count:Int,
    val next:String?,
    val previous:String?,
    val results:List<ResultsModel>,
)
