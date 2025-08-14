package top.figsq.poketip.poketip.api;

import org.bukkit.entity.Player;
import top.figsq.poketip.poketip.api.pokemon.IPokemonWrapper;

public interface IOnCapture {
    void onCapture(Player player, IPokemonWrapper<?> pokemonWrapper);
}
