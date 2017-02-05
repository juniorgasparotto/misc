package cardmanager.ciandt.com.cardmanager.infrastructure;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cardmanager.ciandt.com.cardmanager.R;

/**
 * Created by root on 12/08/16.
 */

public class LoadingDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_LoadingDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View root = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_loading, container, false);


        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        ImageView img = (ImageView)view.findViewById(R.id.img_loading);
        img.setBackgroundResource(R.drawable.animation_loading);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();

        return view;
    }
}
