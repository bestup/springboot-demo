package com.tool.main;

import com.tool.entity.ProvincePlatform;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author halo.l
 * @desc: 使用说明：  根据javaBean 生成对应的sql语句， 已实现 增 查 改 , 对应的javaBean必须要生成set/get方法
 * 使用场景：  单表对应的实体，完成按照驼峰命名的方法来定义属性名称可用， 其他方式在解析字段时可能会出现问题，
 * 在项目中我们使用mybatis_plus，生成实体类， 字段多，手写sql很废时， 因此提供sql生成的工具类
 */

public class GeneratorSqlMain {

    /**
     * 这里定义内部类仅为举例说明， 实际环境下替换成自己定义的java类即可， Demo可删除
     */
    static class Demo{
        private String id;
        private String name;
        private String age;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getAge() {
            return age;
        }
        public void setAge(String age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        ProvincePlatform demo = new ProvincePlatform();
        //GeneratorSqlMain.insertMapper(demo);
        GeneratorSqlMain.updateMapper(demo, null);
        //GeneratorSqlMain.selectMapper(demo);
        //GeneratorSqlMain.sqlMapper(demo, true);
    }

    /**
     * 生成对应 <sql></sql>
     * @param obj    实体类
     * @param alias  是否添加别名项, 默认是false： 不添加， true:添加
     */
    public static void sqlMapper(Object obj, Boolean alias) {
        if(Objects.isNull(alias)) {
            alias = false;
        }

        if(!Objects.isNull(obj)) {
            StringBuffer sql = new StringBuffer();
            System.out.println("******************************<sql></sql>语句 开始生成******************************");
            sql.append("\r\n");

            sql.append("<sql id=\"query\">");
            sql.append("\r\n");

            //获取全部是属性名
            String[] strArr = Tools.getFiledName(obj);
            for (int i = 0; i < strArr.length; i++) {
                if (i == 0) {
                    sql.append("\t\t");
                }

                if(alias) {
                    if (i != strArr.length - 1) {
                        sql.append("${alias}." + Tools.humpToUnderline(strArr[i]) + ", ");
                    } else {
                        sql.append("${alias}." + Tools.humpToUnderline(strArr[i]));
                    }
                } else {
                    if (i != strArr.length - 1) {
                        sql.append(Tools.humpToUnderline(strArr[i]) + ", ");
                    } else {
                        sql.append(Tools.humpToUnderline(strArr[i]));
                    }
                }
                if(i != strArr.length - 1 ) {
                    sql.append("\r\n");
                    sql.append("\t\t");
                }
            }

            sql.append("\n");
            sql.append("</sql>");

            System.err.println(sql);
            sql.append("\r\n");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("******************************<sql></sql>语句 完成******************************");
        }
    }

    /**
     * 生成select 语句
     * @param obj
     */
    public static void selectMapper(Object obj) {
        if (obj != null) {
            StringBuffer sql = new StringBuffer();
            System.out.println("******************************select语句 开始生成******************************");
            sql.append("\r\n");

            //获取类名
            String name = obj.getClass().getName();

            sql.append("<select id=\"select\" parameterType=\"" + name + "\" resultMap=\"BaseResultMap\">");
            sql.append("\r\n\t");
            sql.append("select ");
            sql.append("\r\n");

            //对类名进行剪切
            name = name.substring(name.lastIndexOf(".") + 1, name.length());
            name = Tools.humpToUnderline(name).substring(1, Tools.humpToUnderline(name).length());

            //获取全部是属性名
            String[] strArr = Tools.getFiledName(obj);

            for (int i = 0; i < strArr.length; i++) {
                if (i == 0) {
                    sql.append("\t\t");
                }
                if (i != strArr.length - 1) {
                    sql.append(Tools.humpToUnderline(strArr[i]) + ",");
                } else {
                    sql.append(Tools.humpToUnderline(strArr[i]));
                }
                if(i != strArr.length - 1) {
                    sql.append("\r\n");
                    sql.append("\t\t");
                }
            }
            sql.append("\r");
            sql.append("\t from ");
            sql.append(name);
            sql.append("\n");
            sql.append("</select>");
            sql.append("\r\n");
            System.err.println(sql);
            sql.append("\r\n");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("******************************select语句 完成******************************");
        }
    }

