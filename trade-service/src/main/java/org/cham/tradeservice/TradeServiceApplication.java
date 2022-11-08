package org.cham.tradeservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.cham.tradeservice.domain.Order;
import org.cham.tradeservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class TradeServiceApplication {

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(TradeServiceApplication.class, args);
	}

	// example consumer using spring-cloud-stream-binder-kafka-streams: 3.2.5
	@Bean
	public BiConsumer<KStream<Long, Order>, KStream<Long, Order>> orders() {
		return (orderBuy, orderSell) -> orderBuy
				.merge(orderSell)
				.peek((k, v) -> {
					log.info("New({}): {}", k, v);
					orderService.saveOrder(v);
				});
	}

	// example consumer using spring-cloud-starter-stream-kafka: 3.2.5
	@Bean
	public Consumer<Order> orderConsumer() {
		return message -> log.info("Received order via " + message + " via spring-cloud-stream-binder-kafka-stream ");
	}

}
