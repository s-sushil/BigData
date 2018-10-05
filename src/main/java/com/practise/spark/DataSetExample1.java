import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SparkConf cnf = new SparkConf().setAppName("firstapp").setMaster("local");
        JavaSparkContext ctx = new JavaSparkContext(cnf);
        JavaRDD<String> filerdd = ctx.textFile("/home/sushil/Desktop/iplist.txt");
        SparkSession session = SparkSession.builder().getOrCreate();
        Dataset<Row> dataset = session.read().format("").csv("/home/sushil/Desktop/testing_pgm/heaterdata2.csv");
        System.out.println("done...");
        dataset.show();
        
    }
}
