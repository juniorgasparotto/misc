package cardmanager.ciandt.com.cardmanager.presentation.login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.infrastructure.LoadingDialogFragment;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainActivity;
import cardmanager.ciandt.com.cardmanager.presentation.register.RegisterActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;
    private EditText mTxtLoginName;
    private EditText mTxtLoginPwd;
    private Button mBtnLogin;
    private Button mBtnNewRegister;
    private LoadingDialogFragment mLoadingDialog;
    private final String DIALOG_LOADING_TAG = "loading_tag";

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mTxtLoginName = (EditText)root.findViewById(R.id.txt_login_name);
        mTxtLoginPwd = (EditText)root.findViewById(R.id.txt_login_pwd);
        mBtnLogin = (Button)root.findViewById(R.id.btn_login);
        mBtnNewRegister = (Button)root.findViewById(R.id.btn_new_register);

        mTxtLoginName.setText("wp@gmail.com");
        mTxtLoginPwd.setText("123456");

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.doLogin(mTxtLoginName.getText().toString(), mTxtLoginPwd.getText().toString());
            }
        });

        mBtnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.doNewRegister(mTxtLoginName.getText().toString());
            }
        });

        new LoginPresenter(this.getContext(), this);

        return root;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showErrorEmail(int resId) {
        mTxtLoginName.setError(this.getContext().getString(resId));
        mTxtLoginName.requestFocus();
    }

    @Override
    public void showErrorPwd(int resId) {
        mTxtLoginPwd.setError(this.getContext().getString(resId));
        mTxtLoginPwd.requestFocus();
    }

    @Override
    public void showLoading() {
        //mLoadingDialog = ProgressDialog.show(this.getContext(), "assa", this.getString(R.string.loading_text), true);
        mLoadingDialog = new LoadingDialogFragment();
        mLoadingDialog.show(this.getFragmentManager(), DIALOG_LOADING_TAG);
    }

    @Override
    public void hideLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showDialogError(String message) {
        Utils.showDialogError(this.getContext(), message);
    }

    @Override
    public void showDefaultDialogError(String message) {
        Utils.showDefaultDialogError(this.getContext(), message);
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void openNewRegisterActivity() {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        String login = mTxtLoginName.getText().toString();
        intent.putExtra("email", login);
        startActivity(intent);
    }
}
