package com.pictimegroupe.FrontVendeur.testWebservice.repository;

import com.pictimegroupe.FrontVendeur.testWebservice.WebService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WebServicesRepository extends CrudRepository<WebService,String> {
List<WebService>  findWebServiceById(String id);
List<WebService> findWebServiceByName(String name);
}
