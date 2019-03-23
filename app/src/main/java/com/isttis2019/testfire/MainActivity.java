package com.isttis2019.testfire;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.tv.TvContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    EditText  edEmail;
    EditText edPassword;
    Button btnsigin;
    TextView tvViewSingin;
    TextView tvViewMessage;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            finish();

            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }

        edEmail=findViewById(R.id.editTextEmail);
        edPassword=findViewById(R.id.editTextPassword);
        btnsigin=findViewById(R.id.buttonSignup);
        tvViewSingin=findViewById(R.id.textViewSignin);
        tvViewMessage=findViewById(R.id.textviewMessage);

        progressDialog=new ProgressDialog(this);

        btnsigin.setOnClickListener((View.OnClickListener) this);
        tvViewSingin.setOnClickListener((View.OnClickListener) this);


    }

    private  void regieterUser(){
            String email=edEmail.getText().toString().trim();
            String password=edPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Email을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "페스워드를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("등록중입니다. 기달려주세요...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           finish();
                           startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                       }else {
                           tvViewMessage.setText("에러유형\n - 이미 등록된 이메일  \n -암호 최소 6자리 이상 \n - 서버에러");
                           Toast.makeText(MainActivity.this, "등록 에러!", Toast.LENGTH_SHORT).show();
                       }
                            progressDialog.dismiss();

                        }
                    });

    }


    public void onClick(View view){

        if (view==btnsigin){
                regieterUser();
        }
        if (view==tvViewSingin){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}



