    /**
     * 生成insert 语句
     *
     * @param obj
     */
    public static void insertMapper(Object obj) {
        if (obj != null) {
            StringBuffer sql = new StringBuffer();
            System.out.println("******************************insert语句 开始生成******************************");
            sql.append("\r\n");

            //获取类名
            String name = obj.getClass().getName();

            sql.append("<insert id=\"insert\" parameterType=\"" + name + "\" useGeneratedKeys=\"true\" keyProperty=\"id\">");
            sql.append("\r\n");
            sql.append("insert into ");

            //对类名进行剪切
            name = name.substring(name.lastIndexOf(".") + 1, name.length());
            name = Tools.humpToUnderline(name).substring(1, Tools.humpToUnderline(name).length());

            //拼接表名
            sql.append(name);
            sql.append("\r\n");

            sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" + "\r\n");

            //获取全部是属性名
            String[] strArr = Tools.getFiledName(obj);

            //用于保存返回的jdbc类型
            List<Map> list = new ArrayList<Map>();
            StringBuffer st = new StringBuffer();
            if (strArr != null && strArr.length > 0) {
                for (int i = 0; i < strArr.length; i++) {
                    //获得属性名
                    String attributeName = strArr[i];
                    String jdbcattributeName = Tools.humpToUnderline(attributeName);
                    String transformation = Tools.transformation(attributeName, obj);
                    if (!"jdbcType=暂无对应数据".equals(transformation)) {
                        Map map = new HashMap<>();
                        //属性名
                        map.put("name", attributeName);
                        //字段类型
                        map.put("type", transformation);
                        list.add(map);
                    } else {
                        //错误的进行记录，防止错误的漏了
                        st.append(attributeName);
                    }
                }

                //获取到全部属性名与字段类型后的处理
                String id = "";
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if ("error=1".equals(list.get(i).get("type"))) {
                            sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null\"> ");
                            sql.append(Tools.humpToUnderline((String) list.get(i).get("name")) + ",");
                            sql.append(" </if>");
                            sql.append("\r\n");
                        } else {
                            sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null and " + list.get(i).get("name") + " != ''\"" + "> ");
                            sql.append(Tools.humpToUnderline((String) list.get(i).get("name")) + ",");
                            sql.append(" </if>");
                            sql.append("\r\n");
                        }
                    }
                }
                sql.append("</trim>");
                sql.append("\r\n");

