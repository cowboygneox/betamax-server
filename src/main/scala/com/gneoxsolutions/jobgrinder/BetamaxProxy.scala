package com.gneoxsolutions.jobgrinder

import co.freeside.betamax.proxy.jetty.ProxyServer
import co.freeside.betamax.{HttpInterceptor, Recorder}

class BetamaxProxy(tapeName: String) extends HttpInterceptor {
  val recorder = new Recorder()
  recorder.insertTape(tapeName)
  val proxyServer = new ProxyServer(recorder)

  def start()   { proxyServer.start() }
  def stop()    {
    recorder.ejectTape()
    proxyServer.stop()
  }
  def isRunning = proxyServer.isRunning
  def port      = proxyServer.getPort
}