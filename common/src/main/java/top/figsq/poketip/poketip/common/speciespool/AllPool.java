package top.figsq.poketip.poketip.common.speciespool;

import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.common.PokeTipPlugin;

import java.util.Collection;

public class AllPool implements SpeciesWrapperPool {
    public static final AllPool INSTANCE = new AllPool();
    @Override
    public Collection<ISpeciesWrapper<?>> values() {
        return PokeTipPlugin.getPokeAPI().getSpeciesWrapperFactory().getAll();
    }

    @Override
    public boolean contains(ISpeciesWrapper<?> wrapper) {
        return values().contains(wrapper);
    }
}
