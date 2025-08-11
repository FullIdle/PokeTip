package top.figsq.poketip.poketip.impl

import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.Species
import lombok.Getter
import top.figsq.poketip.poketip.PokeTip
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper

class PokemonWrapper(private val original: Pokemon) : IPokemonWrapper<Pokemon>() {
    override fun getSpecies(): ISpeciesWrapper<Species> {
        return PokeTip.INSTANCE.speciesWrapperFactory.create(original.species)
    }

    override fun getName(): String {
        return this.original.species.translatedName.string
    }

    override fun getOriginal(): Pokemon {
        return this.original
    }

    override fun getType(): Class<Pokemon> {
        return Pokemon::class.java
    }
}
