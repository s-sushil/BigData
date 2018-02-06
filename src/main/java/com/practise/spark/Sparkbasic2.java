package com.practise.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.List;

public class Sparkbasic2 {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("basic2").setMaster("local");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        System.out.println("hi...");

        JavaRDD<String> filerdd = ctx.textFile("/Users/sushil/Documents/Docs/employee_records.txt");

        System.out.println(filerdd.count());
        System.out.println(filerdd.count());







    }
}
