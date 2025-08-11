package top.figsq.poketip.poketip.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import lombok.Getter;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;

/**
 * 实现类
 */
@Getter
public class SpeciesWrapper extends ISpeciesWrapper<EnumSpecies> {
    private final EnumSpecies original;

    public SpeciesWrapper(EnumSpecies original) {
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

    /**
     * 1.12.2没有这玩意
     */
    @Override
    public boolean isMythical() {
        return original.isLegendary();
    }

    @Override
    public int getGeneration() {
        return original.getGeneration();
    }

    @Override
    public Class<EnumSpecies> getType() {
        return EnumSpecies.class;
    }
}
