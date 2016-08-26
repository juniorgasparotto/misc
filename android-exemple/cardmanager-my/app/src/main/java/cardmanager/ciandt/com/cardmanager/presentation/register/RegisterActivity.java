package cardmanager.ciandt.com.cardmanager.presentation.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.infrastructure.LoadingDialogFragment;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainActivity;
import cardmanager.ciandt.com.cardmanager.presentation.terms.TermsActivity;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private EditText mTxtName;
    private EditText mTxtEmail;
    private EditText mTxtPhone;
    private EditText mTxtCardNumber;
    private Button mBtnOpenCam;
    private CheckBox mChkAcceptTerms;
    private Button mBtnRegister;
    private RegisterContract.Presenter mPresenter;
    private LoadingDialogFragment mLoadingDialog;
    private final String DIALOG_LOADING_TAG = "loading_tag";
    private final int SCAN_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // create presenter
        new RegisterPresenter(this, this);

        bindElements();
        configureToolBar();
        configureButtons();
        addListernersToEnableRegisterButtonIfValid();
        createValidationsOnLostFocus();
        configureTermsAndConditionals();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorName(int resId) {
        mTxtName.setError(getString(resId));
    }

    @Override
    public void showErrorEmail(int resId) {
        mTxtEmail.setError(getString(resId));
    }

    @Override
    public void showErrorPhoneNumber(int resId) {
        mTxtPhone.setError(getString(resId));
    }

    @Override
    public void showErrorCardNumber(int resId) {
        mTxtCardNumber.setError(getString(resId));
    }

    @Override
    public void showErrorTermsNotAccept(int resId) {
        mChkAcceptTerms.setError(getString(resId));
    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialogFragment();
        mLoadingDialog.show(this.getSupportFragmentManager(), DIALOG_LOADING_TAG);
    }

    @Override
    public void hideLoading() {
        mLoadingDialog.dismiss();;
    }

    @Override
    public void showDialogError(String message) {
        Utils.showDialogError(this, message);
    }

    @Override
    public void showDefaultDialogError(String message) {
        Utils.showDefaultDialogError(this, message);
    }

    @Override
    public void openCamera() {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.

        startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
    }

    @Override
    public void openUseTerms() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openLoginActivity() {
        onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                String cardNumber = scanResult.cardNumber;
                mTxtCardNumber.setText(cardNumber);
            }
            else {
                showErrorCardNumber(R.string.error_cant_read_can_number);
            }
        }
    }

    private void addListernersToEnableRegisterButtonIfValid() {
        // enable when all fields is ok
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableRegisterButtonIfValid();
            }
        };

        mTxtName.addTextChangedListener(textWatcher);
        mTxtEmail.addTextChangedListener(textWatcher);
        mTxtPhone.addTextChangedListener(textWatcher);
        mTxtCardNumber.addTextChangedListener(textWatcher);
        mChkAcceptTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                enableRegisterButtonIfValid();

                if (mPresenter.validateTerms(b, true))
                    mChkAcceptTerms.setError(null);
            }
        });
    }

    private void enableRegisterButtonIfValid() {
        String name = mTxtName.getText().toString();
        String email = mTxtEmail.getText().toString();
        String phone = mTxtPhone.getText().toString();
        String cardNumber = mTxtCardNumber.getText().toString();
        boolean isTermsAccepted = mChkAcceptTerms.isChecked();

        boolean isOK = true;
        if (!mPresenter.validateName(name, false)) isOK = false;
        if (!mPresenter.validateEmail(email, false)) isOK = false;
        if (!mPresenter.validatePhoneNumber(phone, false)) isOK = false;
        if (!mPresenter.validateCardNumber(cardNumber, false)) isOK = false;
        if (!mPresenter.validateTerms(isTermsAccepted, false)) isOK = false;
        if (isOK)
            enableButton();
        else
            disableButton();
    }

    private void createValidationsOnLostFocus() {
        // validate on lost focus
        mTxtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    if (mPresenter.validateName(mTxtName.getText().toString(), true))
                        mTxtName.setError(null);

            }
        });

        mTxtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    if (mPresenter.validateEmail(mTxtEmail.getText().toString(), true))
                        mTxtEmail.setError(null);
            }
        });

        mTxtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    if (mPresenter.validatePhoneNumber(mTxtPhone.getText().toString(), true))
                        mTxtPhone.setError(null);
            }
        });

        mTxtCardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    if (mPresenter.validateCardNumber(mTxtCardNumber.getText().toString(), true))
                        mTxtCardNumber.setError(null);
            }
        });
    }

    private void enableButton() {
        mBtnRegister.setAlpha(1f);
        mBtnRegister.setEnabled(true);
    }

    private void disableButton() {
        mBtnRegister.setEnabled(false);
        mBtnRegister.setAlpha(.5f);
    }

    private void configureTermsAndConditionals() {
        // terms and conditionals
        String termsText = getString(R.string.chk_accept_terms_text);
        String termsTextClickable = getString(R.string.chk_accept_terms_text_clicable);
        ClickableSpan termsClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                mPresenter.doOpenUseTerms();
                mChkAcceptTerms.setEnabled(true);
            }
        };
        Utils.createLinkToCheckBox(mChkAcceptTerms, termsText, termsTextClickable, termsClick);
    }

    private void bindElements() {
        mTxtName = (EditText)this.findViewById(R.id.txt_name);
        mTxtEmail = (EditText)this.findViewById(R.id.txt_email);
        mTxtPhone = (EditText)this.findViewById(R.id.txt_phone_number);
        mTxtCardNumber = (EditText) this.findViewById(R.id.txt_card_number);
        mBtnOpenCam = (Button)this.findViewById(R.id.btn_open_cam);
        mChkAcceptTerms = (CheckBox) this.findViewById(R.id.chk_accept_terms);
        mBtnRegister = (Button)this.findViewById(R.id.btn_register);
    }

    private void loadRequest() {
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        mTxtEmail.setText(email);
    }

    private void configureToolBar() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void configureButtons() {
        disableButton();
        mBtnOpenCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.doOpenCamera();
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mTxtName.getText().toString();
                String email = mTxtEmail.getText().toString();
                String phone = mTxtPhone.getText().toString();
                String cardNumber = mTxtCardNumber.getText().toString();
                boolean isTermsAccepted = mChkAcceptTerms.isChecked();
                mPresenter.doRegister(name, email, phone, cardNumber, isTermsAccepted);
            }
        });
    }
}
