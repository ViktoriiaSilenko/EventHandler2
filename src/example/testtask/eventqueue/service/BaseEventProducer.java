package example.testtask.eventqueue.service;

import static java.util.Objects.requireNonNull;
import example.testtask.eventqueue.model.MyEvent;
import example.testtask.eventqueue.service.EventHandler;

public abstract class BaseEventProducer extends Thread {
	private final EventHandler eventHandler;
	
	public BaseEventProducer(EventHandler eventHandler) {
		this.eventHandler = requireNonNull(eventHandler);
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
