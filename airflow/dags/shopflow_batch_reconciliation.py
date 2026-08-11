from datetime import datetime,timedelta
import os,psycopg2
from airflow import DAG
from airflow.operators.python import PythonOperator

DB=dict(host=os.getenv("SHOPFLOW_POSTGRES_HOST","postgres"),port=int(os.getenv("SHOPFLOW_POSTGRES_PORT","5432")),
        dbname=os.getenv("SHOPFLOW_POSTGRES_DB","shopflow"),user=os.getenv("SHOPFLOW_POSTGRES_USER","shopflow"),
        password=os.getenv("SHOPFLOW_POSTGRES_PASSWORD","shopflow"))

def reconcile():
 c=psycopg2.connect(**DB)
 with c.cursor() as cur:
  cur.execute("SELECT COUNT(*) FROM orders"); processed=cur.fetchone()[0]
  cur.execute("SELECT COUNT(*) FROM analytics_events"); analytics=cur.fetchone()[0]
  rejected=max(processed-analytics,0)
  score=100 if processed==0 else round(max(0,(processed-rejected)/processed*100),2)
  cur.execute("""INSERT INTO pipeline_runs(pipeline_name,status,records_processed,records_rejected,quality_score,finished_at)
                 VALUES(%s,%s,%s,%s,%s,CURRENT_TIMESTAMP)""",("shopflow_batch_reconciliation","SUCCESS" if score>=95 else "WARNING",processed,rejected,score))
 c.commit();c.close()

with DAG("shopflow_batch_reconciliation",start_date=datetime(2026,1,1),schedule="0 2 * * *",catchup=False,
         default_args={"retries":1,"retry_delay":timedelta(minutes=2)},tags=["shopflow","de"]) as dag:
 t=PythonOperator(task_id="reconcile_and_quality_check",python_callable=reconcile)
