package jp.samples.github.event;

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

    public <T> Subscription onEvent(Class<T> eventClass, Action1<T> action) {
        return onEvent(eventClass, AndroidSchedulers.mainThread(), action);
    }

    public <T> Subscription onEvent(Class<T> eventClass, Scheduler scheduler, Action1<T> action) {
        return subject.ofType(eventClass).observeOn(scheduler).subscribe(action);
    }


}
