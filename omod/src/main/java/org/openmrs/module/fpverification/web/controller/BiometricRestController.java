package org.openmrs.module.fpverification.web.controller;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;
import org.openmrs.module.fpverification.Utils.Utils;
import org.openmrs.module.fpverification.db.NdrDBManager;
import org.openmrs.module.fpverification.fragment.controller.FpverificationHomeFragmentController;
import org.openmrs.module.fpverification.model.ndr.*;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/fpverification")
public class BiometricRestController {
	
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/biometrics")
    @ResponseBody
    public String extractBiometrics(
            @RequestParam(value = "startdate", required = true) String startdate,
            @RequestParam(value = "enddate", required = true) String enddate,
            @RequestParam(value = "patientidentifiers", required = false) String patientidentifiers,
            HttpServletRequest request) {


		// Check authentication - Add this block for authentication
		if (!Context.isAuthenticated()) {
			LOG.warn("Unauthorized access attempt to biometrics API");
			return gson.toJson(Collections.singletonMap("error", "Authentication required"));
		}

		List<String> outputList = new ArrayList<>();
		int xmlFileCount = 0;
		String formattedDate2 = new SimpleDateFormat("ddMMyyHHmmss").format(new Date());
		try {

			list = new ArrayList<>();
			Utils.ensureReportFolderExistDelete(request, reportType);
			nd.openConnection();
			if (patientidentifiers != null && !patientidentifiers.isEmpty()) {
				list = nd.getPatientsWithBiometrics(startdate, enddate, patientidentifiers);
				System.out.println(patientidentifiers + " is called with identifiers");
			} else {
				list = nd.getPatientsWithBiometrics(startdate, enddate);
				System.out.println("Reach not call with identifiers");
			}
			if (this.list.isEmpty()) {
				this.nd.closeConnection();
				return this.gson.toJson("No record found");
			}
			generateBiometricXmlFiles(startdate, enddate, request);

			String facilityName = Utils.getFacilityName();
			String IPShortName = Utils.getIPShortName();
			String datimCode = Utils.getFacilityDATIMId();
			String zipFileName = IPShortName + "_" + "Fingerprintverification" + "_" + datimCode + "_" + formattedDate2 + ".zip";
			String filepath = Utils.zipFolder(request, reportFolder, zipFileName, reportType);

			outputList.add(zipFileName);
			outputList.add(dateFormat.format(new Date()));
			try {
				outputList.add(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			} catch (Exception e) {
				LOG.warn("Error formatting date", e);
				outputList.add(new Date().toString());
			}

			// Count the number of XML files generated
			File reportDirectory = new File(reportFolder);
			if (reportDirectory.isDirectory()) {
				File[] files = reportDirectory.listFiles();
				if (files != null) {  // Check if the array is not null
					for (File file : files) {
						if (file.isFile() && file.getName().endsWith(".xml")) {
							xmlFileCount++;
						}
					}
				}
			}
			outputList.add(String.valueOf(xmlFileCount));
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
	
	// Copy all your other methods here, unchanged
	
	private void generateBiometricXmlFiles(String startdate, String enddate, HttpServletRequest request) throws Exception {
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
				patientIdentifier = result.getString("identifier");
				String fingerPosition = result.getString("fingerPosition");
				int imageQuality = result.getInt("imageQuality");
				String template = result.getString("new_template");
				String hashed = result.getString("hashed");
				XMLGregorianCalendar dateCaptured = u.getXmlDateTime(result.getDate("date_created"));
				
				switch (fingerPosition) {
					case "RightThumb":
						rightFingerType.setRightThumb(template);
						rightFingerType.setHashedRightThumb(hashed);
						rightFingerType.setRightThumbQuality(imageQuality);
						break;
					case "RightIndex":
						rightFingerType.setRightIndex(template);
						rightFingerType.setHashedRightIndex(hashed);
						rightFingerType.setRightIndexQuality(imageQuality);
						break;
					case "RightMiddle":
						rightFingerType.setRightMiddle(template);
						rightFingerType.setHashedRightMiddle(hashed);
						rightFingerType.setRightMiddleQuality(imageQuality);
						break;
					case "RightWedding":
						rightFingerType.setRightWedding(template);
						rightFingerType.setHashedRightWedding(hashed);
						rightFingerType.setRightWeddingQuality(imageQuality);
						break;
					case "RightSmall":
						rightFingerType.setRightSmall(template);
						rightFingerType.setHashedRightSmall(hashed);
						rightFingerType.setRightSmallQuality(imageQuality);
						break;
					case "LeftThumb":
						leftFingerType.setLeftThumb(template);
						leftFingerType.setHashedLeftThumb(hashed);
						leftFingerType.setLeftThumbQuality(imageQuality);
						break;
					case "LeftIndex":
						leftFingerType.setLeftIndex(template);
						leftFingerType.setHashedLeftIndex(hashed);
						leftFingerType.setLeftIndexQuality(imageQuality);
						break;
					case "LeftMiddle":
						leftFingerType.setLeftMiddle(template);
						leftFingerType.setHashedLeftMiddle(hashed);
						leftFingerType.setLeftMiddleQuality(imageQuality);
						break;
					case "LeftWedding":
						leftFingerType.setLeftWedding(template);
						leftFingerType.setHashedLeftWedding(hashed);
						leftFingerType.setLeftWeddingQuality(imageQuality);
						break;
					case "LeftSmall":
						leftFingerType.setLeftSmall(template);
						leftFingerType.setHashedLeftSmall(hashed);
						leftFingerType.setLeftSmallQuality(imageQuality);
						break;
				
				}
				
				PatientDemographicsType demographics = new PatientDemographicsType();
				demographics.setPatientIdentifier(patientIdentifier);
				fingerPrintsType.setDateCaptured(dateCaptured);
				fingerPrintsType.setRightHand(rightFingerType);
				fingerPrintsType.setLeftHand(leftFingerType);
				fingerPrintsType.setVisitDate(dateCaptured);
				fingerPrintsType.setVisitId(visitID.format(result.getDate("date_created")));
				fingerPrintsType.setCaptureCount(result.getInt("recapture_count"));
				demographics.setFingerPrints(fingerPrintsType);
				containerTemplate.setPatientDemographics(demographics);
				containerTemplate.setEmrType("NMRS");
			}
			
			exportXML(patientIdentifier, request);
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
		String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
		
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
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		LOG.error("Error handling request", ex);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return gson.toJson(Collections.singletonMap("error", ex.getMessage()));
	}
	
	@ExceptionHandler(APIAuthenticationException.class)
	@ResponseBody
	public String handleAuthenticationException(APIAuthenticationException ex, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return gson.toJson(Collections.singletonMap("error", "Authentication required"));
	}
}
