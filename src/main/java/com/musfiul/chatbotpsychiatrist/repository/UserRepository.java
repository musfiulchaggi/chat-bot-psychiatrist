package com.musfiul.chatbotpsychiatrist.repository;

import com.musfiul.chatbotpsychiatrist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    @Modifying
    @Query(value = "update ms_user set status = :status where id = :id", nativeQuery = true)
    void updateStatus(@Param("id") String id, @Param("status") Boolean status);
    @Query(value = "select * from ms_user where user_account_id = :id", nativeQuery = true)
    User findByUserAccountId(@Param("id") String userAccountId);
}
