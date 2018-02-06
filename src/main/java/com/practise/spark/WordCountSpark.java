package com.practise.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class WordCountSpark {
    public static void main(String[] args) throws Exception {

        // Define a configuration to use to interact with Spark
        SparkConf conf = new SparkConf().setAppName("word count").setMaster("local");

        // Create a Java version of the Spark Context from the configuration
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Load the input data, which is a text file read from the command line
        JavaRDD<String> input = sc.textFile("/Users/sushil/Documents/Docs/temp.txt");

        // Java 7 and earlier
        JavaRDD<String> words = input.flatMap(
                new FlatMapFunction<String, String>() {
                    public List<String> call(String s) {
                        return Arrays.asList(s.split(" "));
                    }
                } );

        // Java 7 and earlier: transform the collection of words into pairs (word and 1)
        JavaPairRDD<String, Integer> counts = words.mapToPair(
                new PairFunction<String, String, Integer>(){
                    public Tuple2<String, Integer> call(String s){
                        return new Tuple2(s, 1);
                    }
                } );

        // Java 7 and earlier: count the words
        JavaPairRDD<String, Integer> reducedCounts = counts.reduceByKey(
                new Function2<Integer, Integer, Integer>(){
                    public Integer call(Integer x, Integer y){ return x + y; }
                } );

        // Save the word count back out to a text file, causing evaluation.
        reducedCounts.saveAsTextFile( "output" );



    }
}
