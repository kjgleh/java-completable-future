package me.kjgleh.future;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PriceFinder {

    private final List<Shop> shops = Arrays.asList(
            new Shop("CoolPang"),
            new Shop("HMarket"),
            new Shop("12th street"),
            new Shop("YouMakePrice"),
            new Shop("FBay")
    );

    public List<String> findPrices(String product) {
        return shops.stream()
                .map(
                        shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product))
        ).collect(Collectors.toList());
    }
}
