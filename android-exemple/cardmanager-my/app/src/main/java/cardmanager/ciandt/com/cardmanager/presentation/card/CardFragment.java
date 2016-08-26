package cardmanager.ciandt.com.cardmanager.presentation.card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;

public class CardFragment extends Fragment implements CardContract.View {
    private CardContract.Presenter mPresenter;
    private ImageView mCardImage;
    private TextView mTxtExpireDate;
    private TextView mTxtLimit;
    private TextView mTxtCurrentDebitValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // default
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        // create presenter
        new CardPresenter(this.getContext(), this);

        // binding
        mCardImage = (ImageView)rootView.findViewById(R.id.card_image);
        mTxtExpireDate = (TextView)rootView.findViewById(R.id.card_txt_expire_date);
        mTxtLimit = (TextView)rootView.findViewById(R.id.card_txt_limit);
        mTxtCurrentDebitValue = (TextView)rootView.findViewById(R.id.card_txt_current_debit_value);

        // load
        mPresenter.loadLoggedUser();

        return rootView;
    }

    @Override
    public void setUser(User user) {
        mPresenter.loadDefaultCard(user);
    }

    @Override
    public void setCard(Card card) {
        if (isAdded()) {
            Glide.with(this).load(Utils.buildImagesUriRepository(this.getContext(), card.cardImagePath)).into(mCardImage);
            mTxtExpireDate.setText(card.dueDate);
            mTxtLimit.setText(card.limitAvailable);
            mTxtCurrentDebitValue.setText(card.invoiceAmount);
        }
    }

    @Override
    public void back() {

    }

    @Override
    public void setPresenter(CardContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showDialogError(String message) {
        Utils.showDialogError(this.getContext(), message);
    }

    @Override
    public void showDefaultDialogError(String message) {
        Utils.showDefaultDialogError(this.getContext(), message);
    }
}