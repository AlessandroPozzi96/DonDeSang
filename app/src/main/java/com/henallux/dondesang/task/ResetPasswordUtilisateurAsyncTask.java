package com.henallux.dondesang.task;

import android.content.Context;
import android.widget.Toast;

import com.henallux.dondesang.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordUtilisateurAsyncTask implements Callback<Void> {
    private Context context;

    public ResetPasswordUtilisateurAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (context == null)
            return;
        if (!response.isSuccessful()) {
            Toast.makeText(context, context.getResources().getString(R.string.mail_notFound), Toast.LENGTH_LONG).show();
            return;
        }
        if (response.code() == 200) {
            Toast.makeText(context, context.getResources().getString(R.string.password_reset_ok), Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(context, context.getResources().getString(R.string.mail_notFound), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        if (context != null) {
            Toast.makeText(context, context.getResources().getString(R.string.mail_notFound), Toast.LENGTH_SHORT).show();
        }
    }
}
