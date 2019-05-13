package reactive

import reactor.core.publisher.Flux
import reactor.core.publisher.ReplayProcessor
import reactor.core.scheduler.Schedulers
import java.time.Duration

class HotCold {
    companion object {
        private val numbersPublisher: Flux<Int>
            get() = Flux.interval(Duration.ofSeconds(1))
                .publishOn(Schedulers.single())
                .take(5)
                .map { it.toInt() }

        fun coldExample() {
            print("starting the subscriptions....")
            subscribeAndWait(numbersPublisher.debug("cold_1"))
            subscribeAndWait(numbersPublisher.debug("cold_2"))
        }

        fun hotExample() {
            val hotPublisher = numbersPublisher.publish()
            hotPublisher.connect()

            val firstSubscription = hotPublisher
                .delaySubscription(Duration.ofSeconds(2))
                .debug("hot_1")
                .subscribe()

            val secondSubscription = hotPublisher
                .delaySubscription(Duration.ofSeconds(3))
                .debug("hot_2")
                .subscribe()

            while(firstSubscription.isDisposed.not() || secondSubscription.isDisposed.not()) {}
        }

        fun replayExample() {
            val replayPublisher = ReplayProcessor.fromIterable(listOf(1, 2, 3, 4, 5)).replay(2).autoConnect()
            replayPublisher.debug("replay_1").subscribe()
            replayPublisher.debug("replay_2").subscribe()
        }
    }
}