package com.gradle.pokemon.db;

import com.gradle.pokemon.model.Pokemon;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PokemonDao {

    @Insert
    public void insertPokemon(Pokemon pokemon);

    @Query("delete from favourites where name =:pokemonName")
    public void deletePokemon(String pokemonName);

    @Query("select * from favourites")
    public LiveData<List<Pokemon>> getStoredPokemons();
}
