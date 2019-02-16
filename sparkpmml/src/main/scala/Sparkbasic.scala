import org.apache.avro.generic.GenericData.StringType
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{IntegerType, StructField, StructType}
import org.apache.spark.streaming.{Seconds, StreamingContext}



object Sparkbasic {
  def main(args: Array[String]): Unit = {
    joinexample()

  }

  def sparkContextDemo(): Unit = {
    // Word Count example
    val sparkconf = new SparkConf().setAppName("wordcount").setMaster("local")
    val sc = new SparkContext(sparkconf)
    val data = sc.textFile("/home/sushil/Desktop/word.txt")
    val words = data.flatMap(line => line.split("\n"))
    val wordcount = words.map(words => (words, 1)).reduceByKey(_ + _)
    wordcount.saveAsTextFile("/home/sushil/Desktop/output")
    sc.stop()
  }

  def sparkSessionDemo(): Unit = {
    val spark = SparkSession.builder().appName("Test").master("local").getOrCreate()
    val data = spark.read.textFile("/home/sushil/Desktop/word.txt").rdd
    val maprdd = data.map(line => (line, line.length))
    maprdd.foreach(println)
  }

  def flatmapDemo(): Unit = {
    val spark = SparkSession.builder().appName("Test").master("local").getOrCreate()
    val data = spark.read.textFile("/home/sushil/Desktop/mail").rdd
    val flatmapfile = data.flatMap(line => line.split(" "))
    flatmapfile.foreach(println)

    // filter(func) Example
    val filterrdd = data.flatMap(line => line.split(" ")).filter(value => value == "spark")
    print("Number of filterRDD  is " + filterrdd.count() + "\n")

    // Iterating the filter RDd
    filterrdd.foreach(println)

    // union and intersection

    val rdd1 = spark.sparkContext.parallelize(Seq((1, "jan", 2016), (3, "nov", 2014), (16, "feb", 2014)))
    val rdd2 = spark.sparkContext.parallelize(Seq((5, "dec", 2014), (17, "sep", 2015), (3, "nov", 2014)))
    val rdd3 = spark.sparkContext.parallelize(Seq((6, "dec", 2011), (16, "may", 2015)))
    val rddUnion = rdd1.union(rdd2).union(rdd3)
    rddUnion.foreach(println)
    rddUnion.saveAsTextFile("/home/sushil/Desktop/output")
    rddUnion.count()

    print("Intersection RDD......")
    val commanrdd = rdd1.intersection(rdd2)
    commanrdd.foreach(println)

    // distinct Example
    print("Distinct RDD......")
    val rdd4 = spark.sparkContext.parallelize(Seq(("tom"), ("sam"), ("mic"), ("sam"), ("alic"), ("tom")))
    val distRdd = rdd4.distinct()
    distRdd.foreach(println)

  }

  def sparkDataFrameDemo(): Unit = {
    import org.apache.spark.sql.functions._
    val spark = SparkSession
      .builder()
      .appName("Test")
      .master("local")
      .getOrCreate()

    val df = spark.read.json("/home/sushil/Desktop/testfiles/emp.json")
    val df2 = spark.read.format("csv").option("header", "true").load("/home/sushil/Desktop/testfiles/ecommercedata.csv")
    df.show()
    //df.groupBy("name").count().show()
    //df2.groupBy("productid").count().show()

    df.filter("age==23 || age==25").show()

    df.filter("age==23").show()



    // df2.select("userid","productid","action").groupBy("productid").count().show()

    //df2.select(df2.col("*")).where(df2.col("Click").leq(10)).show()
    //df2.filter("action=Click").show()
  }


