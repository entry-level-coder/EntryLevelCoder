package com.entrylevelcoder.entrylevelcoder.repositories;

import com.entrylevelcoder.entrylevelcoder.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findById(long id);
}
