from django.db import models
#from django.forms import ModelForm
from django.contrib.auth.models import User as DjangoUser
#from django.contrib.auth.models import User

class City(models.Model):
    country = models.ForeignKey('Country')
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)
    timezone = models.IntegerField(null=True)
    lat = models.DecimalField(max_digits=12,decimal_places=8)
    lng = models.DecimalField(max_digits=12,decimal_places=8)
    wikipedia_url = models.URLField()
    wikitravel_url = models.URLField()


    def __unicode__(self):
        return self.name +", "+ str(self.country)

    class Meta:
        verbose_name_plural = 'Cities'
        
class CitySearch(models.Model):
    name = models.CharField(max_length=200)

class Country(models.Model):
    plug = models.ForeignKey('Plug', null=True)
    languages = models.ManyToManyField('Language', null=True)
    currency = models.ForeignKey('Currency', null=True)
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200, null=True)
    wikipedia_url = models.URLField()
    wikitravel_url = models.URLField()

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
    description = models.CharField(max_length=200)
    lat = models.DecimalField(max_digits=12,decimal_places=8)
    lng = models.DecimalField(max_digits=12,decimal_places=8)
    wikipedia_url = models.URLField()

    ACTIVITY_CHOICES = (('H', 'Housing'),
        ('F', 'Food'),
        ('T', 'Things'))
    place_type = models.CharField(max_length=1, choices=ACTIVITY_CHOICES)

    def __unicode__(self):
        return self.name

class Plug(models.Model):
    name = models.CharField(max_length=200)
    description = models.CharField(max_length=200)

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
    facebook_access_token = models.CharField(max_length=200)
    foaf_profile = models.CharField(max_length=999999)

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
