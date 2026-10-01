package farid.aghazada.song_service.DTO.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record SongCreationDTO(

        @NotNull(message = "ID is required")
        @Positive(message = "ID must be a positive number")
        Integer id,

        @NotNull(message = "Song name is required")
        @Size(min = 1, max = 100, message = "Song name must be between 1 and 100 characters")
        String name,

        @NotNull(message = "Artist name is required")
        @Size(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
        String artist,

        @NotNull(message = "Album name is required")
        @Size(min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
        String album,

        @NotNull(message = "Duration is required")
        @Pattern(regexp = "^([0-5][0-9]):([0-5][0-9])$",
                 message = "Duration must be in mm:ss format with leading zeros")
        String duration,

        @NotNull(message = "Year is required")
        @Pattern(regexp = "^(19|20)\\d{2}$",
                 message = "Year must be between 1900 and 2099")
        String year
        ) {
}
