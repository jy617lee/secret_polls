package secret.poll.nemo.secret_polls;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import secret.poll.nemo.secret_polls.UtillClass.AnswerClass;
import secret.poll.nemo.secret_polls.UtillClass.PollResultClass;
import secret.poll.nemo.secret_polls.UtillClass.UserProfileClass;

import static secret.poll.nemo.secret_polls.SplashActivity.getUserFromSharedPreference;

/**
 * Created by jeeyu_000 on 2018-02-09.
 */

public class HeartListActivity extends AppCompatActivity{
    @BindView(R.id.answer_list) RecyclerView mAnswerListView;
    @BindView(R.id.answer_sum)  TextView mAnswerSum;
    @OnClick(R.id.btn_poll)
    public void goPollActivity(){
        Intent intent = new Intent(this, PollActivity.class);
        startActivity(intent);
        finish();
    }

    private FirebaseDatabase mDB;
    private DatabaseReference mDBRefAnswerList;
    private DatabaseReference mDBRefUserProfile;
    private DatabaseReference mDBRefQuestionList;
    private String myPhoneNum;
    private UserProfileClass user;
    private ArrayList<PollResultClass> pollResArr;
    private List<AnswerClass> answerArr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_list_activity);
        ButterKnife.bind(this);

        //유저 정보
        user = UserProfileClass.getUserProfile();
        if(user.getPhoneNum() == null){
            getUserFromSharedPreference(getApplicationContext());
        }
        myPhoneNum = user.getPhoneNum();

        //DB 정보
        mDB = FirebaseDatabase.getInstance();
        mDBRefAnswerList = mDB.getReference("POLL_RESULT").child(myPhoneNum);
        mDBRefUserProfile = mDB.getReference("USER_PROFILE");
        mDBRefQuestionList = mDB.getReference("QUESTION_LIST");

        //나의 답변 목록 가져오기
        mDBRefAnswerList.addListenerForSingleValueEvent(mAnswerListListener);
    }

    private int userIdx, questionIdx;
    private AnswerAdapter adapter;
    private ValueEventListener mAnswerListListener = new ValueEventListener() {
        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cnt = Integer.parseInt(dataSnapshot.getChildrenCount()+"");
                pollResArr = new ArrayList<>(cnt);
                answerArr = new ArrayList<>(cnt);
                adapter = new AnswerAdapter(getApplicationContext(), answerArr);
                mAnswerSum.setText(cnt+"");
                mAnswerListView.setAdapter(adapter);
                mAnswerListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                for(DataSnapshot answerData : dataSnapshot.getChildren()){
                    PollResultClass resObj = answerData.getValue(PollResultClass.class);
                    pollResArr.add(resObj);
                }

                //답변 목록에서 질문 내용과 유저 정보를 가져오기
                userIdx = 0; questionIdx = 0;
                for(int i = 0; i < cnt; i++) {
                    answerArr.add(userIdx, new AnswerClass());
                    //유저 정보
                    mDBRefUserProfile.child(pollResArr.get(i).getPickUserIdx())
                            .addListenerForSingleValueEvent(mUserListener);

                    //질문 내용
                    String questionGroupNum = pollResArr.get(i).getQuestionGroupIdx()+"";
                    String questionNum = pollResArr.get(i).getQuestionNumIdx()+"";
                    mDBRefQuestionList.child(questionGroupNum).child(questionNum)
                            .addListenerForSingleValueEvent(mQuestionListener);
                }
            }

        @Override public void onCancelled(DatabaseError databaseError) {}
    };

    private ValueEventListener mQuestionListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String question = dataSnapshot.getValue(String.class);
            AnswerClass answer = answerArr.get(questionIdx);
            answer.question = question;
            if(userIdx > questionIdx){
                adapter.notifyItemChanged(questionIdx);
            }
            questionIdx++;
        }

        @Override public void onCancelled(DatabaseError databaseError) {}
    };

    private ValueEventListener mUserListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            UserProfileClass user = dataSnapshot.getValue(UserProfileClass.class);
            AnswerClass answer = answerArr.get(userIdx);
            answer.gender = user.getGender();
            answer.schoolKind = user.getSchoolKind();
            answer.grade = user.getGrade();
            userIdx++;
        }

        @Override public void onCancelled(DatabaseError databaseError) {}
    };

    public class AnswerAdapter extends
            RecyclerView.Adapter<AnswerAdapter.ViewHolder>{
        //[START ViewHolder]
        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mQuestionTxt, mSchoolKindTxt, mSchoolGradeTxt;
            LinearLayout mBackground;

            public ViewHolder(View itemView) {
                super(itemView);
                mQuestionTxt = itemView.findViewById(R.id.question);
                mSchoolKindTxt = itemView.findViewById(R.id.school_kind);
                mSchoolGradeTxt = itemView.findViewById(R.id.school_grade);
                mBackground = itemView.findViewById(R.id.background);
            }
        }
        //[END ViewHolder]

        private Context mContext;
        private List<AnswerClass> mAnswers;

        public AnswerAdapter(Context context, List<AnswerClass> answers){
            mAnswers = answers;
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View answerView = inflater.inflate(R.layout.item_answer, parent, false);
            ViewHolder viewHolder = new ViewHolder(answerView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            AnswerClass answer = mAnswers.get(position);
            if(answer.gender == null || answer.question == null){

            }else {
                TextView mQuestionText = holder.mQuestionTxt;
                mQuestionText.setText(answer.question);

                TextView mSchoolKindTxt = holder.mSchoolKindTxt;
                mSchoolKindTxt.setText(answer.schoolKind);

                TextView mSchoolGradeTxt = holder.mSchoolGradeTxt;
                mSchoolGradeTxt.setText(answer.grade+"");

                LinearLayout mBackground = holder.mBackground;
                if (answer.gender.equals("m")) {
                    mBackground.setBackgroundColor(mContext.getColor(R.color.colorPrimary));
                } else {
                    mBackground.setBackgroundColor(mContext.getColor(R.color.colorAccent));
                }
            }
        }

        @Override
        public int getItemCount() {
            return mAnswers.size();
        }
    }


}
