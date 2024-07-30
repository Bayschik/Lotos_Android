package kg.geekspro.android_lotos.models.mainmodels

data class ActionsModel(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: ArrayList<Result>
) {
    data class Result(
        val banner: String,
        val id: Int,
        val title: String
    )
}