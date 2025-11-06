package com.example.vocabgo.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class UserWalletState(
    val ruby: Int,
    val energy: Energy
)

@JsonClass(generateAdapter = true)
data class Energy(
    val current: Int,
    val max: Int,
    val regenTime: Int,
    val lastRegen: String
)
data class UserProfileResponse(
    @Json(name = "userProfile")
    val userProfile: UserProfile,

    @Json(name = "userWallet")
    val userWallet: UserWallet
)
@JsonClass(generateAdapter = true)
data class UserProfile(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "public_id")
    val publicId: String,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "birth_date")
    val birthDate: String? = null,

    @Json(name = "gender")
    val gender: String? = null,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "given_name")
    val givenName: String,

    @Json(name = "family_name")
    val familyName: String,

    @Json(name = "email")
    val email: String
)

@JsonClass(generateAdapter = true)
data class UserWallet(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "kp_points")
    val kpPoints: Int,

    @Json(name = "rubys")
    val rubys: Int,

    @Json(name = "energy")
    val energy: Int,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "energy_last_updated")
    val energyLastUpdated: String
)
