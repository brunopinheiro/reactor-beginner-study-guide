package reactive

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class StreamSubscriptions {
    companion object {
        fun disposeExample() {
            val neverEndingPublisher = Mono.defer { Mono.never<Unit>() }
                .publishOn(Schedulers.single())
                .debug("neverEnding")

            val subscription = neverEndingPublisher.subscribe()
            println("")
            println("sleeping for 5 seconds...")
            Thread.sleep(5000)
            println("ok, disposing subscription...")
            subscription.dispose()
        }
    }
}