package com.gradle.pokemon.di;

import android.app.Application;

import com.gradle.pokemon.db.PokemonDB;
import com.gradle.pokemon.db.PokemonDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {


    //ana 3araft dagger hilt hatgyb el db el hya 3aizaha f el method el t7t mnin 3an tari2 el method dy bs mch aktar
    @Provides
    @Singleton
    public static PokemonDB provideDB(Application application) {
        return Room.databaseBuilder(application, PokemonDB.class, "fav_DB")
                .fallbackToDestructiveMigration() //allows room to destructively recreate db tables if migrations are not found when migrating the old schemas to the latest ones, if failed will throw an illegalExceptio
                .allowMainThreadQueries()   //we could use rxJava to run this in background
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDao provideDao(PokemonDB pokemonDB){
        return pokemonDB.pokemonDao();
    }
}
