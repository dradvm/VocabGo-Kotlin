package com.example.vocabgo.module

import com.example.vocabgo.data.service.AuthService
import com.example.vocabgo.data.service.GameService
import com.example.vocabgo.data.service.ProgressService
import com.example.vocabgo.data.service.UserService
import com.example.vocabgo.data.service.VocabularyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideVocalaryService(retrofit: Retrofit) : VocabularyService {
        return retrofit.create(VocabularyService::class.java)
    }
    @Provides
    @Singleton
    fun provideGameService(retrofit: Retrofit) : GameService {
        return retrofit.create(GameService::class.java)
    }
    @Provides
    @Singleton
    fun provideProgressService(retrofit: Retrofit) : ProgressService {
        return retrofit.create(ProgressService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit) : UserService {
        return retrofit.create(UserService::class.java)
    }
}