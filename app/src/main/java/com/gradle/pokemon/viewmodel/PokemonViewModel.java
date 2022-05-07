package com.gradle.pokemon.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import com.gradle.pokemon.model.Pokemon;
import com.gradle.pokemon.model.PokemonResponse;
import com.gradle.pokemon.repository.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PokemonViewModel extends ViewModel {
    private Repository repository;

    MutableLiveData<ArrayList<Pokemon>> pokemonList = new MutableLiveData<>();

    @Inject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonList() {
        return pokemonList;
    }

    @SuppressLint("CheckResult")
    public void getPokemons(){
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> pokemonsResult = pokemonResponse.getPokemonsResult();
                        for (Pokemon pokemon : pokemonsResult){
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl(" https://pokeres.bastionbot.org/images/pokemon"+pokemonIndex[pokemonIndex.length-1]+".png");
                        }
                        return pokemonsResult;                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->pokemonList.setValue(result),
                        error-> Log.d("TAG", "getPokemons: "+error.getMessage()));
    }
}
