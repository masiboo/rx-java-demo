package org.example;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class RxJavaApp
{
    private static int start = 5, count = 2;

    public static void main( String[] args )
    {
  //      Observable.just(1, 2, 3, 4, 5)
   //             .subscribe(System.out::println);

/*        createObservableWithJust();
        createObservableFromIterable();
        createObservableUsingCreate();
        tryObserver();
        createColdObservable();
        createHotAndConnectableObservable();

        throwException();
        throwExceptionUsingCallable();


        createObservableUsingEmpty();
        createObservableUsingNever();
        tryObservableRange();

        tryObservableDefer();

        tryObservableFromCallable();

        tryObservableInterval();

        createCompletable();
        createMaybe();
        createSingle();

        RxJavaDisposable.handleDisposable();
        RxJavaDisposable.handleDisposableInObserver();
        RxJavaDisposable.handleDisposableOutsideObserver();
        RxJavaDisposable.compositeDisposable();

        RxJavaOperator.mapOperator();
        RxJavaOperator.mapOperatorReturnsDifferentData();
        RxJavaOperator.filterOperator();
        RxJavaOperator.combineMapAndFilter();
        RxJavaOperator.takeOperator();
        RxJavaOperator.takeWithInterval();
        RxJavaOperator.takeWhileOperator();
        RxJavaOperator.skipOperator();
        RxJavaOperator.skipWhileOperator();
        RxJavaOperator.distinctOperator();
        RxJavaOperator.distinctWithKeySelector();
        RxJavaOperator.distinctUntilChangedOperator();
        RxJavaOperator.distinctUntilChangedWithKeySelector();
        RxJavaOperator.useDefaultIfEmpty();
        RxJavaOperator.useSwitchIfEmpty();
        RxJavaOperator.useRepeat();
        RxJavaOperator.useScan();
        RxJavaOperator.useScanWithInitialValue();
        RxJavaOperator.useSorted();
        RxJavaOperator.useSortedWithOwnComparator();
        RxJavaOperator.useSortedOnNonComparator();
        RxJavaOperator.delay();
        RxJavaOperator.delayError();

        RxJavaOperator.containsWithPremitive();
        RxJavaOperator.containsWithNonPremitive();
        RxJavaOperator.exDoOnError();
        RxJavaOperator.exOnErrorResumeNext();
        RxJavaOperator.exOnErrorReturn();
        RxJavaOperator.exOnErrorReturnItem();


      // RxJavaOperator.retryWithPredicate();
        RxJavaOperator.exRetry();
        RxJavaOperator.exRetryUntil();
        RxJavaOperator.exDoOnSubscribe();
        RxJavaOperator.exDoOnNext();
        RxJavaOperator.exDoOnComplete();

        RxJavaOperator.exDoFinally();
        RxJavaOperator.exDoOnDispose();

        RxJavaOperator.exMerge();
        RxJavaOperator.exMergeArray();
        RxJavaOperator.exMergeIterable();
        RxJavaOperator.exMergeWith();
        RxJavaOperator.exMergeInfinite();

        RxJavaOperator.exZip();
        RxJavaOperator.exZipWith();
        RxJavaOperator.exZipIterable();
        */
        RxJavaOperator.exFlatMap();
        RxJavaOperator.exFlatMapBiFunction();
    }

    /**
     * In this method we've created an integer observable
     * using Observable's static method just()
     * This method converts the items provided to an Observable
     * So later we can subscribe and get the item one by one to take action
     */
    private static void createObservableWithJust() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);

        observable.subscribe(item -> System.out.println(item));
        System.out.println("-------------X-----------");
    }

    /**
     * In this method we've created an integer observable
     * using Observable's static method fromIterable()
     * This method converts anything which extend or implement iterable, to an observable
     * So later we can subscribe and get the item one by one to take action
     */
    private static void createObservableFromIterable() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Observable<Integer> observable = Observable.fromIterable(list);

        observable.subscribe(item -> System.out.println(item));

        System.out.println("-------------X-----------");
    }

    /**
     * In this method we've created an integer observable
     * using Observable's static method create()
     * Emitter here is an interface
     * By calling it's onNext(), onComplete() and onError() method
     * we can push events to our subscribers
     * So our subscribers can subscribe and get the item one by one to take action
     */
    private static void createObservableUsingCreate() {
        Observable<Integer> observable = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onNext(4);
            emitter.onNext(5);
            //emitter.onNext(null);
            emitter.onComplete();
        });

        observable.subscribe(item -> System.out.println(item),
                error -> System.out.println("There was error: " + error.getLocalizedMessage()),
                () -> System.out.println("Completed"));
        System.out.println("-------------X-----------");
    }

    private static void tryObserver(){
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        System.out.println("Trying Observer");
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                // We're going to discuss about onSubscribe() on upcoming episodes
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }

        };

        observable.subscribe(observer);

        System.out.println("-------------X-----------");
    }

    /**
     * Creates Cold Observable using Observable.just()
     * Because each and every onNext() gets their separate emission
     */
    private static void createColdObservable() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        System.out.println("Trying ColdObservable");
        observable.subscribe(item -> System.out.println("Observer 1: " + item));

        pause(3000);

        observable.subscribe(item -> System.out.println("Observer 2: " + item));
    }

    /**
     * Creates a Hot Observable
     * The moment we call the publish() method on Observable.just()
     * It Converts the Observable to a Connectable Observable
     * Connectable Observable doesn't start it's emission right after you subscribe
     * The moment we call connect() method it starts emission
     * Any Observer which subscribes after calling connect() misses emissions
     */
    private static void createHotAndConnectableObservable() {
        ConnectableObservable<Integer> observable = Observable.just(1, 2, 3, 4, 5).publish();
        System.out.println("Trying HotAndConnectableObservable");
        observable.subscribe(item -> System.out.println("Observer 1 hot: " + item));
    }

    /**
     * This method uses Observable.error() to pass a new instance of exception directly
     * So their observers get the same exception instance everytime
     */
    private static void throwException() {
        Observable observable = Observable.error(new Exception("An Exception"));
        System.out.println("Tying throwException");
        observable.subscribe(System.out::println, error -> System.out.println("Error 1: " + error.hashCode()));
        observable.subscribe(System.out::println, error -> System.out.println("Error 2: " + error.hashCode()));
        System.out.println("-------------X-----------");
    }

    /**
     * This method uses Observable.error() to pass a new Instance of Callable
     * which takes an Exception as it's return type through lambda
     * So their Observers gets a new instance of exception on onError() every time
     */
    private static void throwExceptionUsingCallable() {
        System.out.println("Tying throwExceptionUsingCallable");
        Observable observable = Observable.error(() -> {
            // We're printing this message to show that new instance gets created before
            // publishing the error to their Observers
            System.out.println("New Exception Created");
            return new Exception("An Exception");
        });
        observable.subscribe(System.out::println, error -> System.out.println("Error 1: " + error.hashCode()));
        observable.subscribe(System.out::println, error -> System.out.println("Error 2: " + error.hashCode()));
    }

    /**
     * Creates Observable using the factory method called empty()
     * Which doesn't emit any item to onNext() and only completes immediately
     * So, we get the callback on onComplete()
     */
    private static void createObservableUsingEmpty() {
        System.out.println("Tying createObservableUsingEmpty");
        Observable observable = Observable.empty();
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("Completed"));
    }

    /**
     * Creates Observable using the factory method called never()
     * Which doesn't emit any item and never completes
     * So, it's Observers are keep waiting until the thread is running
     * Observable.never() is primarily used for testing purposes
     */
    private static void createObservableUsingNever() {
        Observable observable = Observable.never();
        System.out.println("Tying createObservableUsingNever");
        observable.subscribe(System.out::println, System.out::println, () -> System.out.println("Completed"));
        // Pause the main thread for the hope that it will print something
        pause(3000);
    }

    private static void tryObservableRange(){
//        Observable<Integer> observable = Observable.range(0, 10);
//        observable.subscribe(System.out::println);

        // it prints from start up to (start + count - 1)
        int start = 5, count = 2;
        System.out.println("tryObservableRange");
        Observable<Integer> observable = Observable.range(start, count);
        observable.subscribe(System.out::println);
        System.out.println("-------------X-----------");
    }

    private static void tryObservableDefer(){
        // it prints from start up to (start + count - 1)
        System.out.println("tryObservableDefer");
        Observable<Integer> observable = Observable.defer(() -> {
            System.out.println("New Observable is created with start = " + start + " and count = " + count);
            return Observable.range(start, count);
        });
        observable.subscribe(item -> System.out.println("Observer 1: " + item));
        count = 3;
        observable.subscribe(item -> System.out.println("Observer 2: " + item));
        System.out.println("-------------X-----------");
    }

    private static void tryObservableFromCallable(){
        Observable<Integer> observable = Observable.fromCallable(() -> {
            System.out.println("Calling Method");
            return getNumber();
        });
        observable.subscribe(System.out::println,
                error -> System.out.println("An Exception Occurred" + error.getLocalizedMessage()));
    }

    /***
     * This method returns an expression which is an int
     * @return a dummy expression (int)
     */
    private static int getNumber() {
        System.out.println("Generating Value");
        return 1 / 0;
    }

    private static void tryObservableInterval(){
        Observable observable = Observable.interval(1, TimeUnit.SECONDS);

        observable.subscribe(item -> System.out.println("Observer 1: " + item));

        pause(2000);

        observable.subscribe(item -> System.out.println("Observer 2: " + item));

        pause(5000);
    }

    /**
     * Creates a single and emit data to it's Observer only once
     */
    private static void createSingle() {
        Single.just("Hello World in createSingle").subscribe(System.out::println);
    }

    /**
     * Creates a Maybe and it may or may not emit data to it's Observers
     * <p>Maybe.empty() has been called here and this factory method doesn't emit, only completes</p>
     */
    private static void createMaybe() {
        Maybe.empty().subscribe(new MaybeObserver<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object o) {
                System.out.println(o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("Done in createMaybe");

            }
        });
    }

    /**
     * Creates a Completable
     * <p>
     * Completable.fromSingle() factory method has been used here which takes a single
     * But it doesn't emit any item to it's Observers
     * </p>
     * <p>
     * Because CompletableObserver doesn't have any onNext() method
     * And it's works is limited to let it's Observers know that something has been completed
     * You may be using this sometime just to test some stuff
     * Otherwise, this is not used much often in production
     * </p>
     */
    private static void createCompletable() {
        Completable.fromSingle(Single.just("Hello World")).subscribe(() -> System.out.println("Done in createCompletable"));
    }




    /**
     * This method sleep the main thread for specified duration
     *
     * @param duration Sleep Duration in Milliseconds
     */
    private static void pause(int duration) {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
