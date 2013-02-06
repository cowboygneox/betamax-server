package com.gneoxsolutions.jobgrinder

import org.specs2.mutable.Specification
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpHost
import org.apache.http.conn.params.ConnRoutePNames

class BetamaxProxyTest extends Specification {
  sequential
  stopOnFail

  "A betamax proxy" should {
    val port  = 3333
    val proxy = new BetamaxProxy("src/test/resources/betamax/tapes", "test", port)

    "Start on port %d".format(port) in {
      proxy.isRunning must beEqualTo(false)

      proxy.start()

      proxy.isRunning must beEqualTo(true)
      proxy.port must beEqualTo(port)
    }

    "Reload a request from Google" in {
      val httpClient = new DefaultHttpClient()

      val proxy = new HttpHost("127.0.0.1", port)
      httpClient.getParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy)

      val host = new HttpHost("www.google.com", 80)
      val get: HttpGet = new HttpGet("/")
      val response = httpClient.execute(host, get)
      
      response.getStatusLine.getStatusCode must beEqualTo(768)
    }

    "Stop" in {
      proxy.isRunning must beEqualTo(true)

      proxy.stop()

      proxy.isRunning must beEqualTo(false)
    }
  }
}
