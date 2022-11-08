package org.cham.tradeservice.service;

import lombok.extern.slf4j.Slf4j;
import org.cham.tradeservice.config.RedisConfiguration;
import org.cham.tradeservice.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private ReactiveRedisOperations reactiveRedisOperations;

    public Mono<Boolean> saveOrder(Order order){
        log.info(" Inside OrderService.saveOrder()" + order.toString());
        Mono<Boolean> isSuccess = reactiveRedisOperations.persist(order);
        log.info(" The save op is " + isSuccess);
        return isSuccess;
    }

    public Flux<Order> getAllOrders(){
        log.info(" Inside OrderService.getAllOrders()");
        return reactiveRedisOperations.keys("*")
                .flatMap(reactiveRedisOperations.opsForValue()::get);
    }
}
