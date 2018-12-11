package com.henallux.dondesang.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.Util;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.fragment.ProfileFragment;
import com.henallux.dondesang.model.Login;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;

public class GetTokenFromApiAsyncTask extends AsyncTask<String, Void, Token> {

        ApiAuthentification apiAuthentification;
        String erreurMessage;
        Activity activity;
        Context context;
        FragmentManager fragmentManager;
        Utilisateur utilisateur;
        public GetTokenFromApiAsyncTask(Utilisateur utilisateur, Activity activity, Context context,FragmentManager fragmentManager)
        {
            this.utilisateur=utilisateur;
            this.apiAuthentification = new ApiAuthentification(utilisateur);
            this.activity = activity;
            this.context=context;
            this.fragmentManager = fragmentManager;
        }

        @Override
        protected Token doInBackground(String... strings) {
            Token token;
            try {
                token = apiAuthentification.getToken();
                return token;
            } catch (Exception e) {
                erreurMessage = e.getMessage();
                return null;
            } catch (ErreurConnectionException e) {
                erreurMessage = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Token result) {
            if (result != null) {
                IMyListener myListener = (IMyListener) activity;
                myListener.setToken(result);

                myListener.setUtilisateur(utilisateur);

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container,profileFragment,"replaceFragmentByRegisterFragment");
                transaction.commit();
            } else {
                Toast.makeText(context,"Erreur :"+erreurMessage, Toast.LENGTH_LONG).show();
            }
        }
    }
