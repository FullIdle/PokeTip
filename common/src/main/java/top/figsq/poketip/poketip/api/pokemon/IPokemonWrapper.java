package top.figsq.poketip.poketip.api.pokemon;

import top.figsq.poketip.poketip.api.Wrapper;

public abstract class IPokemonWrapper<T> extends Wrapper<T> {
    public abstract ISpeciesWrapper<?> getSpecies();
    public abstract String getName();
}
