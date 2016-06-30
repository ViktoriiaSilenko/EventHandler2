package example.testtask.eventqueue.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import example.testtask.eventqueue.service.BaseEventProducer;
import example.testtask.eventqueue.service.EventHandler;
import example.testtask.eventqueue.service.EventHandlerImpl;
import example.testtask.eventqueue.service.EventProducer;
import example.testtask.eventqueue.service.EventProducerFactory;

/**
 * 
 * @author Viktoria Silenko
 * 
 * Реализация обработчика событий (MyEvent и EventHandler.java). 
 * Необходимо написать реализацию  интерфейса EventHandler .  
 * События поступают на обработку из разных, никак не синхронизованных между собой,  потоков.  
 * События должны быть обработаны в том порядке – в котором они поступили в EventHandler. 
 * Необходимо реализовать механизм запуска и остановки обработчика.  
 * В случае вызова метода EventHandler. stopHandler()  должен обработать все имеющиеся в очереди события и только после этого завершить работу. 
 * Так же необходимо минимизировать процессорные затраты во время простоя обработчика(когда нет новых событий,  а старые уже обработаны). 
 * При реализации не использовать пакет java.util.concurrent.
 *
 */

public class Runner {

	public static void main(String[] args) throws Exception {

		EventHandler eventHandler = new EventHandlerImpl();
		eventHandler.startHandler();

		List<EventProducer> eventProducers = EventProducerFactory.from(eventHandler).createEventProducers();
		for (BaseEventProducer eventProducer : eventProducers) {
			eventProducer.start();
		}

		TimeUnit.MILLISECONDS.sleep(500);

		eventHandler.stopHandler();
		for (EventProducer eventProducer : eventProducers) {
			System.out.println(eventProducer);
		}
		
		System.out.println(eventHandler);

	}

}
