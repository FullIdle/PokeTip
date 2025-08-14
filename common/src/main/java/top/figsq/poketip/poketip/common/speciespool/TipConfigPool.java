package top.figsq.poketip.poketip.common.speciespool;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import top.figsq.poketip.poketip.api.IOnCapture;
import top.figsq.poketip.poketip.api.IOnSpawn;
import top.figsq.poketip.poketip.api.SpeciesWrapperPool;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapper;

import java.util.Collection;

public class TipConfigPool implements SpeciesWrapperPool,
        IOnSpawn,
        IOnCapture {
    public final SpeciesWrapperPool include;
    public final SpeciesWrapperPool exclude;
    public final String spawnTip;
    public final String captureTip;

    public TipConfigPool(SpeciesWrapperPool include, SpeciesWrapperPool exclude, String spawnTip, String captureTip) {
        this.include = include;
        this.exclude = exclude;
        this.spawnTip = spawnTip;
        this.captureTip = captureTip;
    }

    @Override
    public Collection<ISpeciesWrapper<?>> values() {
        return removeExclude(include.values());
    }

    public Collection<ISpeciesWrapper<?>> removeExclude(Collection<ISpeciesWrapper<?>> collection) {
        collection.removeAll(exclude.values());
        return collection;
    }

    @Override
    public boolean contains(ISpeciesWrapper<?> wrapper) {
        return !exclude.contains(wrapper) && include.contains(wrapper);
    }

    /**
     * 更具配置进行委托提示
     * 这里不会进行判断是否是这个池子内的，因为这是委托过来的
     */
    @Override
    public void onSpawn(Location location, IPokemonWrapper<?> wrapper) {
        if (this.spawnTip == null) return;
        Bukkit.broadcastMessage(this.spawnTip
                .replace("{pokemon_name}", wrapper.getName())
                .replace("{loc_world}", location.getWorld().getName())
                .replace("{loc_x}", location.getBlockX() + "")
                .replace("{loc_y}", location.getBlockY() + "")
                .replace("{loc_z}", location.getBlockZ() + "")
        );
    }

    @Override
    public void onCapture(Player player, IPokemonWrapper<?> wrapper) {
        if (this.captureTip == null) return;
        Bukkit.broadcastMessage(
                PlaceholderAPI.setPlaceholders(
                        player,
                        this.spawnTip.replace("{pokemon_name}", wrapper.getName())
                )
        );
    }
}
