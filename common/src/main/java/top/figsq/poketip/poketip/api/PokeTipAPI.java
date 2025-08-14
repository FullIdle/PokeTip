package top.figsq.poketip.poketip.api;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;
import top.figsq.poketip.poketip.api.pokemon.ISpeciesWrapperFactory;
import top.figsq.poketip.poketip.common.TipConfigManager;
import top.figsq.poketip.poketip.common.speciespool.TipConfigPool;

/**
 * 注意 方法注释
 * @see PokeTipAPI#onSpawn(Location, IPokemonWrapper)
 */
public interface PokeTipAPI extends IOnSpawn, IOnCapture {
    ISpeciesWrapperFactory<?> getSpeciesWrapperFactory();

    /**
     * 当宝可梦生成后委托转发给该方法
     */
    @Override
    default void onSpawn(Location location, IPokemonWrapper<?> pokemonWrapper) {
        /*==>委托给配置池去处理<==*/
        for (TipConfigPool pool : TipConfigManager.check(pokemonWrapper.getSpecies()))
            pool.onSpawn(location, pokemonWrapper);
    }

    @Override
    default void onCapture(Player player, IPokemonWrapper<?> pokemonWrapper) {
        /*==>委托给配置池去处理<==*/
        for (TipConfigPool pool : TipConfigManager.check(pokemonWrapper.getSpecies()))
            pool.onCapture(player, pokemonWrapper);
    }
}
