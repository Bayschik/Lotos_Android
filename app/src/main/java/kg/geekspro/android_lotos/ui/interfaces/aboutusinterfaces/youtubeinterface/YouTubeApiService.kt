package kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.youtubeinterface

import kg.geekspro.android_lotos.models.aboutusmodels.youtubemodel.YouTubeModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("api/v1/my_videos/")
    suspend fun getYouTube(
        @Query("page") page: Int = 1
    ): Call<YouTubeModel>
}

//    interface YouTubeService {
//        @GET("videos")
//        suspend fun getYouTubeVideos(@Query("page") page: Int): Response<YouTubeModel> //
//    }


//    @GET
//    fun getNextPage(
//        @Url url: String
//    ): Response<YouTubeModel>
//
//    @GET
//    fun getPreviousPage(
//        @Url url: String
//    ): Response<YouTubeVideo>
//
//    @GET("api/v1/videos/{videoId}")
//    fun getVideoById(
//        @Path("videoId") videoId: String
//    ): Call<YouTubeVideo>
//
//    @GET("api/v1/videos/{videoId}")
//    fun getVideoById(
//        @Path("videoId") videoId: Int
//    ): Response<YouTubeVideo.Result1>
