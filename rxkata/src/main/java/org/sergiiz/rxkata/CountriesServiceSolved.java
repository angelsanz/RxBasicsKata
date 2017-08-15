package org.sergiiz.rxkata;


import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

class CountriesServiceSolved implements CountriesService {

    private BiFunction<Long,Long,Long> sum = (a, b) -> a + b;

    @Override
    public Single<String> countryNameInCapitals(Country country) {
        return null; // put your solution here
    }

    public Single<Integer> countCountries(List<Country> countries) {
        return null; // put your solution here
    }

    public Observable<Long> listPopulationOfEachCountry(List<Country> countries) {
        return null; // put your solution here;
    }

    @Override
    public Observable<String> listNameOfEachCountry(List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Observable<Country> listOnly3rdAnd4thCountry(List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Single<Boolean> isAllCountriesPopulationMoreThanOneMillion(List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillionWithTimeoutFallbackToEmpty(final FutureTask<List<Country>> countriesFromNetwork) {
        return null; // put your solution here
    }

    @Override
    public Observable<String> getCurrencyUsdIfNotFound(String countryName, List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Single<Map<String, Long>> mapCountriesToNamePopulation(List<Country> countries) {
        return null; // put your solution here
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(Observable<Country> countryObservable1,
                                                     Observable<Country> countryObservable2) {
        return null; // put your solution here
    }

    @Override
    public Single<Boolean> areEmittingSameSequences(Observable<Country> countryObservable1,
                                                    Observable<Country> countryObservable2) {
        return null; // put your solution here
    }

    @Override
    public Observable<Tuple<String, Long>> getAveragePopulationByCurrency(List<Country> countries) {
        return Observable.fromIterable(countries)
                .groupBy(Country::getCurrency)
                .flatMap(groupOfCountriesByCurrency -> {
                    Observable<Country> countryByCurrency = groupOfCountriesByCurrency.cache();

                    Single<Long> numberOfCountries = countryByCurrency
                            .count();
                    Maybe<Long> sumOfPopulations = countryByCurrency
                            .map(Country::getPopulation)
                            .reduce(sum);

                    return Observable.zip(sumOfPopulations.toObservable(), numberOfCountries.toObservable(),
                    (sumOfPopulations_, numberOfCountries_) -> {
                        long averagePopulation = sumOfPopulations_ / numberOfCountries_;
                        String currency = groupOfCountriesByCurrency.getKey();

                        return Tuple.of(currency, averagePopulation);
                    });
                });
    }
}
