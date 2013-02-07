package com.gneoxsolutions.jobgrinder

import sys.ShutdownHookThread
import java.io.File
import co.freeside.betamax.TapeMode
import co.freeside.betamax.TapeMode._

object ProxyMain extends App {

  val defaultPort = 8080

  def runApp(tapePath: String, tapeMode: TapeMode, port: Int = defaultPort) {
    val proxy: BetamaxProxy = new BetamaxProxy(tapePath, port, tapeMode)

    proxy.start()

    ShutdownHookThread(proxy.stop()).join()
  }

  def printUsage() {
    val file = new File(this.getClass.getProtectionDomain.getCodeSource.getLocation.toURI)
    println("Usage: ./%s {tape path} {tape mode} [port=%d]".format(file.getName, defaultPort))
    println("{tape mode} options: READ_WRITE, READ_ONLY, READ_SEQUENTIAL, WRITE_ONLY, WRITE_SEQUENTIAL")
    println("Examples:")
    println(" * ./%s tapes/recorded.yaml READ_ONLY".format(file.getName))
    println(" * ./%s /Users/user/stored-tapes/usecase1.yaml READ_WRITE %d".format(file.getName, defaultPort + 1))
  }

  try {
    args.toList match {
      case tapePath :: "READ_WRITE"       :: Nil          => runApp(tapePath, READ_WRITE)
      case tapePath :: "READ_ONLY"        :: Nil          => runApp(tapePath, READ_ONLY)
      case tapePath :: "READ_SEQUENTIAL"  :: Nil          => runApp(tapePath, READ_SEQUENTIAL)
      case tapePath :: "WRITE_ONLY"       :: Nil          => runApp(tapePath, WRITE_ONLY)
      case tapePath :: "WRITE_SEQUENTIAL" :: Nil          => runApp(tapePath, WRITE_SEQUENTIAL)
      case tapePath :: "READ_WRITE"       :: port :: Nil  => runApp(tapePath, READ_WRITE,       port.toInt)
      case tapePath :: "READ_ONLY"        :: port :: Nil  => runApp(tapePath, READ_ONLY,        port.toInt)
      case tapePath :: "READ_SEQUENTIAL"  :: port :: Nil  => runApp(tapePath, READ_SEQUENTIAL,  port.toInt)
      case tapePath :: "WRITE_ONLY"       :: port :: Nil  => runApp(tapePath, WRITE_ONLY,       port.toInt)
      case tapePath :: "WRITE_SEQUENTIAL" :: port :: Nil  => runApp(tapePath, WRITE_SEQUENTIAL, port.toInt)
      case _                                      => printUsage()
    }
  } catch {
    case e: NumberFormatException  => printUsage()
  }
}
