package secret.poll.nemo.secret_polls;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public interface ProfileInputCompleteInterface {
    public final int SELECT_SCHOOL = 0;
    public final int SELECT_GRADE = 1;
    public final int SELECT_GENDER = 2;

    public void complete(int stage);
}
