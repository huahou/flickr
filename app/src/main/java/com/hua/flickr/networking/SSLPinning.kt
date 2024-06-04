package com.hua.flickr.networking

import android.content.Context
import com.hua.flickr.R
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SSLPinning {
    fun getPinnedClient(context: Context): OkHttpClient {
        try {
            val cf = CertificateFactory.getInstance("X.509")
            val caInput = context.resources.openRawResource(R.raw.flickr)
            val ca: Certificate

            try {
                ca = cf.generateCertificate(caInput)
            } finally {
                caInput.close()
            }

            // Create a KeyStore containing our trusted CAs
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            keyStore.setCertificateEntry("ca", ca)

            // Create a TrustManager that trusts the CAs in our KeyStore
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)

            // Create an SSLContext that uses our TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, null)

            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslContext.socketFactory,
                tmf.trustManagers[0] as X509TrustManager
            )

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}