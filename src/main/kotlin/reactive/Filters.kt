package reactive

import reactor.core.publisher.Flux
import java.time.Duration

class Filters {
    companion object {
        val numbersPublisher: Flux<Int>
                // the delay here was not really necessary, but I wanted to make it easy to see the events being emitted
                get() = Flux.fromIterable(listOf(1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 3, 3, 3, 2, 2, 1, 0)).delayElements(Duration.ofMillis(500))

        fun distinctExamples() {
            println("distinct x distinctUntilChanged")
            subscribeAndWait(numbersPublisher.distinct().debug("distinct"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.distinctUntilChanged().debug("distinctUntilChanged"))
        }

        fun sampleExample() {
            subscribeAndWait(numbersPublisher.sample(Duration.ofSeconds(2)).debug("sample"))
        }

        fun skipExamples() {
            println("skip x skipUntil x skipWhile")
            subscribeAndWait(numbersPublisher.skip(5).debug("skip"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.skipUntil { it > 3 }.debug("skipUntil"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.skipWhile { it < 3 }.debug("skipWhile"))
        }
    }
}
