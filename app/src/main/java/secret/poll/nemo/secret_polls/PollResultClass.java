package secret.poll.nemo.secret_polls;

/**
 * Created by jeeyu_000 on 2018-02-09.
 */

public class PollResultClass {
    private int questionGroupIdx;
    private int questionNumIdx;
    private String pickUserIdx;

    public PollResultClass(){}

    public PollResultClass(int questionGroup, int questionNum, String pickUser){
        questionGroupIdx = questionGroup;
        questionNumIdx = questionNum;
        pickUserIdx = pickUser;
    }

    public int getQuestionGroupIdx() {
        return questionGroupIdx;
    }

    public void setQuestionGroupIdx(int questionGroupIdx) {
        this.questionGroupIdx = questionGroupIdx;
    }

    public int getQuestionNumIdx() {
        return questionNumIdx;
    }

    public void setQuestionNumIdx(int questionNumIdx) {
        this.questionNumIdx = questionNumIdx;
    }

    public String getPickUserIdx() {
        return pickUserIdx;
    }

    public void setPickUserIdx(String pickUserIdx) {
        this.pickUserIdx = pickUserIdx;
    }
}
