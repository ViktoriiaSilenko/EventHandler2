package example.testtask.eventqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class EventProducerFactory {
	private static final int EVENT_PRODUCER_COUNT = 3;
	
	
	private final EventHandler eventHandler;
	
	private EventProducerFactory(EventHandler eventHandler) {
		this.eventHandler = Objects.requireNonNull(eventHandler);
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
