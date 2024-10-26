package com.devInnovators.Whatchdog.Query.application.interfaces;


import com.devInnovators.Whatchdog.Query.domain.model.Status;

import com.devInnovators.Whatchdog.Query.application.DTO.ReportDTO;

import java.util.List;

public interface QueryReportServiceInterface {

    ReportDTO findReportById(String id);

   /*  List<ReportDTO> findReportByCitizenId(String id);

    List<ReportDTO> findReportByEstado(Status status); */
  

}
