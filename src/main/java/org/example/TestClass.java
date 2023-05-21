package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class Foo {
    private Bar bar;
}

@Getter
@AllArgsConstructor
class Bar {
    private Bim bim;
}

@Getter
@AllArgsConstructor
class Bim {
    private long v = 0;
}

@Getter
@AllArgsConstructor
public class TestClass {
    private Foo foo;
}
