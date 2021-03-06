package com.gradle.pokemon.db;


import com.gradle.pokemon.model.Pokemon;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Pokemon.class, version = 1, exportSchema = false)
public abstract class PokemonDB extends RoomDatabase {

    public abstract PokemonDao pokemonDao();
}
