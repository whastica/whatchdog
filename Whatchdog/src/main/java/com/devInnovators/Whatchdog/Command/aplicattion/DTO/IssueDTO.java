package com.devInnovators.Whatchdog.Command.aplicattion.DTO;


import com.devInnovators.Whatchdog.Command.domain.model.Priority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    
    private String id;
    private String category;
    private Priority priority;
}
