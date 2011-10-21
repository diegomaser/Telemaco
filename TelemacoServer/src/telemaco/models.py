from django.db import models
#from django.forms import ModelForm
from django.contrib.auth.models import User as DjangoUser
#from django.contrib.auth.models import User

class City(models.Model):
    country = models.ForeignKey('Country')
    rdf = models.CharField(max_length=999999, null=True)
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    timezone = models.IntegerField(null=True)
    timezone_dst = models.IntegerField(null=True)
    lat = models.DecimalField(max_digits=12,decimal_places=8, null=True)
    lng = models.DecimalField(max_digits=12,decimal_places=8, null=True)
    wikipedia_url = models.URLField(null=True)
    wikitravel_url = models.URLField(null=True)
    
#    janHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    janLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    janMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    janPrecipitationDays = models.IntegerField(null=True)
#    janPrecipitationMm = models.IntegerField(null=True)
#    janSun = models.IntegerField(null=True)
#    
#    febHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    febLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    febMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    febPrecipitationDays = models.IntegerField(null=True)
#    febPrecipitationMm = models.IntegerField(null=True)
#    febSun = models.IntegerField(null=True)
#    
#    marHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    marLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    marMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    marPrecipitationDays = models.IntegerField(null=True)
#    marPrecipitationMm = models.IntegerField(null=True)
#    marSun = models.IntegerField(null=True)
#    
#    aprHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    aprLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    aprMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    aprPrecipitationDays = models.IntegerField(null=True)
#    aprPrecipitationMm = models.IntegerField(null=True)
#    aprSun = models.IntegerField(null=True)
#    
#    junHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    junLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    junMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    junPrecipitationDays = models.IntegerField(null=True)
#    junPrecipitationMm = models.IntegerField(null=True)
#    junSun = models.IntegerField(null=True)
#    
#    julHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    julLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    julMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    julPrecipitationDays = models.IntegerField(null=True)
#    julPrecipitationMm = models.IntegerField(null=True)
#    julSun = models.IntegerField(null=True)
#    
#    augHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    augLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    augMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    augPrecipitationDays = models.IntegerField(null=True)
#    augPrecipitationMm = models.IntegerField(null=True)
#    augSun = models.IntegerField(null=True)
#    
#    sepHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    sepLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    sepMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    sepPrecipitationDays = models.IntegerField(null=True)
#    sepPrecipitationMm = models.IntegerField(null=True)
#    sepSun = models.IntegerField(null=True)
#
#    octHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    octLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    octMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    octPrecipitationDays = models.IntegerField(null=True)
#    octPrecipitationMm = models.IntegerField(null=True)
#    octSun = models.IntegerField(null=True)
#
#    novHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    novLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    novMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    novPrecipitationDays = models.IntegerField(null=True)
#    novPrecipitationMm = models.IntegerField(null=True)
#    novSun = models.IntegerField(null=True)
#    
#    decHighC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    decLowC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    decMeanC = models.DecimalField(max_digits=4, decimal_places=2, null=True)
#    decPrecipitationDays = models.IntegerField(null=True)
#    decPrecipitationMm = models.IntegerField(null=True)
#    decSun = models.IntegerField(null=True)

    def __unicode__(self):
        return self.name +", "+ str(self.country)

    class Meta:
        verbose_name_plural = 'Cities'
        
class CitySearch(models.Model):
    name = models.CharField(max_length=200)

class Country(models.Model):
    name = models.CharField(max_length=200)
    rdf = models.CharField(max_length=999999, null=True)
    description = models.CharField(max_length=200, null=True)
    wikipedia_url = models.URLField(null=True)
    wikitravel_url = models.URLField(null=True)

    plug = models.ManyToManyField('Plug', null=True)
    plug_frequency = models.CharField(max_length=10)
    plug_voltage = models.CharField(max_length=10)
    
    languages = models.ManyToManyField('Language', null=True)
    currency = models.ForeignKey('Currency', null=True)

    def __unicode__(self):
        return self.name

    class Meta:
        verbose_name_plural = 'Countries'

