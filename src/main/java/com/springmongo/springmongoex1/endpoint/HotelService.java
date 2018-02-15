package com.springmongo.springmongoex1.endpoint;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.springmongo.springmongoex1.mongo.model.Address;
import com.springmongo.springmongoex1.mongo.model.Hotel;
import com.springmongo.springmongoex1.mongo.model.QHotel;
import com.springmongo.springmongoex1.mongo.model.Review;
import com.springmongo.springmongoex1.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author 372153
 *
 */
@RestController
@RequestMapping("/hotel")
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/insert")
    public boolean insertHotel() {

        Hotel hotel = new Hotel();
        hotel.setAddress(new Address(5, "GST Road"));
        hotel.setName("ITC");
        List<Review> reviews1 = Arrays.asList(new Review("Good"), new Review("Bad"));
        hotel.setReviews(reviews1);
        hotel.setStayCost(1000);
        hotelRepository.insert(hotel);

        return true;
    }

    @GetMapping("/findAll")
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("/find/{id}")
    public Hotel getHotelById(@PathVariable("id") String id) {
        return hotelRepository.findById(id).get(0);
    }

    @GetMapping("/findByStreet/{street}")
    public List<Hotel> getHotelByStreet(@PathVariable("street") String street) {
        return hotelRepository.findByStreet111111(street);
    }

    @GetMapping("/findByStaycostLessThan/{maxPrice}")
    public List<Hotel> getHotelByStaycostLT(@PathVariable("maxPrice") int maxPrice) {
        return hotelRepository.findByStayCostLessThan(maxPrice);
    }

    @GetMapping("/findByStaycost/{stayCost}")
    public List<Hotel> getHotelByStaycost(@PathVariable("stayCost") int stayCost) {

        QHotel qHotel = new QHotel("Hotel");
        BooleanExpression findByCost = qHotel.stayCost.eq(stayCost);
        return (List<Hotel>)hotelRepository.findAll(findByCost);
    }

    @GetMapping("/findByStaycost/{min}/{max}")
    public List<Hotel> getHotelByStaycost(@PathVariable("min") int min, @PathVariable("max") int max) {

        QHotel qHotel = new QHotel("Hotel");
        BooleanExpression minCost = qHotel.stayCost.gt(min);
        BooleanExpression maxCost = qHotel.stayCost.lt(max);
        return (List<Hotel>)hotelRepository.findAll(minCost.and(maxCost));

    }

    @GetMapping("/findByNameAndStaycost/{name}/{stayCost}")
    public List<Hotel> getHotelByNameAndStaycost(@PathVariable("name") String name, @PathVariable("stayCost") int stayCost) {
        return hotelRepository.findHotelsByNameAndStayCost(name, stayCost);
    }

    @GetMapping("/findByAddressStreet/{street}")
    public List<Hotel> getHotelByAddressStreet(@PathVariable("street") String street) {
        return hotelRepository.findHotelsByAddress_Street(street);
    }

    @GetMapping("/deleteByName/{name}")
    public List<Hotel> deleteHotelByName(@PathVariable("name") String name) {
        return hotelRepository.deleteByName(name);
    }

    /* /findAllPageable?page=0&size=1&sort=name */
    @GetMapping("/findAllPageable")
    public Page<Hotel> getAllHotelsPageable(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

   @GetMapping("/findByNamePageable/{name}")
    public Page<Hotel> getHotelsByNamePageable(@PathVariable("name") String name, Pageable pageable) {
        return hotelRepository.findByName(name, pageable);
    }

    /*@GetMapping("/findByNameSlice/{name}")
    public Slice<Hotel> getHotelsByNameSlice(@PathVariable("name") String name, Pageable pageable) {    // totalPages: 2, totalElements: 3 - These 2 properties will not be returned in slice
        return hotelRepository.findByName(name, pageable);
    }*/

    @GetMapping("/findByNameAndStaycostPredicate/{name}/{stayCost}")
    public List<Hotel> getHotelByNameAndStaycostPredicate(@PathVariable("name") String name, @PathVariable("stayCost") int stayCost) {
        QHotel qHotel = new QHotel("Hotel");
        Predicate predicate = qHotel.name.eq(name).and(qHotel.stayCost.eq(stayCost));
        return (List<Hotel>) hotelRepository.findAll(predicate);
    }




}

