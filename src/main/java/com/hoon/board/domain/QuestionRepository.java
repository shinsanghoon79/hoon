package com.hoon.board.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	@Query("select u from User u where u.name = :name")
	User findByUserName(@Param("name") String name);
	
	@Query("select u from User u ")
	List<User> findByUserList(Sort sort);
	
	@Query("select count(*) from User u where u.name = :name")
	long countByUserName(@Param("name") String name);
	
	@Query(value="select if(count(*) > 0, 'TRUE', 'FALSE') AS result from user  where name = :name", nativeQuery = true)
	String booleanUserName(@Param("name") String name);
	
	public static Specification<Question> withName(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
   }
	
   public static Specification<Question> withContents(String contents) {
        return (root, query, builder) -> builder.like(root.get("contents"), "%" + contents + "%");
   }
	
	public static Specification<Question> withTitle(String title) {
		 return (root, query, builder) -> builder.equal(root.get("title"), title);
   }
	
	@Query(value =  "   SELECT				     " + 
					"       a.id,			     " + 
					"       a.create_date,	     " + 
					"       a.modified_date,	 " + 
					"       a.contents,		     " + 
					"       a.count_of_answer,   " + 
					"       a.title,			 " + 
					"       a.writer_id 		 " + 
					"    FROM				     " + 
					"        question a	 LEFT OUTER JOIN	     " +
					"        user b ON  a.writer_id = b.user_id	 " +
				    "  	 WHERE 1+1	 		                     " + 		   
				    "  	 AND a.title LIKE	 CONCAT('%',:title,'%')  " + 
				    "  	 AND a.contents LIKE	 CONCAT('%',:contents,'%') " + 
				    "  	 AND b.name LIKE	 CONCAT('%',:name,'%')     ",
			countQuery =  "   SELECT				     " + 
						"      count(*)			     " + 
						"    FROM				     " + 
						"        question a	 LEFT OUTER JOIN	     " +
						"        user b ON  a.writer_id = b.user_id	 " +
					    "  	 WHERE 1+1	 		                     " + 		   
					    "  	 AND a.title LIKE	 CONCAT('%',:title,'%')  " + 
					    "  	 AND a.contents LIKE	 CONCAT('%',:contents,'%') " + 
					    "  	 AND b.name LIKE	 CONCAT('%',:name,'%')     "
				,nativeQuery = true)

	Page<Question> findBySearchPage(@Param("title") String title,
							  @Param("contents") String contents,
							  @Param("name") String name,
							  Pageable pageable);
}
