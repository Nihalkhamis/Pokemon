package com.gradle.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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

    private Button to_fav_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonRV = findViewById(R.id.pokemonRV);
        pokemonAdapter = new PokemonAdapter(this);

        to_fav_btn = findViewById(R.id.to_fav_btn);

        pokemonRV.setAdapter(pokemonAdapter);

        setUpSwipe();

        to_fav_btn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, FavActivity.class));
        });

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

    private void setUpSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {   //helps me to move something in recyclerView, takes 2 params the first one about the movement of the component up or down and the second one about the movement to right or left
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition(); //position of swiped item in recyclerView
                Pokemon swipedPokemon = pokemonAdapter.getPokemonAt(swipedPokemonPosition);
                pokemonViewModel.insertPokemon(swipedPokemon);
                pokemonAdapter.notifyDataSetChanged();
                Log.d("TAG", "onSwiped: before toast in insertion");
                Toast.makeText(MainActivity.this, "pokemon added to fav", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onSwiped: after toast in insertion");

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(pokemonRV);
    }
}