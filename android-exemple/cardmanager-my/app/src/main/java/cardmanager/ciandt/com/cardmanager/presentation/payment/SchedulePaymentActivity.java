package cardmanager.ciandt.com.cardmanager.presentation.payment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;

public class SchedulePaymentActivity extends AppCompatActivity implements SchedulePaymentContract.View {

    public static final String EXTRA_USER = "USER";
    private SchedulePaymentContract.Presenter mPresenter;
    private EditText mTxtAccountName;
    private EditText mTxtBarCode;
    private EditText mTxtDueDate;
    private Button mBtnSchedule;
    private Button mBtnOpenCam;
    public User mUser;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SchedulePaymentActivity mThis = this;
    private TimePickerDialog mTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_payment);

        // create presenter
        new SchedulePaymentPresenter(this, this);

        bindElements();
        configureToolBar();
        configureButtons();
        addListenersToEnableRegisterButtonIfValid();
        createValidationsOnLostFocus();
        setDateTimeField();

        mPresenter.loadLoggedUser();
    }

    @Override
    public void setUser(User user) {
        this.mUser = user;
    }

    private void bindElements() {
        mTxtAccountName = (EditText) this.findViewById(R.id.schedule_txt_account_name);
        mTxtDueDate = (EditText) this.findViewById(R.id.schedule_txt_account_date);
        mTxtBarCode = (EditText) this.findViewById(R.id.schedule_txt_account_code_barres);
        mBtnSchedule = (Button) this.findViewById(R.id.schedule_btn_schedule);
        mBtnOpenCam = (Button) this.findViewById(R.id.schedule_btn_open_cam);

        //mTxtAccountName.setText("ba");
        //mTxtDueDate.setText("12/12/1999 - 00:00");
        //mTxtBarCode.setText("1232");
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        final Calendar newDate = Calendar.getInstance();

        // Launch Time Picker Dialog
        mTimePickerDialog = new TimePickerDialog(mThis, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                mTxtDueDate.setText(Utils.formatDate(mThis, newDate.getTime(), R.string.schedule_format_date_and_time));
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
        
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                mTimePickerDialog.show();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void configureToolBar() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureButtons() {
        disableButton();
        mBtnOpenCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.doOpenCamera();
            }
        });
        mBtnSchedule.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String name = mTxtAccountName.getText().toString();
                String dueDate = mTxtDueDate.getText().toString();
                String barCode = mTxtBarCode.getText().toString();
                mPresenter.doSchedule(mUser, name, dueDate, barCode);
            }
        });
    }

    private void addListenersToEnableRegisterButtonIfValid() {
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

        mTxtAccountName.addTextChangedListener(textWatcher);
        mTxtBarCode.addTextChangedListener(textWatcher);
        mTxtDueDate.addTextChangedListener(textWatcher);
    }

    private void enableRegisterButtonIfValid() {
        String accountName = mTxtAccountName.getText().toString();
        String dueDate = mTxtDueDate.getText().toString();
        String barCode = mTxtBarCode.getText().toString();

        boolean isOK = true;
        if (!mPresenter.validateAccountName(accountName, false)) isOK = false;
        if (!mPresenter.validateDueDate(dueDate, false)) isOK = false;
        if (!mPresenter.validateBarCode(barCode, false)) isOK = false;
        if (isOK)
            enableButton();
        else
            disableButton();
    }

    private void createValidationsOnLostFocus() {
        // validate on lost focus
        mTxtAccountName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    if (mPresenter.validateAccountName(mTxtAccountName.getText().toString(), true))
                        mTxtAccountName.setError(null);

            }
        });

        mTxtDueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (mPresenter.validateDueDate(mTxtDueDate.getText().toString(), true))
                        mTxtDueDate.setError(null);
                }
                else {
                    fromDatePickerDialog.show();
                }
            }
        });

        mTxtBarCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b)
                    if (mPresenter.validateBarCode(mTxtBarCode.getText().toString(), true))
                        mTxtBarCode.setError(null);
            }
        });
    }


    private void enableButton() {
        mBtnSchedule.setAlpha(1f);
        mBtnSchedule.setEnabled(true);
    }

    private void disableButton() {
        mBtnSchedule.setEnabled(false);
        mBtnSchedule.setAlpha(.5f);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void setPresenter(SchedulePaymentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorAccountName(int resId) {
        mTxtAccountName.setError(getString(resId));
    }

    @Override
    public void showErrorDueDate(int resId) {
        mTxtDueDate.setError(getString(resId));
    }

    @Override
    public void showErrorBarCode(int resId) {
        mTxtBarCode.setError(getString(resId));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void openPayments() {
        finish();
    }

    @Override
    public void openCamera() {

    }

    @Override
    public void systemSchedule(long time) {
        SchedulePaymentNotify.setAlarm(this, time);
    }
}
