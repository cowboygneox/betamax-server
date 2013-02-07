package com.gneoxsolutions.jobgrinder

import org.specs2.mutable.Specification
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.{HttpResponse, HttpHost}
import org.apache.http.conn.params.ConnRoutePNames
import org.apache.http.util.EntityUtils
import java.net.URI

class BetamaxProxyTest extends Specification {
  sequential
  stopOnFail

  "A betamax proxy" should {
    val port  = 3333
    val proxy = new BetamaxProxy("src/test/resources/betamax/tapes/test.yaml", port)

    val httpClient = new DefaultHttpClient()
    httpClient.getParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("127.0.0.1", port))

    def get[T](url: String)(block: HttpResponse => T) = {
      val uri: URI = new URI(url)

      val get: HttpGet = new HttpGet(uri)
      val response = httpClient.execute(get)
      val entity = response.getEntity

      try {
        block(response)
      } finally {
        EntityUtils.consume(entity)
      }
    }

    "Start on port %d".format(port) in {
      proxy.isRunning must beEqualTo(false)

      proxy.start()

      proxy.isRunning must beEqualTo(true)
      proxy.port must beEqualTo(port)
    }

    "Reload a response from Google" in {
      get("http://www.google.com/") { response =>
        response.getStatusLine.getStatusCode must beEqualTo(768)
      }
    }

    "Stop" in {
      proxy.isRunning must beEqualTo(true)

      proxy.stop()

      proxy.isRunning must beEqualTo(false)
    }
  }
}
