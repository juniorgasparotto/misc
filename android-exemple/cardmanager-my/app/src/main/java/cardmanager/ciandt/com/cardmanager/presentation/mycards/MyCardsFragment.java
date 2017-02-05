package cardmanager.ciandt.com.cardmanager.presentation.mycards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cardmanager.ciandt.com.cardmanager.R;
import cardmanager.ciandt.com.cardmanager.data.model.Card;
import cardmanager.ciandt.com.cardmanager.data.model.User;
import cardmanager.ciandt.com.cardmanager.infrastructure.Utils;
import cardmanager.ciandt.com.cardmanager.presentation.card.CardContract;
import cardmanager.ciandt.com.cardmanager.presentation.main.MainContract;

public class MyCardsFragment extends Fragment implements MyCardsContract.View {
    private MyCardsContract.Presenter mPresenter;
    private LinearLayout mImagesWrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mycards, container, false);
        mImagesWrapper = (LinearLayout) rootView.findViewById(R.id.mycards_cards_wrapper);

        new MyCardsPresenter(this.getContext(), this);
        mPresenter.loadLoggedUser();

        return rootView;
    }

    @Override
    public void setUser(User user) {
        mPresenter.loadCards(user);
    }

    @Override
    public void setCards(ArrayList<Card> cards) {
        View.OnClickListener imageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setCardDefault((Card)view.getTag(view.getId()));
            }
        };

        for (Card card : cards) {
            ImageView image = new ImageView(getContext());
            image.setTag(image.getId(), card);
            image.setOnClickListener(imageClick);
            mImagesWrapper.addView(image);
            Glide.with(this).load(Utils.buildImagesUriRepository(this.getContext(), card.cardImagePath)).into(image);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(image.getLayoutParams());
            lp.setMargins(0, 0, 0, 80 * -1);
            image.setLayoutParams(lp);
        }
    }

    @Override
    public void setCard(Card card) {
        MainContract.View main = (MainContract.View)this.getContext();
        main.openHome();
    }

    @Override
    public void setPresenter(MyCardsContract.Presenter presenter) {
        mPresenter = presenter;
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

    @Override
    public void back() {

    }
}