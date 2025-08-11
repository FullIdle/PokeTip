package top.figsq.poketip.poketip;

import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnActionPokemon;
import lombok.val;
import me.fullidle.ficore.ficore.common.api.event.ForgeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import top.figsq.poketip.poketip.api.IListener;
import top.figsq.poketip.poketip.api.PokeTipAPI;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;
import top.figsq.poketip.poketip.common.PokeTipPlugin;
import top.figsq.poketip.poketip.impl.PokemonWrapper;
import top.figsq.poketip.poketip.impl.SpeciesWrapperFactory;

public class PokeTip extends PokeTipPlugin implements PokeTipAPI, IListener, Listener {
    @Override
    public SpeciesWrapperFactory getSpeciesWrapperFactory() {
        return SpeciesWrapperFactory.INSTANCE;
    }

    @Override
    public PokeTipAPI getAPI() {
        return this;
    }

    @Override
    public IListener[] getListeners() {
        return new IListener[]{this};
    }

    @Override
    public void register() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    /**
     * 触发时间并调转发委托
     *
     * @see PokeTipAPI#onSpawn(Location, IPokemonWrapper)
     */
    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onEvent(ForgeEvent event) {
        if (event.getForgeEvent() instanceof SpawnEvent) {
            val e = (SpawnEvent) event.getForgeEvent();
            if (!(e.action instanceof SpawnActionPokemon)) return;
            val action = (SpawnActionPokemon) e.action;
            //这个事件触发前已经调用过create方法了，所以再次使用实际是上次的调用数据
            val ep = action.getOrCreateEntity();
            //很可惜位置是在事件后设置的所以得用定制任务调到下一个tick
            Bukkit.getScheduler().runTask(PokeTip.getInstance(), () -> {
                val world = Bukkit.getWorld(ep.level.dimension().location().getPath());
                val loc = new Location(world, ep.getX(), ep.getY(), ep.getZ());
                onSpawn(loc, new PokemonWrapper(ep.getPokemon()));
            });
        }
    }
}
