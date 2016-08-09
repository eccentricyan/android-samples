package jp.samples.github;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class GithubSubscriber<T> extends Subscriber<T> {

    protected boolean isHttp404(Throwable error) {
        return error instanceof HttpException && ((HttpException) error).code() == 404;
    }

}
