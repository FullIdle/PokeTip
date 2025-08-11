package top.figsq.poketip.poketip.api;

import java.util.Collection;

public interface IPool<T> {
    Collection<T> values();
    boolean contains(T t);
}
