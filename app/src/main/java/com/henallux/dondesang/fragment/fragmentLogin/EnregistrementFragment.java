package com.henallux.dondesang.fragment.fragmentLogin;

import android.content.Intent;
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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.henallux.dondesang.DataAcces.ApiAuthentification;
import com.henallux.dondesang.R;
import com.henallux.dondesang.exception.ErreurConnectionException;
import com.henallux.dondesang.model.Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class EnregistrementFragment extends Fragment {

    TextView txtStatus;
    LoginButton loginButtonFacebook;
    FragmentManager fragmentManager;
    Button buttonLogin;
    Button buttonRegister;

    String idFacebook;
    String emailFacebook;
    String birthdayFacebook;
    String nameFacbeook;
    CallbackManager callbackManager;
    /*
    !!!!!!!!!!!!
    Vous pouvez déterminer si quelqu’un est actuellement connecté en vérifiant AccessToken.getCurrentAccessToken() et Profile.getCurrentProfile().
     */


    /*
    Si vous ajoutez le bouton à un Fragment, vous devez également mettre à jour votre activité pour utiliser votre fragment. Vous pouvez personnaliser les propriétés de Login button et enregistrer un rappel dans votre méthode onCreate() ou onCreateView(). Les propriétés que vous pouvez personnaliser comprennent LoginBehavior, DefaultAudience, ToolTipPopup.Style et les autorisations associées à LoginButton. Par exemple :
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //transmettre les résultats de la demande de connexion à LoginManager via callbackManager.
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initializeControls()
    {
        // créez un callbackManager chargé de gérer les réponses aux demandes de connexion en appelant CallbackManager.Factory.create.
        callbackManager = CallbackManager.Factory.create();
        loginButtonFacebook = getView().findViewById(R.id.login_button);
        loginButtonFacebook.setFragment(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        return inflater.inflate(R.layout.fragment_enregistrement,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeControls();
        loginButtonFacebook.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));

        loginButtonFacebook.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // si succes loginResult contient le nouveau accesToken et les sont autorisations accordées ou refuses
                        loginDonSang(loginResult); // --> on créer un compte ou on se connecte au compte
                    }

                    @Override
                    public void onCancel() {
                        Log.i("tag","cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.i("tag","erreur");
                    }
                });

        buttonRegister = getView().findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container,registerFragment,"replaceFragmentByRegisterFragment");
                transaction.commit();
            }
        });

        buttonLogin = getView().findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.enregistrement_container,loginFragment,"replaceFragmentByLoginFragment");
                transaction.commit();
            }
        });



    }

    private void loginDonSang(LoginResult loginResult) {


        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("tag", response.toString());
                        Log.i("tag", object.toString());

                        // Application code
                        try {
                            emailFacebook = object.getString("email");
                            birthdayFacebook  = object.getString("birthday"); // 01/31/1980 format
                            idFacebook = object.getString("email");
                            nameFacbeook = object.getString("name");
                        }catch (JSONException jsE){}
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();

        Log.i("tag",request.getAccessToken().getToken());

        new getTokenFromAPI().execute();

    }

    private  class getTokenFromAPI extends AsyncTask<String,Void,Token>
    {

        protected void onPreExecute(){}

        @Override
        protected Token doInBackground(String... strings) {
            Token token;
            ApiAuthentification apiAuthentification = new ApiAuthentification("Gwynbleidd","MotDePasseNonHashé");
            try{
                token = apiAuthentification.getToken();
                return token;
            }
            catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                return null;
            } catch (ErreurConnectionException e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG);
                return  null;
            }
        }

        @Override
        protected void onPostExecute(Token result) {
            Log.i("tag",result.getAccess_token());
        }
        // log bien le token
    }
}