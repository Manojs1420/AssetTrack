/*
 * package com.titan.irgs.serviceRequest.serviceImpl;
 * 
 * 
 * 
 * import java.io.File; import java.nio.file.Files; import java.nio.file.Path;
 * import java.nio.file.Paths; import java.util.Date; import java.util.List;
 * 
 * import javax.persistence.EntityNotFoundException;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * 
 * 
 * 
 * @Service public class SRFileUploadService implements ISRFileUploadService {
 * 
 * private Logger logger = LoggerFactory.getLogger(this.getClass());
 * 
 * @Autowired private SRFileUploadRepository srFileUploadRepository;
 * 
 * @Autowired private ServiceRequestRepository serviceRequestRepository;
 * 
 *//**
	 * getAllSRFileUpload -> Method
	 * 
	 * @param ->none
	 * @return list of saved SRFileUpload entity
	 */
/*
 * 
 * 
 * 
 * 
 * @Override public List<SRFileUpload> getAllSRFileUpload() { return
 * srFileUploadRepository.findAll(); }
 * 
 *//**
	 * getSRFileUploadById -> Method
	 * 
	 * @param srFileUploadId
	 * @return saved srFileUpload entity
	 */
/*
 * 
 * 
 * 
 * 
 * @Override public SRFileUpload getSRFileUploadById(Long srFileUploadId) {
 * SRFileUpload srFileUpload = srFileUploadRepository.findOne(srFileUploadId);
 * if (srFileUpload == null) {
 * logger.error("SRFileUpload with srFileUploadId {} not found",
 * srFileUploadId); throw new
 * EntityNotFoundException("SRFileUpload with srFileUploadId " + srFileUploadId
 * + " not found"); } return srFileUpload; }
 * 
 *//**
	 * saveSRFileUpload ->Method
	 * 
	 * @param srFileUpload
	 * @return saved srFileUpload entity data
	 */
/*
 * 
 * 
 * 
 * 
 * @Override public SRFileUpload saveSRFileUpload(SRFileUpload srFileUpload) {
 * srFileUpload.setUploadedDate(new Date()); srFileUpload.setModifiedDate(null);
 * return srFileUploadRepository.save(srFileUpload);
 * 
 * }
 * 
 *//**
	 * updateSRFileUpload ->Method
	 * 
	 * @param srFileUpload entity
	 * @return updated srFileUpload entity
	 */
/*
 * 
 * 
 * 
 * 
 * @Override public SRFileUpload updateSRFileUpload(SRFileUpload srFileUpload) {
 * srFileUpload.setModifiedDate(new Date()); SRFileUpload srFileUpload1 =
 * srFileUploadRepository.findOne(srFileUpload.getSrFileUploadId()); if
 * (srFileUpload1 == null) {
 * logger.error("SRFileUpload with srFileUploadId {} not found",srFileUpload.
 * getSrFileUploadId()); throw new
 * EntityNotFoundException("SRFileUpload with srFileUploadId " +
 * srFileUpload.getSrFileUploadId() + " not found"); } return
 * srFileUploadRepository.save(srFileUpload); }
 * 
 *//**
	 * deleteSRFileUploadById ->Method
	 * 
	 * @param srFileUploadId
	 * @return none
	 */
/*
 * 
 * 
 * 
 * 
 * @Override public void deleteSRFileUploadById(Long srFileUploadId) {
 * SRFileUpload srFileUpload = srFileUploadRepository.findOne(srFileUploadId);
 * if (srFileUpload == null) {
 * logger.error("SRFileUpload with srFileUploadId {} not found",
 * srFileUploadId); throw new
 * EntityNotFoundException("SRFileUpload with srFileUploadId " + srFileUploadId
 * + " not found"); } String fn=srFileUpload.getUploadedFileName();
 * System.out.println(fn); File file=new
 * File("D:\\newepdproject\\titan-epd\\target\\classes\\uploads\\files\\files\\"
 * +fn); System.out.println(file+"--------"); file.delete();
 * file.deleteOnExit();
 * 
 * System.out.println(file.delete()+"adter delete");
 * 
 * Path
 * path=Paths.get("D:\\newepdproject\\titan-epd\\Upload\\images\\files\\files"+
 * srFileUpload.getUploadedFileName());
 * System.out.println(path+"path------------");
 * 
 * srFileUploadRepository.delete(srFileUploadId); }
 * 
 *//**
	 * getUploadedSRFileInfosOnServiceRequestId ->Method
	 * 
	 * @param serviceRequestId
	 * @return list of saved SRFileUpload entity data
	 *//*
		 * @Override public List<SRFileUpload>
		 * getUploadedSRFileInfosOnServiceRequestId(Long serviceRequestId) {
		 * ServiceRequest serviceRequest =
		 * serviceRequestRepository.findOne(serviceRequestId); if (serviceRequest ==
		 * null) { logger.error("serviceRequest with serviceRequestId {} not found",
		 * serviceRequestId); throw new
		 * EntityNotFoundException("serviceRequest with serviceRequestId " +
		 * serviceRequestId + " not found"); } return serviceRequest.getSrFileUploads();
		 * }
		 * 
		 * @Override public List<SRFileUpload>
		 * getSRFileInfosByServiceRequestIdAndFileUploadedType(Long
		 * serviceRequestId,String fileUploadedType) { List<SRFileUpload> srFileUploads
		 * = srFileUploadRepository.findByServiceRequestIdAndFileUploadedType(
		 * serviceRequestId, fileUploadedType); return srFileUploads; }
		 * 
		 * 
		 * 
		 * @Override public List<SRFileUpload> getByServiceRequestId(Long
		 * serviceRequestId) {
		 * 
		 * System.out.println("INSIDE"); return
		 * srFileUploadRepository.getSRFileUploadByServiceRequestId(serviceRequestId); }
		 * 
		 * @Override public SRFileUpload getSRFileUploadByServiceRequestId(Long
		 * serviceRequestId) {
		 * 
		 * return
		 * srFileUploadRepository.getSRFileUploadOnServiceRequestId(serviceRequestId); }
		 * 
		 * @Override public SRFileUpload findByServiceRequestSrStatus(String srStatus) {
		 * 
		 * return srFileUploadRepository.findByServiceRequestSrStatus(srStatus); }
		 * 
		 * @Override public List<SRFileUpload>
		 * getListSRFileUploadOnServiceRequestIdAndFileUploadedType(Long
		 * serviceRequestId, String fileUploadedType) {
		 * 
		 * return srFileUploadRepository.
		 * getListSRFileUploadOnServiceRequestIdAndFileUploadedType(serviceRequestId,
		 * fileUploadedType); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */