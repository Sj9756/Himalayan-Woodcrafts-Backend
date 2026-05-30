package com.santosh.userservice.repo;

import com.santosh.userservice.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface  UserProfileRepository extends JpaRepository<UserProfile,Integer>  {

}
