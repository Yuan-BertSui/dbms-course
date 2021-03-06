package dbms.suiyuan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suiyuan
 * @description: 实现describe SQL语句功能实现
 */
public class Describe {

    public static void describeSql(String sql) throws Exception {

        //得到分隔符
        String sep = SQLConstant.getSeparate();

        //获得当前路径,到数据库文件夹
        String path = SQLConstant.getNowPath();
        String tableName = sql.substring(9, sql.length() - 1);

        //当前表的完整路径
        String nowPath = path + "\\" + tableName + "_info.txt";
        List<String> list = getTableDescribe(nowPath);

        //对获取的list进行处理并打印
        List<List<String>> lists = new ArrayList<>();
        String[] s1 = list.get(0).split(sep);
        String[] s2 = list.get(1).split(sep);
        String[] s3 = list.get(2).split(sep);
        for (int i = 0; i < s1.length; i++) {
            List<String> list1 = new ArrayList<>();
            list1.add(s1[i]);
            list1.add(s2[i]);
            list1.add(s3[i]);
            lists.add(list1);
        }
        List<String> list4 = new ArrayList<>();
        list4.add("ColumnName");
        list4.add("Type");
        list4.add("ExtraRestriction");

        System.out.println(TableGenerator.generateTable(list4, lists));
        Input.get();
    }

    /**
     * @param path
     * @return
     * @description: 从文本文件(表)的前三行读取表的描述信息
     */
    private static List<String> getTableDescribe(String path) {

        List<String> list = new ArrayList<>();

        //对文件进行读取
        try {
            File file = new File(path);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s = "";
            int index = 1;
            while ((s = bufferedReader.readLine()) != null && index < 4) {
                index++;
                list.add(s);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
