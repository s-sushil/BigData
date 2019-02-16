import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        demo();
    }

    public static void svm(){
        SparkSession spark = SparkSession.builder().appName("Classification").master("local[1]").getOrCreate();
        // Load training data
        Dataset<Row> training = spark.read().format("libsvm")
                .load("/home/sushil/Desktop/testing_pgm/machine learning/dataset/lpsa.dat");
        System.out.println(training.take(2));

    }

    public static void regextest() {
        String s = "1527226414395,5,19.25,30,0";
        String s2 = s.replaceAll("([0-9]{1,}.[0-9]{1,},[0-9]{1,}.[0-9]{1,},[0-9]{1,}.[0-9]{1,}),(.*)","$1");
        System.out.println(s);
        System.out.println(s2);
    }

    public static void demo(){

        String sub ="Mr. ";

        List<String> al=new ArrayList<String>();
        List<String> al2=new ArrayList<String>();
        al.add("Amit");
        al.add("Vijay");
        al.add("Kumar");
        al.add(1,"Sachin");

        for(String s : al){
            System.out.println(al2.add(sub+"hellow "+s));
        }
        for(String s : al2){
            System.out.println(s);
        }
    }
}
