package top.figsq.poketip.poketip.common.speciespool;

import lombok.Getter;
import top.figsq.poketip.poketip.api.IPool;
import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.common.PokeTipPlugin;

import java.util.Collection;
import java.util.Collections;

@Getter
public class SimplePool implements SpeciesWrapperPool {
    private final ISpeciesWrapper<?> species;

    public SimplePool(ISpeciesWrapper<?> species) {
        this.species = species;
    }

    @Override
    public Collection<ISpeciesWrapper<?>> values() {
        return Collections.singleton(species);
    }

    @Override
    public boolean contains(ISpeciesWrapper<?> wrapper) {
        return this.species.equals(wrapper);
    }
}
