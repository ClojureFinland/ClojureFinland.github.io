# ClojureFinland.github.io

Static site generator for Clojure Finland web-page.

[hiccup](https://github.com/weavejester/hiccup) is used for generating HTML and [garden](https://github.com/noprompt/garden) for CSS.

## Prerequisities

Clojure CLI tools ([instructions](https://clojure.org/guides/getting_started))

## Local development

Clone from https://github.com/ClojureFinland/ClojureFinland.github.io

Webserver and watchers can be started from REPL. See [dev.clj](./dev/dev.clj).

REPL with development deps can be started simply by `clj -A:dev`

``` shell
✗ clj -A:dev
Clojure 1.10.1
user=> (require '[dev])
nil
user=> (ns dev)
dev=> (clojure-finland/build!)
Wrote ./dist/index.html
Wrote ./dist/styles.css
nil
dev=> port
8889
dev=> serve-dir
"./dist"
dev=> (server/start! {:port port :root serve-dir})
2020-06-06 16:08:03.167:INFO::main: Logging initialized @408925ms to org.eclipse.jetty.util.log.StdErrLog
2020-06-06 16:08:03.208:INFO:oejs.Server:main: jetty-9.4.29.v20200521; built: 2020-05-21T17:20:40.598Z; git: 77c232aed8a45c818fd27232278d9f95a021095e; jvm 14.0.1+7
2020-06-06 16:08:03.255:INFO:oejs.AbstractConnector:main: Started ServerConnector@30a7653e{HTTP/1.1, (http/1.1)}{0.0.0.0:8889}
2020-06-06 16:08:03.255:INFO:oejs.Server:main: Started @409014ms
Server started http://localhost:8889
nil
```

## Build

Generate html and css files under `./dist`

`clj -A:build`

## Deploy

Deployment to Github Pages happens automatically via Github Actions once changes are merged into `master` branch.

## License

Copyright © 2020 Clojure Finland

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
