import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SaveMode
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable


object SparkStreamingApp {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("mapExample").master("local").getOrCreate()

    //  create a StreamingContext, the main entry point for all streaming functionality
    val rdd1 = spark.sparkContext.parallelize(List(20,32,45,62,8,5))
    val sum = rdd1.reduce(_+_)
    println(sum)

    val sumfold = rdd1.fold(0)((x,y)=>x+y)
    println("fold exampl..."+sumfold)


  }

}
