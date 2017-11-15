package com.example.admin.notificationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignup;
    TextView btnLogin, btnForgotPass;
    EditText input_email, input_pass;
    RelativeLayout activity_sign_up;

    private FirebaseAuth auth;
    Snackbar snackbar;

    //profile
    private DatabaseReference databaseProfile;
    private  String[] departs ={"Department name","Finance","IT", "HR","Administrative Information Service"};
    TextView etStuffNo,signup_name;
    Spinner spDepart;

    public static String department,stuffno,CONTEXT,name;

    int idTotal =0;
    Firebase ref;
    private DatabaseReference db,profiledb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
            CONTEXT ="SignUpActivity";

        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Furniture");
        //View
        btnSignup = (Button) findViewById(R.id.signup_btn_register);
        input_email = (EditText) findViewById(R.id.signup_email);
        input_pass = (EditText) findViewById(R.id.signup_password);
        btnLogin = (TextView) findViewById(R.id.signup_btn_login);
        btnForgotPass = (TextView) findViewById(R.id.signup_btn_forgot_pass);
        activity_sign_up = (RelativeLayout) findViewById(R.id.activity_sign_up);

        //profile
        etStuffNo = (EditText) findViewById(R.id.etStuffNo);
        signup_name = (EditText) findViewById(R.id.signup_name);
        spDepart = (Spinner) findViewById(R.id.spDepart);



        db = FirebaseDatabase.getInstance().getReference("Users");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,departs);
        spDepart.setAdapter(adapter);
        spDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department=departs[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        ///end profile

        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        //Init Firebase Auth
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signup_btn_login){
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
        }
        else if (view.getId() == R.id.signup_btn_forgot_pass){
            startActivity(new Intent(SignUpActivity.this,ForgotPasswordAcitivity.class));
            finish();


        }

        else if (view.getId() == R.id.signup_btn_register){
            signUpUser(input_email.getText().toString(),input_pass.getText().toString());


        }


    }

    private void signUpUser(final String email, final String password) {

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String id  = auth.getCurrentUser().getUid();
                            stuffno=etStuffNo.getText().toString();
                            name=signup_name.getText().toString();


                            profiledb = FirebaseDatabase.getInstance().getReference("Users/"+id+"/Profile");

                                    Firebase.setAndroidContext(SignUpActivity.this);

                                    ProfilePojo profilePojo= new ProfilePojo();

                                    profilePojo.setDepartmentName(department);
                                    profilePojo.setStuffNo(stuffno);
                                     profilePojo.setName(name);
                                    profilePojo.setLogId(auth.getCurrentUser().getUid());

                            profiledb.setValue(profilePojo);

                            Intent intent = new Intent(SignUpActivity.this,LandingScreen.class);
                            startActivity(intent);

                            snackbar = Snackbar.make(activity_sign_up, "Register success : ",Snackbar.LENGTH_SHORT);
                            snackbar.show();



                        }
                        else {

                            snackbar = Snackbar.make(activity_sign_up, "Error: "+task.getException(),Snackbar.LENGTH_SHORT);
                            snackbar.show();



                        }

                    }
                });

    }




}

