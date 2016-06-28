package example.testtask.eventqueue;

import java.util.Objects;

public abstract class BaseEventProducer extends Thread {
	private final EventHandler eventHandler;
	
	public BaseEventProducer(EventHandler eventHandler) {
		this.eventHandler = Objects.requireNonNull(eventHandler);
	}
	
	@Override
	public void run() {
		System.out.println("Event " + this + " started in " + System.nanoTime());
		while (true) {
			try {
				eventHandler.addEvent(createEvent());
			} catch (Exception e) {
				break;
			}
		}
	}

	protected abstract MyEvent createEvent();
}
