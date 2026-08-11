import sys
sys.path.insert(0,"streaming")
from quality_rules import validate
def test_valid(): assert validate({"orderId":1,"userId":1,"amount":100,"timestamp":"2026-08-12T00:00:00Z"})==[]
def test_invalid(): assert validate({"orderId":1,"userId":1,"amount":-1,"timestamp":"x"})
