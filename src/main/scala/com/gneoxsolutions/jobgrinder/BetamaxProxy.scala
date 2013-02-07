package com.gneoxsolutions.jobgrinder

import co.freeside.betamax.proxy.jetty.ProxyServer
import co.freeside.betamax._
import java.util.Properties

class BetamaxProxy(tapePath: String, proxyPort: Int, tapeMode: TapeMode) extends HttpInterceptor {

  val (tapeRoot, tapeName) = tapePath.replaceAll(".yaml", "").splitAt(tapePath.lastIndexOf("/"))

  val properties = new Properties()
  properties.setProperty("betamax.defaultMode"    , tapeMode.toString)
  properties.setProperty("betamax.ignoreLocalhost", "true")
  properties.setProperty("betamax.sslSupport"     , "true")
  properties.setProperty("betamax.tapeRoot"       , tapeRoot)
  properties.setProperty("betamax.proxyPort"      , proxyPort.toString)
  properties.setProperty("betamax.proxyTimeout"   , "20000")

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