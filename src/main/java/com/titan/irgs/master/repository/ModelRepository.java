package com.titan.irgs.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.titan.irgs.master.domain.Model;

/**
 * This is ModelRepository interface(implemented on Jpa framework) which are responsible to perform CRUD operation on model table
 * @author 
 *
 */
@Repository
public interface ModelRepository extends JpaRepository<Model, Long>,JpaSpecificationExecutor<Model>{
	
	Model findByModelName(String modelName);
	
	Model findByModelNoAndWebMasterWebMasterId(String modelNo,Long webMasterId);
	Model findByModelNo(String modelNo);
	@Async
	@Query("FROM Model m where m.brand.brandId=:brandId")
	List<Model> findModelByBrandId(@Param("brandId")Long  brandId);
	
	Model findByModelNoAndBrandBrandName(String modelNo,String brandName);

	@Query(value ="SELECT w.web_master_name,m.model_name,m.model_no,b.brand_name,m.remarks FROM model m left join brand b on m.brand_id=b.brand_id\r\n" + 
			"inner join web_master w on w.web_master_id=m.web_master_id where b.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAllForExcel(@Param("id")Long id);
	
	@Query(value ="SELECT w.web_master_name,m.model_name,m.model_no,b.brand_name,m.remarks FROM model m left join brand b on m.brand_id=b.brand_id\r\n" + 
			"inner join web_master w on w.web_master_id=m.web_master_id ",nativeQuery = true)
	List<Object[]> getAllForExcel();
	

    @Query(value="SELECT e.* FROM model e where e.web_master_id=:id\r\n" ,nativeQuery=true)
List<Model> getModelByVerticalId(@Param("id")Long id);
	
}
