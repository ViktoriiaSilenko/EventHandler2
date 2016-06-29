package example.testtask.eventqueue.model;

import java.util.Random;

import example.testtask.eventqueue.model.MyEvent;
import example.testtask.eventqueue.service.EventHandler;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class EventProducer extends BaseEventProducer {

	private static final int MAX_DELAY = 100;
	private int eventCount;
	private final String name;
	
	public EventProducer(String name, EventHandler eventHandler) {
		super(eventHandler);
		this.name = name;
	}

	@Override
	protected MyEvent createEvent() {
		eventCount++;
		try {
			MILLISECONDS.sleep(new Random().nextInt(MAX_DELAY));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return new MyEvent("Event of producer " + name + " in " + System.nanoTime());
	}

	public int getEventCount() {
		return eventCount;
	}
	
	@Override
	public String toString() {
		return name + ", eventCount = " + eventCount;
	}
}
