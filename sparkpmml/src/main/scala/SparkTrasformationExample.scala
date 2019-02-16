import org.apache.spark.sql.SparkSession

object SparkTrasformationExample {


  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("mapExample").master("local").getOrCreate()
    val data = spark.read.textFile("/home/sushil/Desktop/testfiles/sparktest.txt").rdd
    val mapFile = data.map(line => (line,line.length))

    mapFile.foreach(println)

    val flatmapfile = data.flatMap(line => line.split( " "))
    flatmapfile.foreach(println)

    // filter(func) Example
    val mapfile2 = data.flatMap(line => line.split(" ")).filter(value => value=="he")
    print("count is :"+mapfile2.count())
    mapfile2.foreach(println)

    // union(dataset) Example
    val rdd1 = spark.sparkContext.parallelize(Seq((1,"jan", 2018),(2, "feb", 2108),(3, "march", 2018)))
    val rdd2 = spark.sparkContext.parallelize(Seq((1,"jan", 2017),(2, "feb", 2107),(3, "march", 2017),(4, "april", 2017),(2, "feb", 2108)))
    val unionrdd = rdd1.union(rdd2)
    unionrdd.foreach(println)
    print("unionrddcount ::"+unionrdd.count())

    // intersection(other-dataset) Example
    val commanrdd = rdd2.intersection(rdd1)
    print("rdd intersection count :::"+commanrdd.count())
    commanrdd.foreach(println)

    // sortByKey() Example
    val rdd3 = spark.sparkContext.parallelize(Seq(("maths",52), ("english",75), ("science",82), ("computer",65), ("maths",85)))
    val sorted = rdd3.sortByKey()
    sorted.foreach(println)

    //  join() Example
    val rdd4 = spark.sparkContext.parallelize(Array(('A',1),('b',2),('c',3)))
    val rdd5 =spark.sparkContext.parallelize(Array(('A',4),('A',6),('b',7),('c',3),('c',8),('A',99)))
    val joinreslut = rdd4.join(rdd5)
    joinreslut.foreach(println)

    val data2 = spark.read.textFile("/home/sushil/Desktop/testfiles/sparktest.txt")
    val mapdata = data.flatMap(lines => lines.split(" "))
    print("Count is :"+mapdata.count())
    println()

  }

}
