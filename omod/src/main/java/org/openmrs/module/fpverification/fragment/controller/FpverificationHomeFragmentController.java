package org.openmrs.module.fpverification.fragment.controller;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.impl.NewProxyPreparedStatement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.context.AppContextModel;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.fpverification.Utils.LoggerUtils;
import org.openmrs.module.fpverification.Utils.Utils;
import org.openmrs.module.fpverification.Utils.ZipUtil;
import org.openmrs.module.fpverification.db.NdrDBManager;
import org.openmrs.module.fpverification.model.ndr.*;
import org.openmrs.module.referenceapplication.ReferenceApplicationConstants;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.*;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FpverificationHomeFragmentController {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	SimpleDateFormat visitID = new SimpleDateFormat("yyyyMMdd");
	
	private static final Log LOG = LogFactory.getLog(FpverificationHomeFragmentController.class);
	
	Container containerTemplate = null;
	
	String reportType = "FingerPrintVerification";
	
	Gson gson = new Gson();
	
	private PreparedStatement pStatement1;
	
	NdrDBManager nd = new NdrDBManager();
	
	private ResultSet result;
	
	private List<Integer> list;
	
	List<String> filesListInDir = new ArrayList<String>();
	
	private String reportFolder;
	
	String dateFormat2;
	
	private String formattedDate;
	
	private Thread thread1;
	
	public FpverificationHomeFragmentController() {
		this.dateFormat2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	public String extractFingerprint(@RequestParam(value = "startdate", required = true) String startdate,
	        @RequestParam(value = "enddate", required = true) String enddate, HttpServletRequest request) {
		List<String> outputList = new ArrayList<>();
		try {

			list = new ArrayList<>();
			Utils.ensureReportFolderExistDelete(request, reportType);
			nd.openConnection();
			list = nd.getPatientBiometricsVerifyDistinctList(startdate, enddate);
			if (this.list.isEmpty()) {
				this.nd.closeConnection();
				return this.gson.toJson("No record found");
			}
			getPatientBiometricsVerifyContainer(startdate, enddate, request);

			String facilityName = Utils.getFacilityName();
			String IPShortName = Utils.getIPShortName();
			String datimCode = Utils.getFacilityDATIMId();
			String zipFileName = IPShortName + "_" + "Fingerprintverification" + "_" + datimCode + "_" + formattedDate + ".zip";
			String filepath = Utils.zipFolder(request, reportFolder, zipFileName, reportType);

			outputList.add(zipFileName);
			outputList.add(dateFormat2);
			outputList.add(String.valueOf(list.size()));
			outputList.add(filepath);

		}catch (Exception e) {

			Logger.getLogger(FpverificationHomeFragmentController.class.getName()).log(Level.SEVERE, null, e);

		}finally {
			try{
				nd.closeConnection();
			}catch (Exception e){

			}
		}
		return gson.toJson(outputList);
	}
	
	public void getPatientBiometricsVerifyContainer(String startdate, String enddate, HttpServletRequest request)
	        throws Exception {
		String patientIdentifier = "";
		for (int i = 0; i < list.size(); i++) {
			System.out.println("getPatientBiometricsVerifyContainer " + String.valueOf(list.get(i)));
			result = nd.getPatientBiometricsVerify(startdate, enddate, Integer.parseInt(String.valueOf(list.get(i))));
			containerTemplate = new Container();
			FingerPrintType fingerPrintsType = new FingerPrintType();
			RightHandType rightFingerType = new RightHandType();
			LeftHandType leftFingerType = new LeftHandType();
			XMLGregorianCalendar dataCaptured = null;
			Calendar cal = Calendar.getInstance();
			NdrDBManager nd = new NdrDBManager();
			Utils u = new Utils();
			
			Date date = new Date();
			MessageHeaderType messageHeaderType = new MessageHeaderType();
			messageHeaderType.setMessageCreationDateTime(u.getXmlDateMessageHeader(cal.getTime()));
			messageHeaderType.setMessageUniqueID(UUID.randomUUID().toString());
			messageHeaderType.setMessageVersion(1.0f);
			messageHeaderType.setXmlType("fingerprintsvalidation");
			
			FacilityType messageSendingOrganisationType = new FacilityType();
			messageSendingOrganisationType.setFacilityID(u.getFacilityDATIMId());
			messageSendingOrganisationType.setFacilityName(u.getFacilityName());
			messageSendingOrganisationType.setFacilityTypeCode(u.getFacilityType());
			
			messageHeaderType.setMessageSendingOrganisation(messageSendingOrganisationType);
			
			containerTemplate.setMessageHeader(messageHeaderType);
			
			while (result.next()) {
				System.out.println("patient ids " + result.getInt("patient_Id"));
				// ResultSet result = nd.getPatientBiometricsVerify(startdate, enddate);
				String fingerposition = result.getString("fingerPosition");
				
				try {
					dataCaptured = u.getXmlDateTime(result.getDate("date_created"));
				}
				catch (Exception e) {
					
				}
				
				//Patient patient = new Patient(result.getInt("patient_Id"));
				patientIdentifier = result.getString("identifier"); //patient.getPatientIdentifier(4).getIdentifier();
				
				if ("RightThumb".equals(fingerposition)) {
					rightFingerType.setRightThumb(result.getString("new_template"));
					rightFingerType.setHashedRightThumb(result.getString("hashed"));
					rightFingerType.setRightThumbQuality(result.getInt("imageQuality"));
					
				} else if ("RightIndex".equals(fingerposition)) {
					rightFingerType.setRightIndex(result.getString("new_template"));
					rightFingerType.setHashedRightIndex(result.getString("hashed"));
					rightFingerType.setRightIndexQuality(result.getInt("imageQuality"));
					
				} else if ("RightMiddle".equals(fingerposition)) {
					rightFingerType.setRightMiddle(result.getString("new_template"));
					rightFingerType.setHashedRightMiddle(result.getString("hashed"));
					rightFingerType.setRightMiddleQuality(result.getInt("imageQuality"));
					
				} else if ("RightWedding".equals(fingerposition)) {
					rightFingerType.setRightWedding(result.getString("new_template"));
					rightFingerType.setHashedRightWedding(result.getString("hashed"));
					rightFingerType.setRightWeddingQuality(result.getInt("imageQuality"));
					
				} else if ("RightSmall".equals(fingerposition)) {
					rightFingerType.setRightSmall(result.getString("new_template"));
					rightFingerType.setHashedRightSmall(result.getString("hashed"));
					rightFingerType.setRightSmallQuality(result.getInt("imageQuality"));
					
				} else if ("LeftThumb".equals(fingerposition)) {
					leftFingerType.setLeftThumb(result.getString("new_template"));
					leftFingerType.setHashedLeftThumb(result.getString("hashed"));
					leftFingerType.setLeftThumbQuality(result.getInt("imageQuality"));
					
				} else if ("LeftIndex".equals(fingerposition)) {
					leftFingerType.setLeftIndex(result.getString("new_template"));
					leftFingerType.setHashedLeftIndex(result.getString("hashed"));
					leftFingerType.setLeftIndexQuality(result.getInt("imageQuality"));
					
				} else if ("LeftMiddle".equals(fingerposition)) {
					leftFingerType.setLeftMiddle(result.getString("new_template"));
					leftFingerType.setHashedLeftMiddle(result.getString("hashed"));
					leftFingerType.setLeftMiddleQuality(result.getInt("imageQuality"));
					
				} else if ("LeftWedding".equals(fingerposition)) {
					leftFingerType.setLeftWedding(result.getString("new_template"));
					leftFingerType.setHashedLeftWedding(result.getString("hashed"));
					leftFingerType.setLeftWeddingQuality(result.getInt("imageQuality"));
					
				} else if ("LeftSmall".equals(fingerposition)) {
					leftFingerType.setLeftSmall(result.getString("new_template"));
					leftFingerType.setHashedLeftSmall(result.getString("hashed"));
					leftFingerType.setLeftSmallQuality(result.getInt("imageQuality"));
					
				}
				
				PatientDemographicsType patientDemographicsType = new PatientDemographicsType();
				patientDemographicsType.setPatientIdentifier(patientIdentifier);
				
				fingerPrintsType.setDateCaptured(dataCaptured);
				fingerPrintsType.setRightHand(rightFingerType);
				fingerPrintsType.setLeftHand(leftFingerType);
				fingerPrintsType.setVisitDate(dataCaptured);
				fingerPrintsType.setVisitId(visitID.format(result.getDate("date_created")));
				fingerPrintsType.setCaptureCount(result.getInt("recapture_count"));
				
				patientDemographicsType.setFingerPrints(fingerPrintsType);
				containerTemplate.setPatientDemographics(patientDemographicsType);
				containerTemplate.setEmrType("NMRS");
				
			}
			
			exportXML(patientIdentifier, request);
			
		}
		
	}
	
	private void writeFile(Container ndrReportTemplate, File file, Marshaller jaxbMarshaller) {
		
		try {
			//	javax.xml.validation.Validator validator = jaxbMarshaller.getSchema().newValidator();
			jaxbMarshaller.marshal(ndrReportTemplate, file);
			
		}
		catch (Exception ex) {
			System.out.println("File " + file.getName() + " throw an exception \n" + ex.getMessage());
			//	throw ex;
		}
		
	}
	
	public void exportXML(String PatientIdentifier, HttpServletRequest request) throws Exception {
		
		JAXBContext jaxbContext;
		String datimCode = Utils.getFacilityDATIMId();
		String IPShortName = Utils.getIPShortName();
		System.out.println("about to create jaxb context");
		// jaxbContext = JAXBContext.newInstance("org.openmrs.module.openhmis.ndrmodel");
		jaxbContext = JAXBContext.newInstance(Container.class);
		System.out.println("done creating jaxb context");
		System.out.println("about to create marshaller");
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		System.out.println("done creating marshaller");
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
		
		if (containerTemplate != null) {
			
			System.out.println("starting xml creating process");
			LOG.info("Testing log4j");
			reportFolder = Utils.ensureReportFolderExist(request, reportType);
			datimCode = datimCode.replace("/", "_");
			PatientIdentifier = PatientIdentifier.replace("/", "_").replace(".", "_");
			
			String fileName = IPShortName + "_" + "Fingerprintverification" + "_" + datimCode + "_" + PatientIdentifier
			        + "_" + formattedDate;
			System.out.println("File name is " + fileName);
			
			String xmlFile = Paths.get(reportFolder, fileName + ".xml").toString();
			
			File aXMLFile = new File(xmlFile);
			
			Boolean b;
			
			b = aXMLFile.createNewFile();
			//System.out.println("creating xml file : " + xmlFile + "was successful : " + b);
			writeFile(containerTemplate, aXMLFile, jaxbMarshaller);
			
		}
	}
	
	private void zipDirectory(File dir, String zipDirName) {
		try {
			populateFilesList(dir);
			//now zip files one by one
			//create ZipOutputStream to write to the zip file
			FileOutputStream fos = new FileOutputStream(zipDirName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String filePath : filesListInDir) {
				System.out.println("Zipping " + filePath);
				//for ZipEntry we need to keep only relative file path, so we used substring on absolute path
				ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				//read the file and write to ZipOutputStream
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}
			zos.close();
			fos.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method populates all the files in a directory to a List
	 * 
	 * @param dir
	 * @throws IOException
	 */
	private void populateFilesList(File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isFile())
				filesListInDir.add(file.getAbsolutePath());
			else
				populateFilesList(file);
		}
	}
	
	boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}
	
}
