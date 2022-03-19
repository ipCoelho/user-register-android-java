package com.symbian.library.usermanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.symbian.library.usermanagement.database.SQLHelper;

public class AddressRegisterActivity extends AppCompatActivity {
//    Properties.
    private EditText cepText;
    private EditText numText;
    private EditText complementText;
    private Button addressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.register_address_activity);
        Window window = getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.defaultColor));

        cepText = findViewById(R.id.edit_text_cep);
        complementText =findViewById(R.id.edit_text_complemento);
        numText = findViewById(R.id.edit_text_numero);
        addressButton = findViewById(R.id.button_cadastrar_salvar_endereco);

        addressButton.setOnClickListener(view -> {
            if(!validate()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Confirmar")
                    .setMessage("Seus dados estão corretos?")
                    .setPositiveButton("Sim!",(dialog1, wich) -> {
                String  cep = cepText.getText().toString();
                String  number = numText.getText().toString();
                String  complement = complementText.getText().toString();
                int userRegister = 0;

                if (getIntent().hasExtra("cod_usuario")) {
                    Bundle extras = getIntent().getExtras();
                    int cod_usuario = extras.getInt("cod_usuario");

                    userRegister = SQLHelper.getInstance(this).addAddress(cod_usuario, cep, number, complement);
                }

                if (userRegister > 0) {
                    Toast.makeText(this, R.string.register_true, Toast.LENGTH_LONG).show();
                    Intent homeActivity = new Intent(this, LoginActivity.class);
                    startActivity(homeActivity);
                } else Toast.makeText(this, R.string.register_false, Toast.LENGTH_LONG).show();
            }).setNegativeButton("",
//                    R.string.cancelar,
                    (dialog1, wich)->{}).create();

            dialog.show();

        });

    }

    public boolean validate() {
        return (!cepText.getText().toString().isEmpty() && !complementText.getText().toString().isEmpty() && !numText.getText().toString().isEmpty());
    }
}