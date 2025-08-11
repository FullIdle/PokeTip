package top.figsq.poketip.poketip.impl

import com.cobblemon.mod.common.api.pokemon.labels.CobblemonPokemonLabels
import com.cobblemon.mod.common.pokemon.Species
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper

class SpeciesWrapper(
    private val species: Species
) : ISpeciesWrapper<Species>() {
    override fun getName(): String {
        return this.species.translatedName.string
    }

    override fun isLegend(): Boolean {
        return this.species.labels.contains(CobblemonPokemonLabels.LEGENDARY)
    }

    override fun isMythical(): Boolean {
        return this.species.labels.contains(CobblemonPokemonLabels.MYTHICAL)
    }

    override fun getGeneration(): Int {
        return this.species.labels.firstOrNull {
            it.startsWith("GENERATION_")
        }
            ?.substring(11)
            ?.replace("[^0-9]", "")
            ?.toInt()
            ?: -1
    }

    override fun getOriginal(): Species {
        return this.species
    }

    override fun getType(): Class<Species> {
        return Species::class.java
    }

}
