// Generated code from Butter Knife. Do not modify!
package cn.ucai.superwechat.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class LoginActivity$$ViewInjector {
  public static void inject(Finder finder, final cn.ucai.superwechat.ui.LoginActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624199, "field 'loginLayoutBack' and method 'onClick'");
    target.loginLayoutBack = (android.widget.LinearLayout) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  public static void reset(cn.ucai.superwechat.ui.LoginActivity target) {
    target.loginLayoutBack = null;
  }
}
