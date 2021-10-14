Ranger
===============
<a href="http://developer.android.com/index.html" target="_blank"><img src="https://img.shields.io/badge/platform-android-green.svg"/></a>
<a href="https://android-arsenal.com/api?level=15" target="_blank"><img src="https://img.shields.io/badge/API-19%2B-green.svg?style=flat"/></a>
<a href="http://opensource.org/licenses/MIT" target="_blank"><img src="https://img.shields.io/badge/License-MIT-blue.svg?style=flat"/></a>

Ranger is custom view which able to act like android seekbar.

# Sample GIF

<p align="center">
  <img src="https://github.com/enofeb/Ranger/blob/master/gif/ranger.gif">
</p>

# Simple Usage

```kotlin
<com.enofeb.view.RangerView
        android:id="@+id/ranger"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
You must give an min and max value after xml declaration.
```kotlin
ranger.minValue = 0.0
ranger.maxValue = 100.0
```

## Features
* You can set and get current value programmatically.
```kotlin
ranger.currentValue = 35.0
val lastValue = ranger.currentValue
```
* You can listen value change instantly.
```kotlin
ranger.onValueChangeListener = { lastValue->
  //no-op
}
```
