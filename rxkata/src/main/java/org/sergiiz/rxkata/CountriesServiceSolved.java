package org.sergiiz.rxkata;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import java.util.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class CountriesServiceSolved implements CountriesService {

    private final int ONE_MILLION = 1_000_000;
    private final BiFunction<Long, Long, Long> sum = (a, b) -> a + b;

    @Override
    public Single<String> countryNameInCapitals(Country country) {
        return Single.just(country)
                .map((c) -> c.getName().toUpperCase());
    }

    public Single<Integer> countCountries(List<Country> countries) {
        return Observable.fromIterable(countries)
                .count()
                .map(count -> Math.toIntExact(count));
    }

    public Observable<Long> listPopulationOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(country -> country.getPopulation());
    }

    @Override
    public Observable<String> listNameOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(country -> country.getName());
    }

    @Override
    public Observable<Country> listOnly3rdAnd4thCountry(List<Country> countries) {
        return Observable.fromIterable(countries)
                .skip(2)
                .take(2);
    }

    @Override
    public Single<Boolean> isAllCountriesPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries)
                .all(country -> country.getPopulation() > ONE_MILLION);
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries)
                .filter(country -> country.getPopulation() > ONE_MILLION);
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillionWithTimeoutFallbackToEmpty(final FutureTask<List<Country>> countriesFromNetwork) {
        return Observable.fromFuture(countriesFromNetwork, 1, TimeUnit.SECONDS, Schedulers.io())
                .onErrorResumeNext(Observable.empty())
                .flatMap(this::listPopulationMoreThanOneMillion);
    }

    @Override
    public Observable<String> getCurrencyUsdIfNotFound(String countryName, List<Country> countries) {
        return Observable.fromIterable(countries)
                .filter(country -> country.getName().equals(countryName))
                .map(country -> country.getCurrency())
                .defaultIfEmpty("USD");
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(List<Country> countries) {
        return Observable.fromIterable(countries)
                .map(country -> country.getPopulation())
                .reduce(sum)
                .toObservable();
    }

    @Override
    public Single<Map<String, Long>> mapCountriesToNamePopulation(List<Country> countriesList) {
        return Observable.fromIterable(countriesList)
                .toMap(country -> country.getName(), country -> country.getPopulation());
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(Observable<Country> countryObservable1,
                                                     Observable<Country> countryObservable2) {
        return Observable.merge(countryObservable1, countryObservable2)
                .map(country -> country.getPopulation())
                .reduce(sum)
                .toObservable();
    }

    @Override
    public Single<Boolean> areEmittingSameSequences(Observable<Country> countryObservable1,
                                                    Observable<Country> countryObservable2) {
        return Observable.sequenceEqual(countryObservable1, countryObservable2);
    }
}
