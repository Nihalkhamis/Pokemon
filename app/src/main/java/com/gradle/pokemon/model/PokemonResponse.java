package com.gradle.pokemon.model;

import java.util.ArrayList;

public class PokemonResponse {
    private int count;
    private String next;
    private String previous;
    private ArrayList<Pokemon> pokemonsResult;

    public PokemonResponse(int count, String next, String previous, ArrayList<Pokemon> pokemonsResult) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.pokemonsResult = pokemonsResult;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<Pokemon> getPokemonsResult() {
        return pokemonsResult;
    }

    public void setPokemonsResult(ArrayList<Pokemon> pokemonsResult) {
        this.pokemonsResult = pokemonsResult;
    }
}
