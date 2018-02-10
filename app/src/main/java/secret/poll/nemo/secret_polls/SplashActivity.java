package secret.poll.nemo.secret_polls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import secret.poll.nemo.secret_polls.ManagerClass.SharedPreferenceManager;
import secret.poll.nemo.secret_polls.UtillClass.UserProfileClass;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferenceManager mSPManager = SharedPreferenceManager.getSharedPreferenceManaer(getApplicationContext());
        String userString = (String) mSPManager.getValue("userProf", String.class, null);

        Intent intent;
        if(userString != null){
            //유저 정보 객체로 가지고 있기
            getUserFromSharedPreference(getApplicationContext());
            intent = new Intent(this, PollActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public static void getUserFromSharedPreference(Context context){
        SharedPreferenceManager mSPManager = SharedPreferenceManager.getSharedPreferenceManaer(context);
        String userString = (String) mSPManager.getValue("userProf", String.class, null);

        Gson gson = new GsonBuilder().create();
        UserProfileClass userProfile = gson.fromJson(userString, UserProfileClass.class);
        UserProfileClass userProfileObj = UserProfileClass.getUserProfile();
        userProfileObj.setUserProfile(userProfile);
    }
}
