# Reactor (Beginner Study Guide)
## Considerations
This guide is not intended to replace the documentation or teach you every thing you should know about (Actually, it does not teach you nothing :sweat_smile:). Its goal is helping you feel more comfortable with one implementation of reactive programming, [Reactor](https://projectreactor.io), providing some questions and suggestions of things you should analyse and figure out by yourself.

I built this guide based on this [gist](https://gist.github.com/williamhjcho/4df6320b306ad02b2a619aac728ed50e).

And, here are some other links that might help you understanding reactive programming:
- [The introduction to Reactive Programming you've been missing](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754)
- [RxMarbles](http://rxmarbles.com)

### What is this project which comes with this guide?
It's really hard visualising streams and how operators works. One thing that really helped me during my learning process was to test some of those operators with different inputs and on different scenarios. At the beginning of your studies it's hard to even know how to setup a project and implement the first scenarios, and for that reason, I implemented some simple examples which I hope can help you get on track!

### How to use and run it?
Here are my suggestions (which are the same things I did when I was learning) on how you should use this project
1. Try to understand the source code for one specific example
2. Try to imagine what would be its result
3. Uncomment the example method on `App.kt` and run the project
```sh
$ ./gradlew run
```
4. Check what was printed on the console
5. _(Optional)_ Try to draw a visual representation of it - [RxMarbles](http://rxmarbles.com)

> Not only for Reactive Programming, but for every framework, new language or tool that I use / study, one thing that really helps me understand things better, is trying figuring out how a function works by reading its signature.
>
> For example: `fun <T, R> Collection<T>.map(transform: (T) -> R) -> Collection<R>`

## Contributing
Just open an issue if you have an idea on to improve it! Things I missed or it might not be important to know at the beginning of your studies. Questions or example are not really clear. I'd love to hear from you :grin:

## ---- The Guide!
### Publishers
- what is a publisher?
- `Mono` _x_ `Flux`
- when which one of those publishers end?
- how to subscribe to then?
- `.doOn...` what does those methods do?
> the goal of this question is to help you noticing something important regarding the `doOn...` methods and their use. first, check the `ReactiveUtils.debug` extension, then, check the outputs of the `errorPublisher` on `Creation.eventsExample`. should you use `doOnError` to handle an error emission on the stream?

- _(Optional)_ considering what `Mono` and `Flux` represent, can you answer why the event emitted by `Mono` is called **success** and the one from `Flux` is **complete**?

### How to create publishers?
- `just`
- `empty`
- `justOrEmpty`
- `error`
- `never`
- `.toMono()`
- `Flux.fromIterable`
- `.toFlux()`
- `Mono.delay` and `Flux.interval`
> why does `Mono` has the `delay` constructor and `Flux` has `interval`?

### Subscriptions
- What is a subscription?
- What is its purpose?

### Operators
> Those are not all the existing operators, just some that I think it is worth to having a look. Notice that most of them are only available for `Flux` publishers.
- `concat` x `merge`
- `combineLatest` x `withLatestFrom`
- `zip`
- `distinct` x `distinctUntilChanged`
- `sample`
- `skip` x `skipUntil` x `skipWhile`
- `buffer` x `buffer(int)` x `collectList`
- `map` x `flatMap` x `concatMap` x `expand`
- `scan` x `reduce`
- `expand`

And here are some you can try implement by yourself
- `take` x `takeUntil` x `takeWhile`
- `takeLast` x `last` x `elementAt`
- `ignoreElements`
- `count`

### Cold x Hot publishers
- (`Mono` and `Flux`) x `ConnectableFlux`
- `ReplayProcessor`

### Schedulers
- what are schedulers?
- what kind of schedulers are available?
- `publishOn` x `subscribeOn`
- `parallel` and `runOn`

### Testing | Debug
- `checkpoint`
- `ReactiveUtils.debug`
- `StepVerifier`
