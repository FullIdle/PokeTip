package top.figsq.poketip.poketip.api;

import org.bukkit.Location;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;

/**
 * 宝可梦生成后的委托接口
 */
public interface IOnSpawn {
    void onSpawn(Location location, IPokemonWrapper<?> pokemonWrapper);
}
