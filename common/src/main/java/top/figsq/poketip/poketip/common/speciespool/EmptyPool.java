package top.figsq.poketip.poketip.common.speciespool;

import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;

import java.util.Collection;
import java.util.Collections;

public class EmptyPool implements SpeciesWrapperPool {
    public static final EmptyPool INSTANCE = new EmptyPool();
    @Override
    public Collection<ISpeciesWrapper<?>> values() {
        return Collections.emptyList();
    }

    @Override
    public boolean contains(ISpeciesWrapper<?> wrapper) {
        return false;
    }
}
