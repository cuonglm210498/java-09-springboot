package com.lecuong.java09springboot.mapper;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Mapper {
    default <T, R> List<R> toList(List<T> list, Function<T, R> map) {
        return list
                .stream()
                .map(map)
                .collect(Collectors.toList());
    }

    default <T, R> Set<R> toSet(List<T> list, Function<T, R> map) {
        return list
                .stream()
                .map(map)
                .collect(Collectors.toSet());
    }
}
