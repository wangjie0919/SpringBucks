package com.wj.jpademo.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

/*
RxJava-响应式编程的用法(基本用法)
 */
@Slf4j
public class TestRxJava {
    public static void main(String[] args) {
        //createMono();
        //1-buffer
       // Flux.range(1, 100).buffer(20).subscribe(System.out::println);
       // Flux.range(1, 10).bufferUntil(i -> i % 2 == 1).subscribe(System.out::println);
        //Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
        //Flux.range(1,15).buffer(3,5).subscribe(System.out::println);
        //2 - filter
        //Flux.range(1,10).filter(i->i%2 == 0).subscribe(System.out::println);
        // 3- window
      //  Flux.range(1, 100).window(20).subscribe(System.out::println);
        //4- zipWith(第二个为格式化输出)
      //  Flux.just("a","b").zipWith(Flux.just("c","d")).subscribe(System.out::println);
      /*  Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);*/
        //5-take()
      /*  Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);*/
      //6-reduce()和reduceWith() -对所有元素进行求和运算。reduceWith的第一个相加的元素为初始值
       /* Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);*/
        //7-merge 和mergeSequential
        //merge()会根据创建时间合并序列；mergeSequential则会严格按照第一个序列的所有元素拼第二个序列。
     /*   Flux.merge(Flux.interval(Duration.of(100,ChronoUnit.MILLIS)).take(5), Flux.interval(Duration.of(50,ChronoUnit.MILLIS), Duration.of(100,ChronoUnit.MILLIS)).take(5))
                .toStream()
                .forEach(System.out::println);
        Flux.mergeSequential(Flux.interval(Duration.of(100,ChronoUnit.MILLIS)).take(5), Flux.interval(Duration.of(50,ChronoUnit.MILLIS), Duration.of(100,ChronoUnit.MILLIS)).take(5))
                .toStream()
                .forEach(System.out::println);*/
        //8-flatMap 和 flatMapSequential 有点没理解 两者区别与（merge 和mergeSequential）一样
      /*  Flux.just(5, 10)
                .flatMap(x -> Flux.interval(Duration.of(x * 10,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)).take(x))
                .toStream()
                .forEach(System.out::println);
        Flux.just(5, 10)
                .flatMapSequential(x -> Flux.interval(Duration.of(x * 10,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)).take(x))
                .toStream()
                .forEach(System.out::println);*/
        //9-concatMap
       /* Flux.just(5, 10)
                .concatMap(x -> Flux.interval(Duration.of(x * 10,ChronoUnit.MILLIS),
                        Duration.of(100,ChronoUnit.MILLIS)).take(x))
                .toStream()
                .forEach(System.out::println);*/
        //10- combineLatest
        //把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素。只要其中任何一个流中产生了新的元素，合并操作就会被执行一次，结果流中就会产生新的元素。
        Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.of(100,ChronoUnit.MILLIS)).take(5),
                Flux.interval(Duration.of(50,ChronoUnit.MILLIS),Duration.of(100,ChronoUnit.MILLIS)).take(10)
        ).toStream().forEach(System.out::println);


    }

    private static void createMono() {
        //创建Mono
        Mono.fromSupplier(() -> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }

    /**
     * 3 - 使用create()新建Flux
     */
    private static void createFluxByCreateM() {
        Flux.create(listSink ->
        {listSink.next("Hello");
        listSink.next("world");
        listSink.complete();}
        ).subscribe(System.out::println);

        Flux.create(sink ->{
            for(int i = 0;i<=10;i++){
              sink.next(i);
        }
        sink.complete();
        }).subscribe(System.out::println);
    }
    /**
     * 2- 使用generate()新建Flux
     */
    private static void createFluxByGenerate() {
        Flux.generate(sink ->{
            sink.next("Hello");
                              sink.complete();}
                              ).subscribe(System.out::println);
        final Random random = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int value = random.nextInt(100);
            list.add(value);
            sink.next(value);
            if (list.size() == 10) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);
    }

    /**
     * 1- 使用Flux的静态方法创建Flux
     */
    private static void createFluxByStatticFlux() {
        Flux.just("Hello","world").subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.range(1,10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println); //指定从0开始的一个Longl类型序列
    }

    /*private static void testAsync() throws InterruptedException {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(123);
                Thread.sleep(3000);
                emitter.onNext(456);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer)  {
                        log.info("tag",integer+"");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable)  {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }*/

    private static void testSimpleRxJava() {
        //1- 被观察者
        Observable novel = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });
        //2- 观察者
        Observer<String> reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                   log.info("onSubscribe~");
            }

            @Override
            public void onNext(String s) {
                log.info("onNext:{}",s);
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError~");
            }

            @Override
            public void onComplete() {
                log.info("onComplete~");
            }
        };
        //3-建立订阅关系
        novel.subscribe(reader);
    }
}