                //获取值属性
                sql.append("<trim prefix=\"values (\"  suffix=\")\" suffixOverrides=\",\">");
                sql.append("\r\n");
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if ("error=1".equals(list.get(i).get("type"))) {
                            sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null\"> ");
                            sql.append("#{" + list.get(i).get("name") + "},");
                            sql.append(" </if>");
                            sql.append("\r\n");
                        } else {
                            sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null and " + list.get(i).get("name") + " != ''\"" + "> ");
                            sql.append("#{" + list.get(i).get("name") + "},");
                            sql.append(" </if>");
                            sql.append("\r\n");
                        }
                    }
                    sql.append("</trim>");
                }
            }
            sql.append("\r\n");
            sql.append("</insert>");
            System.err.println(sql);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("******************************insert语句 完成******************************");
            System.err.println("无法转换的属性：" + st.toString());
        }
    }

    /**
     * 生成更新的语句
     *
     * @param obj
     * @param isBank 生成的<if>标签是否加 不等于空串，默认不加
     */
    public static void updateMapper(Object obj, Boolean isBank) {
        if(null == isBank) {
            isBank = false;
        }
        if (obj != null) {
            StringBuffer sql = new StringBuffer();
            System.out.println("******************************update语句 开始生成******************************");
            sql.append("\r\n");

            //获取类名
            String name = obj.getClass().getName();

            sql.append("<update id=\"update\" parameterType=\"" + name + "\">");
            sql.append("\r\n");

            //对类名进行剪切
            name = name.substring(name.lastIndexOf(".") + 1, name.length());
            name = Tools.humpToUnderline(name).substring(1, Tools.humpToUnderline(name).length());

            //拼接表名
            sql.append("update ");
            sql.append(name);

            //换行
            sql.append("\r\n");
            sql.append("<trim prefix=\"set\" suffixOverrides=\",\">" + "\r\n");

            //获取全部是属性名
            String[] strArr = Tools.getFiledName(obj);

            //用于保存返回的jdbc类型
            List<Map> list = new ArrayList<Map>();

            StringBuffer st = new StringBuffer();
            if (strArr != null && strArr.length > 0) {
                for (int i = 0; i < strArr.length; i++) {
                    //获得属性名
                    String attributeName = strArr[i];
                    String jdbcattributeName = Tools.humpToUnderline(attributeName);
                    String transformation = Tools.transformation(attributeName, obj);
                    if (!"jdbcType=暂无对应数据".equals(transformation)) {
                        Map map = new HashMap<>();
                        //属性名
                        map.put("name", attributeName);
                        //字段类型
                        map.put("type", transformation);
                        //数据库字段名
                        map.put("jdbcname", jdbcattributeName);
                        list.add(map);
                    } else {
                        //错误的进行记录，防止错误的漏了
                        st.append(attributeName);
                    }
                }

                //获取到全部属性名与字段类型后的处理
                String id = "";
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        //默认第一条为主键
                        if (i == 0) {
                            if ("error=1".equals(list.get(i).get("type"))) {
                                id = "where " + list.get(i).get("jdbcname") + "=#{" + list.get(i).get("name") + "}";
                            } else {
                                id = "where " + list.get(i).get("jdbcname") + "=#{" + list.get(i).get("name") + "," + list.get(i).get("type") + "}";
                            }
                        } else {
                            if ("error=1".equals(list.get(i).get("type"))) {
                                if(isBank) {
                                    sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null and " + list.get(i).get("name") + " != ''\"" +  ">");
                                }else {
                                    sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null\">");
                                }
                                sql.append(" " + list.get(i).get("jdbcname") + "=#{" + list.get(i).get("name") + "},");
                                sql.append(" </if>");
                                sql.append("\r\n");
                            } else {
                                if(isBank) {
                                    sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null and " + list.get(i).get("name") + " != ''\"" + ">");
                                }else {
                                    sql.append("\t" + "<if test=\"" + list.get(i).get("name") + " != null\">");
                                }
                                //sql.append("\r");
                                sql.append(" " + list.get(i).get("jdbcname") + "=#{" + list.get(i).get("name") + "," + list.get(i).get("type") + "},");
                                //sql.append("\r");
                                sql.append(" " + "</if>");
                                sql.append("\r\n");
                            }
                        }
                    }
                    sql.append("</trim>");
                    sql.append("\r\n");
                    sql.append(id);
                    sql.append("\r\n");
                    sql.append("</update>");
                    sql.append("\r\n");
                    System.err.println(sql);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("******************************update语句 完成******************************");
                    System.err.println("无法转换的属性：" + st.toString());
                }
            }
        }
    }

    /**
     * 内部工具类
     */
    static class Tools {
        private final static String UNDERLINE = "_";

        /**
         * 下划线命名转为驼峰命名
         *
         * @param para 下划线命名的字符串
         * @return
         */
        public static String underlineToHump(String para) {
            StringBuilder result = new StringBuilder();
            String[] a = para.split(UNDERLINE);
            for (String s : a) {
                if (!para.contains(UNDERLINE)) {
                    result.append(s);
                    continue;
                }
                if (result.length() == 0) {
                    result.append(s.toLowerCase());
                } else {
                    result.append(s.substring(0, 1).toUpperCase());
                    result.append(s.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }

        /***
         * 驼峰命名转为下划线命名
         * @param para 驼峰命名的字符串
         * @return
         */
        public static String humpToUnderline(String para) {
            StringBuilder sb = new StringBuilder(para);
            //定位
            int temp = 0;
            if (!para.contains(UNDERLINE)) {
                for (int i = 0; i < para.length(); i++) {
                    if (Character.isUpperCase(para.charAt(i))) {
                        sb.insert(i + temp, UNDERLINE);
                        temp += 1;
                    }
                }
            }
            return sb.toString().toLowerCase();
        }

        /**
         * 获取全部属性名并返回一个属性数组
         */
        public static String[] getFiledName(Object o) {
            // Field类的属性信息
            Field[] fields = o.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        }

        /**
         * 根据属性名转换为对应的 mybatis 的 jdbcType类型
         *
         * @param attribute 属性名
         * @param obj       model
         * @return
         */
        public static String transformation(String attribute, Object obj) {
            String type = getHandleType(attribute, obj);
            if (type != null && !"".equals(type) && !"null".equals(type)) {
                if ("String".equals(type)) {
                    return "jdbcType=VARCHAR";
                } else if ("BigDecimal".equals(type)) {
                    return "jdbcType=NUMERIC";
                } else if ("boolean".equals(type)) {
                    return "jdbcType=BOOLEAN";
                } else if ("byte".equals(type)) {
                    return "jdbcType=TINYINT";
                } else if ("short".equals(type)) {
                    return "jdbcType=SMALLINT";
                } else if ("int".equals(type)) {
                    return "jdbcType=INTEGER";
                } else if ("long".equals(type)) {
                    return "jdbcType=BIGINT";
                } else if ("float".equals(type)) {
                    return "jdbcType=REAL";
                } else if ("double".equals(type)) {
                    return "jdbcType=DOUBLE";
                } else if ("Time".equals(type)) {
                    return "jdbcType=TIME";
                } else if ("Timestamp".equals(type)) {
                    return "jdbcType=TIMESTAMP";
                } else if ("Clob".equals(type)) {
                    return "jdbcType=CLOB";
                } else if ("Blob".equals(type)) {
                    return "jdbcType=BLOB";
                } else if ("Array".equals(type)) {
                    return "jdbcType=ARRAY";
                } else if ("Struct".equals(type)) {
                    return "jdbcType=STRUCT";
                } else if ("Long".equals(type)) {
                    //return "jdbcType=BIGINT";
                    return "error=1";
            /*注意，发现date类型存在一个问题。如果传入实体对象里的字段是java.util.Date或者java.sql.Date或者java.sql.Time或者java.sql.Timestamp
                1， jdbcType并未指定的情况下，则返回日期和时分秒，
                2, jdbcType指定为”JdbcType.DATE”，则只返回日期，不带时分秒
                3, jdbcType指定为”JdbcType.TIME”，则只有时分秒有效！
                上述情况，还与数据库有关系，现测试到sybase如果传入new Date(), 那么保存的只有日期，没有具体时间，所以日期暂时不指定jdbcType
                对于Ibatis操作Date/Time/DateTime，总结如下：
                    将pojo的属性类型设置为java.sql.Date（或java.sql.Time, java.sql.Timestamp），此时会严格遵循这三种类型的语义。但此方法因存在前文中提到的性能问题，在JDK1.6以前的JDK版本中能少使用就少使用。
                    如果你想在pojo中使用java.util.Date， 则要注意：
                    完整的日期时间，要确保jdbcType为空，或为DATE,TIME以外的值
                    只需要时间，要指定jdbcType=”TIME”
                    只需要日期，要指定jdbcType=”DATE”
                */
                } else if ("Date".equals(type)) {
                    return "error=1";
                } else if ("LocalDateTime".equals(type)) {
                    return "error=1";
                }else if ("Integer".equals(type)) {
                    return "error=1";
                } else {
                    return "jdbcType=暂无对应数据";
                }
            }
            return "jdbcType=暂无对应数据";
        }

        /**
         * 获取字符串处理后的 属性类型
         * 经过本方法处理后的不在是 class java.lang.String  这种类型，而是 String 这样的类型
         *
         * @param attribute 属性名
         * @param obj       model
         * @return 属性对应的类型
         */
        public static String getHandleType(String attribute, Object obj) {
            List<Map> typeValueByName = getTypeValueByName(attribute, obj);
            if (typeValueByName != null && typeValueByName.size() == 1) {
                String type = null;
                for (int i = 0; i < typeValueByName.size(); i++) {
                    Map map = typeValueByName.get(0);
                    type = (String) map.get("type");
                }
                if (type != null) {
                    int contain = type.indexOf(".");
                    //判断是否有.来确定是否要截取
                    if (contain != -1) {
                        //截取 从 . 最后出现的位置开始
                        type = type.substring(type.lastIndexOf(".") + 1, type.length());
                        return type;
                    } else {
                        return type;
                    }
                }
            }
            return null;
        }

        /**
         * 根据属性名获取属性值 和 类型
         *
         * @param fieldName 属性名
         * @param obj       对象
         * @return 属性值 和 类型
         */
        public static List<Map> getTypeValueByName(String fieldName, Object obj) {
            try {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getTer = "get" + firstLetter + fieldName.substring(1);
                Method method = obj.getClass().getMethod(getTer, new Class[]{});
                Object value = method.invoke(obj, new Object[]{});
                List<Map> list = new ArrayList<>();
                Map map = new HashMap<>();
                map.put("value", value); //值
                Field declaredField = obj.getClass().getDeclaredField(fieldName);//根据属性名获取属性
                String type = declaredField.getType().toString();
                map.put("type", type);
                list.add(map);
                return list;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
