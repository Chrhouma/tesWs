package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.Delta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeltaRepository  extends CrudRepository<Delta,String> {
    List<Delta> findAllById(String id);
    List<Delta> findDeltaByServiceRecordId(String id);

}
