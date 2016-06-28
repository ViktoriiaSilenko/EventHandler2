package example.testtask.eventqueue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(MAX_DELAY));
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
