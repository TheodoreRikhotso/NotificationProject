package com.example.admin.notificationproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    public static String userId = "";
    public static String currentUserId = "";
    Button btnLogin;
    EditText input_email, input_password;
    TextView btnSignup, btnForgotPass;
    private ProgressDialog mDialog;
    RelativeLayout activity_main;

public static String ACCOUNT_CHECK="login";
    private FirebaseAuth auth;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MAIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tbSignIn);
        toolbar.setTitle(" ");
        mDialog = new ProgressDialog(this);


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_sign));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                if(ACCOUNT_CHECK!="sign") {
    startActivity(new Intent(LoginActivity.this, LandingScreen.class));
}
                }
            }
        };
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            //profile activity here
            if(ACCOUNT_CHECK!="sign") {
                finish();
                startActivity(new Intent(getApplicationContext(), LandingScreen.class));
            }
        }
//        mGoogleBtn = (SignInButton) findViewById(R.id.googleBtn) ;
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

//        mGoogleApiClient = new GoogleApiClient.Builder(getBaseContext())
//                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                        Toast.makeText(LoginActivity.this, "You Got an Error", Toast.LENGTH_LONG).show();
//
//                    }
//                })
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });

        //View
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        input_email = (EditText) findViewById(R.id.login_email);
        input_password = (EditText) findViewById(R.id.login_password);
        // btnSignup = (TextView) findViewById(R.id.login_btn_signup);
        btnForgotPass = (TextView) findViewById(R.id.login_btn_forgot_password);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        ///  btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //Init Firebase Auth


        //check already session , if ok-> DashBoard
        if (auth.getCurrentUser() != null ) {
            if(ACCOUNT_CHECK!="sign") {
                startActivity(new Intent(LoginActivity.this, LandingScreen.class));
            }

        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn_forgot_password) {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordAcitivity.class));
            finish();
        }
//        if (view.getId() == R.id.login_btn_signup) {
//            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//            finish();
//        }
        if (view.getId() == R.id.login_btn_login) {
            String email = input_email.getText().toString();
            String password = input_password.getText().toString();


            if (email.isEmpty()) {
                input_email.setError("Email is empty");


            } else {

                if (password.isEmpty()) {
                    input_password.setError("Password is empty");
                } else {
                    loginUser(email, password);


                }
            }


        }

    }

    private void loginUser(String email, final String password) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user.sendEmailVerification();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            boolean emailVerified = user.isEmailVerified();
            if (emailVerified) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mDialog.setMessage("Signing in ...");
                                mDialog.show();

                                if (task.isSuccessful()) {
                                    if(ACCOUNT_CHECK!="sign") {
                                        startActivity(new Intent(LoginActivity.this, LandingScreen.class));
                                        mDialog.dismiss();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    String emails = task.getException().getMessage();
                                    if (emails.contains("email")) {
                                        input_email.setError(task.getException().getMessage());
                                    } else {
                                        input_password.setError(task.getException().getMessage());
                                    }

//
                                }

                            }
                        });
            }
            else
            {
                mDialog.dismiss();
                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Email is not verified", Toast.LENGTH_SHORT).show();

                //restart this activity

            }
        }else {

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    if(ACCOUNT_CHECK!="sign") {
                                    startActivity(new Intent(LoginActivity.this, LandingScreen.class));
                                    mDialog.dismiss();
                                    }
                                } else {
                                    mDialog.dismiss();
                                    String emails = task.getException().getMessage();
                                    if (emails.contains("email")) {
                                        input_email.setError(task.getException().getMessage());
                                    } else {
                                        input_password.setError(task.getException().getMessage());
                                    }

//
                                }

                            }
                        });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(mAuthListener);
    }

//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            GoogleSignInAccount account = result.getSignInAccount();
//            firebaseAuthWithGoogle(account);
//        } else {
//
//        }
//    }



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

