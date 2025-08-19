package org.example.BloomFilter;

import java.util.function.Function;



public class Main {
    public static void main(String[] args){
        int size = 100;
        Function<String, Integer>[] hashFunctions = new Function[3];
        hashFunctions[0] = (s) ->  s.hashCode();
        hashFunctions[1] = (s) ->  s.length();
        hashFunctions[2] = (s) -> (int) s.charAt(0);
        BloomFilter<String> bloomFilter = new BloomFilter<String>(size, hashFunctions );
        bloomFilter.add("1234");
        bloomFilter.add("142");
        bloomFilter.add("8343");
        System.out.println(bloomFilter.contains("1234"));
        System.out.println(bloomFilter.contains("142"));
        System.out.println(bloomFilter.contains("8343"));
        System.out.println(bloomFilter.contains("34ewq3re"));
    }
}
