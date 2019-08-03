# databinding extension
-keepclassmembers class * extends androidx.databinding.ViewDataBinding {
   public static *** inflate(...);
}