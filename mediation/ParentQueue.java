package mediation;

public interface ParentQueue<T> {
	void push(T message);
	T pop();
}
