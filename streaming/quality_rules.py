def validate(event):
    failures=[]
    if not event.get("orderId"): failures.append(("MISSING_ORDER_ID","orderId is missing"))
    if event.get("userId") is None: failures.append(("MISSING_USER_ID","userId is missing"))
    try:
        if float(event.get("amount", -1)) < 0: failures.append(("INVALID_AMOUNT","amount is negative"))
    except Exception: failures.append(("INVALID_AMOUNT","amount is not numeric"))
    if not event.get("timestamp"): failures.append(("MISSING_TIMESTAMP","timestamp is missing"))
    return failures
