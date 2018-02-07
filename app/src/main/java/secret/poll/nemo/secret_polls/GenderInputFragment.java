package secret.poll.nemo.secret_polls;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class GenderInputFragment extends Fragment {
    @BindView(R.id.male) TextView mMale;
    @BindView(R.id.female) TextView mFemale;

    @OnClick(R.id.male)
    public void selectMale(){
        mMale.setBackgroundColor(getContext().getColor(R.color.colorAccent));
        mFemale.setBackgroundColor(getContext().getColor(R.color.white));
        mCurGender = MALE;
    }

    @OnClick(R.id.female)
    public void selectFemale(){
        mFemale.setBackgroundColor(getContext().getColor(R.color.colorAccent));
        mMale.setBackgroundColor(getContext().getColor(R.color.white));
        mCurGender = FEMALE;
    }

    @OnClick(R.id.button_select_gender)
    public void selectGender(){
        if(mCurGender == null){
            Toast.makeText(getContext(), "no gender selected", Toast.LENGTH_SHORT).show();
        }else{
            //유저 객체에 성별 정보 저장
            UserProfileClass user = UserProfileClass.getUserProfile();
            user.setmGender(mCurGender);

            //다음으로 넘어간다
            ProfileInputCompleteInterface completeInterface = (ProfileInputCompleteInterface) getContext();
            completeInterface.complete(ProfileInputCompleteInterface.SELECT_GENDER);
        }
    }
    private String mCurGender = null;
    private final String MALE = "m";
    private final String FEMALE = "f";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gender_input_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
