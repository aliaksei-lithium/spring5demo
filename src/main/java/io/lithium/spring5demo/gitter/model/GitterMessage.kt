package io.lithium.spring5demo.gitter.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

import java.time.Instant

@JsonDeserialize(using = GitterMessageDeserializer::class)
class GitterMessage(var id: String?, var text: String?, var html: String?, var fromUser: GitterUser?, var sent: Instant?)