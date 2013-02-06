package com.gneoxsolutions.jobgrinder

import sys.ShutdownHookThread
import java.io.File

object ProxyMain extends App {

  def runApp(tapeRoot: String, tapeName: String, port: Int) {
    val proxy: BetamaxProxy = new BetamaxProxy(tapeRoot, tapeName, port)

    proxy.start()

    ShutdownHookThread(proxy.stop()).join()
  }

  def printUsage() {
    val file = new File(this.getClass.getProtectionDomain.getCodeSource.getLocation.toURI)
    println("Usage: ./%s {tape directory} {tape name} {port}".format(file.getName))
  }

  try {
    args.toList match {
      case tapeRoot :: tapeName :: port :: Nil => runApp(tapeRoot, tapeName, port.toInt)
      case _ => printUsage()
    }
  } catch {
    case e: NumberFormatException => printUsage()
  }
}
