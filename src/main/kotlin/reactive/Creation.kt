package reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

class Creation {
    companion object {
        fun eventsExample() {
            // won't include `never()` here :D

            Mono.just("Hello").debug("singlePublisher").subscribe()
            println("---------------------------------")

            Mono.empty<Unit>().debug("emptyPublisher").subscribe()
            println("---------------------------------")

            Mono.justOrEmpty<String?>(null).debug("nonOptionalPublisher").subscribe()
            println("---------------------------------")

            Mono.error<Unit>(Error("Something went wrong")).debug("errorPublisher").subscribe()
            println("---------------------------------")

            "Hello".toMono().debug("toMonoPublisher").subscribe()
            println("---------------------------------")

            Flux.fromIterable(listOf(1, 2, 3, 4)).debug("fromIterablePublisher").subscribe()
            println("---------------------------------")

            listOf(4, 3, 2, 1).toFlux().debug("toFluxPublisher").subscribe()
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