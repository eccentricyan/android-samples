package jp.samples.github.event;

import com.trello.rxlifecycle.LifecycleProvider;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxEventBus {

    private final Subject<Object, Object> subject = new SerializedSubject<>(PublishSubject.create());

    public RxEventBus() {
    }

    public void post(Object event) {
        subject.onNext(event);
    }

    public <T> void subscribe(LifecycleProvider<?> lifecycleProvider,
                                      Class<T> eventClass, Action1<T> action) {
        subscribe(lifecycleProvider, AndroidSchedulers.mainThread(), eventClass, action);
    }

    public <T> void subscribe(LifecycleProvider<?> lifecycleProvider, Scheduler scheduler,
                                      Class<T> eventClass, Action1<T> action) {
        subject
            .ofType(eventClass)
            .compose(lifecycleProvider.bindToLifecycle())
            .observeOn(scheduler)
            .subscribe(action);
    }

}
