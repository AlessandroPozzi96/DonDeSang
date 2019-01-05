package com.henallux.dondesang.task;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.model.Imagepromotion;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetImagespromoAsyncTask implements Callback<List<Imagepromotion>> {
    private Context context;
    private String urlDefaut = "https://image.noelshack.com/fichiers/2019/01/5/1546591486-48270691-733625447024877-3505170836589903872-n.jpg";
    private ImageView imageView;

    public GetImagespromoAsyncTask(FragmentActivity context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    public void onResponse(Call<List<Imagepromotion>> call, Response<List<Imagepromotion>> response) {
        if (context == null || imageView == null)
            return;
        if (!response.isSuccessful()) {
            Picasso.with(context).load(urlDefaut).into(imageView);
            Toast.makeText(this.context, Constants.MSG_ERREUR_CHARGEMENT_IMAGES, Toast.LENGTH_LONG).show();
            return;
        }

        String url = response.body().get(response.body().size() - 1).getUrl();
        if (url == null)
            url = urlDefaut;
        Picasso.with(context)
                .load(url)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, Constants.MSG_ERREUR_CHARGEMENT_IMAGES, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onFailure(Call<List<Imagepromotion>> call, Throwable t) {
        if (context == null || imageView == null)
                return;
        Picasso.with(context).load(urlDefaut).into(imageView);
        Toast.makeText(this.context, Constants.MSG_ERREUR_CHARGEMENT_IMAGES, Toast.LENGTH_LONG).show();
    }

}
