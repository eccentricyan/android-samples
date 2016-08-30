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

    public <T> Subscription subscribe(LifecycleProvider<?> lifecycleProvider,
                                      Class<T> eventClass, Action1<T> action) {
        return subscribe(lifecycleProvider, AndroidSchedulers.mainThread(), eventClass, action);
    }

    public <T> Subscription subscribe(LifecycleProvider<?> lifecycleProvider, Scheduler scheduler,
                                      Class<T> eventClass, Action1<T> action) {
        return subject
                .ofType(eventClass)
                .compose(lifecycleProvider.bindToLifecycle())
                .observeOn(scheduler)
                .subscribe(action);
    }

}
