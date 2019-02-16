
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.classification.DecisionTreeClassifier;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.SparkSession;

public class IrisPMMLGenerator {
    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("pmml").setMaster("local");
        SparkContext sc = new SparkContext(conf);

        String path = "/home/sushil/Desktop/testing_pgm/machine learning/flower_data2.csv";

        //String path = "/home/sushil/Desktop/testing_pgm/machine learning/flower_data2.csv";

        //String path = "/home/sushil/Desktop/testing_pgm/machine learning/pmmlmodel/heaterdata.csv";

        JavaRDD<String> data = sc.textFile(path, 10).toJavaRDD();

        //System.out.println("FileData : "+data.take(3));

        JavaRDD<Vector> parsedData = data.map(s -> {
            String[] sarray = s.split(",");
            double[] values = new double[sarray.length];
            for (int i = 0; i < sarray.length-1; i++) {
                values[i] = Double.parseDouble(sarray[i]);
            }
            return Vectors.dense(values);
        });

        parsedData.cache();

        System.out.println("parsedData : "+parsedData.take(6));

        int numClusters = 3;
        int numIterations = 20;

        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);
        clusters.toPMML("/home/sushil/Desktop/testing_pgm/machine learning/Iris_KMeansCluster.pmml");

        System.out.println("done..");
    }
}
