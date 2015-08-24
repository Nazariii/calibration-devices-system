package com.softserve.edu.controller.calibrator;

import com.softserve.edu.dto.CalibrationTestDTO;
import com.softserve.edu.dto.CalibrationTestDataDTO;
import com.softserve.edu.dto.calibrator.GlobalCalibrationTestDTO;
import com.softserve.edu.dto.calibrator.TestGenerallDTO;
import com.softserve.edu.entity.CalibrationTest;
import com.softserve.edu.entity.CalibrationTestData;
import com.softserve.edu.service.CalibrationTestService;
import com.softserve.edu.service.exceptions.NotAvailableException;
import com.softserve.edu.service.utils.CalibrationTestDataList;
import com.softserve.edu.service.utils.CalibrationTestList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/calibrator/calibrationTests/")
public class CalibrationTestController {

    @Autowired
    private CalibrationTestService testService;

    private final Logger logger = Logger.getLogger(CalibrationTestController.class);

    private static final String contentExtPattern = "^.*\\.(jpg|JPG|gif|GIF|png|PNG|tif|TIF|)$";


    /**
     * Returns calibration-test by ID
     *
     * @param testId
     * @return calibration-test
     */
    @RequestMapping(value = "getTest/{testId}", method = RequestMethod.GET)
    public CalibrationTestDTO getCalibrationTest(@PathVariable Long testId) {
        CalibrationTest foundTest = testService.findTestById(testId);
        CalibrationTestDTO testDTO = new CalibrationTestDTO(foundTest.getName(), foundTest.getTemperature(), foundTest.getSettingNumber(),
                foundTest.getLatitude(), foundTest.getLongitude(), foundTest.getConsumptionStatus(), foundTest.getTestResult());
        return testDTO;
    }


    /**
     * Finds all calibration-tests form database
     *
     * @return a list of calibration-tests
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAllCalibrationTests() {
        try {
            CalibrationTestList list = testService.findAllCalibrationTests();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotAvailableException exception) {
            throw new com.softserve.edu.exceptions.NotFoundException(exception);
        }
    }

    /**
     * Saves calibration-test in database
     *
     * @param globalTestDTO object with calibration-test data
     * @param testId        Long of calibration-test ID for saving calibration-test
     * @return a response body with http status {@literal CREATED} if everything
     * calibration-test successfully created or else http
     * status {@literal CONFLICT}
     */
    @RequestMapping(value = "add/{testId}", method = RequestMethod.POST)
    public void createCalibrationTest(@RequestBody TestGenerallDTO formdata, @PathVariable Long testId) {
        CalibrationTest calibrationTest = testService.findTestById(testId);
        List<CalibrationTestDataDTO> testDatas = formdata.getSmallForm();
        System.out.println(testDatas.size());
        for (CalibrationTestDataDTO data : testDatas) {
            CalibrationTestData testData = new CalibrationTestData();
            testData.setGivenConsumption(data.getGivenConsumption());
            testData.setAcceptableError(data.getAcceptableError());
            testData.setVolumeOfStandard(data.getVolumeOfStandart());
            testData.setInitialValue(data.getInitialValue());
            testData.setEndValue(data.getEndValue());
            testData.setVolumeInDevice(data.getVolumeInDevice());
            testData.setActualConsumption(data.getActualConsumption());
            testData.setConsumptionStatus(data.getConsumptionStatus());
            testData.setCalculationError(data.getCalculationError());
            testData.setTestResult(data.getTestResult());
            testData.setCalibrationTest(calibrationTest);
            testService.createNewCalibrationTestData(testData);
        }


//        calibrationTest = new CalibrationTest(formdata.getTestFormData().getName(), formdata.getTestFormData().getTemperature(), formdata.getTestFormData().getSettingNumber(), formdata.getTestFormData().getLatitude(),
//                formdata.getTestFormData().getLongitude(), formdata.getTestFormData().getConsumptionStatus(), formdata.getTestFormData().getTestResult());
        System.out.println("");
    }

    /**
     * Edit calibration-test in database
     *
     * @param testDTO           object with calibration-test data
     * @param calibrationTestId
     * @return a response body with http status {@literal OK} if calibration-test
     * successfully edited or else http status {@literal CONFLICT}
     */
    @RequestMapping(value = "edit/{calibrationTestId}", method = RequestMethod.POST)
    public ResponseEntity editCalibrationTest(@PathVariable Long calibrationTestId, @RequestBody CalibrationTestDTO testDTO) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            testService.editTest(calibrationTestId, testDTO.getName(), testDTO.getTemperature(),
                    testDTO.getSettingNumber(), testDTO.getLatitude(), testDTO.getLongitude(), testDTO.getConsumptionStatus(), testDTO.getTestResult());
        } catch (Exception e) {
            logger.error("GOT EXCEPTION " + e.getMessage());
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(httpStatus);
    }

    /**
     * Deletes selected calibration-test by Id
     *
     * @param calibrationTestId
     * @return a response body with http status {@literal OK} if calibration-test
     * successfully deleted
     */
    @RequestMapping(value = "delete/{calibrationTestId}", method = RequestMethod.POST)
    public void deleteCalibrationTest(@PathVariable Long calibrationTestId) {
        testService.deleteTest(calibrationTestId);
    }


    /**
     * Finds all calibration-tests data form database
     *
     * @return a list of calibration-tests data
     */
    @RequestMapping(value = "/{calibrationTestId}/testData", method = RequestMethod.GET)
    public ResponseEntity findAllCalibrationTestData(@PathVariable Long calibrationTestId) {
        try {
            CalibrationTestDataList list = testService.findAllTestDataAsociatedWithTest(calibrationTestId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NotAvailableException exception) {
            logger.error("Not found " + exception);
            throw new com.softserve.edu.exceptions.NotFoundException(exception);
        }
    }

    /**
     * Uploads a photoes to chosen directory bu calibration-test ID
     *
     * @param file              chosen file oject
     * @param idCalibrationTest
     * @return httpStatus 200 OK if everything did well
     */
    @RequestMapping(value = "uploadPhotos", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFilePhoto(@RequestBody MultipartFile file, @RequestParam Long idCalibrationTest) {
        ResponseEntity<String> httpStatus = new ResponseEntity(HttpStatus.OK);
        try {
            String originalFileName = file.getOriginalFilename();
            String fileType = originalFileName.substring(originalFileName.lastIndexOf('.'));
            if (Pattern.compile(contentExtPattern, Pattern.CASE_INSENSITIVE).matcher(fileType).matches()) {
                testService.uploadPhotos(file.getInputStream(), idCalibrationTest, originalFileName);
            } else {
                logger.error("Failed to load file ");
                httpStatus = new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Failed to load file " + e.getMessage());
            httpStatus = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return httpStatus;
    }

    @RequestMapping(value = "createEmptyTest/{verificationId}", method = RequestMethod.GET)
    public Long createEmptyTest(@PathVariable String verificationId) {
        testService.createEmptyTest(verificationId);
        CalibrationTest test = testService.findByVerificationId(verificationId);
        Long testId = test.getId();
        return testId;
    }


}

