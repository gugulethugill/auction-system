package com.gugulethugillz.auctionsystem.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResourceForm {
    @NotNull(message = "Referenced asset ID required")
    private Long id;

    @NotNull(message = "Image file is required.")
    private MultipartFile imageFile;
}
