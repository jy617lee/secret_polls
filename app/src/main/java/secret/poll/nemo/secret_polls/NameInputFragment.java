package secret.poll.nemo.secret_polls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class NameInputFragment extends Fragment {
    @BindView(R.id.field_name) EditText mNameField;
    @OnClick(R.id.button_input_name)
    public void inputName(){
        String name = mNameField.getText().toString();
        if(TextUtils.isEmpty(name)){
            mNameField.setError("empty name");
        }else{
            //유저 객체에 이름 정보 저장
            UserProfileClass user = UserProfileClass.getUserProfile();
            user.setName(name);

            //다음으로 넘어간다
            ProfileInputCompleteInterface completeInterface = (ProfileInputCompleteInterface) getContext();
            completeInterface.complete(ProfileInputCompleteInterface.INPUT_NAME);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_input_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
