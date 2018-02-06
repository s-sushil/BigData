package com.practise.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import java.util.Arrays;
import java.util.List;


public class SparkBasic1 {
    public static void main(String args[]){
        try {
            String s="";
            String inputfile = "/Users/sushil/Documents/Docs/employee_records.txt";

            SparkConf conf = new SparkConf().setAppName("firstapp").setMaster("local");
            JavaSparkContext ctx = new JavaSparkContext(conf);

            JavaRDD<String> filerdd = ctx.textFile(inputfile);
            System.out.print("take transformation ::....... "+filerdd.take(4));
            System.out.print("storage lavel............."+filerdd.getStorageLevel());

            JavaRDD<String> fileresrdd =  filerdd.map(new Function<String, String>() {
                public String call(String str){
                    //return  str+"ing"+"\n";
                    return  null ;//s+=str;
                }
            });
            fileresrdd.foreach(new VoidFunction<String>() {
                public void call(String s){
                    System.out.print("String   ....... "+s+"   ");
                }
            });
            System.out.print("collect transformation..."+filerdd.collect().toString());
            List<Integer> list = Arrays.asList(1,2,3,4,5,6);
            JavaRDD<Integer> numrdd = ctx.parallelize(list);
            JavaRDD<Integer> resrdd  = numrdd.map(new Function<Integer, Integer>() {
                public Integer call(Integer i) throws Exception {
                    return i*2;
                }
            });
            resrdd.foreach(new VoidFunction<Integer>(){
                public void call(Integer i) throws Exception{
                    System.out.print("mull ....... "+i+"   ");
                }
            });

            // ******************************************************************************

            JavaRDD<Integer> evenRdd = numrdd.filter(new Function<Integer, Boolean>(){
                public Boolean call(Integer i){
                    System.out.print(i%2);
                    return i%2 == 0;
                }
            });
            evenRdd.foreach(new VoidFunction<Integer>(){
                public void call(Integer i) throws Exception{
                    System.out.print("even num ....... "+i+"  ");
                }
            });
        }
        catch (Exception e){
            System.out.print(e);
        }
    }
}
