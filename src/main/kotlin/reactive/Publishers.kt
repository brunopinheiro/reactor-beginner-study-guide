package reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import reactor.core.scheduler.Schedulers
import java.time.Duration

class Publishers {
    companion object {
        fun monoExamples() {
            Mono.just("Hello").debug("singleMonoPublisher").subscribe()
            println("---------------------------------")

            Mono.empty<Unit>().debug("emptyMonoPublisher").subscribe()
            println("---------------------------------")

            Mono.justOrEmpty<String?>(null).debug("nonOptionalMonoPublisher").subscribe()
            println("---------------------------------")

            Mono.error<Unit>(Error("Something went wrong")).debug("errorMonoPublisher").subscribe()
            println("---------------------------------")

            // won't implement never()! :D

            "Hello".toMono().debug("toMonoPublisher").subscribe()
            println("---------------------------------")

            subscribeAndWait(
                Mono.delay(Duration.ofSeconds(10))
                    .publishOn(Schedulers.single())
                    .map { it.toInt() }
                    .debug("monoDelay")
            )
            println("---------------------------------")
        }

        fun fluxExamples() {
            Flux.just("Hello").debug("singleFluxPublisher").subscribe()
            println("---------------------------------")

            Flux.empty<Unit>().debug("emptyFluxPublisher").subscribe()
            println("---------------------------------")

            Flux.error<Unit>(Error("Something went wrong")).debug("errorFluxPublisher").subscribe()
            println("---------------------------------")

            // won't implement never()! :D

            Flux.fromIterable(listOf(1, 2, 3, 4)).debug("fromIterableFluxPublisher").subscribe()
            println("---------------------------------")

            listOf(4, 3, 2, 1).toFlux().debug("toFluxPublisher").subscribe()
            println("---------------------------------")

            Flux.fromIterable(listOf(1, 2, 3, 4, 5))
                .map { if(it == 3) throw Error("Something went wrong") else it }
                .debug("fluxEmittingError")
                .subscribe(null, { println("received error: $it") })
            println("---------------------------------")

            subscribeAndWait(
                Flux.interval(Duration.ofSeconds(1))
                    .publishOn(Schedulers.single())
                    .map { it.toInt() }
                    .take(10)
                    .debug("fluxInterval")
            )
            println("---------------------------------")
        }

        fun deferExample() {
            var count = 0

            fun generateNumber(publisher: String) =
                (++count).apply { println("generating number $this for publisher $publisher...") }

            println("---------------------------------")
            println("creating publishers...")
            val toMono = generateNumber("toMono").toMono().debug("toMono")
            val justMono = Mono.just(generateNumber("just")).debug("just")
            val deferMono = Mono.defer { Mono.just(generateNumber("defer")) }.debug("defer")

            println("---------------------------------")
            println("starting subscriptions....")
            listOf(toMono, justMono, deferMono).forEach { it.subscribe(); println("") }
        }
    }
}

