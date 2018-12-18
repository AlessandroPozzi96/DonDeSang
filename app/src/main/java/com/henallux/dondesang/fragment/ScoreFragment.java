package com.henallux.dondesang.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
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
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.fragment.fragmentLogin.EnregistrementFragment;
import com.henallux.dondesang.fragment.fragmentLogin.LoginFragment;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;
import com.henallux.dondesang.task.changerLesDonneesAsyncTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    com.facebook.share.widget.ShareButton fb_share_button;
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
        utilisateur = myListener.getUtilisateur();
        FacebookSdk.sdkInitialize(getActivity().getBaseContext());
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
        Picasso.with(getContext()).load("https://fr.cdn.v5.futura-sciences.com/buildsv6/images/mediumoriginal/1/2/e/12eac4fff4_82618_panda.jpg").into(imageToShare);
        buttonSharePhoto = getView().findViewById(R.id.buttonSharePhoto);

        // INIT FB
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());

        buttonSharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {

                        // augmenter le score
                        //new changerLesDonneesAsyncTask(utilisateur,getActivity()).execute();
                        utilisateur.setScore(utilisateur.getScore()+50);
                        UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
                        Call<Utilisateur> requete = utilisateurService.putUtilisateur(token.getAccess_token(),utilisateur.getLogin(),utilisateur);
                        requete.enqueue(new Callback<Utilisateur>() {
                            @Override
                            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(getContext(),"Vous avez gagné 50 points",Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getContext(),"erreur dans le put",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Utilisateur> call, Throwable t) {
                                Toast.makeText(getContext(),"erreur dans le partage",Toast.LENGTH_LONG).show();
                            }
                        });

                        Toast.makeText(getActivity(),"share succes",Toast.LENGTH_LONG).show();
                        Log.i("tag","sucess");
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(),"share Cancel",Toast.LENGTH_LONG).show();
                        Log.i("tag","cancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Log.i("tag","error");

                    }
                });

                Picasso.with(getContext()).load("https://fr.cdn.v5.futura-sciences.com/buildsv6/images/mediumoriginal/1/2/e/12eac4fff4_82618_panda.jpg").into(target);
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
            textViewVosPoints.setText("Connecter vous pour voir vos points");
            textViewPartagerImage.setText("Connecter vous pour partager l'image et gagner des points !");
            buttonSharePhoto.setEnabled(false);
        }
        else {
            textViewVosPoints.setText(utilisateur.getScore()+" points");
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
        fb_share_button = getView().findViewById(R.id.fb_share_button);
    }
}