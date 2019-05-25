# Reactor (Beginner Study Guide)
It’s really hard visualising streams and what operators do when you’re learning reactive programming. Things seem to be unnecessarily complicated, which can demotivate some people to keep studying.

One thing that really helped me understand was to implement streams using the different kind of constructors, applying operators, check the results and comparing with others. And that’s why I created this guide. It contains some “questions” and implemented examples which can help you start your reactive programming “journey”.

This guide is not intended to replace the documentation or teach reactive programming. If you haven’t read anything about it yet, here are some good starting points:

- [The introduction to Reactive Programming you’ve been missing](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754)
- [RxMarbles](http://rxmarbles.com)
- [Expert to Expert: Brian Beckman and Erik Meijer - Inside the .NET Reactive Framework (Rx) - YouTube](https://www.youtube.com/watch?v=looJcaeboBY)

The questions and examples on this guide are based on the [Reactor](https://projectreactor.io) implementation. And I built it based on this [gist](https://gist.github.com/williamhjcho/4df6320b306ad02b2a619aac728ed50e).

### And how should I use it?
Well, here are my suggestions on how to use this guide:

1. Move to the next question. If you don’t know the answer, try to figure out what would be the answer based on your current context and naming of the classes or methods.

> One thing that might help you figure out what a method is supposed to do, is reading its signature: the arguments’ names and types, and return’s type. Actually, this tip is valid for other frameworks and languages.

2. Check the example related to that question, and try to understand the source code written for it. Think about what would be its output.
3. Uncomment the example method on `App.kt` and run the project
```sh
$ ./gradlew run
```
4. Check what was printed on the console, and see if your answer was correct. Otherwise, try to answer it again.
5. _(Optional)_ Try to draw a visual representation of it - [RxMarbles](http://rxmarbles.com)

## Contributing
Just open an issue if you have an idea on to improve it! Things I missed or it might not be important to know at the beginning of your studies. Questions or example are not really clear. I’d love to hear from you :grin:

## —— The Guide!
### Publishers
- what is a publisher?
- `Mono` x `Flux`
	- when which one of those publishers end?
	- how to subscribe to then?

> There are some methods called `doOn...` available when applying operators to streams. Those methods are operators, and not a way of subscribing to streams. They’re supposed to be used as “side effects” of a stream, like logging, metrics and debug. Notice that `ReactiveUtils.debug` method is implemented using then! I think this an important thing to mention because, maybe you face a method `doOnError` and think you should use it to handle error emissions, but it should not be used for that. It won’t capture the error, which means the error will be thrown if you won’t have any error handler. One good example is `Publishers.kt:57`, which has the `doOnError` operator added to it, but the error stills being emitted.

#### How to create publishers?
**Mono**:
- `Mono.just`
- `Mono.empty` x `Mono.justOrEmpty`
- `Mono.error`
- `Mono.never`
- `.toMono()`
- `Mono.delay`

**Flux**:
- `Flux.just`
- `Flux.empty`
- `Flux.never`
- `Flux.fromIterable`
- `.toFlux()`
- `Flux.interval`

> can you guess why `Mono` has a constructor called `delay` and `Flux` has one called `interval` instead?

There’s a constructor called `defer` which is available for both: `Mono` and `Flux` which I think it is worth to spend some time trying to figure out what it does and what would be a good use of it.

### Subscriptions
- What is a subscription? 
- What is its purpose?

### Operators
> Those are not all the existing operators, just some that I think it is worth to having a look. Notice that most of them are only available for `Flux` publishers.

**Combinations**:
- `concat` x `merge`
- `combineLatest` x `withLatestFrom`
- `zip`

**Filters**:
- `distinct` x `distinctUntilChanged`
- `sample`
- `skip` x `skipUntil` x `skipWhile`

**Transformations**:
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