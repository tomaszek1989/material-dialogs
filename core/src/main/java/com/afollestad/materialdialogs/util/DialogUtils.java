package com.afollestad.materialdialogs.util;

/** @author Aidan Follestad (afollestad) */
public class DialogUtils {

  //    @SuppressWarnings("ConstantConditions")
  //    public static float resolveFloat(Context context, int attr) {
  //        TypedArray a = context.obtainStyledAttributes(null, new int[]{attr});
  //        try {
  //            return a.getFloat(0, 0);
  //        } finally {
  //            a.recycle();
  //        }
  //    }

  // @ColorInt
  // public static int getDisabledColor(Context context) {
  //  final int primaryColor = resolveColor(context, android.R.attr.textColorPrimary);
  //  final int disabledColor = isColorDark(primaryColor) ? Color.BLACK : Color.WHITE;
  //  return adjustAlpha(disabledColor, 0.3f);
  // }

  // Try to resolve the colorAttr attribute.
  // public static ColorStateList resolveActionTextColorStateList(
  //    Context context, @AttrRes int colorAttr, ColorStateList fallback) {
  //  TypedArray a = context.getTheme().obtainStyledAttributes(new int[] { colorAttr });
  //  try {
  //    final TypedValue value = a.peekValue(0);
  //    if (value == null) {
  //      return fallback;
  //    }
  //    if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
  //        && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
  //      return getActionTextStateList(context, value.data);
  //    } else {
  //      final ColorStateList stateList = a.getColorStateList(0);
  //      if (stateList != null) {
  //        return stateList;
  //      } else {
  //        return fallback;
  //      }
  //    }
  //  } finally {
  //    a.recycle();
  //  }
  // }

  // Get the specified color resource, creating a ColorStateList if the resource
  // points to a color value.
  // public static ColorStateList getActionTextColorStateList(Context context, @ColorRes int
  // colorId) {
  //  final TypedValue value = new TypedValue();
  //  context.getResources().getValue(colorId, value, true);
  //  if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
  //      && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
  //    return getActionTextStateList(context, value.data);
  //  } else {
  //
  //    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
  //      //noinspection deprecation
  //      return context.getResources().getColorStateList(colorId);
  //    } else {
  //      return context.getColorStateList(colorId);
  //    }
  //  }
  // }

  // public static void showKeyboard(final DialogInterface di) {
  //  final MaterialDialog dialog = (MaterialDialog) di;
  //  if (dialog.getInputEditText() == null) {
  //    return;
  //  }
  //  dialog
  //      .getInputEditText()
  //      .post(
  //          new Runnable() {
  //            @Override
  //            public void run() {
  //              dialog.getInputEditText().requestFocus();
  //              InputMethodManager imm =
  //                  (InputMethodManager)
  //                      dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
  //              if (imm != null) {
  //                imm.showSoftInput(dialog.getInputEditText(), InputMethodManager.SHOW_IMPLICIT);
  //              }
  //            }
  //          });
  // }

  // public static void hideKeyboard(final DialogInterface di) {
  //  final MaterialDialog dialog = (MaterialDialog) di;
  //  if (dialog.getInputEditText() == null) {
  //    return;
  //  }
  //  InputMethodManager imm =
  //      (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
  //  if (imm != null) {
  //    final View currentFocus = dialog.getCurrentFocus();
  //    IBinder windowToken = null;
  //    if (currentFocus != null) {
  //      windowToken = currentFocus.getWindowToken();
  //    } else if (dialog.getView() != null) {
  //      windowToken = dialog.getView().getWindowToken();
  //    }
  //    if (windowToken != null) {
  //      imm.hideSoftInputFromWindow(windowToken, 0);
  //    }
  //  }
  // }

  // public static ColorStateList getActionTextStateList(Context context, int newPrimaryColor) {
  //  final int fallBackButtonColor =
  //      DialogUtils.resolveColor(context, android.R.attr.textColorPrimary);
  //  if (newPrimaryColor == 0) {
  //    newPrimaryColor = fallBackButtonColor;
  //  }
  //  int[][] states =
  //      new int[][] {
  //          new int[] { -android.R.attr.state_enabled }, // disabled
  //          new int[] {} // enabled
  //      };
  //  int[] colors = new int[] { DialogUtils.adjustAlpha(newPrimaryColor, 0.4f), newPrimaryColor };
  //  return new ColorStateList(states, colors);
  // }

  // public static int[] getColorArray(Context context, @ArrayRes int array) {
  //  if (array == 0) {
  //    return null;
  //  }
  //  TypedArray ta = context.getResources().obtainTypedArray(array);
  //  int[] colors = new int[ta.length()];
  //  for (int i = 0; i < ta.length(); i++) {
  //    colors[i] = ta.getColor(i, 0);
  //  }
  //  ta.recycle();
  //  return colors;
  // }
}
