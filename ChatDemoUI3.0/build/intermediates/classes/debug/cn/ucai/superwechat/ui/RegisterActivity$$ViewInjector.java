// Generated code from Butter Knife. Do not modify!
package cn.ucai.superwechat.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class RegisterActivity$$ViewInjector {
  public static void inject(Finder finder, final cn.ucai.superwechat.ui.RegisterActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624203, "field 'userNameEditText'");
    target.userNameEditText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624237, "field 'userNickEditText'");
    target.userNickEditText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624207, "field 'passwordEditText'");
    target.passwordEditText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624240, "field 'confirmPwdEditText'");
    target.confirmPwdEditText = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131624234, "field 'registerLayoutBack' and method 'onClick'");
    target.registerLayoutBack = (android.widget.LinearLayout) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131624241, "field 'register' and method 'onClick'");
    target.register = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  public static void reset(cn.ucai.superwechat.ui.RegisterActivity target) {
    target.userNameEditText = null;
    target.userNickEditText = null;
    target.passwordEditText = null;
    target.confirmPwdEditText = null;
    target.registerLayoutBack = null;
    target.register = null;
  }
}
