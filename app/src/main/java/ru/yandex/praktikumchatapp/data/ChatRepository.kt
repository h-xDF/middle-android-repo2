package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.microseconds

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {
    companion object {
        private val DELAY_FACTOR = 2
        private val INITIAL_DELAY = 500 //ms
        private val MAX_ATTEMPT = 3
    }

    fun getReplyMessage(): Flow<String> {
        var tempDelay = INITIAL_DELAY
        return api.getReply().retryWhen {_,attempt->
            delay(tempDelay.microseconds)
            tempDelay *= DELAY_FACTOR
            attempt < MAX_ATTEMPT
        }
    }
}