package com.practise.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class AverageAndTotalSalCompute {
    public static void main(String args[]){
        Configuration conf  = new Configuration();
        try {
            Job job = new Job(conf,"Average and total sal");
            try {
                job.setJarByClass(AverageAndTotalSalCompute.class);
                job.setMapperClass(MyMapper.class);
                job.setReducerClass(MyReducer.class);
                job.setOutputKeyClass(Text.class);
                job.setOutputValueClass(FloatWritable.class);

                Path input = new Path(args[0]);
                Path output = new Path(args[1]);
                FileInputFormat.addInputPath(job, input);
                FileOutputFormat.setOutputPath(job, output);
                FileSystem fs = FileSystem.get(conf);
                if(fs.exists(output))
                    fs.delete(output,true);
                System.exit(job.waitForCompletion(true) ? 0 : 1);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(e);
        }

    }

    public static class MyMapper extends Mapper<LongWritable, Text, Text, FloatWritable>{
        public void map(LongWritable key, Text val, Context con){
            String words[] = val.toString().split("\\t");
            String sex = words[3];
            try {
                Float sal = Float.parseFloat(words[8]);
                con.write(new Text(sex),new FloatWritable(sal));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MyReducer extends Reducer<Text, FloatWritable, Text, Text>{
        public void reduce(Text key, Iterable<FloatWritable> values, Context con){
            Float total =0f;
            int count = 0;
            for(FloatWritable val : values){
                total+=val.get();
                count++;
            }
            Float avg = total/count;
            String out  = "Total Sal: "+total+" Average Sa: "+avg;
            try {
                con.write(key, new Text(out));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
