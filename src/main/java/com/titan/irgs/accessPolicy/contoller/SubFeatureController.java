/*
 * package com.titan.irgs.accessPolicy.contoller;
 * 
 * import java.util.List; import java.util.stream.Collectors;
 * 
 * import javax.servlet.http.HttpServletRequest;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PathVariable; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.titan.irgs.WebConstantUrl.WebConstantUrl; import
 * com.titan.irgs.accessPolicy.domain.SubFeatures; import
 * com.titan.irgs.accessPolicy.mapper.SubFeatureMapper; import
 * com.titan.irgs.accessPolicy.model.SubFeaturesVo; import
 * com.titan.irgs.accessPolicy.service.SubFeaturesService;
 * 
 * @RestController
 * 
 * @RequestMapping(WebConstantUrl.SUBFUTURE) public class SubFeatureController {
 * 
 * @Autowired SubFeaturesService subFuturesService;
 * 
 * @Autowired SubFeatureMapper subFuturesMapper;
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(ModuleController.class);
 * 
 * @PostMapping(WebConstantUrl.save) public ResponseEntity<?>
 * saveRole(@RequestBody SubFeaturesVo subFuturesVo,HttpServletRequest request){
 * 
 * logger.info("getConfirm: Received request: " +
 * request.getRequestURL().toString() + ((request.getQueryString() == null) ? ""
 * : "?" + request.getQueryString().toString())); SubFeatures
 * subFeatures=subFuturesMapper.convertModeltoDomain(subFuturesVo);
 * subFeatures.setCreatedBy(1l); subFeatures.setUpdatedBy(1l);
 * subFeatures.setEnabledStatus(true);
 * subFeatures=subFuturesService.save(subFeatures);
 * subFuturesVo=subFuturesMapper.convertDomaintoModel(subFeatures); return new
 * ResponseEntity<>(subFuturesVo,HttpStatus.OK);
 * 
 * }
 * 
 * @GetMapping(WebConstantUrl.getAll) public ResponseEntity<?>
 * getAll(HttpServletRequest request){
 * 
 * logger.info("getConfirm: Received request: " +
 * request.getRequestURL().toString() + ((request.getQueryString() == null) ? ""
 * : "?" + request.getQueryString().toString())); List<SubFeaturesVo>
 * subSuturesVos=subFuturesService.getAll()
 * .stream().map(subFuturesMapper::convertDomaintoModel)
 * .collect(Collectors.toList()); return new
 * ResponseEntity<>(subSuturesVos,HttpStatus.OK);
 * 
 * }
 * 
 * @GetMapping(WebConstantUrl.getById) public ResponseEntity<?>
 * getById(@PathVariable("id") Long id,HttpServletRequest request){
 * logger.info("getConfirm: Received request: " +
 * request.getRequestURL().toString() + ((request.getQueryString() == null) ? ""
 * : "?" + request.getQueryString().toString())); SubFeatures
 * subFutures=subFuturesService.getById(id); SubFeaturesVo
 * subFuturesVo=subFuturesMapper.convertDomaintoModel(subFutures); return new
 * ResponseEntity<>(subFuturesVo,HttpStatus.OK);
 * 
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 */