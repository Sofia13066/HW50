package actors;
import mediation.ParentQueue;

public class MessageConsumer extends Thread {
	ParentQueue<String> queue;
	int messageTimeMillis;

	public MessageConsumer(ParentQueue<String> queue, int messageTimeMillis) {
		super();
		this.queue = queue;
		this.messageTimeMillis = messageTimeMillis;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			String message = queue.pop();
			System.out.printf("%s -> consumer %d\n", message, getId());
			try {
				Thread.sleep(messageTimeMillis);
			} catch (InterruptedException e) {

			}
		}
	}
}