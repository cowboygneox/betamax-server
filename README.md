# Betamax Proxy Server
*Use the power of [betamax](http://freeside.co/betamax/) in a standalone proxy server*

## Download
Binaries are located [here](tree/master/bin)

## Usage
```
Usage: ./betamax-server-0.1-SNAPSHOT.jar {tape path} {tape mode} [port=8080]
{tape mode} options: READ_WRITE, READ_ONLY, READ_SEQUENTIAL, WRITE_ONLY, WRITE_SEQUENTIAL
Examples:
 * ./betamax-server-0.1-SNAPSHOT.jar tapes/recorded.yaml READ_ONLY
 * ./betamax-server-0.1-SNAPSHOT.jar /Users/user/stored-tapes/usecase1.yaml READ_WRITE 8081
```

## Development
This project was built around the [Betamax Project](https://github.com/robfletcher/betamax) using [Scala](http://www.scala-lang.org/), [SBT](https://github.com/sbt/sbt), and [SBT-Assembly](https://github.com/sbt/sbt-assembly).