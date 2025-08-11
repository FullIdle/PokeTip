package top.figsq.poketip.poketip.impl;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import lombok.Getter;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.PokeTip;

@Getter
public class PokemonWrapper extends IPokemonWrapper<Pokemon> {
    private final Pokemon original;

    public PokemonWrapper(Pokemon original){
        this.original = original;
    }

    @Override
    public ISpeciesWrapper<EnumSpecies> getSpecies() {
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
