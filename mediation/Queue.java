package mediation;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Queue<T> implements ParentQueue<T> {
	LinkedList<T> messages = new LinkedList<>();
	int buffer;
	Lock mutex = new ReentrantLock();
	Condition producerWaitingCondition = mutex.newCondition();
	Condition consumerWaitingCondition = mutex.newCondition();



		public Queue(int maxSize) {
			this.buffer = maxSize;
	}

	@Override
	public void push(T message) {

		mutex.lock();
		try {
				while (messages.size() == buffer){
					try {
						producerWaitingCondition.await();

					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				messages.add(message);
				consumerWaitingCondition.signal();
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public T pop() {
		mutex.lock();
		try {
				while (messages.isEmpty()){
					try {
						consumerWaitingCondition.await();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				T res = messages.removeFirst();;
				producerWaitingCondition.signal();
				return res;

		} finally {
			mutex.unlock();
		}

	}
}