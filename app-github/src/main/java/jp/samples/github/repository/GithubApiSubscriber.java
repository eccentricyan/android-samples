package jp.samples.github.repository;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class GithubApiSubscriber<T> extends Subscriber<T> {

    protected boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
