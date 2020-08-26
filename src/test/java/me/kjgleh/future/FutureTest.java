package me.kjgleh.future;

import org.junit.jupiter.api.Test;

import java.util.List;

public class FutureTest {

    @Test
    public void call_blocking() {
        PriceFinder priceFinder = new PriceFinder();
        long start = System.nanoTime();
        List<String> prices = priceFinder.findPrices("Apple");
        for (String str : prices) {
            System.out.println(str);
        }
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("완료 시간:" + duration + " msecs");
    }
}
