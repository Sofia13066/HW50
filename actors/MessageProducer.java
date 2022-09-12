package actors;
import mediation.ParentQueue;

public class MessageProducer extends Thread {
	ParentQueue<String> queue;
	int nMessages;
	int sendIntervalMillis;

	public MessageProducer(ParentQueue<String> queue, int nMessages, int sendIntervalMillis) {
		super();
		this.queue = queue;
		this.nMessages = nMessages;
		this.sendIntervalMillis = sendIntervalMillis;
	}

	@Override
	public void run() {
		for (int i = 0; i < nMessages; i++) {
			try {
				Thread.sleep(sendIntervalMillis);
			} catch (InterruptedException e) {
			}
			String message = "message#" + i;
			queue.push(message);
			System.out.printf("%s <- producer %d\n", message, getId());
		}
	}
}
