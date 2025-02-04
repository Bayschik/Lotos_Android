package kg.geekspro.android_lotos.ui.adapters.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.contactsinterface.WhatsAppInterface
import kg.geekspro.android_lotos.ui.interfaces.aboutusinterfaces.youtubeinterface.YouTubeApiService
import kg.geekspro.android_lotos.ui.interfaces.fcmtoken.FcmApiService
import kg.geekspro.android_lotos.ui.interfaces.maininterfeces.MainApiService
import kg.geekspro.android_lotos.ui.interfaces.profileinterfaces.ApiService
import kg.geekspro.android_lotos.ui.prefs.prefsprofile.Pref
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://lotos.pp.ua/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor:HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor():HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideFcmApiService(retrofit: Retrofit): FcmApiService {
        return retrofit.create(FcmApiService::class.java)
    }

    @Provides
    fun provideMainApiService(retrofit: Retrofit): MainApiService {
        return retrofit.create(MainApiService::class.java)
    }

    @Provides
    fun provideYoutubeApiService(retrofit: Retrofit):YouTubeApiService {
        return retrofit.create(YouTubeApiService::class.java)
    }


    @Provides
    fun provideWhatsAppApiService(retrofit: Retrofit): WhatsAppInterface {
        return retrofit.create(WhatsAppInterface::class.java)
    }


    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Pref.PREF_NAME, Context.MODE_PRIVATE)
    }

}