package secret.poll.nemo.secret_polls;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class ProfileInputActivity extends AppCompatActivity implements ProfileInputCompleteInterface{
    private FirebaseDatabase mDB;
    private DatabaseReference mDBRefUserProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_input_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.profile_input_fragment, new SchoolInputFragment());
        ft.commit();

        mDB = FirebaseDatabase.getInstance();
        mDBRefUserProfile = mDB.getReference("USER_PROFILE");
    }

    @Override
    public void complete(int stage) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(stage){
            case ProfileInputActivity.SELECT_SCHOOL : {
                ft.replace(R.id.profile_input_fragment, new GradeInputFragment());
                break;
            }
            case ProfileInputActivity.SELECT_GRADE : {
                ft.replace(R.id.profile_input_fragment, new GenderInputFragment());
                break;
            }
            case ProfileInputActivity.SELECT_GENDER : {
                ft.replace(R.id.profile_input_fragment, new NameInputFragment());
                break;
            }
            case ProfileInputCompleteInterface.INPUT_NAME : {
                sendUserProfileToDB();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        ft.commit();
    }

    public void sendUserProfileToDB(){
        UserProfileClass user = UserProfileClass.getUserProfile();
        String phoneNum = user.getPhoneNum();
        mDBRefUserProfile.child(phoneNum).setValue(user);
    }
}
