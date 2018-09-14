package demo1;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * ItemProcessor类。
 */
@Component("csvItemProcessor")
public class CsvItemProcessor implements ItemProcessor<Student, Student> {

    /**
     * 对取到的数据进行简单的处理。
     *
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    @Override
    public Student process(Student student) throws Exception {
        /* 合并ID和名字 */
        student.setName(student.getID() + "--" + student.getName());
        /* 年龄加2 */
        student.setAge(student.getAge() + 2);
        /* 分数加10 */
        student.setScore(student.getScore() + 10);
        /* 将处理后的结果传递给writer */
        System.out.println("processing---");
        return student;
    }
}
