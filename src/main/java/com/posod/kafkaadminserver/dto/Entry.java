package com.posod.kafkaadminserver.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Entry<K, V> {

    K key;
    V value;

    public static <K, V> Entry<K, V> of(K left, V right) {
        return new Entry<>(left, right);
    }
}
