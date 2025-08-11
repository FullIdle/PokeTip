package top.figsq.poketip.poketip.impl;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import lombok.Getter;
import top.figsq.poketip.poketip.PokeTip;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;

@Getter
public class PokemonWrapper extends IPokemonWrapper<Pokemon> {
    private final Pokemon original;

    public PokemonWrapper(Pokemon original){
        this.original = original;
    }

    @Override
    public ISpeciesWrapper<Species> getSpecies() {
        return ((PokeTip) PokeTip.getInstance()).getSpeciesWrapperFactory().create(original.getSpecies());
    }

    @Override
    public String getName() {
        return this.original.getLocalizedName();
    }

    @Override
    public Class<Pokemon> getType() {
        return Pokemon.class;
    }
}
