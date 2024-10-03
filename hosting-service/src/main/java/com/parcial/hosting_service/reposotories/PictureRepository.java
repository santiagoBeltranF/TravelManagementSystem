package com.parcial.hosting_service.reposotories;

import com.parcial.hosting_service.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
