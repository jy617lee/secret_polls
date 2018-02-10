package secret.poll.nemo.secret_polls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import secret.poll.nemo.secret_polls.UtillClass.UserProfileClass;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class GradeInputFragment  extends Fragment implements View.OnClickListener{
    @BindView(R.id.grade_1) TextView mGrade1;
    @BindView(R.id.grade_2)  TextView mGrade2;
    @BindView(R.id.grade_3)  TextView mGrade3;
    @BindView(R.id.button_select_grade) Button btnSelectGrade;

    private final String TAG = "GradeInputFragment";
    private int mPastGrade = -1;
    private int mCurGrade = -1;
    private TextView[] gradeArr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grade_input_fragment, container, false);
        ButterKnife.bind(this, view);
        gradeArr = new TextView[]{null, mGrade1, mGrade2, mGrade3};
        mGrade1.setOnClickListener(this);
        mGrade2.setOnClickListener(this);
        mGrade3.setOnClickListener(this);
        btnSelectGrade.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.grade_1 : {
                changeCurGrade(1);
                break;
            }
            case R.id.grade_2 : {
                changeCurGrade(2);
                break;
            }
            case R.id.grade_3 : {
                changeCurGrade(3);
                break;
            }
            case R.id.button_select_grade : {
                if(mCurGrade < 0){
                    Toast.makeText(getContext(), "no grade selected", Toast.LENGTH_SHORT).show();
                }else{
                    //유저 객체에 학년 정보 저장
                    UserProfileClass user = UserProfileClass.getUserProfile();
                    user.setGrade(mCurGrade);

                    //다음으로 넘어간다
                    ProfileInputCompleteInterface completeInterface = (ProfileInputCompleteInterface) getContext();
                    completeInterface.complete(ProfileInputCompleteInterface.SELECT_GRADE);
                }
            }
        }
    }

    private void changeCurGrade(int curGrade){
        mPastGrade = mCurGrade;
        mCurGrade = curGrade;
        if(mPastGrade > -1){
            gradeArr[mPastGrade].setBackgroundColor(getContext().getColor(R.color.white));
        }
        gradeArr[mCurGrade].setBackgroundColor(getContext().getColor(R.color.colorAccent));
    }
}
