Ranger
===============
[![](https://jitpack.io/v/enofeb/Ranger.svg)](https://jitpack.io/#enofeb/Ranger)
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

## Main Features
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
* You can disable indicator.
```kotlin
ranger.isIndicatorEnabled = false
```

## XML Attributes

```xml
<declare-styleable name="RangerView">
    <attr name="baseBarColor" format="color" />
    <attr name="subBarColor" format="color" />
    <attr name="circleColor" format="color" />
    <attr name="indicatorColor" format="color" />
    <attr name="indicatorTextColor" format="color" />
    <attr name="indicatorTextSize" format="dimension" />
    <attr name="barHeight" format="integer" />
    <attr name="circleRadius" />
    <attr name="indicatorRadius" format="integer" />
    <attr name="isIndicatorEnabled" format="boolean" />
</declare-styleable>
```

## Download
* Add Jitpack to your root `build.gradle` repositories.
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

* Add Ranger to your module dependencies.
```groovy
dependencies {
    implementation 'com.github.enofeb:Ranger:1.5'
}
```

## License
```
MIT License

Copyright (c) 2021 Enes Zor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```