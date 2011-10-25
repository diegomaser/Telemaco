from difflib import SequenceMatcher
from telemaco.models import Place

#Reference: http://en.wikipedia.org/wiki/Approximate_string_matching
#Possible improvements: http://seatgeek.com/blog/dev/fuzzywuzzy-fuzzy-string-matching-in-python

def ratio(stra, strb):
    m = SequenceMatcher(None, stra, strb)
    ratio = m.ratio()
    return round(ratio*100, 2)

def getPlace(topic):
    max_ratio = 0
    max_place = None
    for p in Place.objects.all():
        ratio = ratio(topic, p.name)
        if ratio > max_ratio:
            max_ratio = ratio
            max_place = p
    return max_place
