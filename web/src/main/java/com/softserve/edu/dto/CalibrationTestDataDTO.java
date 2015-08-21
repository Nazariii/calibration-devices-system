package com.softserve.edu.dto;

import com.softserve.edu.entity.CalibrationTestData;
import com.softserve.edu.entity.util.CalibrationTestResult;


public class CalibrationTestDataDTO {

    private Double givenConsumption;
    private Integer acceptableError;
    private Integer volumeOfStandart;
    private Double initialValue;
    private Double endValue;
    private Double volumeInDevice;
    private Double testTime;
    private Double actualConsumption;
    private String consumptionStatus;
    private Double calculationError;
    private CalibrationTestResult testResult;

    public Double getGivenConsumption() {
        return givenConsumption;
    }

    public void setGivenConsumption(Double givenConsumption) {
        this.givenConsumption = givenConsumption;
    }

    public Integer getAcceptableError() {
        return acceptableError;
    }

    public void setAcceptableError(Integer acceptableError) {
        this.acceptableError = acceptableError;
    }

    public Integer getVolumeOfStandart() {
        return volumeOfStandart;
    }

    public void setVolumeOfStandart(Integer volumeOfStandart) {
        this.volumeOfStandart = volumeOfStandart;
    }

    public Double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Double initialValue) {
        this.initialValue = initialValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public Double getVolumeInDevice() {
        return volumeInDevice;
    }

    public void setVolumeInDevice(Double volumeInDevice) {
        this.volumeInDevice = volumeInDevice;
    }

    public Double getTestTime() {
        return testTime;
    }

    public void setTestTime(Double testTime) {
        this.testTime = testTime;
    }

    public Double getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Double actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public String getConsumptionStatus() {
        return consumptionStatus;
    }

    public void setConsumptionStatus(String consumptionStatus) {
        this.consumptionStatus = consumptionStatus;
    }

    public Double getCalculationError() {
        return calculationError;
    }

    public void setCalculationError(Double calculationError) {
        this.calculationError = calculationError;
    }

    public CalibrationTestResult getTestResult() {
        return testResult;
    }

    public void setTestResult(CalibrationTestResult testResult) {
        this.testResult = testResult;
    }

    public CalibrationTestData saveTestData() {
        CalibrationTestData calibrationTestData = new CalibrationTestData();
        calibrationTestData.setGivenConsumption(givenConsumption);
        calibrationTestData.setAcceptableError(acceptableError);
        calibrationTestData.setVolumeOfStandard(volumeOfStandart);
        calibrationTestData.setInitialValue(initialValue);
        calibrationTestData.setEndValue(endValue);
        calibrationTestData.setVolumeInDevice(volumeInDevice);
        calibrationTestData.setActualConsumption(actualConsumption);
        calibrationTestData.setConsumptionStatus(consumptionStatus);
        calibrationTestData.setCalculationError(calculationError);
        calibrationTestData.setTestResult(CalibrationTestResult.SUCCESS);
        return calibrationTestData;
    }
}