package secret.poll.nemo.secret_polls;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class TutorialActivity extends AppCompatActivity{
    @OnClick(R.id.button_play_poll)
    public void goToPoll(){
        Intent intent = new Intent(this, PollActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        ButterKnife.bind(this);
    }
}
