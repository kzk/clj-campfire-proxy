# About

clj-campfire-proxy is a proxy server to Campfire API.

# Requirements

* [leiningen](https://github.com/technomancy/leiningen)
* [foreman](https://github.com/ddollar/foreman)
* [heroku](https://github.com/heroku/heroku)

# Running locally

Please create .env file at first.

```bash
$ cp .env.sample .env
$ emacs .env
$ export $(cat .env)
$ lein deps
$ foreman start
```

# Running as Heroku app

```bash
$ heroku create clj-campfire-proxy-production --stack cedar
$ heroku config:add CAMPFIRE_USER=hogehogeinc
$ heroku config:add CAMPFIRE_ROOM=312312
$ heroku config:add CAMPFIRE_APIKEY=000000000000000000000000000000
$ git push heroku master
```

# Test

```bash
$ curl http://localhost:5000/recv/clk --data-binary @test/sample.json -H 'Content-Type: application/json; charset=utf-8'
```

# See also

* [Campfire API](http://developer.37signals.com/campfire/)
* [Cloudkick Webhooks](https://support.cloudkick.com/Addresses#Webhooks)
