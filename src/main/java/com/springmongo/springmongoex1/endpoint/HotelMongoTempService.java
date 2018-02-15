package com.springmongo.springmongoex1.endpoint;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.springmongo.springmongoex1.mongo.model.Address;
import com.springmongo.springmongoex1.mongo.model.Hotel;
import com.springmongo.springmongoex1.mongo.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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

    // Not clear
    @GetMapping("/findByNameUsingCriteriaPattern/{name}")
    public List<Hotel> getHotelByIdCriteriaPattern(@PathVariable("name") String name) {
        Criteria criteria = where("name").regex(Pattern.compile(".*"+name));
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Hotel.class);
    }

    @PostMapping("/save")
    public boolean upsertHotel(@RequestBody Hotel hotel) {
        mongoTemplate.save(hotel);
        return true;
    }

    @GetMapping("/updateName/{oldName}/{newName}")
    public boolean updateHotelName(@PathVariable("oldName") String oldName, @PathVariable("newName") String newName) {

        Query query = query(where("name").is(oldName));
        Update update = update("name",newName);
        mongoTemplate.updateFirst(query, update, Hotel.class);
        return true;
    }

    @GetMapping("/updateName2/{oldName}/{newName}")
    public boolean updateHotelName2(@PathVariable("oldName") String oldName, @PathVariable("newName") String newName) {

        Hotel hotel = mongoTemplate.find(query(where("name").is(oldName)), Hotel.class).get(0); // Both are same. Notice the arg of Basic query
       /* BasicQuery query = new BasicQuery("{name:'"+oldName+"'}");
        Hotel hotel = mongoTemplate.find(query, Hotel.class).get(0); */

        hotel.setName(newName);
        mongoTemplate.save(hotel);
        return true;
    }

    @GetMapping("/updateName3/{oldName}/{addrNo}/{addrStreet}/{newName}")
    public boolean updateHotelName3(@PathVariable("oldName") String oldName, @PathVariable("addrNo") int addrNo,
                                    @PathVariable("addrStreet") String addrStreet, @PathVariable("newName") String newName) {
        Hotel hotel = mongoTemplate.find(
                query(
                        where("name").is(oldName)
                                .and("address.no").is(addrNo)
                                .and("address.street").is(addrStreet)
                ), Hotel.class).get(0);

        hotel.setName(newName);
        mongoTemplate.save(hotel);
        return true;
    }

    @GetMapping("/updateName4/{oldName}/{addrNo}/{addrStreet}/{newName}")
    public boolean updateHotelName4(@PathVariable("oldName") String oldName, @PathVariable("addrNo") int addrNo,
                                    @PathVariable("addrStreet") String addrStreet, @PathVariable("newName") String newName) {
        Query query = query(
                where("name").is(oldName)
                        .and("address.no").is(addrNo)
                        .and("address.street").is(addrStreet)
        );
        Update update = update("name", newName);
        mongoTemplate.updateMulti(query, update, Hotel.class);
        return true;
    }

    @GetMapping("/remove/{name}/{addrNo}/{addrStreet}")
    public boolean removeHotel(@PathVariable("name") String name, @PathVariable("addrNo") int addrNo,
                                    @PathVariable("addrStreet") String addrStreet) {
        Query query = query(
                where("name").is(name)
                        .and("address.no").is(addrNo)
                        .and("address.street").is(addrStreet)
        );
        mongoTemplate.remove(query, Hotel.class);
        return true;
    }



}
