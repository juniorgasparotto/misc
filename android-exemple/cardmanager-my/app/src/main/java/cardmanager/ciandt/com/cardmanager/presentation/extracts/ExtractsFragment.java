package cardmanager.ciandt.com.cardmanager.presentation.extracts;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.presentation.card.CardContract;
import cardmanager.ciandt.com.cardmanager.presentation.card.CardPresenter;

public class ExtractsFragment extends Fragment implements ExtractsContract.View {
    private ExtractsContract.Presenter mPresenter;

    private TextView mTxtCardNumber;
    private ImageView mImgCardFlag;
    private ListView mListExtracts;
    private View mHeader;
    private View mHeaderRelativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // default
        View rootView = inflater.inflate(R.layout.fragment_extracts, container, false);

        // create presenter
        new ExtractsPresenter(this.getContext(), this);

        // bind elements
        mHeader = (View)getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_extracts_header, null);
        mListExtracts = (ListView)rootView.findViewById(R.id.purchase_listview_extracts);
        mListExtracts.addHeaderView(mHeader, null, false);
        mHeaderRelativeLayout = (RelativeLayout)mHeader.findViewById(R.id.purchase_layout_header);
        mTxtCardNumber = (TextView)mHeaderRelativeLayout.findViewById(R.id.purchase_txt_card_number);
        mImgCardFlag = (ImageView)mHeaderRelativeLayout.findViewById(R.id.purchase_img_card_flag);

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
            Glide.with(this).load(Utils.buildImagesUriRepository(this.getContext(), card.flagImagePath)).into(mImgCardFlag);

            mTxtCardNumber.setText(card.number);

            GradientDrawable drawable = (GradientDrawable) mHeaderRelativeLayout.getBackground();

            int colorId = Color.parseColor("#000000");

            try {
                colorId = Color.parseColor(card.color);
            }
            catch (Exception ex) {

            }

            drawable.setColor(colorId);
            card.purchases.add(card.purchases.get(0));
            card.purchases.add(card.purchases.get(0));
            card.purchases.add(card.purchases.get(0));
            card.purchases.add(card.purchases.get(0));
            card.purchases.add(card.purchases.get(0));
            card.purchases.add(card.purchases.get(0));
            card.purchases.add(card.purchases.get(0));
            PurchaseAdapter adapter = new PurchaseAdapter(this.getContext(), R.layout.fragment_extracts_item, card.purchases);
            mListExtracts.setAdapter(adapter);
        }
    }

    @Override
    public void back() {

    }

    @Override
    public void setPresenter(ExtractsContract.Presenter presenter) {
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