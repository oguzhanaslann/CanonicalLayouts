package com.oguzhanaslann.canonicalayouts

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize


val people = listOf(
    Person("Abongile Malinga", R.drawable.person_1),
    Person("Edy Daud", R.drawable.person_2),
    Person("Lucia Caldas", R.drawable.person_3),
)

@Parcelize
class Person(
    val personNane: String,
    @DrawableRes val imageRes: Int
) : Parcelable