package top.figsq.poketip.poketip.api.pokemon;

/**
 * 感觉之后如果要用使用比较占内存
 * 暂时还没用到的
 */
public interface IPokemonWrapperFactory<T> {
    IPokemonWrapper<T> create(T original);
}
