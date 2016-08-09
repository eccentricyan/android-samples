package jp.samples.github;

import rx.Subscription;

public class SubscriptionUtil {

    private SubscriptionUtil() {
    }

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
