package com.springmongo.springmongoex1.endpoint;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.springmongo.springmongoex1.mongo.model.Address;
import com.springmongo.springmongoex1.mongo.model.Hotel;
import com.springmongo.springmongoex1.mongo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/hotelTemp")
public class HotelMongoTempService {

    @Autowired
    public MongoTemplate mongoTemplate;

    @GetMapping("/insert")
    public boolean insertHotel() {

        Hotel hotel = new Hotel();
        hotel.setAddress(new Address(5, "Mount Road"));
        hotel.setName("Temple towers");
        List<Review> reviews1 = Arrays.asList(new Review("Good"), new Review("Bad"));
        hotel.setReviews(reviews1);
        hotel.setStayCost(5000);

        mongoTemplate.insert(hotel);
        return true;
    }
    @GetMapping("/findAll")
    public List<Hotel> getAllHotels() {
        return mongoTemplate.findAll(Hotel.class);
    }

    @GetMapping("/find/{id}")
    public List<Hotel> getHotelById(@PathVariable("id") String id) {
        return mongoTemplate.find(query(where("id").is(id)), Hotel.class);   // findOne will return single Hotel object
    }

    @GetMapping("/findByNameUsingCriteria/{name1}/{name2}")
    public List<Hotel> getHotelByIdCriteria(@PathVariable("name1") String name1, @PathVariable("name2") String name2) {

        Criteria criteria = new Criteria();
        criteria.orOperator(where("name").is(name1), where("name").is(name2));
        //criteria.orOperator(Criteria.where("name").is(name1), Criteria.where("name").is(name2));  // Both are same

        Query query = new Query(criteria);
        //query.addCriteria(Criteria.where("name").is(name1));  // This is other syntax

        return mongoTemplate.find(query, Hotel.class);
    }


}
