package com.oguzhanaslann.canonicalayouts

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize


val people = listOf(
    Person("Abongile Malinga", R.drawable.person_1, "+351 935301963", "l.caldas@discoverquill.org"),
    Person("Edy Daud", R.drawable.person_2, "05 151 9476", "i.gallapeni@architectempire.com"),
    Person("Lucia Caldas", R.drawable.person_3, "9014655455", "r.eleftheriadis@greenbird.io")
)

@Parcelize
class Person(
    val personNane: String,
    @DrawableRes val imageRes: Int,
    val phone: String,
    val email: String
) : Parcelable