package org.generation.jfs_data_jpa_demo.repositories;

import org.generation.jfs_data_jpa_demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
