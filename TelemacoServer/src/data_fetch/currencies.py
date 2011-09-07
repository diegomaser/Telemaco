from telemaco.models import Currency
import webservices as ws

def getCurrencies():
    # Currency
    currencies = Currency.objects.all()
    for currency in currencies:
        print "Getting info for currency: ", currency.name
        currency.rate = ws.getCurrencyExchange("EUR", currency.code)
        currency.save()