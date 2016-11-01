package com.honor.forall.util;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import rx.Observable;
import rx.schedulers.Schedulers;

public final class ParallelExecutionUtils {

    private ParallelExecutionUtils() {}

    public static <T1, T2, R> R execute(BiFunction<T1, T2, R> merge, Supplier<T1> job1, Supplier<T2> job2) {
        return Observable.<R>create(s -> {
            Observable<T1> j1 = async(job1);
            Observable<T2> j2 = async(job2);

            Observable<R> response = Observable.zip(j1, j2, (t1, t2) -> {
                return merge.apply(t1, t2);
            });

            s.onNext(response.toBlocking().single());
            s.onCompleted();
        }).toBlocking().single();
    }

    private static <T> Observable<T> async(Supplier<T> job) {
        return Observable.<T>create(s -> {
            s.onNext(job.get());
            s.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
}
