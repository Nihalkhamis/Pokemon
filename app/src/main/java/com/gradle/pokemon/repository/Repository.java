package com.gradle.pokemon.repository;

import com.gradle.pokemon.db.PokemonDao;
import com.gradle.pokemon.model.Pokemon;
import com.gradle.pokemon.model.PokemonResponse;
import com.gradle.pokemon.network.PokemonApiService;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import io.reactivex.rxjava3.core.Observable;


public class Repository {
    private PokemonApiService pokemonApiService;   //pokemonApiService will be provided by dagger hilt via RetrofitModule
    private PokemonDao pokemonDao;

    @Inject
    public Repository(PokemonApiService pokemonApiService, PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons(){
        return pokemonApiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon){
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName){
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getFavPokemons(){
       return pokemonDao.getStoredPokemons();
    }
}
