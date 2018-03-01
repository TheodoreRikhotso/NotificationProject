package com.example.admin.notificationproject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
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
    private ProgressDialog mDialog;

    private FirebaseAuth auth;
    Snackbar snackbar;
    private static final int ALERT_DIALOG = 1;
    //profile
    private DatabaseReference databaseProfile;
    private String[] departs = {"Department name", "Finance", "IT", "HR", "Administrative Information Service"};
    TextView etStuffNo, signup_name;
    private String email, password;
    Spinner spDepart;
    Boolean dismiss = false;

    public static String department, stuffno, CONTEXT, name;
    private Boolean sendVeri = false;
    int idTotal = 0;
    Firebase ref;
    private DatabaseReference db, profiledb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        CONTEXT = "SignUpActivity";

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbSignUp);
        toolbar.setTitle(" ");


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_sign));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDialog = new ProgressDialog(this);

        DatabaseReference databaseCatalogg = FirebaseDatabase.getInstance().getReference("Furniture");
        //View
        btnSignup = (Button) findViewById(R.id.signup_btn_register);
        input_email = (EditText) findViewById(R.id.signup_email);
        input_pass = (EditText) findViewById(R.id.signup_password);
        findViewById(R.id.btn_verify_email).setOnClickListener(this);
        // btnLogin = (TextView) findViewById(R.id.signup_btn_login);
        //  btnForgotPass = (TextView) findViewById(R.id.signup_btn_forgot_pass);
        activity_sign_up = (RelativeLayout) findViewById(R.id.activity_sign_up);

        //profile
        // etStuffNo = (EditText) findViewById(R.id.etStuffNo);
        signup_name = (EditText) findViewById(R.id.signup_name);
        // spDepart = (Spinner) findViewById(R.id.spDepart);

        //email & password
        email = input_email.getText().toString();
        password = input_pass.getText().toString();


        db = FirebaseDatabase.getInstance().getReference("Users");

//        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,departs);
//        spDepart.setAdapter(adapter);
//        spDepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                department=departs[i];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        ///end profile

        btnSignup.setOnClickListener(this);
        // btnForgotPass.setOnClickListener(this);
        // btnLogin.setOnClickListener(this);


        //Init Firebase Auth
        auth = FirebaseAuth.getInstance();
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.signup_btn_login){
//            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
//            finish();
//        }
//        else if (view.getId() == R.id.signup_btn_forgot_pass) {
//            startActivity(new Intent(SignUpActivity.this,ForgotPasswordAcitivity.class));
//            finish();
//        }





        if (view.getId() == R.id.signup_btn_register) {


//email & password
            email = input_email.getText().toString();
            password = input_pass.getText().toString();
            //Validation
            name = signup_name.getText().toString();

            if (name.isEmpty()) {
                signup_name.setError("Username must not be empty");
            } else {
                if (email.isEmpty()) {

                    input_email.setError("Email is empty");
                } else {

//                    if (email.contains("@mlab.co.za")) {


                        if (password.isEmpty()) {
                            input_pass.setError("Password is empty");
                        } else {
                            if (password.length() >= 7) {
                                int upperCaseCounter = 0, lowerCaseCounter = 0, digitCounter = 0, whiteSpaceCounter = 0, specialCounter = 0;

//                                try {
//                                    byte[] bytes = password.getBytes();
//                                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//                                    BufferedReader br = new BufferedReader(new InputStreamReader(bais));
//
//
//                                    String pass = br.readLine();
//
//                                    for (int i = 0; i < pass.length(); i++) {
//                                        char ch = pass.charAt(i);
//                                        if (Character.isAlphabetic(ch)) {
//                                            if (Character.isUpperCase(ch)) {
//                                                upperCaseCounter += 1;
//                                            } else {
//                                                lowerCaseCounter += 1;
//                                            }
//                                        } else if (Character.isDigit(ch)) {
//                                            digitCounter += 1;
//                                        } else {
//                                            if (Character.isWhitespace(ch)) {
//                                                whiteSpaceCounter += 1;
//                                            } else {
//                                                specialCounter += 1;
//                                            }
//                                        }
//                                    }
//                                    if (upperCaseCounter > 0) {
//                                        if (lowerCaseCounter > 2) {
//                                            if (specialCounter > 0) {
//                                                if (digitCounter > 1) {


                                                    signUpUser(email, password);
//                                                } else {
//                                                    input_pass.setError("Password must contain at least 2 uppercase");
//                                                }
//
//                                            } else {
//                                                input_pass.setError("Password must contain at least one special character");
//                                            }
//
//                                        } else {
//                                            input_pass.setError("Password must contain at least 3 lowercase");
//                                        }
//
//                                    } else {
//                                        input_pass.setError("Password must contain at least one uppercase");
//                                    }
//
//
//                                } catch (IOException e) {
//                                    System.out.println("error in input.");
//                                }
////
//
                            } else {
                                input_pass.setError("Password must contains more than 6 characters  ");
                            }
                        }
//                    } else {
//                        input_email.setError("Email must contain @mlab.co.za");
//                    }


                }


            }


        }
    }

    private void signUpUser(final String email, final String password) {
        mDialog.setMessage("Sending Verification Email ...");
        mDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String id = auth.getCurrentUser().getUid();
//                            stuffno=etStuffNo.getText().toString();


                            profiledb = FirebaseDatabase.getInstance().getReference("Users/" + id + "/Profile");

                            Firebase.setAndroidContext(SignUpActivity.this);

                            ProfilePojo profilePojo = new ProfilePojo();
                            name = signup_name.getText().toString();

//

                            profilePojo.setName(name);
                            profilePojo.setLogId(auth.getCurrentUser().getUid());


                            profiledb.setValue(profilePojo);


                            snackbar = Snackbar.make(activity_sign_up, " : ", Snackbar.LENGTH_SHORT);
                            snackbar.show();

                            Snackbar snackbar = Snackbar
                                    .make(activity_sign_up, " Signing In!!", Snackbar.LENGTH_LONG)
                                    .setAction("Done", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Snackbar snackbar1 = Snackbar.make(activity_sign_up, "Message is restored!", Snackbar.LENGTH_SHORT);
                                            snackbar1.show();
                                        }
                                    });

                            snackbar.show();

                            Intent intent = new Intent(SignUpActivity.this, LandingScreen.class);
                            startActivity(intent);
                            //showDialog( ALERT_DIALOG );

                            mDialog.dismiss();
                        } else {
                            dismiss = false;
                            mDialog.dismiss();
                            String emails = task.getException().getMessage();
                            if (emails.contains("email")) {
                                input_email.setError(task.getException().getMessage());
                            } else {
                                input_email.setError(task.getException().getMessage());
                            }
                            snackbar = Snackbar.make(activity_sign_up, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_SHORT);
                            snackbar.show();


                        }


                    }
                });


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        if (id == ALERT_DIALOG) {
            @SuppressLint("RestrictedApi") ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.MyStyle);
            AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
            builder.setMessage("Verification email sent to")
                    .setTitle("Verification")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

//                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    dialog.dismiss();
                                }
                            }
                    );
            dialog = builder.create();
        }
        if (dialog == null) {
            dialog = super.onCreateDialog(id);
        }
        return dialog;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();

        }
        return true;
    }




}