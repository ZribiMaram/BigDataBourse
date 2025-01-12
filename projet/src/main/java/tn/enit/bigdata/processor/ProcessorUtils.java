package tn.enit.bigdata.processor;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import com.datastax.spark.connector.japi.CassandraJavaUtil;

import scala.Tuple2;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Column;
import static org.apache.spark.sql.functions.*;

import tn.enit.bigdata.entity.AverageActionData;
import tn.enit.bigdata.entity.BourseAction;
import org.apache.spark.sql.expressions.Window;
import static com.datastax.spark.connector.japi.CassandraStreamingJavaUtil.javaFunctions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ProcessorUtils {

    public static SparkConf getSparkConf(Properties prop) {
        SparkConf sparkConf = new SparkConf()
                .setAppName(prop.getProperty("tn.enit.bigdata.spark.app.name"))
                .setMaster(prop.getProperty("tn.enit.bigdata.spark.master"))
                .set("spark.cassandra.connection.host", prop.getProperty("tn.enit.bigdata.cassandra.host"))
                .set("spark.cassandra.connection.port", prop.getProperty("tn.enit.bigdata.cassandra.port"))
                .set("spark.cassandra.auth.username", prop.getProperty("tn.enit.bigdata.cassandra.username"))
                .set("spark.cassandra.auth.password", prop.getProperty("tn.enit.bigdata.cassandra.password"))
                .set("spark.cassandra.connection.keep_alive_ms", prop.getProperty("tn.enit.bigdata.cassandra.keep_alive"));


 
        if ("local".equals(prop.getProperty("tn.enit.bigdata.env"))) {
            sparkConf.set("spark.driver.bindAddress", "127.0.0.1");
        }
        return sparkConf;
    }

 
    public static void saveLocationToCassandra(final JavaDStream<BourseAction> dataStream) {
        System.out.println("Saving to Cassandra...");

    
        HashMap<String, String> columnNameMappings = new HashMap<>();
        columnNameMappings.put("id", "id");
        columnNameMappings.put("actionNom", "actionNom");
        columnNameMappings.put("prix", "prix");
      


     
        javaFunctions(dataStream).writerBuilder("bourseactionkeyspace", "bourse_action",
                CassandraJavaUtil.mapToRow(BourseAction.class, columnNameMappings)).saveToCassandra();
                
             
    }

    public static void saveAvgToCassandra(JavaRDD<AverageActionData> rdd) {
        CassandraJavaUtil.javaFunctions(rdd)
                .writerBuilder("bourseActionKeyspace", "average_action_data", CassandraJavaUtil.mapToRow(AverageActionData.class))
                .saveToCassandra();
    }

 
 
  public static void saveDataToHDFS(final JavaDStream<BourseAction> dataStream, String saveFile, SparkSession sql) {
        System.out.println("Saving to HDFS...");

        dataStream.foreachRDD(rdd -> {
            if (rdd.isEmpty()) {
                return;
            }
            Dataset<Row> dataFrame = sql.createDataFrame(rdd, BourseAction.class);

            // Select and save required columns
            Dataset<Row> dfStore = dataFrame.selectExpr("id", "actionNom","prix","timestamp");
            dfStore.printSchema();
            dfStore.write().mode(SaveMode.Append).parquet(saveFile);
        });
    }
 
 
    public static BourseAction transformData(Row row) {
        System.out.println(row);
        return new BourseAction(row.getString(0),row.getString(1), row.getDouble(2));
    }

    public static List<AverageActionData> runBatch(SparkSession sparkSession, String parquetPath, String outputPath) {
        System.out.println("Running Batch Processing");

        // Read data from the parquet file
        Dataset<Row> dataFrame = sparkSession.read().parquet(parquetPath);
        System.out.println(dataFrame);

        // Convert DataFrame to JavaRDD
        JavaRDD<BourseAction> rdd = dataFrame.javaRDD().map(row -> transformData(row));

  
        JavaRDD<AverageActionData> averageDataRDD = rdd
                .mapToPair(BourseAction -> new Tuple2<>(BourseAction.getId(), BourseAction))
                .groupByKey()
                .mapValues((Iterable<BourseAction> BourseActions) -> {
                    double totalPrix = 0;
                    
                    long count = 0;

                    for (BourseAction BourseAction : BourseActions) {
                        totalPrix += BourseAction.getPrix();
                       
                        count++;
                    }

                    // Calculate averages
                    double avgPrix = totalPrix / count;
                  

                    return new AverageActionData(BourseActions.iterator().next().getId(), avgPrix);
                })
                .values();

        // Collect results into a list
        List<AverageActionData> averageDataList = averageDataRDD.collect();

         
        for (AverageActionData data : averageDataList) {
            System.out.println("action ID: " + data.getId() + ", Avg prix: " + data.getAveragePrix() );
        }

        return averageDataList;
    }


      
}
 