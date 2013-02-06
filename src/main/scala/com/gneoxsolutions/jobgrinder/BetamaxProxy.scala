package com.gneoxsolutions.jobgrinder

import co.freeside.betamax.proxy.jetty.ProxyServer
import co.freeside.betamax.{HttpInterceptor, Recorder}
import java.util.Properties

class BetamaxProxy(tapeRoot: String, tapeName: String, proxyPort: Int) extends HttpInterceptor {

  val properties = new Properties()
  properties.setProperty("betamax.defaultMode", "READ_WRITE")
  properties.setProperty("betamax.ignoreLocalhost", "true")
  properties.setProperty("betamax.sslSupport", "true")
  properties.setProperty("betamax.tapeRoot", tapeRoot)
  properties.setProperty("betamax.proxyPort", proxyPort.toString)
  properties.setProperty("betamax.proxyTimeout", "20000")

  val recorder = new Recorder(properties)
  recorder.insertTape(tapeName)
  val proxyServer = new ProxyServer(recorder)

  def start()   {
    proxyServer.start()
  }
  def stop()    {
    recorder.ejectTape()
    proxyServer.stop()
  }
  def isRunning = proxyServer.isRunning
  def port      = proxyServer.getPort
}