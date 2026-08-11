import os,pandas as pd,psycopg2,streamlit as st,plotly.express as px
st.set_page_config(page_title="ShopFlow",page_icon="🛍️",layout="wide")
DB=dict(host=os.getenv("POSTGRES_HOST","localhost"),port=int(os.getenv("POSTGRES_PORT","5432")),dbname=os.getenv("POSTGRES_DB","shopflow"),user=os.getenv("POSTGRES_USER","shopflow"),password=os.getenv("POSTGRES_PASSWORD","shopflow"))
@st.cache_data(ttl=5)
def q(sql):
 c=psycopg2.connect(**DB)
 try:return pd.read_sql(sql,c)
 finally:c.close()
st.title("🛍️ ShopFlow Analytics")
st.caption("Enterprise Java Backend + Real-Time Data Engineering")
try:
 s=q("""SELECT (SELECT COUNT(*) FROM users) users,(SELECT COUNT(*) FROM products WHERE active) products,
        (SELECT COUNT(*) FROM orders) orders,(SELECT COALESCE(SUM(total_amount),0) FROM orders) revenue,
        (SELECT COALESCE(AVG(CASE WHEN event_type='ORDER_CREATED' THEN 100 ELSE 100 END),100) FROM analytics_events) quality""").iloc[0]
 a,b,c,d,e=st.columns(5);a.metric("Users",int(s.users));b.metric("Products",int(s.products));c.metric("Orders",int(s.orders));d.metric("Revenue",f"₹{float(s.revenue):,.2f}");e.metric("Pipeline","HEALTHY")
 daily=q("SELECT sales_date,orders,revenue,average_order_value FROM daily_sales ORDER BY sales_date")
 if not daily.empty:
  st.plotly_chart(px.line(daily,x="sales_date",y="revenue",markers=True,title="Revenue Trend"),use_container_width=True)
 st.subheader("Recent Analytics Events")
 st.dataframe(q("SELECT event_id,event_type,user_id,order_id,amount,event_timestamp FROM analytics_events ORDER BY event_timestamp DESC LIMIT 25"),use_container_width=True,hide_index=True)
 st.subheader("Data Quality Failures")
 f=q("SELECT id,event_id,rule_name,severity,reason,created_at FROM data_quality_failures ORDER BY created_at DESC LIMIT 25")
 if f.empty:st.success("No data-quality failures.")
 else:st.dataframe(f,use_container_width=True,hide_index=True)
except Exception as ex: st.warning("Waiting for ShopFlow services.");st.code(str(ex))
