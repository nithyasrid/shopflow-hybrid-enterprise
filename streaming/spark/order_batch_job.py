from pyspark.sql import SparkSession
from pyspark.sql.functions import col,count,sum as sum_,avg,to_date
spark=SparkSession.builder.appName("ShopFlowBatch").getOrCreate()
df=spark.read.option("header",True).option("inferSchema",True).csv("data/orders.csv")
out=df.filter(col("status")=="CONFIRMED").withColumn("order_date",to_date("created_at")).groupBy("order_date").agg(count("*").alias("orders"),sum_("total_amount").alias("revenue"),avg("total_amount").alias("aov"))
out.write.mode("overwrite").parquet("data/analytics")
spark.stop()
