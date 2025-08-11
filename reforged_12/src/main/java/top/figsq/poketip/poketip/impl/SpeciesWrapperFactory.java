package top.figsq.poketip.poketip.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import lombok.val;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapperFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SpeciesWrapperFactory implements ISpeciesWrapperFactory<EnumSpecies> {
    public static final SpeciesWrapperFactory INSTANCE = new SpeciesWrapperFactory();
    private static final EnumMap<EnumSpecies,SpeciesWrapper> spMap = new EnumMap<>(EnumSpecies.class);

    @Override
    public ISpeciesWrapper<EnumSpecies> create(String name) throws IllegalArgumentException {
        return spMap.computeIfAbsent(EnumSpecies.getFromNameAnyCase(name), SpeciesWrapper::new);
    }

    @Override
    public ISpeciesWrapper<EnumSpecies> create(EnumSpecies original) {
        return spMap.computeIfAbsent(original, SpeciesWrapper::new);
    }

    @Override
    public ISpeciesWrapper<EnumSpecies> create(int dex) throws IllegalArgumentException {
        return spMap.computeIfAbsent(EnumSpecies.getFromDex(dex), SpeciesWrapper::new);
    }

    /**
     * 这类最终是arraylist
     */
    @Override
    public Collection<ISpeciesWrapper<?>> getAll() {
        val values = EnumSpecies.values();
        if (spMap.size() >= values.length) return new ArrayList<>(spMap.values());
        return Arrays.stream(values).map(this::create).collect(Collectors.toCollection(ArrayList::new));
    }
}
