package demo;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) throws ParseException {
        execCmd(args);
    }

    public static void execCmd(String[] args) throws ParseException {
        /*这些参数可以不写，第二个boolen类型的参数并不是必/非必填*/
        /*1. 定义 Options 代码片段*/
        // 创建 Options 对象
        Options options = new Options();
        // 添加 --alive/-alive 参数：探活
        // false ： 不允许为参数赋值
        options.addOption("alive", false, "活着");
        // 添加 --name 参数
        // true ： 必须有=，即使=后边没有值
        options.addOption("name", true, "姓名");
        // 添加 --sex 参数
        options.addOption("sex", true, "性别");

        /*
         * 2. 解析 Options 代码片段
         * 这里需要注意，CommandLineParser有三个不同的解析器
         * PosixParser只能解析"--"
         * GnuParser能解析"-"/"--"
         * 当然也可以实现接口-CommandLineParser，扩展并自定义
         */
        CommandLineParser parser =  new GnuParser();
        CommandLine cmd = parser.parse(options, args);

        /*3.实现逻辑*/
        if(cmd.hasOption("alive")) {
            // 这里显示简短的帮助信息
            HelpFormatter formatter = new HelpFormatter();
            System.out.println("我的状态是：活着");
            formatter.printHelp("[options]", options);
            return;
        }

        if(cmd.hasOption("name")) {
            System.out.println("我的名字是："+cmd.getOptionValue("name"));
        }

        if(cmd.hasOption("sex")) {
            System.out.println("我的性别是："+cmd.getOptionValue("sex"));
        }

    }
}
