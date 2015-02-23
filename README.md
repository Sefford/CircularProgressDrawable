CircularProgressDrawable
========================

A drawable with capabilities to indicate progress.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CircularProgressDrawable-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1071)

Why a drawable?
---------------

If you want to achieve interesting effects and animations on Android, a surprisingly easy and fast
way to achieve it is subclassing a Drawable instead of subclassing a Button or an ImageView.

In order to implement advanced behavior over the Drawable, you can still implement your own animations
by using Android animations framework or by the use of listeners on the views you are using.

Download
--------

### Bundle

CircularProgressDrawable comes bundled in `aar` format. Grab the latest bundle from [here](http://search.maven.org/remotecontent?filepath=com/sefford/circular-progress-drawable/1.3/circular-progress-drawable-1.3.aar)

### Maven

```XML
<dependency>
    <groupId>com.sefford</groupId>
    <artifactId>circular-progress-drawable</artifactId>
    <version>1.31</version>
    <type>aar</type>
</dependency>
```

### Gradle 

```groovy
compile 'com.sefford:circular-progress-drawable:1.31@aar'
```

Composition
-----------

The Circular Progress Drawable is made out of three differentiated components:

* The first one is what is known as the *inner circle*. The radius of the drawable and can be scaled up and down to achieve a progress effect or to serve a background for a TextView on top to indicate its meaning. 
* The next is the *outline ring*. This ring will surround the inner circle and it is intended as
 a subtle cue of the empty state of the progress.
* The last is the *outer ring*. It will be used for several purposes. The first is to show the progress
of the drawable. In the current version, the progress ring is filled from the bottom and counter-clockwise.

Usage
-----
Circular Progress Drawable can be defined programatically and the only parameters it will require
will be the different colours and the size of the outer ring.

Scale Property for the inner ring can be used to achieve a variety of effects, as pulsating effects, overshoot
or to make it disappear completely.

The drawable allows both for a progress and an indetermination mode. In the indetermination mode
instead of filling the outer ring, a 90º arc will be shown that can be animated to spin around the
inner circle.

![Overshoot Example](overshoot.gif) ![Colorfill Example](colorfill.gif)

Tips
----

* Take advantage of the Android Animators to achieve a variety of effects around Circular Progress Drawable.
* If you want more interesting feedback you can use *onTouchListeners* over your View. 
* Have a look at the sample for some ideas.

Proguard
--------

If you rely on Animators for animation and your application is obfuscated using Proguard, be advised
that your Circular Progress Drawable animations could stop working. In order to avoid that add
`-keepclassmembers class com.sefford.circularprogressdrawable.CircularProgressDrawable { *;}`
to your proguard.conf file.

License
-------
    Copyright 2014 Sefford.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




