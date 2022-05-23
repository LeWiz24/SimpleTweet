
New Twitter icons taken from the [June 5, 2017 redesign](https://techcrunch.com/2017/06/15/twitter-tweaks-its-design-again-in-an-attempt-to-woo-newcomers/).

Since all the icons are mostly black, they require to be tinted to the Twitter colors (`android:tint`).  See http://guides.codepath.com/android/Drawables#applying-tints-to-drawables for more context.

Many icons include a selected and deselected versions (i.e. `ic_vector_retweet.xml` and `ic_vector_retweet_stroke.xml`).  You can use a state list drawable to define these two different states:

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true" android:drawable="@drawable/ic_vector_home"/>
    <item android:drawable="@drawable/ic_vector_home_stroke"/>
</selector>
```