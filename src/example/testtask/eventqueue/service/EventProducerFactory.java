package example.testtask.eventqueue.service;

import java.util.ArrayList;
import java.util.List;
import example.testtask.eventqueue.model.EventProducer;
import static java.util.Objects.requireNonNull;


public class EventProducerFactory {
	private static final int EVENT_PRODUCER_COUNT = 5;
	
	private final EventHandler eventHandler;
	
	private EventProducerFactory(EventHandler eventHandler) {
		this.eventHandler = requireNonNull(eventHandler);
	}

	public List<EventProducer> createEventProducers() {
		List<EventProducer> eventProducers = new ArrayList<>();

		for (int i = 0; i < EVENT_PRODUCER_COUNT; i++) {
			eventProducers.add(new EventProducer("Producer" + i, eventHandler));
		}
		
		return eventProducers;
	}

	
	public static EventProducerFactory from(EventHandler eventHandler) {
		return new EventProducerFactory(eventHandler);
	}
	
}
