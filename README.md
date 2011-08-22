# About

A proxy server to Campfire API, by Clojure.

# Install

```bash
$ lein install
```

# Run

```bash
$ lein run -m proxy.web
```

# Test

```bash
$ curl http://localhost:5000/recv/clk --data-binary @test/sample.json -H 'Content-Type: application/json; charset=utf-8'
```
