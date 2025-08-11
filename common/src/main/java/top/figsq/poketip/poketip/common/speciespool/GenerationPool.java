package top.figsq.poketip.poketip.common.speciespool;

import lombok.Getter;
import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.common.PokeTipPlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Getter
public class GenerationPool implements SpeciesWrapperPool {
    private final int gen;

    public GenerationPool(int gen) {
        this.gen = gen;
    }

    @Override
    public Collection<ISpeciesWrapper<?>> values() {
        return PokeTipPlugin.getPokeAPI().getSpeciesWrapperFactory().getAll().stream()
                .filter(wrapper -> wrapper.getGeneration() == this.gen)
                .collect(Collectors.toList());
    }

    @Override
    public boolean contains(ISpeciesWrapper<?> wrapper) {
        return wrapper.getGeneration() == this.gen;
    }
}
