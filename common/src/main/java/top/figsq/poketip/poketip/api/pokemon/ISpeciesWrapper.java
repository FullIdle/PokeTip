package top.figsq.poketip.poketip.api.pokemon;

import top.figsq.poketip.poketip.api.Wrapper;

public abstract class ISpeciesWrapper<T> extends Wrapper<T> {
    public abstract String getName();
    public abstract boolean isLegend();
    public abstract boolean isMythical();
    public abstract int getGeneration();
}
