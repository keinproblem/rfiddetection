package connector;

import core.AlertEvent;
import io.reactivex.functions.Consumer;


/**
 * Very basic interface for a subscribe mechanism in a publish-subscribe pattern.
 */
public interface ConnectorStrategy {

    /**
     * Used for a subscription to an {@link io.reactivex.Observable}
     *
     * @return a consumer implementation for subscription
     */
    Consumer<AlertEvent> provideAlertEventConsumer();
}
