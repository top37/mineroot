package demo1;

/** Pojo类_Student */
public class Student {
    /** ID */
    private String ID = "";
    /** 名字 */
    private String name = "";
    /** 年龄 */
    private int age = 0;
    /** 分数 */
    private float score = 0;
    /*getter 和setter已删除*/

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
