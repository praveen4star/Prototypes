package org.example.BloomFilter;

import java.util.BitSet;
import java.util.function.Function;

public class BloomFilter<T>{
    private final BitSet bitSet;
    private final Function<T, Integer>[] hashFunctions;

    public BloomFilter(int bitSetSize, Function<T, Integer>[] hashFunctions) {
        this.bitSet = new BitSet(bitSetSize);
        this.hashFunctions = hashFunctions;
    }
    public void add(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = hashFunction.apply(element);
            int index = hash % bitSet.size();
            bitSet.set(index, true);
        }
    }
    public boolean contains(T element) {
        for (Function<T, Integer> hashFunction : hashFunctions) {
            int hash = hashFunction.apply(element);
            int index = hash % bitSet.size();
            if (!bitSet.get(index)) {
                 return  false;
            }
        }
        return true;
    }
}
