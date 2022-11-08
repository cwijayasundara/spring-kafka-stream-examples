package org.cham.orderservice;

import lombok.extern.slf4j.Slf4j;
import org.cham.orderservice.domain.Order;
import org.cham.orderservice.domain.OrderType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class OrderServiceApplication {
	private static long orderId = 0;

	LinkedList<Order> buyOrders = new LinkedList<>(List.of(
			new Order(++orderId, 1, "Elon Mask", 1, 100, LocalDateTime.now(), OrderType.BUY, 1000),
			new Order(++orderId, 2, "Joe Bloggs", 1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
			new Order(++orderId, 3, "Tom Johns", 1, 100, LocalDateTime.now(), OrderType.BUY, 1030),
			new Order(++orderId, 4, "Gramme King",1, 200, LocalDateTime.now(), OrderType.BUY, 1050),
			new Order(++orderId, 5, "Jim Carry", 1, 200, LocalDateTime.now(), OrderType.BUY, 1000),
			new Order(++orderId, 11, "Bob Marley", 1, 100, LocalDateTime.now(), OrderType.BUY, 1050)
	));

	LinkedList<Order> sellOrders = new LinkedList<>(List.of(
			new Order(++orderId, 6, "Jeff Bridges", 1, 200, LocalDateTime.now(), OrderType.SELL, 950),
			new Order(++orderId, 7, "Andor Black", 1, 100, LocalDateTime.now(), OrderType.SELL, 1000),
			new Order(++orderId, 8, "Jane Scott", 1, 100, LocalDateTime.now(), OrderType.SELL, 1050),
			new Order(++orderId, 9, "Cam Harley", 1, 300, LocalDateTime.now(), OrderType.SELL, 1000),
			new Order(++orderId, 10, "Kiara Small", 1, 200, LocalDateTime.now(), OrderType.SELL, 1020)
	));

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public Supplier<Message<Order>> orderBuySupplier() {
		return () -> {
			if (buyOrders.peek() != null) {
				Message<Order> o = MessageBuilder
						.withPayload(buyOrders.peek())
						.setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(buyOrders.poll()).getId())
						.build();
				log.info("Order: {}", o.getPayload());
				return o;
			} else {
				return null;
			}
		};
	}

	@Bean
	public Supplier<Message<Order>> orderSellSupplier() {
		return () -> {
			if (sellOrders.peek() != null) {
				Message<Order> o = MessageBuilder
						.withPayload(sellOrders.peek())
						.setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(sellOrders.poll()).getId())
						.build();
				log.info("Order: {}", o.getPayload());
				return o;
			} else {
				return null;
			}
		};
	}
}
