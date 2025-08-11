package top.figsq.poketip.poketip.impl;

import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import lombok.Getter;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;

/**
 * 实现类
 */
@Getter
public class SpeciesWrapper extends ISpeciesWrapper<Species> {
    private final Species original;

    public SpeciesWrapper(Species original) {
        if (original == null) throw new IllegalArgumentException("EnumSpecies can't be null");
        this.original = original;
    }

    @Override
    public String getName() {
        return original.getLocalizedName();
    }

    @Override
    public boolean isLegend() {
        return original.isLegendary();
    }

    @Override
    public boolean isMythical() {
        return original.isMythical();
    }

    @Override
    public int getGeneration() {
        return original.getGeneration();
    }

    @Override
    public Class<Species> getType() {
        return Species.class;
    }
}
