package pl.ratemyrestaurant.service.placesconnectorservice.impl;

import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.service.placesconnectorservice.PlacesConnector;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.utils.StreamUtils;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GooglePlacesConnector implements PlacesConnector {

    private GooglePlaces client;

    public GooglePlacesConnector(){
        client = new GooglePlaces("AIzaSyDwiEyXS5W2lZus1riL0MZ0j_Yfn6TxC2A");
    }

    public Set<Place> retrieveRestaurants(UserSearchCircle userSearchCircle) throws InterruptedException {
        Set<Place> places = new HashSet<>();
//        List<Place> bars = retrieveRestaurants(userSearchCircle, "bar");
//        List<Place> foods = retrieveRestaurants(userSearchCircle, "food");
//        Thread.sleep(100);
//        List<Place> cafes = retrieveRestaurants(userSearchCircle, "cafe");
//        Thread.sleep(100);
        List<Place> restaurants = retrieveRestaurants(userSearchCircle, "restaurant");
//        places.addAll(foods);
//        places.addAll(bars);
//        places.addAll(cafes);
        places.addAll(restaurants);
        return places.stream().filter(StreamUtils.distinctByKey(Place::getPlaceId)).collect(Collectors.toSet());
    }

    public List<Place> retrieveRestaurants(UserSearchCircle userSearchCircle, String name){
        return client.getNearbyPlaces(userSearchCircle.getLat(), userSearchCircle.getLng(), userSearchCircle.getRadius()
                , GooglePlaces.MAXIMUM_RESULTS, Param.name("type").value(name));
    }
}