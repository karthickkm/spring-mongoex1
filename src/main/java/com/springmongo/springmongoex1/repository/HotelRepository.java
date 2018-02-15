package com.springmongo.springmongoex1.repository;

import com.springmongo.springmongoex1.mongo.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 372153
 *
 */
@Repository
public interface HotelRepository extends MongoRepository<Hotel, String>, QueryDslPredicateExecutor<Hotel>{
	
	public List<Hotel> findById(String id);
	public List<Hotel> findByStayCostLessThan(int maxPrice);
	
	@Query(value = "{address.street:?0}", fields= "{name:1, stayCost:1}")      // Fields to be returned. If it is empty, the complete collection will be returned.
	public List<Hotel> findByStreet111111(String street);
	
	public List <Hotel> deleteByName(String name);

	public List<Hotel> findHotelsByNameAndStayCost(String name, int stayCost);	// findByNameAndStayCost also works fine

	public List<Hotel> findHotelsByAddress_Street(String street);

	public Page<Hotel> findByName(String name, Pageable pageable);

	//public Slice<Hotel> findByName(String name, Pageable pageable);
		
}
