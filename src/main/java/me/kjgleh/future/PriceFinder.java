package me.kjgleh.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PriceFinder {

    private final List<Shop> shops = Arrays.asList(
            new Shop("CoolPang"),
            new Shop("HMarket"),
            new Shop("12th street"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("YouMakePrice"),
            new Shop("FBay")
    );

    public List<String> findPrices(String product) {
        return shops.stream()
                .map(
                        shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithParallel(String product) {
        return shops.parallelStream()
                .map(
                        shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(
                        shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<String> findPricesWithCompletableFutureAndExecutor(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(
                        shop -> CompletableFuture.supplyAsync(
                                () -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)),
                                executor
                        )
                )
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private final Executor executor = Executors.newFixedThreadPool(
            Math.min(shops.size(), 100),
            r -> {
                Thread t = new Thread(r);
                /**
                 * 자바에서 일반 스레드가 실행중이면 자바 프로그램은 종료되지 않습니다.
                 * 일반 스레드가 한 없이 기다리게 되어 종료되지 않는 일이 벌어지면 문제가 생길 수 있습니다.
                 * 반면 데몬 스레드는 자바 프로그램이 종료될 때 강제로 실행이 종료될 수 있습니다.
                 * 성능은 같으므로 무한히 기다리는 것을 방지하기 위해 데몬 스레드로 설정을 해줍니다.
                 */
                t.setDaemon(true);
                return t;
            }
    );

}
