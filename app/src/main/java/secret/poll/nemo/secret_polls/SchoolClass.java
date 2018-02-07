package secret.poll.nemo.secret_polls;

/**
 * Created by jeeyu_000 on 2018-02-07.
 */

public class SchoolClass {
    private String city;
    private String name;
    private String kind;
    private int idx;

    public SchoolClass(){}

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
