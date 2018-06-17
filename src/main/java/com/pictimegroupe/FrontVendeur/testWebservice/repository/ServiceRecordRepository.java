package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.ScenarioRecord;
import com.pictimegroupe.FrontVendeur.testWebservice.ServiceRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ServiceRecordRepository extends CrudRepository<ServiceRecord,String> {
    List<ServiceRecord> findServiceRecordsById(String id);
  //  <ServiceRecord> findAllByScenarioRecord(String id);
}
