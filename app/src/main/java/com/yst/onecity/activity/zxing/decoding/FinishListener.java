package com.yst.onecity.activity.zxing.decoding;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * Simple listener used to exit the app in a few cases.
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21
 */
public final class FinishListener
    implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener, Runnable {

  private final Activity activityToFinish;

  public FinishListener(Activity activityToFinish) {
    this.activityToFinish = activityToFinish;
  }

  @Override
  public void onCancel(DialogInterface dialogInterface) {
    run();
  }

  @Override
  public void onClick(DialogInterface dialogInterface, int i) {
    run();
  }

  @Override
  public void run() {
    activityToFinish.finish();
  }

}
