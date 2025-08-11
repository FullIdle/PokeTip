package top.figsq.poketip.poketip.impl;

import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import lombok.val;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapperFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SpeciesWrapperFactory implements ISpeciesWrapperFactory<Species> {
    public static final SpeciesWrapperFactory INSTANCE = new SpeciesWrapperFactory();
    private static final Map<Species,SpeciesWrapper> spMap = new HashMap<>();
    /*我遇见的开1.16.5的人都挺有钱，相比机器的不会乐色，所以直接缓存所有物种名获取到map，其实消耗没多大~*/
    private static final Map<String,Species> searchCache = new HashMap<>();
    static {
        for (Species species : PixelmonSpecies.getAll()) {
            searchCache.put(species.getName().toLowerCase(),species);
            searchCache.put(species.getLocalizedName().toLowerCase(), species);
        }
    }

    @Override
    public ISpeciesWrapper<Species> create(String name) throws IllegalArgumentException {
        val value = PixelmonSpecies.fromName(name).getValueUnsafe();
        return spMap.computeIfAbsent(value == null ? search(name) : value, SpeciesWrapper::new);
    }

    private Species search(String name){
        return searchCache.get(name.toLowerCase());
    }

    @Override
    public ISpeciesWrapper<Species> create(Species original) {
        return spMap.computeIfAbsent(original, SpeciesWrapper::new);
    }

    @Override
    public ISpeciesWrapper<Species> create(int dex) throws IllegalArgumentException {
        val species = PixelmonSpecies.fromDex(dex);
        if (!species.isPresent()) throw new IllegalArgumentException("No such species with dex " + dex);
        return spMap.computeIfAbsent(species.get(), SpeciesWrapper::new);
    }

    /**
     * 这类最终是arraylist
     */
    @Override
    public Collection<ISpeciesWrapper<?>> getAll() {
        val values = PixelmonSpecies.getAll();
        if (spMap.size() >= values.size()) return new ArrayList<>(spMap.values());
        return values.stream().map(this::create).collect(Collectors.toCollection(ArrayList::new));
    }
}
