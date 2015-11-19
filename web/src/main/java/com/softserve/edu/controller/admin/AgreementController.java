package com.softserve.edu.controller.admin;

import com.softserve.edu.dto.PageDTO;
import com.softserve.edu.dto.admin.AgreementDTO;
import com.softserve.edu.entity.device.Device;
import com.softserve.edu.entity.organization.Agreement;
import com.softserve.edu.service.admin.AgreementService;
import com.softserve.edu.service.user.SecurityUserDetailsService;
import com.softserve.edu.service.utils.ListToPageTransformer;
import com.softserve.edu.service.utils.TypeConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/admin/agreement/")
public class AgreementController {

    private static Logger logger = Logger.getLogger(AgreementController.class);

    @Autowired
    private AgreementService agreementService;

    /**
     * Get agreement by id
     *
     * @param id id of agreement to find
     * @return agreementDTO
     */
    @RequestMapping(value = "get/{id}")
    public AgreementDTO getAgreement(@PathVariable("id") Long id) {
        Agreement agreement = agreementService.findAgreementById(id);
        return new AgreementDTO(agreement.getId(), agreement.getCustomer().getId(),
                agreement.getExecutor().getId(), agreement.getCustomer().getName(), agreement.getExecutor().getName(),
                agreement.getNumber(), agreement.getDeviceCount(), agreement.getDeviceType().name(),
                agreement.getCustomer().getOrganizationTypes().iterator().next().name(), agreement.getDate());
    }

    /**
     * Add new agreement
     *
     * @param agreementDTO agreement to add
     * @return http status
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity addAgreement(@RequestBody AgreementDTO agreementDTO) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            agreementService.add(agreementDTO.getCustomerId(), agreementDTO.getExecutorId(), agreementDTO.getNumber(),
                    agreementDTO.getDeviceCount(), new Date(), Device.DeviceType.valueOf(agreementDTO.getDeviceType()));
        } catch (Exception e) {
            logger.error("Error when adding agreement", e);
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity(httpStatus);
    }

    /**
     * Edit selected agreement
     *
     * @param agreementDTO agreement to edit
     * @param agreementId  id of agreement to edit
     * @return http status
     */
    @RequestMapping(value = "edit/{agreementId}", method = RequestMethod.POST)
    public ResponseEntity editAgreement(@RequestBody AgreementDTO agreementDTO,
                                        @PathVariable Long agreementId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            agreementService.update(agreementId, agreementDTO.getCustomerId(), agreementDTO.getExecutorId(),
                    agreementDTO.getNumber(), agreementDTO.getDeviceCount(), new Date(), Device.DeviceType.valueOf(agreementDTO.getDeviceType()));
        } catch (Exception e) {
            logger.error("GOT EXCEPTION WHEN UPDATE AGREEMENT", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Disable agreement
     *
     * @param agreementId id of agreement to disable
     * @return http status
     */
    @RequestMapping(value = "disable/{agreementId}", method = RequestMethod.GET)
    public ResponseEntity disableAgreement(@PathVariable Long agreementId) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            agreementService.disableAgreement(agreementId);
        } catch (Exception e) {
            logger.error("GOT EXCEPTION ", e);
            httpStatus = HttpStatus.CONFLICT;
        }
        return new ResponseEntity(httpStatus);
    }

    /**
     * Return page of agreements according to filters and sort order
     *
     * @param pageNumber   number of page to return
     * @param itemsPerPage count of items on page
     * @param sortCriteria sorting criteria
     * @param sortOrder    order of sorting
     * @param searchData   filtering data
     * @return sorted and filtered page of agreement
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}/{sortCriteria}/{sortOrder}", method = RequestMethod.GET)
    public PageDTO<AgreementDTO> pageDeviceCategoryWithSearch(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage,
                                                              @PathVariable String sortCriteria, @PathVariable String sortOrder,
                                                              AgreementDTO searchData) {
        Map<String, String> searchDataMap = TypeConverter.ObjectToMap(searchData);
        searchDataMap.put("isAvailable", "true");
        ListToPageTransformer<Agreement> queryResult = agreementService.getCategoryDevicesBySearchAndPagination(
                pageNumber,
                itemsPerPage,
                searchDataMap,
                sortCriteria,
                sortOrder
        );
        List<AgreementDTO> content = queryResult.getContent().stream()
                .map(agreement -> new AgreementDTO(agreement.getId(), agreement.getCustomer().getId(),
                        agreement.getExecutor().getId(), agreement.getCustomer().getName(),
                        agreement.getExecutor().getName(), agreement.getNumber(), agreement.getDeviceCount(),
                        agreement.getDeviceType().name(), agreement.getDate()))
                .collect(Collectors.toList());
        return new PageDTO<>(queryResult.getTotalItems(), content);
    }

    /**
     * Return page of agreements
     * @param pageNumber   number of page to return
     * @param itemsPerPage count of items on page
     * @return page of agreement
     */
    @RequestMapping(value = "{pageNumber}/{itemsPerPage}", method = RequestMethod.GET)
    public PageDTO<AgreementDTO> getDeviceCategoryPage(@PathVariable Integer pageNumber, @PathVariable Integer itemsPerPage) {
        return pageDeviceCategoryWithSearch(pageNumber, itemsPerPage, null, null, null);
    }

    @RequestMapping(value = "earliest_date", method = RequestMethod.GET)
    public String getArchivalVerificationEarliestDateByProviderId(@AuthenticationPrincipal SecurityUserDetailsService.CustomUserDetails user) {
        if (user != null) {
            java.util.Date gottenDate = agreementService.getEarliestDateAvailableAgreement();
            java.util.Date date = null;
            if (gottenDate != null) {
                date = new Date(gottenDate.getTime());
            } else {
                return null;
            }
            DateTimeFormatter dbDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDateTime localDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            String isoLocalDateString = localDate.format(dbDateTimeFormatter);
            return isoLocalDateString;
        } else {
            return null;
        }
    }
}
