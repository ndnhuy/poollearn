package com.ndnhuy.poollearn;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Thread)
    public static class Plan {
        private double[] randomDoubles;

        @Setup(Level.Iteration)
        public void setup() {
            var n = 2_000_000;
            var gen = new RandomDataGenerator();
            randomDoubles = new double[n];
            for (int i = 0; i < n; i++) {
                randomDoubles[i] = gen.nextUniform(0.0, Double.MAX_VALUE);
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 3, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 3, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void init(Plan plan, Blackhole bh) {
        var cnt = 0;
        for (int i = 0; i < plan.randomDoubles.length; i++) {
            if (plan.randomDoubles[i] < 0.5) {
                cnt++;
            }
        }
        bh.consume(cnt);
    }
}