class Currency(models.Model):
    name = models.CharField(max_length=200)
    code = models.CharField(max_length=4, unique=True)
    rate = models.DecimalField(max_digits=15,decimal_places=10, null=True)

    def __unicode__(self):
        return self.name

    class Meta:
        verbose_name_plural = 'Currencies'

class Item(models.Model):
    place = models.ForeignKey('Place')
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    image = models.ImageField(upload_to='img/items/')
    
    def __unicode__(self):
        return self.name

class Language(models.Model):
    name = models.CharField(max_length=200)
    code = models.CharField(max_length=5, null=True)

    def __unicode__(self):
        return self.name

class Note(models.Model):
    trip = models.ForeignKey('Trip')
    name = models.CharField(max_length=200)
    text = models.CharField(max_length=1000)
    
    def __unicode__(self):
        return self.name

class Place(models.Model):
    name = models.CharField(max_length=200)
    city = models.ForeignKey('City')
    rdf = models.CharField(max_length=999999, null=True)
    description = models.CharField(max_length=200, null=True)
    lat = models.DecimalField(max_digits=12,decimal_places=8, null=True)
    lng = models.DecimalField(max_digits=12,decimal_places=8, null=True)
    wikipedia_url = models.URLField(null=True)

    ACTIVITY_CHOICES = (('H', 'Housing'),
        ('F', 'Food'),
        ('T', 'Things'))
    place_type = models.CharField(max_length=1, choices=ACTIVITY_CHOICES)

    def __unicode__(self):
        return self.name

class Plug(models.Model):
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    image = models.URLField()

    def __unicode__(self):
        return self.name


class Transport(models.Model):
    trip = models.ForeignKey('Trip')
    departure = models.ForeignKey('City', related_name='departure_city')
    arrival = models.ForeignKey('City', related_name='arrival_city')
    place = models.CharField(max_length=200)
    date = models.DateTimeField()
    code = models.CharField(max_length=10)
    reservation = models.CharField(max_length=200)
    TRANSPORT_CHOICES = (('C', 'Car/Motorcycle'),
                         ('P', 'Plane'),
                         ('F', 'Ferry/Cruise'),
                         ('T', 'Train'),
                         ('B', 'Bus'))
    transp_type = models.CharField(max_length=1,choices=TRANSPORT_CHOICES)
    
    def __unicode__(self):
        return self.departure + ' - ' + self.arrival

class Trip(models.Model):
    cities = models.ManyToManyField('City', through = 'CityVisit')
    places = models.ManyToManyField('Place', through = 'PlaceVisit')
    user = models.ForeignKey('User')
    
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    start_date = models.DateField('starting date')
    end_date = models.DateField('ending date')
    
    def __unicode__(self):
        return self.name

class User(DjangoUser):
    city = models.ForeignKey('City') # onetoonefield? quiza sea mejor alternativa
    facebook_access_token = models.CharField(max_length=200, null=True)
    foaf_profile = models.CharField(max_length=999999, null=True)

class CityVisit(models.Model):
    trip = models.ForeignKey('Trip')
    city = models.ForeignKey('City')
    date = models.DateField()

class PlaceVisit(models.Model):
    trip = models.ForeignKey('Trip')
    place = models.ForeignKey('Place')
    date = models.DateTimeField()
    order = models.IntegerField()

#######################################################
# OLD ONES

#class Activity(models.Model):
#    ACTIVITY_CHOICES = (('H', 'Housing'),
#        ('F', 'Food'),
#        ('T', 'Things'))
#    name = models.CharField(max_length=200)
#    description = models.CharField(max_length=200)
#    act_type = models.CharField(max_length=1, choices=ACTIVITY_CHOICES)
#    place = models.CharField(max_length=200)
#    price = models.DecimalField(max_digits=10,decimal_places=2)
#    reservation = models.CharField(max_length=200)
#    showInGuide = models.BooleanField()
#    trip = models.ForeignKey(Trip)
#    city = models.ForeignKey(City)

#    def __unicode__(self):
#        return self.name

#    class Meta:
#        verbose_name_plural = 'Activities'
