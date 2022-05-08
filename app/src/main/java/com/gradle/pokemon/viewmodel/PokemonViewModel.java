package com.gradle.pokemon.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import com.gradle.pokemon.model.Pokemon;
import com.gradle.pokemon.model.PokemonResponse;
import com.gradle.pokemon.repository.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PokemonViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favPokemonList = null;

    public LiveData<List<Pokemon>> getFavPokemonList() {
        return favPokemonList;
    }

    @Inject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    @SuppressLint("CheckResult")
    public void getPokemons() {
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> pokemonsResult = pokemonResponse.getPokemonsResult();
                        for (Pokemon pokemon : pokemonsResult) {
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonIndex[pokemonIndex.length - 1] + ".png");
                        }
                        Log.d("TAG", "apply: VIEW MODEL name: " + pokemonsResult.get(0).getName());
                        Log.d("TAG", "apply: VIEW MODEL image: " + pokemonsResult.get(0).getUrl());
                        return pokemonsResult;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemonList.setValue(result),
                        error -> Log.d("TAG", "getPokemons: " + error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getFavPokemons() {
        favPokemonList = repository.getFavPokemons();
    }



}
