package example.testtask.eventqueue;

import java.util.LinkedList;
import java.util.Queue;

public class EventHandlerImpl extends Thread implements EventHandler {
	
	private enum State {
		CREATED,
		STARTED,
		STOPPED
	}

	private final Queue<MyEvent> events;
	private volatile State state;


	public EventHandlerImpl() {
		events = new LinkedList<>();
		state = State.CREATED;
	}

	/*
	 * @see root.EventHandler#addEvent(root.MyEvent)
	 */
	@Override
	public void addEvent(MyEvent event) throws Exception {
		synchronized (events) {
			if (state == State.STARTED) {
				System.out.println(state + ", " + event.getName()  + " is added in " + System.nanoTime());
				events.add(event);
				events.notify();
			} else {
				throw new Exception();
			}
		}

	}

	@Override
	public void startHandler() throws Exception {
		System.out.println("Event handler started in " + System.nanoTime());
		if (state != State.CREATED) {
			throw new RuntimeException("Cannot start started or stopped handler");
		} 
		state = State.STARTED;
		start();
	}

	@Override
	public void stopHandler() throws Exception {
		if (state != State.STARTED) {
			throw new RuntimeException("Cannot stop not started handler");
		}
		System.out.println("Event handler stopped in " + System.nanoTime());
		state = State.STOPPED;
		synchronized (events) {
			events.notify();
		}
	}

	@Override
	public void run() {
		while (true) {
			if (state == State.STARTED) {
				synchronized (events) {
					if (events.isEmpty()) {
						try {
							events.wait();
						} catch (InterruptedException e) {
							throw new RuntimeException("Thread cannot be interrupted before invocation stopHandler method");
						}
					} else {
						handle(events.poll());
					}
				}
			} else {
				while (!events.isEmpty()) {
					handle(events.poll());
				}
				
				break;
			}
		}
	}

	protected void handle(MyEvent event) {
		try {
			System.out.println("Handle " + event);
			event.execute();
		} catch (InterruptedException e) {
			throw new RuntimeException("Handler must handle all ivents");
		}
	}
	
	@Override
	public String toString() {
		return "events queue: " + events;
	}

}
