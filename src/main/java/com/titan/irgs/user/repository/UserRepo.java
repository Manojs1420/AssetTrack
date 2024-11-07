package com.titan.irgs.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.titan.irgs.user.domain.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{

	@Query("from User u where u.username=:username")
	User findByUsername(@Param("username")String username);
	
	@Query("from User u where u.id=:id")
	User getByUserId(@Param("id")Long id);
	
	@Query(value="select id from users u where u.id=:id",nativeQuery = true)
	List<Long> findByUserId(@Param("id")Long id);
 
	List<User> findByRoleWebRoleId(Long id);

	@Query(value="SELECT * from store s \r\n"
			+ "inner join users u on u.username=s.store_code\r\n"
			+ "inner join role_wise_departments rd on rd.role_wise_departments_id=u.role_wise_departments_id\r\n"
			+ "where rd.web_role_id=:webRoleId and rd.department_ids=:departmentId",nativeQuery = true)
	List<User> findByWebRoleIdAndDepartmentId(@Param("webRoleId")Long webRoleId,@Param("departmentId")Long departmentId);
	
	@Query(value="select * from users u \r\n"
			+ "inner join store s on s.store_code=u.username\r\n"
			+ "where u.username=:username and u.inventory_user='true'",nativeQuery = true)
	List<User> findByInventoryUser(@Param("username")String username);

	
	@Query(value = "SELECT u.email,r.role_name,re.region_name,wm.web_master_name FROM users u "
			+ "inner join web_role w on u.web_role_id=w.web_role_id "
			+ "inner join role r on w.role_id=r.role_id "
			+ "inner join web_master wm on wm.web_master_id=w.access_master_id "
			+ "inner join cluster c on c.web_role_id=w.web_role_id "
			+ "inner join region re on re.region_id=c.region_id  "
			+ "where wm.web_master_name='IRSG' and re.region_name=:region "
			+ "group by u.email,r.role_name,re.region_name,wm.web_master_name ; ",nativeQuery = true)
	List<Object[]> getIrsgUsersEmailsOnregions(@Param("region")String region);

	@Query(value = "SELECT u.username,r.role_name,r1.role_name as reporting_to,wm.web_master_name,d.department_name,\r\n"
			+ "						ot.opertion_type,STUFF((SELECT ',' + r.region_name FROM  cluster_user cu \r\n"
			+ "						inner join cluster c on c.cluster_id=cu.cluster_id \r\n"
			+ "						inner join region r on r.region_id=c.region_id \r\n"
			+ "						WHERE u.id = cu.user_id FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '') as  regionType,\r\n"
			+ "						u.first_name,u.last_name,u.email,u.phone_no,u.account_non_locked FROM users u \r\n"
			+ "						inner join web_role wr on wr.web_role_id=u.web_role_id \r\n"
			+ "						inner join role r on r.role_id=wr.role_id \r\n"
			+ "						inner join role r1 on r1.role_id=wr.reporting_id \r\n"
			+ "						inner join web_master wm on wm.web_master_id=wr.access_master_id \r\n"
			+ "						left join role_wise_departments rd on rd.role_wise_departments_id=u.role_wise_departments_id\r\n"
			+ "						left join department d on d.department_id=rd.department_ids\r\n"
			+ "						inner join opertion_type ot on ot.web_role_id=wr.web_role_id",nativeQuery = true)
	List<Object[]> getAllForExcel();
	
	
	
	
	
	@Query(value = "SELECT u.* FROM cluster c inner join cluster_user cu on c.cluster_id=cu.cluster_id "
			+ "inner join region r on r.region_id=c.region_id "
			+ "inner join web_role w on w.web_role_id=c.web_role_id "
			+ "inner join web_master wm on wm.web_master_id=w.access_master_id "
			+ "inner join role ro on ro.role_id=w.role_id "
			+ "inner join users u on u.id=cu.user_id "
			+ "where wm.web_master_name=:busssinessVerticalName and region_name=:region",nativeQuery = true)
	List<User> findByUsersUsingBussinessVerticalAndRegion(String busssinessVerticalName, String region);
	
	User findByIdAndRoleWebRoleId(Long id,Long webRoleId);
	
	@Query(value = "SELECT r.region_name FROM  cluster_user cu inner join cluster c on c.cluster_id=cu.cluster_id inner join region r on r.region_id=c.region_id where cu.user_id=:id",nativeQuery = true)
	List<String> getAllRegionForUserId(Long id);

	@Query(value = "SELECT u.username,r.role_name,r1.role_name as reporting_to,wm.web_master_name,d.department_name,\r\n"
			+ "						ot.opertion_type,STUFF((SELECT ',' + r.region_name FROM  cluster_user cu \r\n"
			+ "						inner join cluster c on c.cluster_id=cu.cluster_id \r\n"
			+ "						inner join region r on r.region_id=c.region_id \r\n"
			+ "						WHERE u.id = cu.user_id FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, '') as  regionType,\r\n"
			+ "						u.first_name,u.last_name,u.email,u.phone_no,u.account_non_locked FROM users u \r\n"
			+ "						inner join web_role wr on wr.web_role_id=u.web_role_id \r\n"
			+ "						inner join role r on r.role_id=wr.role_id \r\n"
			+ "						inner join role r1 on r1.role_id=wr.reporting_id \r\n"
			+ "						inner join web_master wm on wm.web_master_id=wr.access_master_id \r\n"
			+ "						left join role_wise_departments rd on rd.role_wise_departments_id=u.role_wise_departments_id\r\n"
			+ "						left join department d on d.department_id=rd.department_ids\r\n"
			+ "						inner join opertion_type ot on ot.web_role_id=wr.web_role_id where wm.web_master_id=:id",nativeQuery = true)
	List<Object[]> getAllForExcel(@Param("id") Long id);
	
	@Query(value="select w.web_role_id from users u \r\n"
			+ "left join web_role w on w.web_role_id=u.web_role_id\r\n"
			+ "left join role r on r.role_id=w.role_id\r\n"
			+ "left join web_master wm on wm.web_master_id=w.access_master_id\r\n"
			+ "where r.role_name='inventory user'",nativeQuery = true)
	List<Long> getAllStores();

	@Query(value="select * from users u \r\n"
			+ "inner join role_wise_departments rd on rd.role_wise_departments_id=u.role_wise_departments_id\r\n"
			+ "where rd.department_ids=:departmentId and u.inventory_user='true'",nativeQuery = true)
	List<User> getAllInventoryUsersByDepartmentId(@Param("departmentId")Long departmentId);

	@Query(value="SELECT *\r\n"
			+ "  FROM users u\r\n"
			+ "  left join web_role wr on wr.web_role_id=u.web_role_id\r\n"
			+ "  left join role_wise_departments rd on rd.role_wise_departments_id=u.role_wise_departments_id\r\n"
			+ "  where wr.access_master_id=:webMasterId ",nativeQuery = true)
	List<User> getAllInventoryUsersByVerticalId(@Param("webMasterId") Long webMasterId);
	
	@Query(value="SELECT * FROM users u \r\n"
			+ "  left join web_role wr on wr.web_role_id=u.web_role_id\r\n"
			+ "  left join web_master wm on wm.web_master_id=wr.access_master_id where u.username=:userName and wm.web_master_name=:webMasterName",nativeQuery = true)
	User getUsersByUserNameAndVerticalName(@Param("userName") String userName,@Param("webMasterName") String webMasterName);

	@Query(value="select u.email from users u \r\n"
			+ "left join web_role wr on wr.web_role_id=u.web_role_id\r\n"
			+ "LEFT JOIN role_wise_departments rd on rd.web_role_id=wr.web_role_id\r\n"
			+ "left join role r on r.role_id=wr.role_id\r\n"
			+ "where (r.role_name like '%HEAD%' OR  r.role_name like '%MANAGER%') and rd.department_ids in (?1)",nativeQuery = true)
	List<String> getEmailForAssetBasedDepartmentHeads(Long assetId);
	
}
