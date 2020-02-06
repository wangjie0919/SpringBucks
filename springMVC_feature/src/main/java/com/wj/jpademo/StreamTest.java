package com.wj.jpademo;

import java.util.Arrays;

public class StreamTest {
    public static void main(String[] args) {
        Arrays.asList("Foo","Bar").stream()
                .filter(e->e.equalsIgnoreCase("foo"))
                .map(e->e.toUpperCase())
                .forEach(System.out::println);

        Arrays.stream(new String[]{"s1","s2","s3"})
                .map(s -> Arrays.asList(s))
                .flatMap(l ->l.stream())
                .forEach(System.out::println);
    }
}
