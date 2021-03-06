package com.mathewsachin.fategrandautomata.prefs.core

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import com.tfcporciuncula.flow.FlowSharedPreferences
import com.tfcporciuncula.flow.Serializer
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PrefMaker(
    val prefs: SharedPreferences,
    val context: Context
) {
    val flowPrefs = FlowSharedPreferences(prefs)

    fun k(@StringRes key: Int) = context.getString(key)

    fun int(@StringRes key: Int, default: Int = 0) =
        Pref(flowPrefs.getInt(k(key), default))

    fun bool(@StringRes key: Int, default: Boolean = false) =
        Pref(flowPrefs.getBoolean(k(key), default))

    fun string(@StringRes key: Int, default: String = "") =
        Pref(flowPrefs.getString(k(key), default))

    private val stringAsIntSerializer =
        object : Serializer<Int> {
            override fun deserialize(serialized: String) = serialized.toInt()
            override fun serialize(value: Int) = value.toString()
        }

    fun stringAsInt(@StringRes key: Int, default: Int = 0) =
        Pref(flowPrefs.getObject(k(key), stringAsIntSerializer, default))

    inline fun <reified T : Enum<T>> enum(
        @StringRes key: Int,
        default: T
    ) = Pref(flowPrefs.getEnum(k(key), default))

    fun stringSet(@StringRes key: Int) =
        Pref(flowPrefs.getStringSet(k(key)))
}