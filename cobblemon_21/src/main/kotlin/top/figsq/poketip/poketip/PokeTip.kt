package top.figsq.poketip.poketip

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.entity.SpawnEvent
import com.cobblemon.mod.common.api.reactive.ObservableSubscription
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.World
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftEntity
import org.bukkit.event.Listener
import top.figsq.poketip.poketip.api.IListener
import top.figsq.poketip.poketip.api.PokeTipAPI
import top.figsq.poketip.poketip.common.PokeTipPlugin
import top.figsq.poketip.poketip.impl.PokemonWrapper
import top.figsq.poketip.poketip.impl.SpeciesWrapperFactory

class PokeTip : PokeTipPlugin(), PokeTipAPI, IListener, Listener {
    companion object {
        lateinit var INSTANCE: PokeTip;
    }

    init {
        INSTANCE = this
    }

    lateinit var subscription: ObservableSubscription<*>

    override fun getSpeciesWrapperFactory(): SpeciesWrapperFactory {
        return SpeciesWrapperFactory
    }

    override fun getAPI(): PokeTipAPI {
        return this
    }

    override fun getListeners(): Array<IListener> {
        return arrayOf(this)
    }

    override fun register() {
        subscription = CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(priority = Priority.NORMAL, ::onEvent)
    }

    fun onEvent(event: SpawnEvent<PokemonEntity>) {
        event.entity.pokemon.species
        onSpawn(
            event.ctx.position.let {
                event.ctx.world
                Location(
                    (event.ctx.world as World).world,
                    it.x.toDouble(),
                    it.y.toDouble(),
                    it.z.toDouble()
                )
            },
            PokemonWrapper(event.entity.pokemon)
        )
    }

    override fun onDisable() {
        super.onDisable()
        subscription.unsubscribe()
    }
}
