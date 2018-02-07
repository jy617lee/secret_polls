package secret.poll.nemo.secret_polls;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class ProfileInputActivity extends AppCompatActivity implements ProfileInputCompleteInterface{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_input_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.profile_input_fragment, new SchoolInputFragment());
        ft.commit();
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
                Toast.makeText(getApplicationContext(), "input complete", Toast.LENGTH_SHORT).show();
//                sendUserProfileToDB();
            }
        }
        ft.commit();
    }
}
