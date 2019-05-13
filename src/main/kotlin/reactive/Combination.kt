package reactive

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.function.BiFunction

class Combination {
    companion object {
        private const val numberOfValues: Long = 5

        private val numbersPublisher: Flux<Int>
            get() = Flux.interval(Duration.ofSeconds(1))
                .publishOn(Schedulers.single())
                .map { it.toInt() }
                .take(numberOfValues)
                .debug("\tnumbers")

        private val lettersPublisher: Flux<String>
            get() = Flux.interval(Duration.ofSeconds(2))
                .publishOn(Schedulers.single())
                .map { listOf("a", "b", "c", "d", "e")[it.toInt()] }
                .take(numberOfValues)
                .debug("\tletters")

        fun concatAndMergeExample() {
            subscribeAndWait((Flux.concat(numbersPublisher, lettersPublisher).debug("concat")))
            println("---------------------------------")
            subscribeAndWait(Flux.merge(numbersPublisher, lettersPublisher).debug("merge"))
        }

        fun combineLatestAndWithLatestFromExample() {
            subscribeAndWait(Flux.combineLatest(numbersPublisher, lettersPublisher, BiFunction(combinator)).debug("combineLatest"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.withLatestFrom(lettersPublisher, BiFunction(combinator)).debug("withLatest"))
        }

        private val combinator: (Int, String) -> String
            get() = { number, letter -> "$number$letter" }

        fun zipExample() {
            subscribeAndWait(Flux.zip(numbersPublisher, lettersPublisher).debug("zip"))
        }
    }
}