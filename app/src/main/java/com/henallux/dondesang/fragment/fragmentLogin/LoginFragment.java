package com.henallux.dondesang.fragment.fragmentLogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.AuthenticationService;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.UtilisateurService;
import com.henallux.dondesang.task.GetTokenFromApiAsyncTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    FragmentManager fragmentManager;

    Button buttonSeConnecter;
    Button loginButtonMDPOublier;
    TextView editUserName;
    TextView editPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonSeConnecter = getView().findViewById(R.id.loginButtonSeConnecter);
        loginButtonMDPOublier = getView().findViewById(R.id.loginButtonMDPOublier);
        editUserName = getView().findViewById(R.id.loginEditUserName);
        editPassword = getView().findViewById(R.id.loginEditPassword);
        buttonSeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificationDonnees()) {

                    final Login login = new Login(editUserName.getText().toString(),editPassword.getText().toString());
                    AuthenticationService authenticationService = ServiceBuilder.buildService(AuthenticationService.class);
                    final Call<Token> request = authenticationService.getToken(login);
                    request.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            Log.i("tag", response.toString());

                            if(response.code()==200)
                            {

                            Token token = response.body(); // token récupérer

                            Gson gson = new Gson();
                            String tokenJSON = gson.toJson(token, Token.class);

                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("tokenAccessJSONString", tokenJSON);
                            editor.commit();

                                ((IMyListener)getActivity()).setToken(token);

                            //Recuperer son profil
                            UtilisateurService utilisateurService = ServiceBuilder.buildService(UtilisateurService.class);
                            Call<Utilisateur> requete = utilisateurService.getUtilisateur("Bearer "+token.getAccess_token(), login.getLogin());
                            requete.enqueue(new Callback<Utilisateur>() {
                                @Override
                                public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                                    if (response.isSuccessful()) {
                                        Utilisateur utilisateur = response.body();

                                        Gson gson = new Gson();
                                        String utilisateurJSON = gson.toJson(utilisateur, Utilisateur.class);

                                        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("utilisateurJSONString", utilisateurJSON);
                                        editor.commit();

                                        ((IMyListener)getActivity()).setUtilisateur(utilisateur);

                                        ProfileFragment profileFragment = new ProfileFragment();
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                        transaction.replace(R.id.fragment_container, profileFragment, "replaceFragmentByRegisterFragment");
                                        transaction.commit();
                                    }else{
                                        Toast.makeText(getContext(),response.message()+"non",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Utilisateur> call, Throwable t) {

                                }
                            });
                            //
                        }
                    }
                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {

                        }
                    });
                    } else {
                    Toast.makeText(getActivity(), "Mauvais info", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginButtonMDPOublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MotDePasseOublieFragment motDePasseOublieFragment = new MotDePasseOublieFragment();
                FragmentManager fragmentManager;
                fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container, motDePasseOublieFragment, "replaceFragmentByMotDePasseOublieFragment");
                transaction.addToBackStack("MotDePasseOublieFragment");
                transaction.commit();
            }
        });
    }

    public boolean verificationDonnees() {
        // return (verificationLogin() && verificationPassword()); Exécute pas les 2 sinons
        boolean loginOk = verificationLogin();
        boolean passwordOk = verificationPassword();
        return loginOk && passwordOk;
    }

    public boolean verificationLogin() {
        if (Util.verificationLoginLongeur(editUserName)) {
            return Util.verificationLoginDisponible(editUserName);
        } else {
            return false;
        }
    }

    public boolean verificationPassword() {
        return Util.verificationPassword(editPassword);
    }

}
