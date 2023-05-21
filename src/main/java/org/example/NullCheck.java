package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class NullCheck {

    private TestClass testClass;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(NullCheck.class.getSimpleName())
            .forks(1)
            .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        testClass = new TestClass(new Foo(new Bar(new Bim(10))));
    }

    @Benchmark
    public void usingOptional() {
        ofNullable(testClass)
            .map(TestClass::getFoo)
            .map(Foo::getBar)
            .map(Bar::getBim)
            .map(Bim::getV)
            .orElse(0L);
    }

    @Benchmark
    public void usingIfNull() {
        long v = 0;
        if (
            testClass != null
            && testClass.getFoo() != null
            && testClass.getFoo().getBar() != null
            && testClass.getFoo().getBar().getBim() != null
        ) {
            v = testClass.getFoo().getBar().getBim().getV();
        }
    }

}
