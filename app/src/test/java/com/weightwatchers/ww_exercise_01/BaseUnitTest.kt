package com.weightwatchers.ww_exercise_01

import com.weightwatchers.ww_exercise_01.data.WeightWatchersRepository
import com.weightwatchers.ww_exercise_01.data.api.NetworkFactory
import com.weightwatchers.ww_exercise_01.data.api.WeightWatchersApiService
import com.weightwatchers.ww_exercise_01.di.NetworkApi
import com.weightwatchers.ww_exercise_01.di.moshi
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import java.io.File


abstract class BaseUnitTest {

    private var mockServer: MockWebServer = MockWebServer()

    private var shouldStartServer = false

    private val retrofitWeightWatchersTestService: WeightWatchersApiService by lazy {
        val networkFactory = NetworkFactory(moshi)
        networkFactory.createApi(
                WeightWatchersApiService::class.java,
                mockServer.url("/").toUrl().toString()
        )
    }


    lateinit var repository: WeightWatchersRepository

    private var currentEndpoint: String = ""
    private var currentResponseCode: Int = 200
    private var currentResponseBody: String = ""


    @Before
    open fun setUp() {
        startMockServer()

        mockkObject(NetworkApi)
        every { NetworkApi.getRetrofit() } returns retrofitWeightWatchersTestService
        repository = WeightWatchersRepository(NetworkApi)

        val appContext = mockk<WeightWatchersApplication>()
        every { appContext.contentResolver } returns mockk()
        every { appContext.getString(R.string.general_error_message) } returns "Server error!"
        WeightWatchersApplication.weightWatchersAppContext = appContext
    }

    fun getJson(path: String): String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    private fun startMockServer() {
        if (!shouldStartServer) {
            shouldStartServer = true
            System.setProperty("javax.net.ssl.trustStoreType", "JKS")
            mockServer = MockWebServer()

            val dispatcher: Dispatcher = object : Dispatcher() {
                @Throws(InterruptedException::class)
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (request.path) {
                        currentEndpoint -> {
                            MockResponse().setResponseCode(currentResponseCode)
                                    .addHeader("Content-Type", "application/json; charset=utf-8")
                                    .setBody(currentResponseBody)
                        }
                        else -> MockResponse().setResponseCode(200)
                    }
                }
            }
            mockServer.dispatcher = dispatcher
            mockServer.start()
        }
    }

    fun setEndpointPath(path: String) {
        currentEndpoint = path
    }

    fun setMockResponseCode(code: Int) {
        currentResponseCode = code
    }

    fun setMockResponseBody(body: String) {
        currentResponseBody = body
    }

    /**
     * Stop Mock web server
     */
    private fun stopMockServer() {
        if (shouldStartServer) {
            mockServer.shutdown()
        }
    }

    @After
    open fun tearDown() {
        stopMockServer()
    }
}