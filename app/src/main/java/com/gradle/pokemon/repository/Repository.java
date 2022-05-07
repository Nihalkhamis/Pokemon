package com.gradle.pokemon.repository;

import com.gradle.pokemon.model.PokemonResponse;
import com.gradle.pokemon.network.PokemonApiService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repository {
    private PokemonApiService pokemonApiService;   //pokemonApiService will be provided by dagger hilt via RetrofitModule

    @Inject
    public Repository(PokemonApiService pokemonApiService) {
        this.pokemonApiService = pokemonApiService;
    }

    public Observable<PokemonResponse> getPokemons(){
        return pokemonApiService.getPokemons();
    }
}
