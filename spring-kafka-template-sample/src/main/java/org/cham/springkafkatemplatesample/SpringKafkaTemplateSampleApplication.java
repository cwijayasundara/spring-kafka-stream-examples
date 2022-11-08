package org.cham.springkafkatemplatesample;

import lombok.extern.slf4j.Slf4j;
import org.cham.springkafkatemplatesample.consumer.KafkaConsumer;
import org.cham.springkafkatemplatesample.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class SpringKafkaTemplateSampleApplication implements CommandLineRunner {
	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private KafkaConsumer kafkaConsumer;

	@Value("${test.topic}")
	private String topic;

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaTemplateSampleApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Inside the run method of the main class - SpringKafkaTemplateSampleApplication");
		String message = "Kafka producer using spring-kafka 2.9.2";
		kafkaProducer.send(topic,message);
		try {
			boolean messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
		} catch(Exception e){
			log.error("Error while consuming the message"+ e.getCause());
		}
	}
}
