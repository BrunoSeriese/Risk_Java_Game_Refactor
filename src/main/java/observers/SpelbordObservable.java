package observers;

public interface SpelbordObservable {
    void register(SpelbordObserver observer);
    void notifyAllObservers();
}
