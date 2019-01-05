package com.henallux.dondesang.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.henallux.dondesang.Constants;
import com.henallux.dondesang.IMyListener;
import com.henallux.dondesang.R;
import com.henallux.dondesang.model.Statistique;
import com.henallux.dondesang.model.Token;
import com.henallux.dondesang.model.Utilisateur;
import com.henallux.dondesang.services.ServiceBuilder;
import com.henallux.dondesang.services.StatistiqueService;
import com.henallux.dondesang.task.LoadStatistiqueAsyncTask;

public class StatistiqueFragment extends Fragment {
    private View view;
    private Utilisateur utilisateur;
    private Token token;
    private IMyListener myListener;
    private TextView nbDons, nbViesSauvees, nbUtilisateurs, nbDonsTot, nbCollectes;
    private EditText edit_NbDons, edit_NbViesSauvees, edit_NbUtilisateurs, edit_NbCollectes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myListener = (IMyListener) getActivity();
        utilisateur = myListener.getUtilisateur();
        token = myListener.getToken();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistique, container, false);

        nbDons = view.findViewById(R.id.textView_NbDons);
        nbViesSauvees = view.findViewById(R.id.textView_NbViesSauvees);
        nbDonsTot = view.findViewById(R.id.textView_NbDonsTot);
        nbCollectes = view.findViewById(R.id.textView_NbCollectesTot);
        nbUtilisateurs = view.findViewById(R.id.textView_NbUtilisateurs);

        edit_NbCollectes = view.findViewById(R.id.editText_NbCollectes);
        edit_NbCollectes.setEnabled(false);
        edit_NbDons = view.findViewById(R.id.editText_NbDons);
        edit_NbDons.setEnabled(false);
        edit_NbViesSauvees = view.findViewById(R.id.editText_NbViesSauvees);
        edit_NbViesSauvees.setEnabled(false);
        edit_NbUtilisateurs = view.findViewById(R.id.editText_NbUtilisateurs);
        edit_NbUtilisateurs.setEnabled(false);

        StatistiqueService statistiqueService = ServiceBuilder.buildService(StatistiqueService.class);
        retrofit2.Call<Statistique> statistiqueCall = statistiqueService.getStatistique("Bearer " + token.getAccess_token(), utilisateur.getLogin());
        Log.d(Constants.TAG_GENERAL, "Login : " + utilisateur.getLogin() + " \n token : " + token.getAccess_token());
        statistiqueCall.enqueue(new LoadStatistiqueAsyncTask(getContext(), edit_NbDons, edit_NbViesSauvees, edit_NbUtilisateurs, edit_NbCollectes, nbDonsTot));

        return view;
    }
}
