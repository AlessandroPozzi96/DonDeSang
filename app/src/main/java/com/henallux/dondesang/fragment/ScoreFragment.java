package com.henallux.dondesang.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myListener =  (IMyListener) getActivity();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        utilisateur = myListener.getUtilisateur();
        token = myListener.getToken();
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
        listCall.enqueue(new GetImagespromoAsyncTask(getActivity(), imageToShare));

        buttonSharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {

                        //new changerLesDonneesAsyncTask(utilisateur,getActivity()).execute();

                        utilisateur.setScore(utilisateur.getScore()+ Constants.AJOUT_SCORE);
                        Log.i("tag",utilisateur.getScore()+" oui");
                        UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
                        Call<Utilisateur> requete = utilisateurService.putUtilisateur("Bearer "+token.getAccess_token(),utilisateur.getLogin(),utilisateur);
                        requete.enqueue(new Callback<Utilisateur>() {

                            @Override
                            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                                Log.i("tag",token.getAccess_token());
                                if(response.isSuccessful()){
                                    utilisateur = response.body();

                                    Toast.makeText(getContext(),R.string.gagner_50points,Toast.LENGTH_LONG).show();
                                    progressBar.setProgress(utilisateur.getScore());
                                    myListener.setUtilisateur(utilisateur);

                                    Gson gson = new Gson();
                                    String utilisateurJSON = gson.toJson(utilisateur, Utilisateur.class);
                                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("utilisateurJSONString", utilisateurJSON);
                                    editor.commit();

                                }else{
                                    Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Utilisateur> call, Throwable t) {
                                Toast.makeText(getContext(),R.string.erreur_partage,Toast.LENGTH_LONG).show();
                            }
                        });

                        Toast.makeText(getActivity(),R.string.reussite_partage,Toast.LENGTH_LONG).show();
                        Log.i("tag","sucess");
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(),R.string.annulation_partage,Toast.LENGTH_LONG).show();
                        Log.i("tag","cancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(),R.string.erreur_partage,Toast.LENGTH_LONG).show();
                        Log.i("tag","error : " + error.getMessage());

                    }
                });
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
            textViewVosPoints.setText(utilisateur.getScore()+ R.string.points);
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