  def test(): Unit = {
    val spark = SparkSession.builder().appName("carexample").master("local").getOrCreate()
    val rawData = spark.sparkContext.textFile("/home/sushil/Desktop/testfiles/sample2.txt")
    val datardd = rawData.map(line => (line, line.replaceAll("Spark", "")))
    datardd.foreach(println)


    val flatmaprdd = rawData.flatMap(line => line.split(" ")).filter(value => value == "spark")
    flatmaprdd.foreach(println)
    println("count is.." + flatmaprdd.count())

    val rdd1 = spark.sparkContext.parallelize(Seq((1, "jan", 2016), (3, "nov", 2014), (16, "feb", 2014)))
    val rdd2 = spark.sparkContext.parallelize(Seq((5, "dec", 2014), (17, "sep", 2015), (3, "nov", 2014)))
    val rdd3 = spark.sparkContext.parallelize(Seq((6, "dec", 2011), (16, "may", 2015), (6, "dec", 2011)))
    val rddUnion = rdd1.union(rdd2)
    val int = rdd1.intersection(rdd2)
    rddUnion.foreach(println)
    //rddUnion.saveAsTextFile("/home/sushil/Desktop/output")
    //int.saveAsTextFile("/home/sushil/Desktop/output")
    val distrdd = rdd3.distinct()
    // distrdd.saveAsTextFile("/home/sushil/Desktop/output")
    rddUnion.count()

    val data2 = spark.sparkContext.parallelize(Array(('k', 5), ('s', 3), ('s', 4), ('p', 7), ('p', 5), ('t', 8), ('k', 6)), 4)
    val grpdata = data2.groupByKey().collect()
    grpdata.foreach(println)

    val words = Array("one", "two", "two", "four", "five", "six", "six", "eight", "nine", "ten", "one", "two", "two", "four", "five", "six", "six", "eight", "nine", "ten")
    val dataword = spark.sparkContext.parallelize(words).map(w => (w, 1)).reduceByKey(_ + _)
    dataword.foreach(println)

    val sorteddata = data2.sortByKey().collect()
    sorteddata.foreach(println)


    val data3 = spark.sparkContext.parallelize(Array(('A', 1), ('b', 2), ('c', 3)))
    val data4 = spark.sparkContext.parallelize(Array(('A', 4), ('A', 6), ('b', 7), ('c', 3), ('c', 8)))
    val joinrdd = data3.join(data4)
    joinrdd.foreach(println)

    val coalres = data2.coalesce(2)
    println("coalease result..........")
    coalres.foreach(println)

    // //////////////// Action Example //////////////////////////
    println("Number of rdd...." + "\n")
    println(data3.count())

    print(dataword.collect().mkString(","))
    println(rawData.collect().mkString("......"))

    // Take(n) Example
    val takex = rawData.take(2)
    takex.foreach(println)

    val countbyval = rawData.map(line => (line, line.length)).countByValue()
    countbyval.foreach(println)

    val reduce_ex = rawData.reduce(_ + _)
    reduce_ex.foreach(println)
  }

  def reducefoldDiff(): Unit = {

    val spark = SparkSession.builder().appName("reduce&foldDiff").master("local[2]").getOrCreate()
    val rdd1 = spark.sparkContext.parallelize(List(20,32,45,62,8,5))
    val sum = rdd1.reduce(_+_)
    println(sum)

    val sumfold = rdd1.fold(0)((x,y)=>x+y)
    println("fold exampl..."+sumfold)
  }

  def coalesceexmp(): Unit = {

    val spark = SparkSession.builder().appName("reduce&foldDiff").master("local[2]").getOrCreate()
    val myrdd1 = spark.sparkContext.parallelize(1 to 1000, 16)
    println("number of partition...."+myrdd1.partitions.length)
    val myrdd2 = myrdd1.coalesce(6,false)
    println("number of partition...."+myrdd2.partitions.length)
    println(myrdd1.collect().mkString(","))
  }

  def joinexample(): Unit = {

    val spark = SparkSession.builder().appName("reduce&foldDiff").master("local[2]").getOrCreate()
    val rdd1 = spark.sparkContext.parallelize(Seq(("m",55),("m",56),("e",57),("e",58),("s",59),("s",54),("z",54)))
    val rdd2 = spark.sparkContext.parallelize(Seq(("m",60),("m",65),("s",61),("s",62),("h",63),("h",64)))
    val joinrdd = rdd1.join(rdd2)
    println(joinrdd.collect().mkString(","))

    val rddleft = rdd1.leftOuterJoin(rdd2)
    println(joinrdd.collect().mkString(","))
  }
  }
