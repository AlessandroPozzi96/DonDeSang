package com.henallux.dondesang.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.henallux.dondesang.Constants;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.fragment.fragmentLogin.LoginFragment;
import com.henallux.dondesang.model.Imagepromotion;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ImagepromotionService;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;
import com.henallux.dondesang.task.GetImagespromoAsyncTask;
import com.henallux.dondesang.task.LoadScoreAsyncTask;
import com.henallux.dondesang.task.ShareImageAsyncTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreFragment extends Fragment {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ImageView imageToShare;
    Button buttonSharePhoto;
    Button buttonSeLoger;
    ProgressBar progressBar;
    TextView textViewVosPoints;
    TextView textViewPartagerImage;
    IMyListener myListener;
    Utilisateur utilisateur;
    Token token;
    Gson gson;


    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if(shareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myListener =  (IMyListener) getActivity();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        if (myListener.getUtilisateur() == null || myListener.getToken() == null) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

            String utilisateurJSONString = sharedPref.getString("utilisateurJSONString",null);
            String tokenAccessJSONString = sharedPref.getString("tokenAccessJSONString",null);
            Gson gson = new Gson();

            if(utilisateurJSONString != null){
                utilisateur = gson.fromJson(utilisateurJSONString,Utilisateur.class);
            }

            if(tokenAccessJSONString != null){
                token = gson.fromJson(tokenAccessJSONString,Token.class);
            }
        }
        else
        {
            utilisateur = myListener.getUtilisateur();
            token = myListener.getToken();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialisationVue();


        ImagepromotionService imagepromotionService = ServiceBuilder.buildService(ImagepromotionService.class);
        Call<List<Imagepromotion>> listCall = imagepromotionService.getImagesPromotions();
        listCall.enqueue(new GetImagespromoAsyncTask(getActivity(), imageToShare,null));


        buttonSharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new ShareImageAsyncTask(utilisateur, token, textViewVosPoints, progressBar, getActivity()));

                ImagepromotionService imagepromotionService = ServiceBuilder.buildService(ImagepromotionService.class);
                Call<List<Imagepromotion>> listCall = imagepromotionService.getImagesPromotions();
                listCall.enqueue(new GetImagespromoAsyncTask(getActivity(), null,target));
                }
        });

        buttonSeLoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnregistrementFragment enregistrementFragment = new EnregistrementFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,enregistrementFragment,"replaceFragmentByLoginFragment");
                transaction.commit();
            }
        });


        if(utilisateur == null)
        {
            textViewVosPoints.setText(R.string.connexion_voir_points);
            textViewPartagerImage.setText(R.string.connexion_partage_gagner_points);
            buttonSharePhoto.setEnabled(false);
        }
        else {
            UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
            Call<Utilisateur> utilisateurCall = utilisateurService.getUtilisateur("Bearer " + token.getAccess_token(), utilisateur.getLogin());
            utilisateurCall.enqueue(new LoadScoreAsyncTask(getContext(), utilisateur, textViewVosPoints, progressBar));
            textViewVosPoints.setText(utilisateur.getScore()+ " " + getResources().getString(R.string.points));
            progressBar.setMax(1000);
            progressBar.setProgress(utilisateur.getScore());
            buttonSeLoger.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initialisationVue() {
        imageToShare = getView().findViewById(R.id.imageToShare);
        progressBar = getView().findViewById(R.id.progressBar);
        textViewVosPoints = getView().findViewById(R.id.textViewVosPoints);
        textViewPartagerImage = getView().findViewById(R.id.textViewPartagerImage);
        buttonSharePhoto = getView().findViewById(R.id.buttonSharePhoto);
        buttonSeLoger = getView().findViewById(R.id.buttonSeLoger);
    }

}