package observers;

public interface GameObservable {
    void register(GameObserver observer);
    void notifyAllObservers();
}
