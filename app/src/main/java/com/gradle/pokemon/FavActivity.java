package com.gradle.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import java.util.List;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private RecyclerView favRV;
    private PokemonAdapter pokemonAdapter;

    private Button to_home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        favRV = findViewById(R.id.favRV);
        pokemonAdapter = new PokemonAdapter(this);

        to_home_btn = findViewById(R.id.to_home_btn);

        favRV.setAdapter(pokemonAdapter);

        setUpSwipe();

        to_home_btn.setOnClickListener(view -> {
            startActivity(new Intent(FavActivity.this,MainActivity.class));
        });

        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        pokemonViewModel.getFavPokemons();

        pokemonViewModel.getFavPokemonList().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                ArrayList<Pokemon> list = new ArrayList<>();
                list.addAll(pokemons);
                pokemonAdapter.setList(list);
            }
        });

    }

    private void setUpSwipe(){
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {   //helps me to move something in recyclerView, takes 2 params the first one about the movement of the component up or down and the second one about the movement to right or left
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition(); //position of swiped item in recyclerView
                Pokemon swipedPokemon = pokemonAdapter.getPokemonAt(swipedPokemonPosition);
                pokemonViewModel.deletePokemon(swipedPokemon.getName());
                pokemonAdapter.notifyDataSetChanged();
                Log.d("TAG", "onSwiped: before toast");
                Toast.makeText(FavActivity.this, "pokemon deleted from fav", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onSwiped: after toast");

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(favRV);
    }
}