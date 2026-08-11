import json, os, time
import psycopg2
from kafka import KafkaConsumer
from quality_rules import validate

K=os.getenv("KAFKA_BOOTSTRAP_SERVERS","kafka:9092")
DB=dict(host=os.getenv("POSTGRES_HOST","postgres"),port=int(os.getenv("POSTGRES_PORT","5432")),
        dbname=os.getenv("POSTGRES_DB","shopflow"),user=os.getenv("POSTGRES_USER","shopflow"),
        password=os.getenv("POSTGRES_PASSWORD","shopflow"))

def wait_db():
    while True:
        try:
            c=psycopg2.connect(**DB);c.close();return
        except Exception as e: print("Waiting for DB:",e);time.sleep(3)

def main():
    wait_db()
    while True:
        try:
            consumer=KafkaConsumer("shopflow.orders","shopflow.payments",
                bootstrap_servers=K,group_id="shopflow-analytics",
                auto_offset_reset="earliest",value_deserializer=lambda v:json.loads(v.decode()))
            break
        except Exception as e: print("Waiting for Kafka:",e);time.sleep(5)
    conn=psycopg2.connect(**DB)
    print("ShopFlow stream processor running")
    for m in consumer:
        e=m.value
        if m.topic!="shopflow.orders": print("EVENT:",m.topic,e);continue
        failures=validate(e); status="FAIL" if failures else "PASS"
        with conn.cursor() as cur:
            cur.execute("""INSERT INTO analytics_events(event_id,event_type,user_id,order_id,amount,payload,event_timestamp)
                           VALUES(%s,%s,%s,%s,%s,%s::jsonb,%s) ON CONFLICT(event_id) DO NOTHING""",
                        (f"{e.get('eventType')}-{e.get('orderId')}",e.get("eventType"),e.get("userId"),e.get("orderId"),e.get("amount"),json.dumps(e),e.get("timestamp")))
            for rule,reason in failures:
                cur.execute("INSERT INTO data_quality_failures(event_id,rule_name,severity,reason) VALUES(%s,%s,%s,%s)",
                            (f"{e.get('eventType')}-{e.get('orderId')}",rule,"HIGH",reason))
        conn.commit()
        print(f"ORDER {e.get('orderId')} | quality={status}")
if __name__=="__main__": main()
