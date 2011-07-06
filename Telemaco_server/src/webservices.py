import simplejson
import urllib, urllib2
from xml.dom.minidom import parseString
#import flickrapi

GOOGLE_MAPS_KEY="abcdef"
#FLICKR_KEY = ""
WIKIPEDIA = 1
WIKITRAVEL = 2
LANG='en'

def getWikiPage(name, page, lang=LANG):
    text = downloadURL(getURL(name, page, lang))
    return text

def getURL(name, page, lang=LANG):
    if page == WIKIPEDIA:
        #return "http://"+lang+".wikipedia.org/w/api.php?action=query&titles=" + name + "&export"
        return "http://"+lang+".wikipedia.org/w/index.php?title=" + name + "&printable=yes"
    elif page == WIKITRAVEL:
        return "http://wikitravel.org/wiki/"+lang+"/index.php?title=" + name + "&printable=yes"
    else:
        pass

def downloadURL(url, lang=LANG):
    opener = urllib2.build_opener()
    opener.addheaders = [('User-agent', 'Mozilla/5.0')]
    #opener.addheaders = [('Accept-Language', lang+";q=0.8,en;q=0.5")]
    infile = opener.open(url)
    return infile.read()
    
def getCurrencyExchange(src, dest):
    url = 'http://finance.yahoo.com/d/quotes.csv?e=.csv&f=sl1d1t1&s=%s%s=X' % (src, dest)
    return downloadURL(url).split(',')[1]

def getMap(name, zoom, lang=LANG):
    coor = getCoordinates(name)
    url = "http://maps.google.com/staticmap?"
    url += "center="+str(coor[0])+","+str(coor[1])+"&"
    url += "zoom="+str(zoom)+"&"
    url += "size=500x500&"
    url += "maptype=roadmap&" # o mobile
    url += "format=png8&" # o png32
    url += "key="+GOOGLE_MAPS_KEY+"&"
    url += "sensor=false"
    url += "&hl="+lang
    return url

def getCoordinates(name, country=None):
    query = urllib.urlencode({'q': name, 'key': GOOGLE_MAPS_KEY, 'sensor':'false', 'output':'csv'})
    url = 'http://maps.google.com/maps/geo?%s' % query
    if country != None:
        url += "&gl="+country

    c = downloadURL(url).split(',')
    return (float(c[2]), float(c[3]))

def translate(text, lang=LANG):
    query = urllib.urlencode({'q' : text, 'langpair':'|' + lang})
    url = 'http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&%s' % (query)
    translation = downloadURL(url)
    json = simplejson.loads(translation)
    return json['responseData']['translatedText']

def search(q):
    query = urllib.urlencode({'q' : q})
    url = 'http://ajax.googleapis.com/ajax/services/search/local?v=1.0&%s&rsz=large' % (query)
    local_search_points = downloadURL(url)
    json = simplejson.loads(local_search_points)
    return json['responseData']['results']

def getTimezone(name, country=None):
    lat, long = getCoordinates(name, country)
    query = urllib.urlencode({'lat': lat, 'lng':long})
    url = 'http://ws.geonames.org/timezoneJSON?formatted=true&style=full&%s' % query
    timezone = downloadURL(url)
    json = simplejson.loads(timezone)
    return json['rawOffset']

def getWeather(name, lang=LANG):
    date = None
    condition = None
    icon = None
    temp = None
    min = None
    max = None
    wind = None
    humidity = None
    weather = []

    query = urllib.urlencode({'weather':name})
    url = 'http://www.google.co.uk/ig/api?%s' % query
    xml = downloadURL(url,lang)
    dom = parseString(xml)

    info = dom.getElementsByTagName('current_conditions')
    for day in dom.getElementsByTagName('forecast_conditions'):
        info.append(day)

    for f in info:
        try:
            date = f.getElementsByTagName('day_of_week')[0].getAttribute('data')
        except IndexError:
            date = 'Current'

        condition = f.getElementsByTagName('condition')[0].getAttribute('data')
        icon = 'http://www.google.co.uk' + f.getElementsByTagName('icon')[0].getAttribute('data')

        try:
            temp = f.getElementsByTagName('temp_c')[0].getAttribute('data')
        except IndexError:
            temp = ''

        try:
            min = f.getElementsByTagName('low')[0].getAttribute('data')
        except IndexError:
            min = ''

        try:
            max = f.getElementsByTagName('high')[0].getAttribute('data')
        except IndexError:
            max = ''

        try:
            wind = f.getElementsByTagName('wind_condition')[0].getAttribute('data')
        except IndexError:
            wind = ''

        try:
            humidity = f.getElementsByTagName('humidity')[0].getAttribute('data')
        except IndexError:
            humidity = ''
        weather.append({'date':date, 'condition':condition, 'icon':icon, 'temp':temp, 'min':min, 'max':max, 'wind':wind, 'humidity':humidity})
    
    return weather


#def getPhotos(place):
#    photos = []
#    flickr = flickrapi.FlickrAPI(FLICKR_KEY)
#    for photo in flickr.walk(tag_mode='all',tags=place):
#        photos.append(photo.get('url'))
#    return photos
