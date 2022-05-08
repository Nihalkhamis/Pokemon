package com.gradle.pokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gradle.pokemon.R;
import com.gradle.pokemon.model.Pokemon;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>{

    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private Context context;

    public PokemonAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
        notifyDataSetChanged();
    }

    public Pokemon getPokemonAt(int position){
        return pokemons.get(position);
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.pokemonName_txt.setText(pokemons.get(position).getName());

        Glide.with(context).load(pokemons.get(position).getUrl())
                .into(holder.pokemon_img);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{

        private ImageView pokemon_img;
        private TextView pokemonName_txt;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_img = itemView.findViewById(R.id.pokemon_img);
            pokemonName_txt = itemView.findViewById(R.id.pokemonName_txt);
        }
    }
}
