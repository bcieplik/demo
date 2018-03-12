package com.bart.demo.message;

import com.bart.demo.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(exported = false)
interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findByCreator(User creator, Pageable pageable);

    @Query("from Message m where m.creator in :users")
    Page<Message> findByCreators(@Param("users") Set<User> users, Pageable pageable);
}