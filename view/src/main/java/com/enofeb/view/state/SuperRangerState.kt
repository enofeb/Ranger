package com.enofeb.view.state

import android.os.Parcelable
import android.view.View
import kotlinx.parcelize.Parcelize

@Parcelize
class SuperRangerState(val savedState: Parcelable?, val currentValue: Double?) :
    View.BaseSavedState(savedState),
    Parcelable