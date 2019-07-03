package reactive

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration

class Transformations {
    companion object {
        private const val numberOfValues: Long = 5
        private val numbersPublisher: Flux<Int>
            get() = Flux.interval(Duration.ofMillis(200))
                .publishOn(Schedulers.single())
                .take(numberOfValues)
                .map { it.toInt() }
                .debug("\tnumbers")

        private fun getLettersPublisher(index: Int) =
            Flux.interval(Duration.ofMillis(500))
                .publishOn(Schedulers.single())
                .take(numberOfValues)
                .map { listOf("a", "b", "c", "d", "e")[it.toInt()] }
                .map { "$index-$it" }
                .debug("\tletters-$index")

        fun bufferCollectExamples() {
            subscribeAndWait(numbersPublisher.buffer().debug("buffer"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.buffer(5).debug("buffer5"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.collectList().debug("collectList"))
        }

        fun concatFlatMapExamples() {
            subscribeAndWait(numbersPublisher.concatMap { getLettersPublisher(it) }.debug("concatMap"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.flatMap { getLettersPublisher(it) }.debug("flatMap"))
        }

        fun expandExamples() {
            val origin = Flux.fromIterable(listOf(1, 2))

            val expandingFunction: (Int) -> Flux<Int> = { index ->
                if(index > 100) {
                    Flux.empty<Int>()
                } else {
                    Flux.fromIterable(listOf(1, 2)).map { index * 10 + it }
                }
            }

            subscribeAndWait(origin.expand(expandingFunction).debug("expand"))
        }

        fun scanReduceExamples() {
            subscribeAndWait(numbersPublisher.scan { acc, value -> acc + value }.debug("scan"))
            println("---------------------------------")
            subscribeAndWait(numbersPublisher.reduce { acc, value -> acc + value }.debug("reduce"))
        }
    }
}