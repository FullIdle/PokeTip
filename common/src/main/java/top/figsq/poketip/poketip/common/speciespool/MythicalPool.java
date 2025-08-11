package top.figsq.poketip.poketip.common.speciespool;

import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.common.PokeTipPlugin;

import java.util.Collection;
import java.util.stream.Collectors;

public class MythicalPool implements SpeciesWrapperPool {
    public static final MythicalPool INSTANCE = new MythicalPool();

    @Override
    public Collection<ISpeciesWrapper<?>> values() {
        return PokeTipPlugin.getPokeAPI().getSpeciesWrapperFactory().getAll().stream()
                .filter(ISpeciesWrapper::isMythical).collect(Collectors.toList());
    }

    @Override
    public boolean contains(ISpeciesWrapper<?> wrapper) {
        return wrapper.isMythical();
    }
}
