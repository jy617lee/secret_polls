package secret.poll.nemo.secret_polls;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeeyu_000 on 2018-02-05.
 */

public class LoginActivity extends AppCompatActivity{
    @BindView(R.id.field_phone_number) EditText mPhoneNumberField;
//    @BindView(R.id.field_verification_code) EditText mVerificationField;
    @BindView(R.id.button_start_verification) Button mStartButton;
//    @BindView(R.id.button_verify_phone) Button mVerifyButton;
//    @BindView(R.id.status) TextView mStatusText;

    @OnClick(R.id.button_start_verification)
    public void signIn(){
        String phonenumber = mPhoneNumberField.getText().toString();
        if(TextUtils.isEmpty(phonenumber)){
            mPhoneNumberField.setError("Invalid Phone Number");
            return;
        }else{
            //유저 인스턴스에 폰 번호를 저장하고 프로필 입력 페이지로 넘어간다
            UserProfileClass mUser = UserProfileClass.getUserProfile();
            mUser.setmPhoneNum(phonenumber);

            Intent intent = new Intent(this, ProfileInputActivity.class);
            startActivity(intent);
        }
    }
//    @OnClick(R.id.button_start_verification)
//    public void startVerification(){
//        if (!validatePhoneNumber()) {
//            return;
//        }
//
//        if(mStartButton.getText().toString().equals("Resend")){
//            resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
//        }else {
//            startPhoneNumberVerification(mPhoneNumberField.toString());
//            mStartButton.setText("Resend");
//        }
//    }
//
//    @OnClick(R.id.button_verify_phone)
//    public void verifyPhone(){
//        String code = mVerificationField.getText().toString();
//
//        if(TextUtils.isEmpty(code)){
//            mVerificationField.setError("Empty");
//            return;
//        }
//
//        verifyPhoneNumberWithCode(mVerificationId, code);
//    }
    private final String TAG = "LoginActivity";
//    private final int STATE_INITIALIZED = 1;
//    private final int STATE_CODE_SENT = 2;
//    private final int STATE_VERIFY_FAILED = 3;
//    private final int STATE_VERIFY_SUCCESS = 4;
//    private final int STATE_SIGNIN_FAILED = 5;
//    private final int STATE_SIGNIN_SUCCESS = 6;
//
//    private FirebaseAuth mAuth;
//    private String mVerificationId;
//    private PhoneAuthProvider.ForceResendingToken mResendToken;
//
//    private boolean mVerificationInProgress = false;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseDatabase mDB;
    private DatabaseReference mDBRefUserProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        mDB = FirebaseDatabase.getInstance();
        mDBRefUserProfile = mDB.getReference("USER_PROFILE");
//        if (savedInstanceState != null) {
//            onRestoreInstanceState(savedInstanceState);
//        }
//
//        mAuth = FirebaseAuth.getInstance();

//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                //verification없이 인증 가능하거나, SMS를 자동으로 인식하여 성공하는 경우
//                Log.d(TAG, "onVerificationCompleted : " + phoneAuthCredential);
//                mVerificationInProgress = false;
//                updateUI(STATE_VERIFY_SUCCESS, phoneAuthCredential);
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Log.w(TAG, "onVerificationFailed", e);
//                mVerificationInProgress = false;
//
//                if(e instanceof FirebaseAuthInvalidCredentialsException){
//                    mPhoneNumberField.setError("Invalid Phone Number");
//                }else if(e instanceof FirebaseTooManyRequestsException){
//                    Toast.makeText(getApplicationContext(), "Today quota exceeded, try tmr", Toast.LENGTH_SHORT).show();
//                }
//                updateUI(STATE_VERIFY_FAILED);
//            }
//
//            @Override
//            public void onCodeSent(String verificationId,
//                                   PhoneAuthProvider.ForceResendingToken token) {
//                Log.d(TAG, "onCodeSent : " + verificationId);
//                mVerificationId = verificationId;
//                mResendToken = token;
//
//                updateUI(STATE_CODE_SENT);
//            }
//        };
    }

//    //[START on_start_check_user]
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//
////        if(mVerificationInProgress && validatePhoneNumber()){
////            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
////        }
//    }
//    //[END on_start_check_user]
//
//    private void startPhoneNumberVerification(String phoneNumber){
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber, 20, TimeUnit.SECONDS,
//                this, mCallbacks
//        );
//
//        mVerificationInProgress = true;
//    }
//
//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token){
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber, 60, TimeUnit.SECONDS,this,
//                mCallbacks, token);
//    }
//
//    //todo 폰 번호 체크 입력 중간에 하기 혹은 start 버튼 눌렀을 때
//    private boolean validatePhoneNumber(){
//        String phonenumber = mPhoneNumberField.getText().toString();
//        if(TextUtils.isEmpty(phonenumber)){
//            mPhoneNumberField.setError("Invalid Phone Number");
//            return false;
//        }
//        return true;
//    }
//
//    private void verifyPhoneNumberWithCode(String verificationId, String code){
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        signInWithPhoneAuthCredential(credential);
//    }
//    private void updateUI(int uiState) {
//        updateUI(uiState, mAuth.getCurrentUser(), null);
//    }
//
//    private void updateUI(FirebaseUser user){
//        if(user != null){
//            updateUI(STATE_SIGNIN_SUCCESS, user);
//        }else{
//            updateUI(STATE_INITIALIZED);
//        }
//    }
//
//    private void updateUI(int uiState, FirebaseUser user){
//        updateUI(uiState, user, null);
//    }
//
//    private void updateUI(int uiState, PhoneAuthCredential cred) {
//        updateUI(uiState, null, cred);
//    }
//
//    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred){
//        switch(uiState){
//            case STATE_INITIALIZED : {
//                enableViews(mStartButton, mPhoneNumberField);
//                disableViews(mVerifyButton, mVerificationField);
//                mStatusText.setText(null);
//                break;
//            }
//
//            case STATE_CODE_SENT : {
//                enableViews(mVerifyButton, mPhoneNumberField, mVerificationField);
//                disableViews(mStartButton);
//                mStatusText.setText("verification code sent");
//                break;
//            }
//
//            case STATE_VERIFY_FAILED : {
//                enableViews(mStartButton, mPhoneNumberField);
//                disableViews(mVerifyButton, mVerificationField);
//                mStatusText.setText("verification failed");
//                break;
//            }
//
//            case STATE_VERIFY_SUCCESS : {
//                disableViews(mStartButton, mVerifyButton, mPhoneNumberField, mVerificationField);
//                mStatusText.setText("verification succeeded");
//                break;
//            }
//
//            case STATE_SIGNIN_FAILED : {
//                //
//                break;
//            }
//
//            case STATE_SIGNIN_SUCCESS : {
//                //
//                break;
//            }
//        }
//
//        if (user == null) {
//            //signed out
//        }else{
//           // go to signed in
//        }
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Log.d(TAG, "signInWithCredential : success");
//                            FirebaseUser user = task.getResult().getUser();
//                            updateUI(STATE_SIGNIN_SUCCESS, user);
//                        }else{
//                            Log.w(TAG,  "signInWithCredential:failure", task.getException());
//                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
//                                mStatusText.setError("Invalid Code");
//                            }else {
//                                mStatusText.setError("fail to sign in");
//                            }
//                        }
//                    }
//                });
//    }
//
//    private void enableViews(View... views){
//        for(View v : views) {
//            v.setEnabled(true);
//        }
//    }
//
//    private void disableViews(View... views){
//        for(View v : views) {
//            v.setEnabled(false);
//        }
//    }
}
