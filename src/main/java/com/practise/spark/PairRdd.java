package com.practise.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class PairRdd {

    private static JavaSparkContext sc;


    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("tuplerdd").setMaster("local");
        JavaSparkContext ctx = new JavaSparkContext(conf);

        List<Tuple2<String,String>> listR = new ArrayList<Tuple2<String,String>>();
        listR.add(new Tuple2<String,String>("a1", "a2"));
        listR.add(new Tuple2<String,String>("b1", "b2"));
        listR.add(new Tuple2<String,String>("c1", "c2"));

        List<Tuple2<String,String>> listS = new ArrayList<Tuple2<String,String>>();
        listS.add(new Tuple2<String,String>("d1", "d2"));
        listS.add(new Tuple2<String,String>("e1", "e2"));
        listS.add(new Tuple2<String,String>("f1", "f2"));
        listS.add(new Tuple2<String,String>("g1", "g2"));

        JavaPairRDD<String,String > R = ctx.parallelizePairs(listR);
        JavaPairRDD<String, String> S = ctx.parallelizePairs(listS);

        JavaPairRDD<Tuple2<String,String>, Tuple2<String,String>> cart = R.cartesian(S);

        // save the result
        cart.saveAsTextFile("/Users/sushil/Documents/Docs/newdoc");

        // done
        ctx.close();
        System.exit(0);
    }
}
