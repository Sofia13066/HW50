package controller;

import actors.MessageConsumer;
import actors.MessageProducer;
import mediation.Queue;
import mediation.ParentQueue;

public class Appl {

	private static final int N_MESSAGES = 50;
	private static final int N_CONSUMERS = 5;
	private static final int MSG_SEND_INTERVAL_MILLIS = 100;
	private static final int MSG_HANDLING_TIME_MILLIS = 1000;
	private static final int QUEUE_MAX_SIZE = 10;

	public static void main(String[] args) throws InterruptedException {
		ParentQueue<String> queue = new Queue<>(QUEUE_MAX_SIZE);
		MessageProducer sender = new MessageProducer(queue, N_MESSAGES, MSG_SEND_INTERVAL_MILLIS);
		sender.start();
		for (int i = 0; i < N_CONSUMERS; i++) {
			new MessageConsumer(queue, MSG_HANDLING_TIME_MILLIS).start();
		}

		Thread.sleep(N_MESSAGES / N_CONSUMERS * MSG_HANDLING_TIME_MILLIS + 1000);

	}

}
