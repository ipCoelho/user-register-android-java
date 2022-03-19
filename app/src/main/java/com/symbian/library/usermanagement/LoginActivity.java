package com.symbian.library.usermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.symbian.library.usermanagement.database.SQLHelper;

public class LoginActivity extends AppCompatActivity {
//    Properties.
    private EditText loginText;
    private EditText passwordText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.defaultColor));

        loginText = findViewById(R.id.edit_text_login);
        passwordText = findViewById(R.id.edit_text_password);
        loginButton = findViewById(R.id.button_entrar);
        registerButton = findViewById(R.id.button_cadastrarse);

        registerButton.setOnClickListener(view -> {
            Intent userRegisterActivity = new Intent(LoginActivity.this, UserRegisterActivity.class);
            startActivity(userRegisterActivity);
        });

        loginButton.setOnClickListener(view -> {
            String login = loginText.getText().toString();
            String password = loginText.getText().toString();

            if (!validate()) {
                int cod_usuario = SQLHelper.getInstance(this).startLogin(login, password);

                if (cod_usuario > 0) startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                else Toast.makeText(LoginActivity.this, "Usuário ou Senha inválidos!", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        });

    }

    public boolean validate() {
        return (loginText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty());
    }
}