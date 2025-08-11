package top.figsq.poketip.poketip.impl

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.Species
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapperFactory


object SpeciesWrapperFactory : ISpeciesWrapperFactory<Species> {
    val cache = mutableMapOf<Species, SpeciesWrapper>()

    override fun create(name: String): ISpeciesWrapper<Species> {
        PokemonSpecies.getByName(name)?.let {
            return cache.getOrPut(it) {SpeciesWrapper(it)}
        }
        throw IllegalArgumentException("No such species: $name")
    }

    override fun create(original: Species): ISpeciesWrapper<Species> {
        return cache.getOrPut(original) {SpeciesWrapper(original)}
    }

    override fun create(dex: Int): ISpeciesWrapper<Species> {
        PokemonSpecies.getByPokedexNumber(dex)?.let {
            return cache.getOrPut(it) {SpeciesWrapper(it)}
        }
        throw IllegalArgumentException("No such species: $dex")
    }

    override fun getAll(): Collection<ISpeciesWrapper<*>?>? {
        if (PokemonSpecies.count() <= cache.size) return cache.values.toList()
        return PokemonSpecies.species.map {
            create(it)
        }.toList()
    }
}
