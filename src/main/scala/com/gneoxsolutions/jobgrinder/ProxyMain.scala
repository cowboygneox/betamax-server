package com.gneoxsolutions.jobgrinder

import sys.ShutdownHookThread

object ProxyMain extends App {
  val proxy: BetamaxProxy = new BetamaxProxy("betamax-tape")

  proxy.start()

  ShutdownHookThread(proxy.stop())

  while (proxy.isRunning)
    Thread.sleep(10)
}
