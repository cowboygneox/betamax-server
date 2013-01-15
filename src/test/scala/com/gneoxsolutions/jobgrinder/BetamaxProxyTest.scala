package com.gneoxsolutions.jobgrinder

import org.specs2.mutable.Specification
import com.ning.http.client.{ProxyServer, AsyncHttpClient}

class BetamaxProxyTest extends Specification {
  sequential
  stopOnFail

  "A betamax proxy" should {
    val proxy = new BetamaxProxy("test")
    val port  = 3333

    "Start on port %d".format(port) in {
      proxy.isRunning must beEqualTo(false)

      proxy.start()

      proxy.isRunning must beEqualTo(true)
      proxy.port must beEqualTo(port)
    }

    "Reload a request from Google" in {
      val asyncHttpClient = new AsyncHttpClient()
      val response = asyncHttpClient.prepareGet("http://www.google.com").setProxyServer(new ProxyServer("127.0.0.1", port)).execute().get()
      response.getStatusCode must beEqualTo(768)
    }

    "Stop" in {
      proxy.isRunning must beEqualTo(true)

      proxy.stop()

      proxy.isRunning must beEqualTo(false)
    }
  }
}
