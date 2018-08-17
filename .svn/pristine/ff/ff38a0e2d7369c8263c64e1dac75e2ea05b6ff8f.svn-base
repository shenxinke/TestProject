package com.yst.onecity.activity.zxing.view;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * 回调结果
 *
 * @author luxuchang
 * @version 3.2.1
 * @date 2017/9/21
 */
public final class ViewfinderResultPointCallback implements ResultPointCallback {
  private final ViewfinderView viewfinderView;

  public ViewfinderResultPointCallback(ViewfinderView viewfinderView) {
    this.viewfinderView = viewfinderView;
  }

  @Override
  public void foundPossibleResultPoint(ResultPoint point) {
    viewfinderView.addPossibleResultPoint(point);
  }

}
