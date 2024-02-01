package com.openclassrooms.starterjwt.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    private Date date;

    @NotNull
    private Long teacher_id;

    @NotNull
    @Size(max = 2500)
    private String description;

    private List<Long> users;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
