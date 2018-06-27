package core;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * This class acts as an generic asynchronous bus
 * It will provide an decoupled asynchronous stream for items with the type {@link T}
 */
public class AlertEventBus<T> {
    private PublishSubject<T> alertEventPublishSubject;

    public AlertEventBus() {
        this.alertEventPublishSubject = PublishSubject.create();
    }

    /**
     * Publish an item
     *
     * @param item item to be published
     */
    public void publish(final T item) {
        this.alertEventPublishSubject.onNext(item);
    }

    /**
     * Register a new subscriber as {@link Consumer}.
     *
     * @param alertEventConsumer the consumer which will subscribe to this bus
     * @return the respective {@link Disposable} for the performed subscription
     */
    public Disposable subscribe(final Consumer<T> alertEventConsumer) {
        return this.alertEventPublishSubject.subscribe(alertEventConsumer);
    }
}
