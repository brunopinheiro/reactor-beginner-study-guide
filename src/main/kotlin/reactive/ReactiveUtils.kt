package reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun <T> Mono<T>.debug(id: String) = this
    .doOnSubscribe { println("$id => subscribed") }
    .doOnNext { println("$id => next: $it") }
    .doOnSuccess { println("$id => success") }
    .doOnError { println("$id => error: $it") }
    .doOnTerminate { println("$id => terminated") }
    .doOnCancel { println("$id => cancelled") }

fun <T> Flux<T>.debug(id: String) = this
    .doOnSubscribe { println("$id => subscribed") }
    .doOnNext { println("$id => next: $it") }
    .doOnComplete {println("$id => complete") }
    .doOnError { println("$id => error: $it") }
    .doOnTerminate { println("$id => terminated") }
    .doOnCancel { println("$id => cancelled") }

fun subscribeAndWait(publisher: Flux<*>) {
    val subscription = publisher.subscribe()
    // since the publishers are running on a different thread,
    // the app would move forward, eventually ending its execution without
    // waiting the publishers
    while(subscription.isDisposed.not()) {}
}

fun subscribeAndWait(publisher: Mono<*>) {
    val subscription = publisher.subscribe()
    while(subscription.isDisposed.not()) {}
}
