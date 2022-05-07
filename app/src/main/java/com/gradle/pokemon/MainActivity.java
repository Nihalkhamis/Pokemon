package com.gradle.pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.os.Bundle;

import com.gradle.pokemon.adapter.PokemonAdapter;
import com.gradle.pokemon.model.Pokemon;
import com.gradle.pokemon.viewmodel.PokemonViewModel;

import java.util.ArrayList;

@AndroidEntryPoint //3ashan hilt t3rf en el application haybd2 mn hina
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private RecyclerView pokemonRV;
    private PokemonAdapter pokemonAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonRV = findViewById(R.id.pokemonRV);
        pokemonAdapter = new PokemonAdapter(this);

        pokemonRV.setAdapter(pokemonAdapter);

//        linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//
//        pokemonRV.setLayoutManager(linearLayoutManager);

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        pokemonViewModel.getPokemons();

        pokemonViewModel.getPokemonList().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                pokemonAdapter.setList(pokemons);
            }
        });
    }
}