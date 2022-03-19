package com.symbian.library.usermanagement;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.symbian.library.usermanagement.database.SQLHelper;

public class UserRegisterActivity extends AppCompatActivity {
//    Properties.
    private EditText loginText;
    private EditText passwordText;
    private EditText nameText;
    private EditText lastnameText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_activity);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.defaultColor));

        nameText = findViewById(R.id.edit_text_nome);
        lastnameText = findViewById(R.id.edit_text_sobrenome);
        loginText = findViewById(R.id.edit_text_login_cadastro);
        passwordText = findViewById(R.id.edit_text_password_cadastro);
        registerButton = findViewById(R.id.button_cadastrar_cadastro);

        registerButton.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(UserRegisterActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Confirmar")
                    .setMessage("Seus dados estão corretos?")
                    .setPositiveButton("Sim!",(dialog1, wich) -> {
                        String  name = nameText.getText().toString();
                        String  lastName = lastnameText.getText().toString();
                        String  login = loginText.getText().toString();
                        String  password = passwordText.getText().toString();
                        int registerUser = SQLHelper.getInstance(this).addUser(name, lastName, login, password);

                        if (registerUser > 0) {
                            Intent addressActivity = new Intent(UserRegisterActivity.this, AddressRegisterActivity.class);
                            addressActivity.putExtra("cod_usuario", registerUser);
                            startActivity(addressActivity);
                        }
                        else Toast.makeText(UserRegisterActivity.this, "Erro ao cadastrar-se!", Toast.LENGTH_LONG).show();
                    }).setNegativeButton("Revisar", (dialog1, wich) -> {}).create();
                    dialog.show();
            }

        });
    }

    public boolean validate() {
        return (!loginText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty() && !nameText.getText().toString().isEmpty() && !lastnameText.getText().toString().isEmpty());
    }
}
