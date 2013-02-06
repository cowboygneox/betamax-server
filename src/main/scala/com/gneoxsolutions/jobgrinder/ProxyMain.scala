package com.gneoxsolutions.jobgrinder

import sys.ShutdownHookThread
import java.io.File

object ProxyMain extends App {

  val defaultPort = 8080

  def runApp(tapePath: String, port: Int = defaultPort) {
    val proxy: BetamaxProxy = new BetamaxProxy(tapePath, port)

    proxy.start()

    ShutdownHookThread(proxy.stop()).join()
  }

  def printUsage() {
    val file = new File(this.getClass.getProtectionDomain.getCodeSource.getLocation.toURI)
    println("Usage: ./%s {tape path} [port=%d]".format(file.getName, defaultPort))
  }

  try {
    args.toList match {
      case tapePath :: Nil         => runApp(tapePath)
      case tapePath :: port :: Nil => runApp(tapePath, port.toInt)
      case _                       => printUsage()
    }
  } catch {
    case e: NumberFormatException  => printUsage()
  }
}
