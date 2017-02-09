## About

Spring 5 and Spring-Boot 2 Demo Application highly expired 
by [bclozel/spring-reactive-university](https://github.com/bclozel/spring-reactive-university)

A lot of test's and playground with Mono/Flow checkout here [reactor/lite-rx-api-hands-on](https://github.com/reactor/lite-rx-api-hands-on)

## Run

### MongoDB

Navigate to _container_ folder and run `docker-compose up -d`.
It will start MongoDB and map the ports.

### Start the application

Change _application.properties_ properties according your setup and [gitter token](https://developer.gitter.im/apps), just sign in 
and check token in your profile.
  
`./gradlew bootRun` and magic will happens!

## API

Host by default: _http://localhost:8080_

* `/` – index page from _resources/templates/index.ftl_ with simple UI for gitter messages streaming
* `/hello/`, `/exchange` – simple API with text output
* `/gitter`, `/gitter/{name}` –  Gitter exposed API's
* `/mate` – example of reactive webclient and mongodb combining
* `gitter/messages` – SSE API for gitter messages

## Links

* [Reactive Streams](http://www.reactive-streams.org)
* [Understanding Reactive Types](https://spring.io/blog/2016/04/19/understanding-reactive-types)
* [Notes on Reactive Programming Part I](https://spring.io/blog/2016/06/07/notes-on-reactive-programming-part-i-the-reactive-landscape)
* [Notes on Reactive Programming Part II](https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code)
* [Notes on Reactive Programming Part III](https://spring.io/blog/2016/07/20/notes-on-reactive-programming-part-iii-a-simple-http-server-application)

## Talks

* [From Imperative To Reactive - Rossen Stoyanchev](https://www.youtube.com/watch?v=fcgK-4HYJpI&t)
* [Reactive Spring](https://www.youtube.com/watch?v=Xm-KjMY_Z_w)
