package com.example.mongodbrealmbasics.di

import com.example.mongodbrealmbasics.Model.Address
import com.example.mongodbrealmbasics.Model.Person
import com.example.mongodbrealmbasics.Model.Pet
import com.example.mongodbrealmbasics.data.MongoRepository
import com.example.mongodbrealmbasics.data.MongoRepositoryImp
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@dagger.Module
object Module {

    @Singleton
    @Provides
    fun provideRealm() : Realm{
        val config=RealmConfiguration.Builder(
            schema = setOf(
                Person::class,Address::class, Pet::class
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }

    @Singleton
    @Provides
    fun provideMongoRepository(realm:Realm) : MongoRepository {
        return MongoRepositoryImp(realm=realm)
    }
}