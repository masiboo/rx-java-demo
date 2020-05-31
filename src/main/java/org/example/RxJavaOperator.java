package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class RxJavaOperator {
    /**
     * Uses the map() operator to transform the value in between,
     * before it reaches to the Observer
     */
    public static void mapOperator() {
        System.out.println("Try mapOperator");
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        observable
                .map(item -> item * 2)
                .subscribe(System.out::println);
    }

    /**
     * Uses the map() operator to transform the value in between,
     * before it reaches to the Observer and here map() emit different data type and
     * Observer just needs to adjust with it or accept the same type of emission
     */
    public static void mapOperatorReturnsDifferentData() {
        System.out.println("Try mapOperatorReturnsDifferentData");
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        observable
                .map(item -> "Hello World!")
                .subscribe(System.out::println);
    }

    /**
     * Uses the filter() operator to filter out the value in between,
     * which doesn't meet the logic specified in filter,
     * and filter() may not emit no item if it no item match that criteria
     */
    public static void filterOperator() {
        System.out.println("try filterOperator");
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        observable
                .filter(item -> item % 2 == 0)
                .subscribe(System.out::println);
    }

    /**
     * Combines the map() and filter() operator together
     * and as map() and filter() both are nothing but an Observable
     * and also works like an Observer, so we can chain them,
     * but the order of operation does matter here.
     * Here filter() will kicks in first and map() will work on the filtered emission,
     * and not the whole emission in general
     */
    public static void combineMapAndFilter() {
        System.out.println("Try combineMapAndFilter");
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        observable
                .filter(item -> item % 2 == 0)
                .map(item -> item * 2)
                .subscribe(System.out::println);
    }

    /**
     * Used take(2) here, which emits only first 2 items and then complete
     */
    public static void takeOperator() {
        System.out.println("Try takeOperator");
        Observable.just(1,2,3,4,5)
                .take(2)
                .subscribe(System.out::println, System.out::println, () -> System.out.println("Completed"));
    }

    /**
     * Used take(2) but with interval here, which emits items for the specified time interval only
     */
    public static void takeWithInterval() {
        System.out.println("Try takeWithInterval");
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .take(2, TimeUnit.SECONDS)
                .subscribe(System.out::println, System.out::println, () -> System.out.println("Completed"));

        pause(5000);
    }

    /**
     * This takeWhile() is like combination of filter and take,
     * the only difference is filter goes through all the items to check if the logic is true
     * whereas takeWhile() keep emitting only some logic is true
     * and it completes once it gets logic as false
     */
    public static void takeWhileOperator() {
        System.out.println("Try takeWhileOperator");
        Observable.just(1,2,3,4,5,1,2,3,4,5)
                .takeWhile(item -> item <= 3)
                .subscribe(System.out::println);
    }

    /**
     * skip(2) is just the opposite of take(2)
     * it will skip first values and emit remaining ones
     */
    public static void skipOperator() {
        System.out.println("Try skipOperator");
        Observable.just(1,2,3,4,5)
                .skip(2)
                .subscribe(System.out::println);
    }

    /**
     * skipWhile() is little is like combination of filter and skip,
     * the only difference is filter goes through all the items to check if the logic is true
     * whereas skipWhile() keep skipping items only if some logic true
     * and once the logic is false it emits remaining items without checking
     */
    public static void skipWhileOperator() {
        System.out.println("Try skipWhileOperator");
        Observable.just(1,2,3,4,5,1,2,3,4,5)
                .skipWhile(item -> item <= 3)
                .subscribe(System.out::println);
    }

    /**
     * Used the distinct() to get the unique emission
     */
    public static void distinctOperator() {
        System.out.println("Try distinctOperator");
        Observable.just(1, 1, 2, 2, 3, 3, 4, 5, 1, 2)
                .distinct()
                .subscribe(System.out::println);
    }

    /**
     * Used the distinct based on the item's property to distinguish emission
     */
    public static void distinctWithKeySelector() {
        System.out.println("Try distinctWithKeySelector");
        Observable.just("foo", "fool", "super", "foss", "foil")
                .distinct(String::length)
                .subscribe(System.out::println);
    }

    /**
     * Used distinctUntilChanged() to avoid consecutive duplicate items one after another
     */
    public static void distinctUntilChangedOperator() {
        System.out.println("Try distinctUntilChangedOperator");
        Observable.just(1, 1, 2, 2, 3, 3, 4, 5, 1, 2)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }

    /**
     * Used distinctUntilChangedOperator() based on the item's property to distinguish the consecutive duplicate items
     */
    public static void distinctUntilChangedWithKeySelector() {
        System.out.println("Try distinctUntilChangedWithKeySelector");
        Observable.just("foo", "fool", "super", "foss", "foil")
                .distinctUntilChanged(String::length)
                .subscribe(System.out::println);
    }

    /**
     * Used defaultIfEmpty() operator so the observer will emit at least a default value
     * if the emission gets empty
     */
    public static void useDefaultIfEmpty() {
        System.out.println("Try useDefaultIfEmpty");
        Observable.just(1,2,3,4,5)
                .filter(item -> item > 10)
                .defaultIfEmpty(100)
                .subscribe(System.out::println);
    }

    /**
     * This will switch to some alternative Observable Source
     * if the emission gets empty
     */
    public static void useSwitchIfEmpty() {
        System.out.println("Try useSwitchIfEmpty");
        Observable.just(1,2,3,4,5)
                .filter(item -> item > 10)
                .switchIfEmpty(Observable.just(6,7,8,9,10))
                .subscribe(System.out::println);
    }

    /**
     * This used repeat operator to specify how many times emission witll repeat
     */
    public static void useRepeat() {
        System.out.println("Try useRepeat");
        Observable.just(1, 2, 3, 4, 5)
                .repeat(3)
                .subscribe(System.out::println);
    }

    /**
     * This uses scan operator to print the sum of the previously emitted item and current one that is going to emit
     */
    public static void useScan() {
        System.out.println("Try useScan");
        Observable.just(1, 2, 3, 4, 5)
                .scan((accumulator, next) -> accumulator + next)
                .subscribe(System.out::println);
    }

    /**
     * This uses scan operator print the sum of the previously emitted item and current one that is going to emit,
     * but this also takes the initial emission into consideration by specifying an initial value
     */
    public static void useScanWithInitialValue() {
        System.out.println("Try useScanWithInitialValue");
        Observable.just(1, 2, 3, 4, 5)
                .scan(10, (accumulator, next) -> accumulator + next)
                .subscribe(System.out::println);
    }

    /**
     * This used sorted operator to sort the operator
     */
    public static void useSorted() {
        System.out.println("Try  useSorted");
        Observable.just(3, 5, 2, 4, 1)
                .sorted()
                .subscribe(System.out::println);
    }

    /**
     * This used sorted operator along with Comparators reverse function
     * to sort and reverse the emission
     */
    public static void useSortedWithOwnComparator() {
        System.out.println("Try useSortedWithOwnComparator");
        Observable.just(3, 5, 2, 4, 1)
                .sorted(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }

    /**
     * This used sorted operator along with Integer's compare function to
     * sort the emission based on their length
     */
    public static void useSortedOnNonComparator() {
        System.out.println("Try useSortedOnNonComparator");
        Observable.just("foo", "john", "bar")
                .sorted((first, second) -> Integer.compare(first.length(), second.length()))
                .subscribe(System.out::println);
    }

    /**
     * Used 'delay' operator to add a delay before the Observable start emission
     * Note: 'delay' doesn't delay each emission, instead it delays the start of the emission
     */
    public static void delay() {
        System.out.println("Try delay");
        Observable.just(1, 2, 3, 4, 5)
                .delay(3000, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);

        pause(5000);
    }

    /**
     * 'delay' operator doesn't add any delay before emitting error
     * This means the error is immediately emitted to it's subscribers by default
     * To delay the emission of error we need to pass delayError parameter as true
     */
    public static void delayError() {
        System.out.println("Try delayError");
        Observable.error(new Exception("Error"))
                .delay(3, TimeUnit.SECONDS, true)
                .subscribe(System.out::println,
                        error -> System.out.println(error.getLocalizedMessage()),
                        () -> System.out.println("Completed"));
        pause(5000);
    }

    /**
     * contains operator checks if the number exist in the Observable emission
     * As soon as it gets the item it emits true or false otherwise
     */
    public static void containsWithPremitive() {
        System.out.println("Try containsWithPremitive");
        Observable.just(1, 2, 3, 4, 5)
                .contains(3)
                .subscribe(System.out::println);
    }

    /**
     * contains operator checks if the specific object exist in the Observable emission
     * based on the Object's hashcode
     * As soon as it gets the item it emits true or false otherwise
     */
    public static void containsWithNonPremitive() {
        System.out.println("Try containsWithNonPremitive");
        User user = new User("mroydroid");
        Observable.just(user)
                .contains(user)
                .subscribe(System.out::println);
    }

    /**
     * a static class for demonstration purpose
     */
    static class User {
        String name;

        User(String name) {
            this.name = name;
        }
    }

    /**
     * The error goes to the doOnError lambda on the chain first, so we can handle it
     */
    public static void exDoOnError() {
        System.out.println("Try exDoOnError");
        Observable.error(new Exception("This is an example error"))
                .doOnError(error -> System.out.println("Error: " + error.getMessage()))
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * Whenever an error is found on the chain, it goes to the onErrorResumeNext
     * As this takes another Observable, the subscriber switch to that Observable
     * to skip the error
     */
    public static void exOnErrorResumeNext() {
        Observable.error(new Exception("This is an example error"))
               // .onErrorResumeNext(Observable.just(1, 2, 3, 4, 5))
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * We can return specific values based on the error type
     * As when we get the error it goes to the onErrorReturn lambda
     */
    public static void exOnErrorReturn() {
        System.out.println("Try exOnErrorReturn");
        Observable.error(new Exception("This is an example error"))
                .onErrorReturn(error -> {
                    if (error instanceof IOException) return 0;
                    else throw new Exception("This is an exception");
                })
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * We can pass an alternative for the subscriber below the chain
     * Whenever error encounters it gives that specific alternative
     */
    public static void exOnErrorReturnItem() {
        System.out.println("Try  exOnErrorReturnItem");
        Observable.error(new IOException("This is an example error"))
                .onErrorReturnItem(1)
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * This retry block try to analyze the error and take decision based on the error whether to retry or not
     * based on our logic inside that block
     */
    public static void retryWithPredicate() {
        System.out.println("try retryWithPredicate");
        Observable.error(new IOException("This is an example error"))
                .retry(error -> {
                    if (error instanceof IOException) {
                        System.out.println("retrying");
                        return true;
                    } else return false;
                })
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * This retry takes the number and tries to retry subscribing and getting the data from the observable again
     */
    public static void exRetry() {
        System.out.println("Try exRetry");
        Observable.error(new Exception("This is an example error"))
                .retry(3)
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * retryUntil depends on the boolean that we pass, it keeps retrying until we pass true based on the logic
     */
    public static void exRetryUntil() {
        System.out.println("Try exRetryUntil");
        AtomicInteger atomicInteger = new AtomicInteger();
        Observable.error(new Exception("This is an example error"))
                .doOnError(error -> {
                    System.out.println(atomicInteger.get());
                    atomicInteger.getAndIncrement();
                })
                .retryUntil(() -> {
                    System.out.println("Retrying");
                    return atomicInteger.get() >= 3;
                })
                .subscribe(
                        System.out::println,
                        error -> System.out.println("Subscribed Error: " + error.getMessage()),
                        () -> System.out.println("Completed"));
    }

    /**
     * doOnSubscribe will get the disposable as soon as we subscribe the specific observable
     */
    public static void exDoOnSubscribe() {
        System.out.println("Try exDoOnSubscribe");
        Observable.just(1, 2, 3, 4, 5)
                .doOnSubscribe(disposable -> System.out.println("doOnSubscribe: Subscribed"))
                .subscribe(System.out::println);
    }

    /**
     * doOnNext will get the item just before it reaches to the downstream of onNext
     */
    public static void exDoOnNext() {
        System.out.println("Try exDoOnNext");
        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(item -> System.out.println("doOnNext: " + ++item))
                .subscribe(System.out::println);
    }

    /**
     * doOnComplete will get void just before it reaches to the downstream of onComplete
     */
    public static void exDoOnComplete() {
        System.out.println("Try exDoOnComplete");
        Observable.just(1, 2, 3, 4, 5)
                .doOnComplete(() -> System.out.println("doOnComplete: Completed"))
                .subscribe(System.out::println, System.out::print, () -> System.out.println("Completed"));
    }

    /**
     * doFinally works after the observable is done or onComplete
     */
    public static void exDoFinally() {
        Observable.just(1, 2, 3, 4, 5)
                .doFinally(() -> System.out.println("doFinally: Completed"))
                .subscribe(System.out::println);
    }

    /**
     * doOnDispose only works if we can dispose the observable explicitly
     * before onComplete or onError
     */
    public static void exDoOnDispose() {
        Observable.just(1, 2, 3, 4, 5)
                .doOnDispose(() -> System.out.println("onDispose: Disposed"))
                .doOnSubscribe(disposable -> disposable.dispose())
                .subscribe(System.out::println);

    }

    /**
     * Uses the static merge function to merge Observables
     * This function can take at most 4 Observables
     */
    public static void exMerge() {
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.just(6, 7, 8, 9, 10);

        Observable.merge(sixToTen, oneToFive).subscribe(System.out::println);
    }

    /**
     * Uses the static mergeArray function to merge unlimited Observables, as it takes vararg
     */
    public static void exMergeArray() {
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.just(6, 7, 8, 9, 10);
        Observable<Integer> elevenToFifteen = Observable.just(11, 12, 13, 14, 15);
        Observable<Integer> sixteenToTwenty = Observable.just(16, 17, 18, 19, 20);
        Observable<Integer> twentyOneToTwentyFive = Observable.just(21, 22, 23, 24, 25);

        Observable.mergeArray(oneToFive, sixToTen, elevenToFifteen, sixteenToTwenty, twentyOneToTwentyFive)
                .subscribe(System.out::println);

    }

    /**
     *  Uses the static merge function to merge List of Observables
     */
    public static void exMergeIterable() {
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.just(6, 7, 8, 9, 10);
        Observable<Integer> elevenToFifteen = Observable.just(11, 12, 13, 14, 15);
        Observable<Integer> sixteenToTwenty = Observable.just(16, 17, 18, 19, 20);
        Observable<Integer> twentyOneToTwentyFive = Observable.just(21, 22, 23, 24, 25);
        List<Observable<Integer>> observableList =
                Arrays.asList(oneToFive, sixToTen, elevenToFifteen, sixteenToTwenty, twentyOneToTwentyFive);

        Observable.merge(observableList).subscribe(System.out::println);

    }

    /**
     * All Observables has the mergeWith function, to easily merge it with another Observable
     * We can't merge with more than one Observable in this case
     */
    public static void exMergeWith() {
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.just(6, 7, 8, 9, 10);

        oneToFive.mergeWith(sixToTen).subscribe(System.out::println);
    }

    /**
     * This shows an implementation of the merge function with infinite Observables
     * As interval emits data as given time
     */
    public static void exMergeInfinite() {
        Observable<String> infinite1 = Observable.interval(1, TimeUnit.SECONDS)
                .map(item -> "From infinite1: " + item);
        Observable<String> infinite2 = Observable.interval(2, TimeUnit.SECONDS)
                .map(item -> "From infinite2: " + item);


        infinite1.mergeWith(infinite2).subscribe(System.out::println);

        pause(6050);
    }

    /**
     * Uses Zip operator to get the stream on the Zipper function
     */
    public static void exZip() {
        System.out.println("Try exZip");
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.range(6, 5);
        Observable<Integer> elevenToFifteen = Observable.fromIterable(Arrays.asList(11, 12, 13, 14, 15));

        Observable.zip(oneToFive, sixToTen, elevenToFifteen, (a, b, c) -> a + b + c)
                .subscribe(System.out::println);

    }

    /**
     * Uses ZipWith operator on the Observable to easily zip One Observable with another
     */
    public static void exZipWith() {
        System.out.println("Try exZipWith");
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.fromIterable(Arrays.asList(6, 7, 8, 9, 10));

        oneToFive.zipWith(sixToTen, Integer::sum)
                .subscribe(System.out::println);
    }

    /**
     * Uses zipIterable operator which takes List of Observables and provides the zipped emission in an array
     */
    public static void exZipIterable() {
        System.out.println("Try exZipIterable");
        Observable<Integer> oneToFive = Observable.just(1, 2, 3, 4, 5);
        Observable<Integer> sixToTen = Observable.just(6, 7, 8, 9, 10);
        Observable<Integer> elevenToFifteen = Observable.just(11, 12, 13, 14, 15);

        List<Observable<Integer>> observables = Arrays.asList(
                oneToFive, sixToTen, elevenToFifteen
        );

        Observable.zip(observables, Arrays::toString, true, 1)
                .subscribe(System.out::println);
    }

    /**
     * This uses the flatMap to return desired Observables to the downstream
     * based on upstream emissions it gets in the Function
     */
    public static void exFlatMap() {
        System.out.println("Try exFlatMap");
        Observable<String> observable = Observable.just("foo", "bar", "jam");
        observable.flatMap((string) -> {
            if (string.equals("bar")) return Observable.empty();
            return Observable.fromArray(string.split(""));
        }).subscribe(System.out::println);
    }

    /**
     * This is similar to normal flatMap except the biFunction
     * which merge upstream emission with the emissions from the flatMap returned Observables from Function
     */
    public static void exFlatMapBiFunction() {
        System.out.println("Try exFlatMapBiFunction");
        Observable<String> observable = Observable.just("foo", "bar", "jam");
        observable.flatMap(string -> Observable.fromArray(string.split("")),
                (actual, second) -> actual + " " + second)
                .subscribe(System.out::println);
    }


    /**
     * This method sleep the main thread for specified duration
     *
     * @param duration Sleep Duration in Milliseconds
     */
    public static void pause(int duration) {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
