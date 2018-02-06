package com.practise.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

public class SparkBasic3
{
    public static void main(String[] args) {
        try {
            final String line="";
            String inputfile = "/Users/sushil/Documents/Docs/sparkfile.txt";

            SparkConf conf = new SparkConf().setAppName("practise").setMaster("local");
            JavaSparkContext ctx = new JavaSparkContext(conf);
            JavaRDD<String> file = ctx.textFile(inputfile);

/*            // RDD is immutable, let's create a new RDD which doesn't contain empty lines

            JavaRDD<String> nonemptylines = file.filter(new Function<String, Boolean>(){
                public Boolean call(String s){
                    if(s == null || s.length() < 1)
                        return false;
                    else
                        return true;
                }
            }
            );
            nonemptylines.foreach(new VoidFunction<String>() {
                public void call(String s) throws Exception {
                    System.out.println("line is.."+s);
                }
            });

            long count = nonemptylines.count();
            System.out.println(String.format("Total lines in "+count));*/
        JavaRDD<String> rdd1 = file.map(new Function<String, String>(){
            public String call(String s){
                String s1="";

                String tok[]=s.split(",");

                for(String s2:tok){
                    return s1+=s2;
                }
                return s1;
            }
        });
        rdd1.foreach(new VoidFunction<String>()
        {
            public void call(String s) throws Exception{
                System.out.println(s);
            }
        });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
