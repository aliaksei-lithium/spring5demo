package io.lithium.spring5demo.gitter.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = GitterUserDeserializer::class)
class GitterUser(var id: String?, var url: String?, var username: String?, var displayName: String?, var avatarUrl: String?,
                 var avatarUrlMedium: String?, var avatarUrlSmall: String?)
