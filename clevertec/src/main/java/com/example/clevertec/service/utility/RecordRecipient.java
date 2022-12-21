package com.example.clevertec.service.utility;

import com.example.clevertec.aspect.exception.NoSuchRecordException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class RecordRecipient {

    public static <T> T getRecordFromTable(Long id, JpaRepository<T, Long> repository, String error) {

        Optional<T> recordFromTable = repository.findById(id);

        return recordFromTable.orElseThrow(() -> new NoSuchRecordException(error + id));
    }
}
