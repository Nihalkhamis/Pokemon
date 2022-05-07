package com.gradle.pokemon.di;


import android.app.Application;

import com.gradle.pokemon.network.PokemonApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(Application.class)     //to be at the whole application scope
public class RetrofitModule {     //to give dagger hilt an object from retrofit when needed cz the repo will need this instance from retrofit as dependency, we created module cz it's not editable class

    @Provides
    @Singleton
    public static PokemonApiService providePokemonApiService(){
        return new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(PokemonApiService.class);
    }
}
